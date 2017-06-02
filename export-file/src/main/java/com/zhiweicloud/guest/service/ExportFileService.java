package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.common.utils.StringUtils;
import com.zhiweicloud.guest.generator.*;
import com.zhiweicloud.guest.generator.train.CountBillGenerator;
import com.zhiweicloud.guest.generator.train.DetailBillGenerator;
import com.zhiweicloud.guest.generator.train.RetailBillGenerator;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.model.TrainPojo;
import com.zhiweicloud.guest.pojo.SheetContentPo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

/**
 * 导出文件Service
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/22 11:16
 * @author tiecheng
 */
@Service
public class ExportFileService {

    private static final Log log = LogFactory.getLog(ExportFileService.class);

    private static MyService.Iface checkClient = SpringBeanUtil.getBean("checkClient");

    private static MyService.Iface trainClient = SpringBeanUtil.getBean("trainClient");

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
        JSONObject jsonObject = getMap(orderCheckDetail, airportCode, userId);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray column = data.getJSONArray("column");
        List rows = JSONArray.parseArray(data.getString("rows"));

        String fileName = orderCheckDetail.getQueryProductName() + "_" + System.currentTimeMillis() + ".xls";

        for (int i = 0; i < 1; i++) {
            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFCellStyle cellStyle1 = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            cellStyle1.setAlignment(HorizontalAlignment.CENTER); // 居中
            cellStyle1.setFont(font);

            HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            HSSFDataFormat dataFormat = workbook.createDataFormat();
            cellStyle2.setAlignment(HorizontalAlignment.CENTER); // 居中
            cellStyle2.setDataFormat(dataFormat.getFormat("#"));

            HSSFCellStyle cellStyle3 = workbook.createCellStyle();
            cellStyle3.setAlignment(HorizontalAlignment.CENTER); // 居中

            String sheetName = orderCheckDetail.getQueryProductName();
            HSSFSheet sheet = workbook.createSheet(sheetName);

            String[] key = new String[column.size()];
            String[] value = new String[column.size()];

            HSSFRow row = sheet.createRow(0);

            int n = 0;
            for (Object o : column) {
                JSONObject object = (JSONObject) o;
                String v = object.getString("displayName");
                String k = object.getString("columnName");
                System.out.println(k);
                System.out.println(v);
                key[n] = k;
                value[n] = v;
                HSSFCell row1Cell = row.createCell(n);
                if (StringUtils.isNotNone(v) && ExcelUtils.isInteger(v)) {
                    row1Cell.setCellStyle(cellStyle2);
                    row1Cell.setCellValue(Double.valueOf(v));
                } else {
                    row1Cell.setCellStyle(cellStyle1);
                    row1Cell.setCellValue(v);
                }
                n++;
            }

            int m = 0;
            for (Object o : rows) {
                HSSFRow rowContent = sheet.createRow(m + 1);
                JSONObject object = (JSONObject) o;
                for (int j = 0; j < key.length; j++) {
                    HSSFCell cell = rowContent.createCell(j);
                    String va = object.getString(key[j]);
                    if (StringUtils.isNotNone(va) && ExcelUtils.isInteger(va)) {
                        cell.setCellStyle(cellStyle2);
                        cell.setCellValue(Double.parseDouble(va));
                    }else {
                        cell.setCellStyle(cellStyle3);
                        cell.setCellValue(va);
                    }
                }
                m++;
            }

            for (int j = 0; j < column.size() ; j++) {
                sheet.setColumnWidth(j,sheet.getRow(1).getCell(j).getStringCellValue().getBytes().length * 256);
            }

            try (OutputStream out = response.getOutputStream()) {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
                workbook.write(out);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 导出账单 - 高铁项目
     * @param trainPojo type 1:统计账单;2:明细账单;3:零售客户统计账单
     * @param response
     */
    public void exportExcelForTrain(TrainPojo trainPojo,HttpServletResponse response){
        if (trainPojo.getType() <0 || trainPojo.getType() == null){
            return;
        }

        String fileName = "账单";
        String sheetName = "账单";

        SingleSheetGenerator generator = null;

        JSONObject result = new JSONObject();

        switch (trainPojo.getType()){
            case 1:
                result  = getDate(trainPojo.getClientName(),trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime());
                generator = new CountBillGenerator(result.getJSONObject("data"),response);
                fileName = "统计账单_" + System.currentTimeMillis() + ".xls";
                sheetName = "统计账单";
                break;
            case 2:
                result  = getDate(trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime(),trainPojo.getType());
                generator = new DetailBillGenerator(result.getJSONObject("data"),response);
                fileName = "明细账单" + System.currentTimeMillis() + ".xls";
                sheetName = "明细账单";
                break;
            case 3:
                result  = getDate(trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime(),trainPojo.getType());
                generator = new RetailBillGenerator(result.getJSONObject("data"),response);
                fileName = "零售客户统计账单" + System.currentTimeMillis() + ".xls";
                sheetName = "零售客户统计账单";
                break;
        }

        if(result.getJSONObject("data") == null){
            return;
        }

        generator.setFileName(fileName);
        generator.setSheetName(sheetName);
        generator.create();
    }

    private JSONObject getDate(String clientName,String trainName,String productName,String startTime,String endTime){
        JSONObject result = null;
        JSONObject params = new JSONObject();
        params.put("operation", "reportCountBill");
        params.put("clientName", clientName);
        params.put("trainName",trainName);
        params.put("productName",productName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        Response re = ClientUtil.clientSendData(trainClient, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            if (log.isInfoEnabled()) {
                log.info(new String(re.getResponseJSON()));
            }
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }
        if (log.isInfoEnabled()) {
            log.info("【 结果: " + result.toString() +" ");
        }
        return result;
    }

    private JSONObject getDate(String trainName,String productName,String startTime,String endTime,Integer type){
        JSONObject result = null;
        JSONObject params = new JSONObject();
        params.put("trainName",trainName);
        params.put("productName",productName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        if (type == 2){
            params.put("operation", "reportDetailBill");

        }
        if (type == 3){
            params.put("operation", "reportRetailBill");
        }
        Response re = ClientUtil.clientSendData(trainClient, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            if (log.isInfoEnabled()) {
                log.info(new String(re.getResponseJSON()));
            }
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }
        if (log.isInfoEnabled()) {
            log.info("【 结果: " + result.toString() +" ");
        }
        return result;
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

        Response re = ClientUtil.clientSendData(checkClient, "businessService", params);
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

        Response re = ClientUtil.clientSendData(checkClient, "businessService", params);
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

        Response re = ClientUtil.clientSendData(checkClient, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
        }

        return JSONObject.parseArray(result.getString("data"), SheetContentPo.class);
    }

}
