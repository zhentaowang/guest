package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        createContentRowForVipCloud(rows, titleMap);
//        createContentRow(rows, titleMap);
        out(getFilePath(fileName));
    }

    public static OutputStream dataToStream(String fileName, String sheetName, List rows, Map<String, String> titleMap){
        initHSSFWorkbook(sheetName);
        autoColumnSize(titleMap.size());
        createFirstRow(rows);
        createContentRowForVipCloud(rows, titleMap);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        workbook
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
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setFont(font);
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
    private static void createContentRowForVipCloud(List dataList, Map<String, String> titleMap) {
        HSSFCellStyle contextstyle = workbook.createCellStyle();
        HSSFDataFormat hssfDataFormat = workbook.createDataFormat();
        for (int i = 0, size = dataList.size(); i < size; i++) {
            HSSFRow row = sheet.createRow(CONTENT_START_POSITION + i);
            Map<String, Object> rowMap = (HashMap) dataList.get(i);
            int j = 0;
            for (Map.Entry<String, String> entry : titleMap.entrySet()) {
                HSSFCell textcell = row.createCell(j);
                Object value = rowMap.get(entry.getKey());
                if (value == null || "".equals(value)) {
                    if (i == (size - 1) && j == 0) {
                        textcell.setCellValue("合计");
                    }else {
                        textcell.setCellType(CellType.BLANK);
                    }
                } else {
                    if (isNum(value)) {
                        if (isInteger(value)) {
                            contextstyle.setDataFormat(hssfDataFormat.getBuiltinFormat("#,#0"));
                        } else {
                            contextstyle.setDataFormat(hssfDataFormat.getBuiltinFormat("#,##0.00"));
                        }
//                            textcell.setCellType(CellType.NUMERIC);
                        textcell.setCellStyle(contextstyle);
                        textcell.setCellValue(Double.parseDouble(String.valueOf(value)));
                    } else {
                        textcell.setCellValue(value.toString());
                    }
                }
                j++;
            }
        }
    }

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
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }

    private static boolean isNum(Object data){
        return data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
    }

    private static boolean isInteger(Object data){
        return data.toString().matches("^[-+]?[\\d]*$");
    }

    public static void main(String[] args) {
        Object o1 = "2017-05-04";
        Object o2 = "愛尚飛";
        Object o3 = "99.2";
        System.out.println(isNum(o1.toString()));
        System.out.println(isNum(o2.toString()));
        System.out.println(isNum(o3.toString()));
    }

}
