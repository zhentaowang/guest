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
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.common.utils.StringUtils;
import com.zhiweicloud.guest.generator.*;
import com.zhiweicloud.guest.generator.train.CountBillGenerator;
import com.zhiweicloud.guest.generator.train.DetailBillGenerator;
import com.zhiweicloud.guest.generator.train.RetailBillGenerator;
import com.zhiweicloud.guest.mapper.CheckMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import com.zhiweicloud.guest.pojo.RowContentPo;
import com.zhiweicloud.guest.pojo.SheetContentPo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                contentGenerator = new FirstClassContentGenerator(getLoungeDateListLocal(checkQueryParam, airportCode,10,userId));
                fileName = "头等舱账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "头等舱账单";
                break;
            case "frequentFlyer":
                contentGenerator = new FrequentFlyerContentGenerator(getLoungeDateListLocal(checkQueryParam, airportCode,9,userId));
                fileName = "常旅客账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "常旅客账单";
                break;
            case "airChina":
                contentGenerator = new AirChinaContentGenerator(getSpecialDateListLocal(checkQueryParam, airportCode, userId));
                fileName = "国际航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
            case "chinaSouthernAirlines":
                contentGenerator = new ChinaSouthernAirlinesContentGenerator(getSpecialDateListLocal(checkQueryParam, airportCode, userId));
                fileName = "南方航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
        }

        ExcelUtils.exportExcel(contentGenerator, fileName, sheetName,response);

    }

    public void exportExcel(OrderCheckDetail orderCheckDetail,String airportCode, Long userId,HttpServletResponse response){
        JSONObject jsonObject = getDateLocal(orderCheckDetail, airportCode, userId);
        JSONObject data = jsonObject.getJSONObject("data");
        if (!data.containsKey("column")) {
            return;
        }
        log.info(data.toJSONString());
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

                switch (sheet.getRow(1).getCell(j).getCellType()){
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                        break;
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        sheet.setColumnWidth(j,sheet.getRow(1).getCell(j).getStringCellValue().getBytes().length * 256);
                        break;
                    default:
                        break;
                }
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
    public void exportExcelForTrain(TrainPojo trainPojo,HttpServletResponse response,Long userId){
        if (trainPojo.getType() <0 || trainPojo.getType() == null){
            return;
        }

        String fileName = "账单";
        String sheetName = "账单";

        SingleSheetGenerator generator = null;

        JSONObject result = new JSONObject();

        switch (trainPojo.getType()){
            case 1:
                result  = getDate(trainPojo.getClientName(),trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime(),userId);
                generator = new CountBillGenerator(result.getJSONObject("data"),response);
                fileName = "统计账单_" + System.currentTimeMillis() + ".xls";
                sheetName = "统计账单";
                break;
            case 2:
                result  = getDate(trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime(),trainPojo.getType(),userId);
                generator = new DetailBillGenerator(result.getJSONObject("data"),response);
                fileName = "明细账单" + System.currentTimeMillis() + ".xls";
                sheetName = "明细账单";
                break;
            case 3:
                result  = getDate(trainPojo.getTrainName(),trainPojo.getProductName(),trainPojo.getStartTime(),trainPojo.getEndTime(),trainPojo.getType(),userId);
                generator = new RetailBillGenerator(result.getJSONObject("data"),response);
                fileName = "零售客户统计账单" + System.currentTimeMillis() + ".xls";
                sheetName = "零售客户统计账单";
                break;
        }

        if(result.getJSONObject("data") == null){
            return;
        }

        if(result.getJSONObject("data").getJSONArray("rows") == null || result.getJSONObject("data").getJSONArray("rows").size() == 0){
            return;
        }

        generator.setFileName(fileName);
        generator.setSheetName(sheetName);
        generator.create();
    }

    private JSONObject getDate(String clientName,String trainName,String productName,String startTime,String endTime,Long userId){
        JSONObject result = null;
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        //params.put("operation", "reportCountBill");
        params.put("clientName", clientName);
        params.put("trainName",trainName);
        params.put("productName",productName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        Response re = ClientUtil.clientSendData(trainClient, "businessService","reportCountBill", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            if (log.isInfoEnabled()) {
                log.info(new String(re.getResponseJSON()));
            }
            result  = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
        }
        return result;
    }

    private JSONObject getDate(String trainName,String productName,String startTime,String endTime,Integer type,Long userId){
        JSONObject result = null;
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        params.put("trainName",trainName);
        params.put("productName",productName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);

        Response re = null;

        if (type == 2){
            //params.put("operation", "reportDetailBill");
            re = ClientUtil.clientSendData(trainClient, "businessService","reportDetailBill", params);
        }
        if (type == 3){
            //params.put("operation", "reportRetailBill");
            re = ClientUtil.clientSendData(trainClient, "businessService","reportRetailBill", params);
        }
        //Response re = ClientUtil.clientSendData(trainClient, "businessService", params);
        if (re !=null && re.getResponeCode().getValue() == 200) {
            if (log.isInfoEnabled()) {
                log.info(new String(re.getResponseJSON()));
            }
            result  = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
        }
        return result;
    }

    private JSONObject getMap(OrderCheckDetail orderCheckDetail,String airportCode, Long userId){
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        //params.put("operation", "customer-checklist");
        params.put("queryCustomerId", orderCheckDetail.getQueryCustomerId());
        params.put("queryProtocolType",orderCheckDetail.getQueryProtocolType());
        params.put("queryProtocolId",orderCheckDetail.getQueryProtocolId());
        params.put("queryProductName",orderCheckDetail.getQueryProductName());

        JSONObject result = null;
        try {
            log.info("服务间调用_Begin");
            Response re = ClientUtil.clientSendData(checkClient, "businessService", "customer-checklist",params);
            if (re !=null && re.getResponeCode().getValue() == 200) {
                result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
            }
            log.info("调用结果_customer-checklist " + result.toJSONString());
            log.info("服务间调用_End");
        }catch (Exception e){
            log.debug("服务间调用异常，需要熔断器");
            throw e;
        }
        return result;
    }

    private JSONObject getDateLocal(OrderCheckDetail orderCheckDetail,String airportCode, Long userId){
        return JSON.parseObject(customerChecklist(orderCheckDetail,airportCode,userId));
    }

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private CheckDynamicColumn checkDynamicColumn;

    private String customerChecklist(OrderCheckDetail orderCheckDetail,String airportCode, Long userId){
        LZResult result = new LZResult<>();
        try {
            orderCheckDetail.setAirportCode(airportCode);
            Map<String, Object> map = new HashMap();
            orderCheckDetail.setCreateUser(userId);
            String productName = orderCheckDetail.getQueryProductName();

            if (productName != null) {
                if (productName.equals("两舱休息室") &&
                        (orderCheckDetail.getQueryProtocolType() == 9 || orderCheckDetail.getQueryProtocolType() == 10)) {
                    productName += orderCheckDetail.getQueryProtocolType().toString();
                }
                orderCheckDetail.setSelectFields(checkDynamicColumn.getColumn(productName));
                orderCheckDetail.setTotalAmount(checkDynamicColumn.getTotalAmount(productName));
                map.put("column", checkDynamicColumn.getHeader(productName));

                orderCheckDetail.setQueryWhere("and o.customer_id = " + orderCheckDetail.getQueryCustomerId() +
                        " and o.protocol_type = " + orderCheckDetail.getQueryProtocolType() +
                        " and o.protocol_id = " + orderCheckDetail.getQueryProtocolId() +
                        " and o.product_name = '" + orderCheckDetail.getQueryProductName() + "'");
            }
            //去掉了分页
            BasePagination<OrderCheckDetail> queryCondition = new BasePagination<>(orderCheckDetail, new PageModel(0, 0));

            List<Map<String, Object>> checkList = checkMapper.customerChecklist(queryCondition);
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
            log.error("CheckService.customerChecklist:", e);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    // 业务本地
    private List<SheetContentPo> getSpecialDateListLocal(CheckQueryParam checkQueryParam, String airportCode, Long userId){
        checkQueryParam.setType("9,10");
        checkQueryParam.setUserId(userId);
        checkQueryParam.setAirportCode(airportCode);
        BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(1, Integer.MAX_VALUE));

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

    private List<SheetContentPo> getLoungeDateListLocal(CheckQueryParam checkQueryParam, String airportCode,int protocolType, Long userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        checkQueryParam.setUserId(userId);
        checkQueryParam.setAirportCode(airportCode);

        // 服务人员名字
        List<LoungeCheckPo> loungeCheckPos = checkMapper.selectLoungeCheckList(checkQueryParam, protocolType);

        List<SheetContentPo> sheetContentPos = new LinkedList<>();
        List<RowContentPo> rowContentPos;
        RowContentPo rowContentPo;
        SheetContentPo sheetContentPo;
        Map<String, Integer> serverNameMap;

        if(loungeCheckPos.size()>0){
            for (LoungeCheckPo loungeCheckPo : loungeCheckPos) {
                rowContentPos = new LinkedList<>();
                sheetContentPo = new SheetContentPo();
                serverNameMap = new HashMap<>();
                int total = 0;
                for (OrderCheckPo orderCheckPo : loungeCheckPo.getOrderCheckPos()) {
                    String serverName = orderCheckPo.getServerName();
                    if(serverNameMap.get(serverName)== null){
                        serverNameMap.put(serverName, 0);
                    }else{
                        serverNameMap.put(serverName, serverNameMap.get(serverName) + 1);
                    }
                    Integer serverNum = orderCheckPo.getServerNum();
                    total += serverNum;
                    Integer guestNum = orderCheckPo.getGuestNum();
                    int length = orderCheckPo.getCheckPassengerPos().size();
                    int value = serverNum - length;
                    for (PassengerCheckPo passengerCheckPo : orderCheckPo.getCheckPassengerPos()) {
                        rowContentPo = new RowContentPo();
                        if (passengerCheckPo.getPassengerType() == 0) { // 主宾
                            if(guestNum == 1){ // 最后一个主宾了,需要把随从都设置进去
                                rowContentPo.setAlongTotal(value == 0 ? null : value);
                            }else {
                                if (value == 0) { // 不需要添加随从人数
                                    rowContentPo.setAlongTotal(null);
                                }
                                if(value > 0){
                                    int average = value / guestNum == 0 ? 1 : value / guestNum; // 随从人数少于贵宾人数
                                    rowContentPo.setAlongTotal(average);
                                    guestNum --;
                                    value = value - average;
                                }
                            }
                        }
                        if (passengerCheckPo.getPassengerType() == 1) { // 随行
                            rowContentPo.setAlongTotal(null);
                        }
                        rowContentPo.setProtocolName(loungeCheckPo.getProtocolName());
                        rowContentPo.setFlightNo(passengerCheckPo.getFlightNo());
                        rowContentPo.setPlanNo(passengerCheckPo.getPlanNo());
                        rowContentPo.setSitNo(passengerCheckPo.getSitNo());
                        rowContentPo.setPassengerType(passengerCheckPo.getPassengerType());
                        rowContentPo.setAirpotCode(passengerCheckPo.getAirpotCode());
                        rowContentPo.setLeg(passengerCheckPo.getLeg());
                        rowContentPo.setCustomerName(loungeCheckPo.getCustomerName());
                        rowContentPo.setFlightDate(loungeCheckPo.getFlightDate() == null ? "" : simpleDateFormat.format(loungeCheckPo.getFlightDate()));
                        rowContentPo.setCabinNo(passengerCheckPo.getCabinNo());
                        rowContentPo.setExpireTime(passengerCheckPo.getExpireTime());
                        rowContentPo.setFlightDepcode(passengerCheckPo.getFlightDepcode());
                        rowContentPo.setFlightArrcode(passengerCheckPo.getFlightArrcode());
                        rowContentPo.setCardNo(passengerCheckPo.getCardNo());
                        rowContentPo.setCardType(passengerCheckPo.getCardType());
                        rowContentPo.setName(passengerCheckPo.getName());
                        rowContentPo.setTicketNo(passengerCheckPo.getTicketNo());
                        rowContentPos.add(rowContentPo);
                    }
                }
                StringBuilder employeeName = new StringBuilder();
                for (Map.Entry<String, Integer> entry : serverNameMap.entrySet()) {
                    employeeName.append(entry.getKey()).append(",");
                }
                employeeName = employeeName.deleteCharAt(employeeName.length() - 1);
                sheetContentPo.setEmployeeName(employeeName.toString());
                sheetContentPo.setTotal(total);
                sheetContentPo.setRowContentPos(rowContentPos);
                sheetContentPos.add(sheetContentPo);
            }
        }

        return sheetContentPos;
    }

    private List<SheetContentPo> getSpecialDateList(CheckQueryParam checkQueryParam, String airportCode, Long userId) throws ParseException {
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        //params.put("operation", "getSpecialDateList");
        params.put("queryFlightDateBegin", checkQueryParam.getQueryFlightDateBegin());
        params.put("queryFlightDateEnd",checkQueryParam.getQueryFlightDateEnd());
        params.put("queryCustomerName",checkQueryParam.getQueryCustomerName());
        params.put("queryProtocolId",checkQueryParam.getQueryProtocolId());

        JSONObject result = null;

        try {
            log.info("服务间调用_Begin");
            Response re = ClientUtil.clientSendData(checkClient, "businessService", "getSpecialDateList",params);
            if (re !=null && re.getResponeCode().getValue() == 200) {
                result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
            }
            log.info("调用结果_getSpecialDateList " + result.toJSONString());
            log.info("服务间调用_End");
        }catch (Exception e){
            log.debug("服务间调用异常，需要熔断器");
            throw e;
        }

        return JSONObject.parseArray(result.getString("data"), SheetContentPo.class);
    }

    private List<SheetContentPo> getLoungeDateList(CheckQueryParam checkQueryParam, String airportCode,int protocolType, Long userId) throws ParseException {
        JSONObject params = new JSONObject();
        params.put("client_id", airportCode);
        params.put("user_id", userId);
        //params.put("operation", "getLoungeDateList");
        params.put("queryFlightDateBegin", checkQueryParam.getQueryFlightDateBegin());
        params.put("queryFlightDateEnd",checkQueryParam.getQueryFlightDateEnd());
        params.put("queryCustomerName",checkQueryParam.getQueryCustomerName());
        params.put("queryProtocolId",checkQueryParam.getQueryProtocolId());
        params.put("protocolType",protocolType);

        JSONObject result = null;

        try {
            log.info("服务间调用_Begin");
            Response re = ClientUtil.clientSendData(checkClient, "businessService", "getLoungeDateList",params);
            if (re !=null && re.getResponeCode().getValue() == 200) {
                result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
            }
            log.info("调用结果_getLoungeDateList " + result.toJSONString());
            log.info("服务间调用_End");
        }catch (Exception e){
            log.debug("服务间调用异常，需要熔断器");
            throw e;
        }

        return JSONObject.parseArray(result.getString("data"), SheetContentPo.class);
    }

}
