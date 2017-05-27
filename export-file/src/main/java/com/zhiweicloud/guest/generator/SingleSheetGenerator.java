package com.zhiweicloud.guest.generator.train;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * SingleSheetGenerator.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/24 18:04
 * @author tiecheng
 */
public abstract class SingleSheetGenerator {

    protected String fileName;

    protected HttpServletResponse response;

    protected JSONObject object;

    protected HSSFWorkbook workbook;

    protected HSSFSheet sheet;

    protected Map<String, HSSFCellStyle> cellStyleMap = new HashMap<>();

    protected void init(){
        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle1.setFont(font);
        cellStyleMap.put("boldCenter",cellStyle1);

        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyleMap.put("center",cellStyle2);

        HSSFDataFormat dataFormat = workbook.createDataFormat();

        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle3.setDataFormat(dataFormat.getFormat("#"));
        cellStyleMap.put("numCenter", cellStyle3);

        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
        cellStyle4.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle4.setDataFormat(dataFormat.getFormat("#.##"));
        cellStyleMap.put("numCenter##", cellStyle4);
    }

    protected void initSheet(String sheetName){
        this.sheet = workbook.createSheet(sheetName);
    }

    protected void initSheet(){
        this.sheet = workbook.createSheet();
    }

    protected void export(){
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(this.fileName, "UTF-8"));
            this.workbook.write(out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SingleSheetGenerator(JSONObject object) {
        init();
        this.workbook = new HSSFWorkbook();
        this.object = object;
    }

    public SingleSheetGenerator(JSONObject object,HttpServletResponse response) {
        init();
        this.response = response;
        this.workbook = new HSSFWorkbook();
        this.object = object;
    }

    public abstract void create();

    public JSONObject getObject() {
        return object;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
