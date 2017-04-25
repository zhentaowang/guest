package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Excel导出工具类
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * @url https://www.zhiweicloud.com
 * @date 2017/3/17 13:42
 * @author tiecheng
 */
public class ExcelUtils {

    private static final Log log = LogFactory.getLog(ExcelUtils.class);

    private ExcelUtils() {
    }

    public static void readExcel(File file) throws IOException{
        String fileName = file.getName();
        String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);
        if("xls".equals(extension)){
            read2003Excel(file);
        }else if("xlsx".equals(extension)){
            read2007Excel(file);
        }
    }

    private static List<List<Object>> read2007Excel(File file) {
        return null;
    }

    private static List<List<Object>> read2003Excel(File file) {
        return null;
    }


    /**
     * 默认的导出路径
     */
    private static final String DEFAULT_PATH = "flie://home/nfs-share/excel";
//    private static final String DEFAULT_PATH = "C:/excel";

    /**
     * 工作簿
     */
    private static HSSFWorkbook workbook;

    /**
     * 工作表
     */
    private static HSSFSheet sheet;

    /**
     * 标题行开始位置
     */
    private static final int TITLE_START_POSITION = 0;

    /**
     * 文本行开始位置
     */
    private static final int CONTENT_START_POSITION = 1;

    /**
     * 导出Excel
     * @param sheetName 工作表名
     * @param titleMap 数据json
     * @param dataList 内容数据
     */
    public static void export(String sheetName,Map<String,String> titleMap,List dataList){
        initHSSFWorkbook(sheetName);
        createFirstRow(titleMap);
        createContentRow(dataList, titleMap);
        out(getFileName());
    }

    /**
     * 导出Excel
     * @param sheetName 工作表名
     * @param dataList 内容数据
     * @param titleList 标题list
     */
    public static void export(String sheetName,JSONArray dataList,List titleList){
        initHSSFWorkbook(sheetName);
        createFirstRow(titleList);
        createContentRow(dataList, titleList);
        out(getFileName());
    }

    /**
     * 导出Excel *目前项目中使用的*
     * @param fileName
     * @param sheetName
     * @param rows
     * @param titleMap
     */
    public static void export(String fileName, String sheetName, List rows, Map<String, String> titleMap) {
        initHSSFWorkbook(sheetName);
        autoColumnSize(titleMap.size());
        createFirstRow(titleMap);
//        createContentRowForVipCloud(rows, titleMap);
//        createContentRow(rows, titleMap);
        out(getFilePath(fileName));
    }

    public static OutputStream workbookToStream(String fileName, String sheetName, List rows, Map<String, String> titleMap){
        initHSSFWorkbook(sheetName);
        autoColumnSize(titleMap.size());
        createFirstRow(rows);
//        createContentRowForVipCloud(rows, titleMap);
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream())) {
            objectOutputStream.writeObject(workbook);
            return objectOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出Excel
     * @param filePath 文件路径
     * @param sheetName 工作表名
     * @param dataList 内容数据
     * @param titleList 标题list
     */
    public static void export(String filePath,String sheetName,JSONArray dataList,List titleList){
        initHSSFWorkbook(sheetName);
        createFirstRow(titleList);
        createContentRow(dataList, titleList);
        out(getFilePath(filePath));
    }

    /**
     * 导出Excel
     * @param filePath 文件路径
     * @param sheetName 工作表名
     * @param dataList 内容数据
     * @param titleList 标题list
     * @param titleMap 标题map 中英文的映射key-value
     */
    public static void export(String filePath,String sheetName,JSONArray dataList,List titleList,Map<String,String> titleMap){
        initHSSFWorkbook(sheetName);
        createFirstRow(titleList,titleMap);
        createContentRow(dataList, titleList);
        out(getFilePath(filePath));
    }

    /**
     * 初始化Excel文件对象
     *
     * @param sheetName 工作表名
     */
    private static void initHSSFWorkbook(String sheetName) {
        workbook = new HSSFWorkbook();
        sheetName = sheetName == null ? getSheetName() : sheetName;
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * 创建第一列
     *
     * @param titleMap 标题栏map
     */
    private static void createFirstRow(Map<String, String> titleMap) {
        HSSFRow row = sheet.createRow(TITLE_START_POSITION);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setFont(font);
        int i = 0;
        for (Map.Entry<String, String> entry : titleMap.entrySet()) {
            HSSFCell textcell = row.createCell(i);
            textcell.setCellStyle(cellStyle);
            textcell.setCellValue(entry.getValue());
            i++;
        }
    }

    /**
     * 创建第一列
     * @param titleList 标题栏list
     */
    private static void createFirstRow(List<String> titleList) {
        HSSFRow row = sheet.createRow(TITLE_START_POSITION);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        for (int i = 0,size = titleList.size(); i < size; i++) {
            HSSFCell textcell = row.createCell(i);
            textcell.setCellStyle(cellStyle);
            textcell.setCellValue(titleList.get(i));
        }
    }

    /**
     * 创建第一列
     * @param titleList 标题栏list
     * @param titleMap 标题栏map
     */
    private static void createFirstRow(List<String> titleList,Map<String, String> titleMap) {
        HSSFRow row = sheet.createRow(TITLE_START_POSITION);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        for (int i = 0,size = titleList.size(); i < size; i++) {
            HSSFCell textcell = row.createCell(i);
            textcell.setCellStyle(cellStyle);
            textcell.setCellValue(titleMap.get(titleList.get(i)));
        }
    }

    /**
     * 创建excel内容
     * @param dataList 对象数据集合
     * @param titleMap 标题栏map
     */
//    private static void createContentRow(List dataList, Map<String, String> titleMap) {
//        try {
//            int i = 0;
//            for (Object obj : dataList) {
//                HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
//                for (int j = 0; j < titleMap.size(); j++) {
//                    String s = titleMap.get(j);
//                    String method = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
//                    Method m = obj.getClass().getMethod(method, null);
//                    String value =   m.invoke(obj, null).toString();
//                    HSSFCell textcell = row.createCell(j);
//                    textcell.setCellValue(value);
//                }
//                i++;
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 项目中定制的方法 非通用.
     * @param dataList 数据集合 *合计行数据格式和数量不一致*
     * @param titleMap 标题集合
     */
//    private static void createContentRowForVipCloud(List dataList, Map<String, String> titleMap) {
//        HSSFCellStyle contextstyle = workbook.createCellStyle();
//        HSSFDataFormat hssfDataFormat = workbook.createDataFormat();
//        for (int i = 0, size = dataList.size(); i < size; i++) {
//            HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
//            Map<String, Object> rowMap = (HashMap) dataList.get(i);
//            int j = 0;
//            for (Map.Entry<String, String> entry : titleMap.entrySet()) {
//                HSSFCell textcell = row.createCell(j);
//                Object value = rowMap.get(entry.getKey());
//                if (value == null || "".equals(value)) {
//                    if (i == (size - 1) && j == 0) {
//                        textcell.setCellValue("合计");
//                    }else {
//                        textcell.setCellType(CellType.BLANK);
//                    }
//                } else {
//                    if (isNum(value)) {
//                        textcell.setCellValue(Double.parseDouble(String.valueOf(value)));
////                        textcell.setCellType(CellType.NUMERIC);
//                        if (isInteger(value)) {
//                            contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
//                        } else {
//                            contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
//                        }
//                        textcell.setCellStyle(contextstyle);
//
//                    } else {
//                        textcell.setCellValue(value.toString());
//                    }
//                }
//                j++;
//            }
//        }
//    }

    private static void createContentRow(List dataList, Map<String, String> titleMap) {
        HSSFCellStyle contextstyle = workbook.createCellStyle();
        for (int i = 0,size = dataList.size(); i < size; i++) {
            HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
            Map<String,Object> rowMap = (HashMap) dataList.get(i);
            int j = 0;
            for (Map.Entry<String, String> entry : titleMap.entrySet()) {
                HSSFCell textcell = row.createCell(j);
                Object value = rowMap.get(entry.getKey());
                setValue(textcell,value,contextstyle);
                j++;
            }
        }
    }

    private static void setValue(HSSFCell cell,Object value,HSSFCellStyle contextstyle){
        if (value == null || "".equals(value)) {
            cell.setCellType(CellType.BLANK);
        } else {
            if (isNum(value)) {
                cell.setCellValue(Double.parseDouble(String.valueOf(value)));
                if (isInteger(value)) {
                    contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                } else {
                    contextstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                }
                cell.setCellStyle(contextstyle);
            }else {
                cell.setCellValue(value.toString());
            }
        }
    }

    /**
     * 创建excel内容
     * @param dataList 对象数据集合
     * @param titleList 标题栏list
     */
    private static void createContentRow(JSONArray dataList, List<String> titleList) {
        Iterator<Object> rows = dataList.iterator();
        int i = 0;
        while (rows.hasNext()) {
            HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
            JSONObject ob = (JSONObject) rows.next();
            int j = 0;
            for (String s : titleList) {
                HSSFCell textcell = row.createCell(j);
                textcell.setCellValue(ob.getString(s));
                j++;
            }
            i++;
        }
    }

    /**
     * 生成标题（第零行创建） 合并单元格的例子
     * @param titleMap 对象属性名称->表头显示名称
     * @param sheetName sheet名称
     */
    private static void createTitleRow(Map<String, String> titleMap, String sheetName) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, titleMap.size() - 1);
        sheet.addMergedRegion(titleRange);
        HSSFRow titleRow = sheet.createRow(TITLE_START_POSITION);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetName);
    }

    private static void autoColumnSize(int size){
        for (int i = 0; i < size; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void out(String filePath){
        try (OutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFilePath(String fileName){
        String path = fileName == null ? (DEFAULT_PATH + "/" + getDefaultFilePath()): checkFileName(fileName);
        return path;
    }

    private static String checkFileName(String fileName){
        if (fileName.contains(".xls")|| fileName.contains(".xlsx")) {
            return DEFAULT_PATH + "/" + fileName;
        }else {
            return DEFAULT_PATH + "/" + fileName + ".xls";
        }
    }

    private static String getDefaultFilePath(){
        return DEFAULT_PATH + "/" + getFileName();
    }

    private static String getFileName(){
        UUID uuid = UUID.randomUUID();
        return uuid + ".xls";
    }

    private static String getSheetName(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 下载.
     *
     * @param downloadPath 下载路径
     * @param response 响应
     */
    public static void download(String downloadPath, HttpServletResponse response) {
        File file = new File(getFilePath(downloadPath));
        String filename = file.getName();
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(getFilePath(downloadPath)));
             OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("UTF-8"), "ISO_8859_1"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            toClient.write(buffer);
            toClient.flush();
            workbook.write(toClient);
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }

    /**
     * 下载
     *
     * @param fileName  文件名
     * @param sheetName 工作表名
     * @param rows      数据
     * @param titleMap  标题
     * @param response  响应
     */
    public static void download(String fileName, String sheetName, List rows, Map<String, String> titleMap,HttpServletResponse response) {
        initHSSFWorkbook(sheetName);
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            createFirstRow(titleMap);
            createContentRow(rows,titleMap);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(String type,String fileName, String sheetName,HttpServletResponse response) {

        // 假数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "张" + i);
            map.put("flightNo", "MU247" + i);
            map.put("planNo", i);
            map.put("ticketNo", "CLK2111" + i);
            map.put("voyage ", "萧山国际机场-广州白云机场");
            map.put("cardType", i % 2 == 0 ? "金卡" : "银卡");
            map.put("cabin", i % 2 == 0 ? "经济舱" : "头等舱");
            map.put("cardNo", "NNN13213" + i);
            map.put("validity", "2017-03-05");
            map.put("follow", "王振涛");
            list.add(map);
        }
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "姓名");
        map.put("flightNo", "航班号");
        map.put("planNo", "机号");
        map.put("ticketNo","客票号码" );
        map.put("voyage ", "航程");
        map.put("cardType", "卡类别");
        map.put("cabin", "舱位");
        map.put("cardNo", "卡号");
        map.put("validity", "有效期");
        map.put("follow", "随行");

        initHSSFWorkbook(sheetName);
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            switch (type){
                case "firstClass":
                    createFirstClassContent(list,map);
                    break;
                case "frequentFlyer":
                    createFrequentFlyerContent(list,map);
                    break;
                case "airChina":
                    createAirChinaTitle();
                    break;
                case "chinaSouthernAirlines":
                    createChinaSouthernAirlinesTitle();
                    break;
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建国航标题栏
     */
    private static void createAirChinaTitle(){

    }

    /**
     * 创建南航标题栏
     */
    private static void createChinaSouthernAirlinesTitle(){

    }

    /**
     * 创建头等舱账单内容
     */
    private static void createFirstClassContent(List<Map<String,Object>> list,Map<String,String> titles){

        int row = 0; // 行号
        int rowLength = list.size();

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中

        // 第一行
        HSSFRow row1 = sheet.createRow(row);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellStyle(cellStyle);
        row1Cell1.setCellValue("航空公司");
        HSSFCell row1Cell2 = row1.createCell(1);
        row1Cell2.setCellStyle(cellStyle);
        row1Cell2.setCellValue("XXX航空公司");
        HSSFCell row1Cell3 = row1.createCell(2);
        row1Cell3.setCellStyle(cellStyle);
        row1Cell3.setCellValue("航    程");
        HSSFCell row1Cell4 = row1.createCell(3);
        row1Cell4.setCellStyle(cellStyle);
        row1Cell4.setCellValue("萧山国际机场-广州白云机场");
        HSSFCell row1Cell5 = row1.createCell(4);
        row1Cell5.setCellStyle(cellStyle);
        row1Cell5.setCellValue("日    期");
        HSSFCell row1Cell6 = row1.createCell(5);
        row1Cell6.setCellStyle(cellStyle);
        row1Cell6.setCellValue("2017-04-25");

        // 第二行
        row++;
        HSSFRow row2 = sheet.createRow(row);
        HSSFCell row2Cell1 = row2.createCell(0);
        row2Cell1.setCellStyle(cellStyle);
        row2Cell1.setCellValue("航 班 号");
        HSSFCell row2Cell2 = row2.createCell(1);
        row2Cell2.setCellStyle(cellStyle);
        row2Cell2.setCellValue("MU2474");
        HSSFCell row2Cell3 = row2.createCell(2);
        row2Cell3.setCellStyle(cellStyle);
        row2Cell3.setCellValue("机    型");
        HSSFCell row2Cell4 = row2.createCell(3);
        row2Cell4.setCellStyle(cellStyle);
        row2Cell4.setCellValue("波音737");
        HSSFCell row2Cell5 = row2.createCell(4);
        row2Cell5.setCellStyle(cellStyle);
        row2Cell5.setCellValue("机    号");
        HSSFCell row2Cell6 = row2.createCell(5);
        row2Cell6.setCellStyle(cellStyle);
        row2Cell6.setCellValue("3");

        for (Map<String, Object> map : list) {
            row++;
            HSSFRow contentRow = sheet.createRow(row);
            HSSFCell cell1 = contentRow.createCell(0);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue("姓名");
            HSSFCell cell2 = contentRow.createCell(1);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(String.valueOf(map.get("name")));
            HSSFCell cell3 = contentRow.createCell(2);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue("客票号码");
            CellRangeAddress cra = new CellRangeAddress(row, row, 3, 5);
            sheet.addMergedRegion(cra);
            HSSFCell cell4 = contentRow.createCell(3);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(String.valueOf(map.get("ticketNo")));
        }

        // 最后第二行
        row++;
        HSSFRow rowPenult = sheet.createRow(row);
        HSSFCell penultCell1 = rowPenult.createCell(0);
        penultCell1.setCellStyle(cellStyle);
        penultCell1.setCellValue("合计人数");
        HSSFCell penultCell2 = rowPenult.createCell(1);
        penultCell2.setCellStyle(cellStyle);
        penultCell2.setCellValue(rowLength);
        HSSFCell penultCell3 = rowPenult.createCell(2);
        penultCell3.setCellStyle(cellStyle);
        penultCell3.setCellValue("服务人员");
        CellRangeAddress cra1 = new CellRangeAddress(row, row, 3, 5);
        sheet.addMergedRegion(cra1);
        HSSFCell penult3 = rowPenult.createCell(3);
        penult3.setCellStyle(cellStyle);
        penult3.setCellValue(new String());
        // 最后一行
        row++;
        HSSFRow rowLast = sheet.createRow(row);
        CellRangeAddress cra2 = new CellRangeAddress(row, row, 0, 1);
        sheet.addMergedRegion(cra2);
        CellRangeAddress cra3 = new CellRangeAddress(row, row, 2, 5);
        sheet.addMergedRegion(cra3);
        HSSFCell last0 = rowLast.createCell(0);
        last0.setCellStyle(cellStyle);
        last0.setCellValue("公司代表签字");
        HSSFCell last2 = rowLast.createCell(2);
        last2.setCellStyle(cellStyle);
        last2.setCellValue(new String());
    }

    /**
     * 创建常旅客标题栏
     */
    private static void createFrequentFlyerContent(List<Map<String,Object>> list,Map<String,String> titles){

        int row = 0; // 行号
        int rowLength = list.size();

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中

        // 第一行
        CellRangeAddress cra1 = new CellRangeAddress(row, row, 1, 6);
        sheet.addMergedRegion(cra1);
        HSSFRow row1 = sheet.createRow(row);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellStyle(cellStyle);
        row1Cell1.setCellValue("航空公司");
        HSSFCell row1Cell2 = row1.createCell(1);
        row1Cell2.setCellStyle(cellStyle);
        row1Cell2.setCellValue("XXX航空公司");
        HSSFCell row1Cell3 = row1.createCell(7);
        row1Cell3.setCellStyle(cellStyle);
        row1Cell3.setCellValue("日期");
        CellRangeAddress cra2 = new CellRangeAddress(row, row ,8, 9);
        sheet.addMergedRegion(cra2);
        HSSFCell row1Cell4 = row1.createCell(8);
        row1Cell4.setCellStyle(cellStyle);
        row1Cell4.setCellValue("2017-07-12");
        row++;
        // 第二行
        HSSFRow row2 = sheet.createRow(row);
        int ii = 0;
        for (Map.Entry<String, String> entry : titles.entrySet()) {
            HSSFCell textcell = row2.createCell(ii);
            textcell.setCellStyle(cellStyle);
            textcell.setCellValue(entry.getValue());
            ii++;
        }

        for (int i = 0; i < rowLength; i++) {
            row ++;
            HSSFRow rowContent = sheet.createRow(row);
            Map<String,Object> rowMap = (HashMap) list.get(i);
            int jj = 0;
            for (Map.Entry<String, String> entry : titles.entrySet()) {
                HSSFCell textcell = rowContent.createCell(jj);
                textcell.setCellStyle(cellStyle);
                textcell.setCellValue(String.valueOf(rowMap.get(entry.getKey())));
                jj++;
            }
        }
        row++;
        // 倒数第二行
        HSSFRow rowPenult = sheet.createRow(row);
        CellRangeAddress cr3 = new CellRangeAddress(row, row, 3, 9);
        sheet.addMergedRegion(cr3);
        HSSFCell penultCell1 = rowPenult.createCell(0);
        penultCell1.setCellStyle(cellStyle);
        penultCell1.setCellValue("合计人数");
        HSSFCell penultCell2 = rowPenult.createCell(1);
        penultCell2.setCellStyle(cellStyle);
        penultCell2.setCellValue(rowLength);
        HSSFCell penultCell3 = rowPenult.createCell(2);
        penultCell3.setCellStyle(cellStyle);
        penultCell3.setCellValue("服务人员");
        HSSFCell penultCell4 = rowPenult.createCell(3);
        penultCell4.setCellStyle(cellStyle);
        penultCell4.setCellValue(new String());
        // 倒数第一行
        row++;
        HSSFRow rowLast = sheet.createRow(row);
        CellRangeAddress cr4 = new CellRangeAddress(row, row, 0, 1);
        sheet.addMergedRegion(cr4);
        CellRangeAddress cr5 = new CellRangeAddress(row, row, 2, 9);
        sheet.addMergedRegion(cr5);
        HSSFCell lastCell1 = rowLast.createCell(0);
        lastCell1.setCellStyle(cellStyle);
        lastCell1.setCellValue("公司代表签字");
        HSSFCell lastCell2 = rowLast.createCell(2);
        lastCell2.setCellStyle(cellStyle);
        lastCell2.setCellValue(new String());
    }

    private static boolean isNum(Object data){
        return data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
    }

    private static boolean isInteger(Object data){
        return data.toString().matches("^[-+]?[\\d]*$");
    }

    public static void createExcel(HttpServletResponse response){
        //创建workbook
        workbook = new HSSFWorkbook();
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        HSSFSheet sheet = workbook.createSheet("sheet1");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            String fileName = "test.xls";// 文件名
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            HSSFRow row = sheet.createRow(0);    //创建第一行
            for(int i = 0;i < 10;i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue("测试数据"+i);
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        Object o1 = "2017-05-04";
//        Object o2 = "愛尚飛";
//        Object o3 = "99.2";
//        System.out.println(isNum(o1.toString()));
//        System.out.println(isNum(o2.toString()));
//        System.out.println(isNum(o3.toString()));

        JSONArray jsonMembers = new JSONArray();
        JSONObject member1 = new JSONObject();
        member1.put("loginname", "zhangfan");
        member1.put("password", "userpass");
        member1.put("email", "10371443@qq.com");
        member1.put("sign_date", "2007-06-12");
        jsonMembers.add(member1);
        JSONObject member2 = new JSONObject();
        member2.put("loginname", "zf");
        member2.put("password", "userpass");
        member2.put("email","8223939@qq.com");
        member2.put("sign_date", "2008-07-16");
        jsonMembers.add(member2);

        List<Map> list = JSON.parseObject(jsonMembers.toJSONString(), new TypeReference<List<Map>>() {});

    }

}
