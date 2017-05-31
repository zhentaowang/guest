package com.zhiweicloud.guest.generator.train;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.common.utils.StringUtils;
import com.zhiweicloud.guest.generator.SingleSheetGenerator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * TrainGenerator.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/26 10:23 
 * @author tiecheng
 */
public abstract class TrainGenerator extends SingleSheetGenerator {

    public TrainGenerator(JSONObject object) {
        super(object);
    }

    public TrainGenerator(JSONObject object, HttpServletResponse response) {
        super(object, response);
    }

    @Override
    public void create() {
        JSONObject column = object.getJSONObject("column");
        List rows = JSONArray.parseArray(object.getString("rows"));

        String[] key = new String[column.size()];
        String[] value = new String[column.size()];

        HSSFRow row = sheet.createRow(0);

        Map<String,String> map = column.toJavaObject(Map.class);

        int i=0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            key[i] = entry.getKey();
            value[i] = entry.getValue();
            HSSFCell row1Cell = row.createCell(i);

            if (StringUtils.isNotNone(entry.getValue())&& ExcelUtils.isNum(entry.getValue())) {
                if(ExcelUtils.isInteger(entry.getValue())){
                    row1Cell.setCellStyle(cellStyleMap.get("numCenter"));
                    row1Cell.setCellValue(Double.valueOf(entry.getValue()));
                }else {
                    row1Cell.setCellStyle(cellStyleMap.get("numCenter##"));
                    row1Cell.setCellValue(Double.valueOf(entry.getValue()));
                }
            }else {
                row1Cell.setCellStyle(cellStyleMap.get("center"));
                row1Cell.setCellValue(entry.getValue());
            }
            i++;
        }

        int m = 0;
        for (Object o : rows) {
            HSSFRow rowContent = sheet.createRow(m + 1);
            JSONObject object = (JSONObject) o;

            for (int j = 0; j < key.length; j++) {
                HSSFCell cell = rowContent.createCell(j);
                String va = object.getString(key[j]);
                if (StringUtils.isNotNone(va)&& ExcelUtils.isNum(va)) {
                    if(ExcelUtils.isInteger(va)){
                        cell.setCellStyle(cellStyleMap.get("numCenter"));
                        cell.setCellValue(Double.valueOf(va));
                    }else {
                        cell.setCellStyle(cellStyleMap.get("numCenter##"));
                        cell.setCellValue(Double.valueOf(va));
                    }
                }else {
                    cell.setCellStyle(cellStyleMap.get("center"));
                    cell.setCellValue(va);
                }
            }
            m++;
        }

        for (int j = 0; j < column.size() ; j++) {

            HSSFCell row1Cell = sheet.getRow(0).getCell(j);

            HSSFCell row2Cell = sheet.getRow(1).getCell(j);

            int length;
            switch (row2Cell.getCellType()){
                case HSSFCell.CELL_TYPE_STRING:
                    length = row1Cell.getStringCellValue().getBytes().length >= row2Cell.getStringCellValue().getBytes().length ? row1Cell.getStringCellValue().getBytes().length : row2Cell.getStringCellValue().getBytes().length;
                    sheet.setColumnWidth(j,length * 256);
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    length = row1Cell.getStringCellValue().getBytes().length >= String.valueOf(row2Cell.getNumericCellValue()).getBytes().length ? row1Cell.getStringCellValue().getBytes().length : String.valueOf(row2Cell.getNumericCellValue()).getBytes().length;
                    sheet.setColumnWidth(j, length* 256);
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }

        export();
    }

}
