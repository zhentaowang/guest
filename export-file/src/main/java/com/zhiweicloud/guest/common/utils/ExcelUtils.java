package com.zhiweicloud.guest.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zhiweicloud.guest.generator.ContentGenerator;
import com.zhiweicloud.guest.pojo.SheetContentPo;
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

    /**
     * 导出Excel
     * 为项目提供了四种账单的导出格式 定制化需求
     *
     * @param contentGenerator 创建文件流对象
     * @param fileName          文件名字(带后缀)
     * @param sheetName         工作表名字
     * @param response          响应
     */
    public static void exportExcel(ContentGenerator contentGenerator, String fileName, String sheetName, HttpServletResponse response) {
        workbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle= workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        contentGenerator.setCellStyle(cellStyle);
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            contentGenerator.setWorkbook(workbook);
            List<SheetContentPo> sheetContentPos = contentGenerator.getSheetContentPos();
            if (sheetContentPos.size() > 1) {
                int i =0;
                for (SheetContentPo sheetContentPo : sheetContentPos) {
                    sheet = workbook.createSheet(sheetName + "_" + i);
                    sheetContentPo.setSheet(sheet);
                    i++;
                }
                contentGenerator.createSheets();
            } else {
                sheet = workbook.createSheet(sheetName);
                sheetContentPos.get(0).setSheet(sheet);
                contentGenerator.createSheets();
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
