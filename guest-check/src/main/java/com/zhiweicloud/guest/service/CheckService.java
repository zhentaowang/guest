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
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.ExcelUtils;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.controller.CheckController;
import com.zhiweicloud.guest.mapper.CheckMapper;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerResponseContext;
import java.io.OutputStream;
import java.util.*;

import static com.alibaba.fastjson.parser.Feature.OrderedField;

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

        //productType 1,2,3 这种格式
        if (checkQueryParam.getQueryProtocolType() != null && !checkQueryParam.getQueryProtocolType().equals("")) {
            if (checkQueryParam.getQueryProtocolType().length() > 0 && checkQueryParam.getQueryProtocolType().contains(",")) {
                String protocolTypeArr[] = checkQueryParam.getQueryProtocolType().split(",");
                for (int i = 0; i < protocolTypeArr.length; i++) {
                    List protocolIdList = this.getProtocolList(protocolTypeArr[i], userId, airportCode);
                    if (protocolIdList.size() > 0) {
                        checkQueryParam.setQueryProtocolId(ListUtil.List2String(protocolIdList));//协议id
                    }
                }
            } else {
                List protocolIdList = this.getProtocolList(checkQueryParam.getQueryProtocolType(), userId, airportCode);
                if (protocolIdList.size() > 0) {
                    checkQueryParam.setQueryProtocolId(ListUtil.List2String(protocolIdList));//协议id
                }
            }
        }

        int total = checkMapper.selectCheckTotal(checkQueryParam);
        List<Map> checkList = checkMapper.selectCheckList(queryCondition);
        for (int i = 0; i < checkList.size(); i++) {

            Map<String, Object> headerMap = new HashMap();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("protocolId", checkList.get(i).get("protocolId"));
            //JSONObject protocolObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getById",paramMap,headerMap));
            JSONObject protocolObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getById?access_token=59UFQ9V7G82O6AFlGYO6hntsmMNARj9ytQSc9E1J&protocolId="+ checkList.get(i).get("protocolId")));
            if (protocolObject != null && protocolObject.get("data") != null) {
                JSONObject protocolObj = JSON.parseObject(protocolObject.get("data").toString());
                checkList.get(i).put("protocolType", protocolObj.get("protocolTypeName"));
            }
        }

        PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }

    private List getProtocolList(String protocolType, Long userId, String airportCode) throws Exception {
        Map<String, Object> headerMap = new HashMap();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", airportCode);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("protocolType", protocolType);
        List protocolIdList = new ArrayList();
        JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolNameDropdownList", headerMap, paramMap));
        if (protocolParam != null) {
            JSONArray protocolArray = protocolParam.getJSONArray("data");
            for (int i = 0; i < protocolArray.size(); i++) {
                JSONObject jsonObject = JSON.parseObject(protocolArray.get(i).toString());
                protocolIdList.add(jsonObject.get("id"));
            }
        }
        return protocolIdList;
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
        Map<String, String> titleMap = new HashMap<>();
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
