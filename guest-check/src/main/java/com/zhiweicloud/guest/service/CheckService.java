/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.excel.generator.*;
import com.zhiweicloud.guest.common.excel.util.ExcelUtils;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.CheckMapper;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class CheckService {

    private static final Logger logger = LoggerFactory.getLogger(CheckService.class);

    private final CheckMapper checkMapper;
    private final CheckDynamicColumn checkDynamicColumn;

    @Autowired
    public CheckService(CheckMapper checkMapper, CheckDynamicColumn checkDynamicColumn) {
        this.checkMapper = checkMapper;
        this.checkDynamicColumn = checkDynamicColumn;
    }

    public LZResult<PaginationResult<Map>> getAll(Long userId, String airportCode, CheckQueryParam checkQueryParam, Integer page, Integer rows) throws Exception {
        BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));

        checkQueryParam.setAirportCode(airportCode);

        int total = checkMapper.selectCheckTotal(checkQueryParam);
        List<Map> checkList = checkMapper.selectCheckList(queryCondition);
        for (int i = 0; i < checkList.size(); i++) {

            Map<String, Object> headerMap = new HashMap();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("protocolTypeId", checkList.get(i).get("protocolType"));
            JSONObject protocolObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolTypeDropdownList",headerMap,paramMap));
            if (protocolObject != null && protocolObject.get("data") != null && JSON.parseArray(protocolObject.get("data").toString()).size() > 0) {

                JSONObject protocolObj = JSON.parseObject(JSON.parseArray(protocolObject.get("data").toString()).get(0).toString());
                checkList.get(i).put("protocolTypeName", protocolObj.get("value"));
            }
        }

        PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }


    public Map<String, Object> customerChecklist(String airportCode, OrderCheckDetail orderCheckDetail, Integer page, Integer rows) throws Exception {
        Map<String, Object> map = new HashMap();
        orderCheckDetail.setAirportCode(airportCode);
        String productName = orderCheckDetail.getQueryProductName();

        if (productName != null) {
            if (productName.equals("两舱休息室") &&
                    (orderCheckDetail.getQueryProtocolType() == 9 || orderCheckDetail.getQueryProtocolType() == 10)) {
                productName += orderCheckDetail.getQueryProtocolType().toString();
            }
            orderCheckDetail.setSelectFields(checkDynamicColumn.getColumn(productName));
            orderCheckDetail.setTotalAmount(checkDynamicColumn.getTotalAmount(productName));
            map.put("column", checkDynamicColumn.getHeader(productName));

            orderCheckDetail.setQueryWhere("and customer_id = " + orderCheckDetail.getQueryCustomerId() +
                    " and protocol_type = " + orderCheckDetail.getQueryProtocolType() +
                    " and protocol_id = " + orderCheckDetail.getQueryProtocolId() +
                    " and product_name = '" + orderCheckDetail.getQueryProductName() + "'");
        }

        BasePagination<OrderCheckDetail> queryCondition = new BasePagination<>(orderCheckDetail, new PageModel(page, rows));


        List<Map<String, Object>> checkList = checkMapper.customerChecklist(queryCondition);
        ArrayList<String> key = new ArrayList<>(Arrays.asList("vipPersonNum","vipPrice","accompanyPersonNum","accompanyPrice","restRoomPersonNum","restRoomPrice","securityCheckPersonNum","securityCheckPrice"));
        Map<String, Object> totalRow = new HashMap<>();

        for(int k = 0; k < checkList.size();k++){
            Map<String,Object> singleRow = checkList.get(k);
            totalRow.put("orderNo","合计");
            for(String dataKey : singleRow.keySet()){
                if (key.contains(dataKey) && singleRow.get(dataKey) != null){
                    Float value = Float.parseFloat(singleRow.get(dataKey).toString());
                    if (totalRow.containsKey(dataKey)){
                        totalRow.put(dataKey, value + Float.parseFloat(totalRow.get(dataKey).toString()));
                    } else {
                        totalRow.put(dataKey, value);
                    }
                }
            }
        }

        checkList.add(totalRow);



        map.put("total", checkList.size());
        map.put("rows", checkList);

        return map;
    }

    public void exportExcel(OrderCheckDetail orderCheckDetail, Map result, HttpServletResponse response){
        JSONArray column = (JSONArray) result.get("column");
        List rows = (List) result.get("rows");
        if (rows.size() > 1) {
            Map<String, String> titleMap = new LinkedHashMap<>();
            column.forEach(x -> {
                String row1 = JSONObject.toJSONString(x, SerializerFeature.WriteMapNullValue);
                Map<String, String> map = JSON.parseObject(row1, LinkedHashMap.class, Feature.OrderedField);
                String[] strArray = new String[2];
                int i = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    strArray[i] = entry.getValue();
                    i++;
                }
                titleMap.put(strArray[1], strArray[0]);
            });
            String fileName = orderCheckDetail.getQueryProductName() + "_" + System.currentTimeMillis() + ".xls";
            String sheetName = orderCheckDetail.getQueryProductName();
            ExcelUtils.download(fileName, sheetName, rows, titleMap,response);
        }
    }

    /**
     * 导出账单
     *
     * @param type     类型
     * @param response 响应
     */
    public void exportBill(List<Map> maps,String type, HttpServletResponse response) {

        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String, Object> rowMap = new HashMap<>();

        for (Map map : maps) {
            rowMap.put("amount", map.get("amount"));
            rowMap.put("flightDepcode", map.get("flightDepcode"));
            rowMap.put("flightArrcode", map.get("flightArrcode"));
            rowMap.put("flightDate", map.get("flightDate"));
            rowMap.put("serverPersonNum", map.get("serverPersonNum"));
            rowMap.put("customerName", map.get("customerName"));
            rowMap.put("price", map.get("price"));
            rowMap.put("airportCode", map.get("airportCode"));
            rowMap.put("planNo", map.get("planNo"));
            rowMap.put("flightNo", map.get("flightNo"));
            rowMap.put("leg", map.get("leg"));
            dataList.add(rowMap);
        }

        /*
         * 默认文件名字
         */
        String fileName = "账单";
        String sheetName = "账单";
        ContentGenerator contentGenerator = null;

        /*
         * 四种定制方法，excel文件用.xls
         */
        switch (type) {
            case "firstClass":
                contentGenerator = new CreateFileForFirstClass(dataList);
                fileName = "头等舱账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "头等舱账单";
                break;
            case "frequentFlyer":
                contentGenerator = new CreateFileForFrequentFlyer(dataList);
                fileName = "常旅客账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "常旅客账单";
                break;
            case "airChina":
                contentGenerator = new AirChinaContentGenerator(dataList);
                fileName = "国际航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
            case "chinaSouthernAirlines":
                contentGenerator = new ChinaSouthernAirlinesContentGenerator(dataList);
                fileName = "南方航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
        }
        ExcelUtils.exportExcel(contentGenerator, fileName, sheetName, response);
    }

    public LZResult<PaginationResult<Map>> getSpecialCheckList(Long userId, String airportCode, CheckQueryParam checkQueryParam, Integer page, Integer rows) {
        BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));

        checkQueryParam.setAirportCode(airportCode);

        int total = checkMapper.selectSpecialCheckListTotal(checkQueryParam);
        List<Map> checkList = checkMapper.selectSpecialCheckList(queryCondition);
        PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }
}
