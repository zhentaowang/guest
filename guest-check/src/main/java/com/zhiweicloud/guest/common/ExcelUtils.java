package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Excel导出工具类
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * @url https://www.zhiweicloud.com
 * @date 2017/3/17 13:42
 * @author tiecheng
 */
public class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * 默认的导出路径
     */
    private static final String DEFAULT_PATH = "C:\\excel";

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
     * 导出Excel 目前项目中使用的
     * @param fileName
     * @param sheetName
     * @param rows
     * @param titleMap
     */
    public static void export(String fileName, String sheetName, List rows, Map<String, String> titleMap) {
        initHSSFWorkbook(sheetName);
        createFirstRow(titleMap);
        createContentRow(rows, titleMap);
        out(getFilePath(fileName));
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
     * @param sheetName 工作表名
     */
    private static void initHSSFWorkbook(String sheetName){
        workbook = new HSSFWorkbook();
        sheetName = sheetName == null ? getSheetName() : sheetName;
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * 创建第一列
     * @param titleMap 标题栏map
     */
    private static void createFirstRow(Map<String, String> titleMap) {
        HSSFRow row = sheet.createRow(TITLE_START_POSITION);
//        int i = 0;
//        for (String s : titleMap.keySet()) {
//            HSSFCell textcell = row.createCell(i);
//            textcell.setCellValue(s);
//            i++;
//        }
        // 中间版本用过
//        for (int i = 0; i < titleMap.size(); i++) {
//            HSSFCell textcell = row.createCell(i);
//            textcell.setCellValue(titleMap.get(i));
//        }
        int i = 0;
        for (Map.Entry<String, String> entry : titleMap.entrySet()) {
            HSSFCell textcell = row.createCell(i);
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

    private static void createContentRow(List dataList, Map<String, String> titleMap) {
        for (int i = 0,size = dataList.size(); i < size; i++) {
            HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
            Map<String,Object> rowMap = (HashMap) dataList.get(i);
            int j = 0;
            for (Map.Entry<String, String> entry : titleMap.entrySet()) {
                HSSFCell textcell = row.createCell(j);
                String value = (String) rowMap.get(entry.getKey());
                textcell.setCellValue(value);
                j++;
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

    private static void out(String filePath){
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getFilePath(String fileName){
        return fileName == null ? (DEFAULT_PATH + "\\" + getDefaultFilePath()): checkFileName(fileName);
    }

    private static String checkFileName(String fileName){
        if (fileName.contains(".xls")|| fileName.contains(".xlsx")) {
            return DEFAULT_PATH + "\\" + fileName;
        }else {
            return DEFAULT_PATH + "\\" + fileName + ".xls";
        }
    }

    private static String getDefaultFilePath(){
        return DEFAULT_PATH + "\\" + getFileName();
    }

    private static String getFileName(){
        UUID uuid = UUID.randomUUID();
        return uuid + ".xls";
    }

    private static String getSheetName(){
        return String.valueOf(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"data\": {\n" +
                "    \"total\": 34,\n" +
                "    \"column\": [\n" +
                "      {\n" +
                "        \"displayName\": \"订单号\",\n" +
                "        \"columnName\": \"orderNo\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"航班日期\",\n" +
                "        \"columnName\": \"flightDate\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"贵宾厅人次\",\n" +
                "        \"columnName\": \"vipPersonNum\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"贵宾厅费用\",\n" +
                "        \"columnName\": \"vipPrice\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"陪同人次\",\n" +
                "        \"columnName\": \"accompanyPersonNum\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"总数\",\n" +
                "        \"columnName\": \"totalAmount\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"displayName\": \"陪同费用\",\n" +
                "        \"columnName\": \"accompanyPrice\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"rows\": [\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000076\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489363200000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"333\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000077\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489363200000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"222\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000078\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489363200000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"222\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000079\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489363200000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"222\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000080\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000081\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000082\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"33\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000083\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"222\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000084\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000085\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000086\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000087\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000088\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"20\",\n" +
                "        \"accompanyPersonNum\": \"30\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000089\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000090\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"10\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000091\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"10\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000092\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000093\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000094\",\n" +
                "        \"accompanyPrice\": \"114\",\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": \"112\",\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000095\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"11\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": 13794,\n" +
                "        \"orderNo\": \"LJG000096\",\n" +
                "        \"accompanyPrice\": \"114\",\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": \"114\",\n" +
                "        \"vipPersonNum\": \"55\",\n" +
                "        \"accompanyPersonNum\": \"66\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000097\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"2000\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000098\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"333\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000099\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"333\",\n" +
                "        \"accompanyPersonNum\": \"444\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000100\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"333\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000101\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"444\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000102\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"555\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000103\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489449600000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000104\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489536000000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000105\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489622400000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000106\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489622400000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"11\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000107\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489622400000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000108\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489622400000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": null,\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"totalAmount\": null,\n" +
                "        \"orderNo\": \"LJG000109\",\n" +
                "        \"accompanyPrice\": null,\n" +
                "        \"flightDate\": 1489622400000,\n" +
                "        \"vipPrice\": null,\n" +
                "        \"vipPersonNum\": \"333\",\n" +
                "        \"accompanyPersonNum\": null\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
//
//        JSONObject jsonObject = JSON.parseObject(json, OrderedField);
//        JSONObject data = jsonObject.getJSONObject("data");
//
//
//        // 获得数据集合
//        List<Map<String, Object>> dataList = new ArrayList<>();
//        JSONArray rows = data.getJSONArray("rows");
//
//        rows.forEach(x->{
//            String row = JSONObject.toJSONString(x, SerializerFeature.WriteMapNullValue);
//            Map<String,Object> map = JSON.parseObject(row, LinkedHashMap.class,Feature.OrderedField);
//            dataList.add(map);
//        });
//
//        // 标题的顺序集合
//        List<String> titleList = new ArrayList<>();
//        JSONObject row = rows.getJSONObject(0);
//        for (String s : row.keySet()) {
//            titleList.add(s);
//        }
//
//        // 获得标题集合
//        Map<String, String> titleMap = new HashMap<>();
//        JSONArray column = data.getJSONArray("column");
//        column.forEach(x->{
//            String row1 = JSONObject.toJSONString(x, SerializerFeature.WriteMapNullValue);
//            Map<String,String> map = JSON.parseObject(row1, LinkedHashMap.class,Feature.OrderedField);
//            String[] strArray = new String[2];
//            int i = 0;
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                strArray[i] = entry.getValue();
//                i++;
//            }
//            titleMap.put(strArray[1],strArray[0] );
//        });
//
//        export("C:\\excel\\test.xls","demo",rows,titleList,titleMap);
//
////        export("demo",rows,titleList);
        Object o = null;
        String s = (String) o;
        System.out.println(s + "測試");
    }


}
