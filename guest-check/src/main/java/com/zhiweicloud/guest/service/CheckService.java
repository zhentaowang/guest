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
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.net.httpserver.Authenticator;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.thrift.server.business.IBusinessService;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.excel.generator.*;
import com.zhiweicloud.guest.common.excel.po.RowContentPo;
import com.zhiweicloud.guest.common.excel.po.SheetContentPo;
import com.zhiweicloud.guest.common.excel.util.ExcelUtils;
import com.zhiweicloud.guest.mapper.CheckMapper;
import com.zhiweicloud.guest.model.CheckPassengerPo;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.LoungeCheckPo;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class CheckService implements IBusinessService {

    private static final Logger logger = LoggerFactory.getLogger(CheckService.class);

    private final CheckMapper checkMapper;
    private final CheckDynamicColumn checkDynamicColumn;

    private static MyService.Iface protocolClient = SpringBeanUtil.getBean("protocolClient");

    @Autowired
    public CheckService(CheckMapper checkMapper, CheckDynamicColumn checkDynamicColumn) {
        this.checkMapper = checkMapper;
        this.checkDynamicColumn = checkDynamicColumn;
    }

    @Override
    public JSONObject handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }



        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "customer-checklist":
                success = customerChecklist(request);
                break;
//            case "exportFile":
//                exportExcel(request);
//                break;
//            case "exportBill":
//                exportBill(request);
//                break;
            case "specialCheckList":
                success = getSpecialCheckList(request);
                break;
            default:
                break;
        }

        return JSON.parseObject(success);
    }

    public String list(JSONObject request){
        LZResult<Object> result = new LZResult<>();
        try {
            CheckQueryParam checkQueryParam = JSON.toJavaObject(request, CheckQueryParam.class);
            checkQueryParam.setAirportCode (request.getString("client_id"));

            //分页参数
            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");
            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
            }
            BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));

            int total = checkMapper.selectCheckTotal(checkQueryParam);
            List<Map> checkList = checkMapper.selectCheckList(queryCondition);
            for (int i = 0; i < checkList.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("user_id", request.getLong("user_id"));
                jsonObject.put("client_id", request.getString("client_id"));
                jsonObject.put("protocolTypeId", checkList.get(i).get("protocolType"));
                jsonObject.put("operation", "getProtocolTypeDropdownList");

                JSONObject protocolObject = new JSONObject();
                Response response = ClientUtil.clientSendData(protocolClient, "businessService", jsonObject);
                if (response != null && response.getResponeCode().getValue() == 200) {
                    protocolObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                }
                if (protocolObject != null && protocolObject.get("data") != null && JSON.parseArray(protocolObject.get("data").toString()).size() > 0) {

                    JSONObject protocolObj = JSON.parseObject(JSON.parseArray(protocolObject.get("data").toString()).get(0).toString());
                    checkList.get(i).put("protocolTypeName", protocolObj.get("value"));
                }
            }

            PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
            result = new LZResult<>(eqr);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
        } catch (Exception e) {
            logger.error("CheckService.list:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 服务账单详情列表  modified on 2017/5/8 by zhengyiyin
     * @return
     * @throws Exception
     */
    public String customerChecklist(JSONObject request){
        LZResult result = new LZResult<>();
        try {
            Map<String, Object> map = new HashMap();
            OrderCheckDetail orderCheckDetail = JSON.toJavaObject(request, OrderCheckDetail.class);
            orderCheckDetail.setAirportCode(request.getString("client_id"));
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
            //去掉了分页
            List<Map<String, Object>> checkList = checkMapper.customerChecklist();
            ArrayList<String> key = new ArrayList<>(Arrays.asList("vipPersonNum","accompanyPersonNum","restRoomPersonNum","securityCheckPersonNum","totalAmount"));
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
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(map);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            logger.error("CheckService.customerChecklist:", e);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 原导出excel http 请求 modified on 2017/5/13 by zhengyiyin
     * @param
     */
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
     * 导出excel thrift 没有response 需改造    modified on 2017/5/13 by zhengyiyin
     * @param request
     */
    public void exportExcel(JSONObject request){
        try {
            HttpServletRequest requestTemp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ServletWebRequest servletWebRequest=new ServletWebRequest(requestTemp);
            HttpServletResponse response=servletWebRequest.getResponse();

            OrderCheckDetail orderCheckDetail = JSON.toJavaObject(request, OrderCheckDetail.class);
            Map result = JSON.parseObject(customerChecklist(request), Map.class);
            if (result != null) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出账单
     *
     */
    public void exportBill(JSONObject request){
        CheckQueryParam checkQueryParam = JSON.toJavaObject(request, CheckQueryParam.class);
        String airportCode = request.getString("client_id");
        try {
            //分页参数
            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");
            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
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
            switch (checkQueryParam.getType()) {
                case "firstClass":
                    contentGenerator = new FirstClassContentGenerator(getLoungeDateList(checkQueryParam, airportCode,10));
                    fileName = "头等舱账单" + "_" + System.currentTimeMillis() + ".xls";
                    sheetName = "头等舱账单";
                    break;
                case "frequentFlyer":
                    contentGenerator = new FrequentFlyerContentGenerator(getLoungeDateList(checkQueryParam, airportCode,9));
                    fileName = "常旅客账单" + "_" + System.currentTimeMillis() + ".xls";
                    sheetName = "常旅客账单";
                    break;
                case "airChina":
                    contentGenerator = new AirChinaContentGenerator(getSpecialDateList(checkQueryParam, airportCode, page, rows));
                    fileName = "国际航空账单" + "_" + System.currentTimeMillis() + ".xls";
                    sheetName = "国际航空账单";
                    break;
                case "chinaSouthernAirlines":
                    contentGenerator = new ChinaSouthernAirlinesContentGenerator(getSpecialDateList(checkQueryParam, airportCode, page, rows));
                    fileName = "南方航空账单" + "_" + System.currentTimeMillis() + ".xls";
                    sheetName = "国际航空账单";
                    break;
            }
//            ExcelUtils.exportExcel(contentGenerator, fileName, sheetName,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<SheetContentPo> getSpecialDateList(CheckQueryParam checkQueryParam, String airportCode, Integer page, Integer rows) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        checkQueryParam.setAirportCode(airportCode);
        BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));
        List<Map> maps = checkMapper.selectSpecialCheckList(queryCondition);
        SheetContentPo sheetContentPo = new SheetContentPo();
        List<SheetContentPo> sheetContentPos = new LinkedList<>();
        List<RowContentPo> rowContentPos = new LinkedList<>();
        RowContentPo rowContentPo;
        for (Map map : maps) {
            rowContentPo = new RowContentPo();
            rowContentPo.setAmout((Double) map.get("amount"));
            rowContentPo.setFlightDepcode((String) map.get("flightDepcode"));
            rowContentPo.setFlightArrcode((String) map.get("flightArrcode"));
            rowContentPo.setFlightDate(String.valueOf(map.get("flightDate")));
            rowContentPo.setServerPersonNum((Double)(map.get("serverPersonNum")));
            rowContentPo.setCustomerName((String) map.get("customerName"));
            rowContentPo.setPrice(((Long)map.get("price")));
            rowContentPo.setAirpotCode((String)map.get("airportCode"));
            rowContentPo.setPlanNo((String) map.get("planNo"));
            rowContentPo.setFlightNo((String) map.get("flightNo"));
            rowContentPo.setLeg((String) map.get("leg"));
            rowContentPos.add(rowContentPo);
        }
        sheetContentPo.setRowContentPos(rowContentPos);
        sheetContentPos.add(sheetContentPo);
        return sheetContentPos;
    }

    private List<SheetContentPo> getLoungeDateList(CheckQueryParam checkQueryParam, String airportCode,int protocolType) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        checkQueryParam.setAirportCode(airportCode);
        List<LoungeCheckPo> loungeCheckPos = checkMapper.selectLoungeCheckList(checkQueryParam, protocolType);
        List<SheetContentPo> sheetContentPos = new LinkedList<>();
        List<RowContentPo> rowContentPos;
        RowContentPo rowContentPo;
        SheetContentPo sheetContentPo;
        for (LoungeCheckPo loungeCheckPo : loungeCheckPos) {
            rowContentPos = new LinkedList<>();
            sheetContentPo = new SheetContentPo();
            for (CheckPassengerPo checkPassengerPo : loungeCheckPo.getCheckPassengerPos()) {
                rowContentPo = new RowContentPo();
                rowContentPo.setFlightNo(checkPassengerPo.getFlightNo());
                rowContentPo.setPlanNo(checkPassengerPo.getPlanNo());
                rowContentPo.setAlongTotal(checkPassengerPo.getAlongTotal());
                rowContentPo.setAirpotCode(checkPassengerPo.getAirpotCode());
                rowContentPo.setLeg(checkPassengerPo.getLeg());
                rowContentPo.setCustomerName(loungeCheckPo.getCustomerName());
                rowContentPo.setFlightDate(loungeCheckPo.getFlightDate() == null ? "" : simpleDateFormat.format(loungeCheckPo.getFlightDate()));
                rowContentPo.setCabinNo(checkPassengerPo.getCabinNo());
                rowContentPo.setExpireTime(checkPassengerPo.getExpireTime());
                rowContentPo.setServerPersonNum(checkPassengerPo.getServerPersonNum());
                rowContentPo.setPrice(checkPassengerPo.getPrice());
                rowContentPo.setAmout(checkPassengerPo.getAmout());
                rowContentPo.setFlightDepcode(checkPassengerPo.getFlightDepcode());
                rowContentPo.setFlightArrcode(checkPassengerPo.getFlightArrcode());
                rowContentPo.setCardNo(checkPassengerPo.getCardNo());
                rowContentPo.setCardType(checkPassengerPo.getCardType());
                rowContentPo.setName(checkPassengerPo.getName());
                rowContentPo.setTicketNo(checkPassengerPo.getTicketNo());
                rowContentPos.add(rowContentPo);
            }
            sheetContentPo.setRowContentPos(rowContentPos);
            sheetContentPos.add(sheetContentPo);
        }
        return sheetContentPos;
    }

    /**
     * modified on 2017/5/13 by zhengyiyin
     * @param request
     * @return
     */
    public String getSpecialCheckList(JSONObject request) {
        try {
            CheckQueryParam checkQueryParam = JSON.toJavaObject(request, CheckQueryParam.class);
            checkQueryParam.setAirportCode(request.getString("client_id"));
            //分页参数
            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");
            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
            }
            BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));

            int total = checkMapper.selectSpecialCheckListTotal(checkQueryParam);
            List<Map> checkList = checkMapper.selectSpecialCheckList(queryCondition);
            PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
            LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
            return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            return this.errorMsg(e);
        }


    }

    private String errorMsg(Exception e){
        e.printStackTrace();
        LZResult result = new LZResult<>();
        result.setMsg(LZStatus.ERROR.display());
        result.setStatus(LZStatus.ERROR.value());
        result.setData(null);
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

}
