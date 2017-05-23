package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.generator.*;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.pojo.SheetContentPo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出文件Service
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/22 11:16
 * @author tiecheng
 */
@Service
public class ExportFileService {

    private static MyService.Iface client = SpringBeanUtil.getBean("checkClient");

    public void exportBill(CheckQueryParam checkQueryParam, String type, HttpServletResponse response, Long userId, String airportCode)throws Exception {

         /*
         * 默认文件名字
         */
        String fileName = "账单";
        String sheetName = "账单";

        // 内容生成器
        ContentGenerator contentGenerator = null;

        if (type == null) {
            return;
        }

        switch (checkQueryParam.getType()) {
            case "firstClass":
                contentGenerator = new FirstClassContentGenerator(getLoungeDateList(checkQueryParam, airportCode,10,userId));
                fileName = "头等舱账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "头等舱账单";
                break;
            case "frequentFlyer":
                contentGenerator = new FrequentFlyerContentGenerator(getLoungeDateList(checkQueryParam, airportCode,9,userId));
                fileName = "常旅客账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "常旅客账单";
                break;
            case "airChina":
                contentGenerator = new AirChinaContentGenerator(getSpecialDateList(checkQueryParam, airportCode, userId));
                fileName = "国际航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
            case "chinaSouthernAirlines":
                contentGenerator = new ChinaSouthernAirlinesContentGenerator(getSpecialDateList(checkQueryParam, airportCode, userId));
                fileName = "南方航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
        }

        ExcelUtils.exportExcel(contentGenerator, fileName, sheetName,response);

    }

    public void exportExcel(OrderCheckDetail orderCheckDetail,String airportCode, Long userId,HttpServletResponse response){
        Map result = getMap(orderCheckDetail, airportCode, userId);
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

    private JSONObject getMap(OrderCheckDetail orderCheckDetail,String airportCode, Long userId){
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        params.put("operation", "customer-checklist");
        params.put("queryCustomerId", orderCheckDetail.getQueryCustomerId());
        params.put("queryProtocolType",orderCheckDetail.getQueryProtocolType());
        params.put("queryProtocolId",orderCheckDetail.getQueryProtocolId());
        params.put("queryProductName",orderCheckDetail.getQueryProductName());

        JSONObject result = null;

        Response re = ClientUtil.clientSendData(client, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }

        return result;

    }

    private List<SheetContentPo> getSpecialDateList(CheckQueryParam checkQueryParam, String airportCode, Long userId) throws ParseException {
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        params.put("operation", "getSpecialDateList");
        params.put("queryFlightDateBegin", checkQueryParam.getQueryFlightDateBegin());
        params.put("queryFlightDateEnd",checkQueryParam.getQueryFlightDateEnd());
        params.put("queryCustomerName",checkQueryParam.getQueryCustomerName());
        params.put("type",checkQueryParam.getType());

        JSONObject result = null;

        Response re = ClientUtil.clientSendData(client, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }

        return JSONObject.parseArray(result.getString("data"), SheetContentPo.class);
    }

    private List<SheetContentPo> getLoungeDateList(CheckQueryParam checkQueryParam, String airportCode,int protocolType, Long userId) throws ParseException {
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        params.put("operation", "getLoungeDateList");
        params.put("queryFlightDateBegin", checkQueryParam.getQueryFlightDateBegin());
        params.put("queryFlightDateEnd",checkQueryParam.getQueryFlightDateEnd());
        params.put("queryCustomerName",checkQueryParam.getQueryCustomerName());
        params.put("type",checkQueryParam.getType());
        params.put("protocolType",protocolType);

        JSONObject result = null;

        Response re = ClientUtil.clientSendData(client, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }

        return JSONObject.parseArray(result.getString("data"), SheetContentPo.class);
    }

}
