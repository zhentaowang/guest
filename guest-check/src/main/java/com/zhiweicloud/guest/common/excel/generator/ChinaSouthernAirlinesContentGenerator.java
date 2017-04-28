package com.zhiweicloud.guest.common.excel.generator;

import com.zhiweicloud.guest.common.excel.po.RowContentPo;
import com.zhiweicloud.guest.common.excel.po.SheetContentPo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * 创建文件内容 -- 南方航空账单
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/25 18:11
 * @author tiecheng
 */
public class ChinaSouthernAirlinesContentGenerator extends ContentGenerator {

    public ChinaSouthernAirlinesContentGenerator(List<SheetContentPo> sheetContentPos) {
        super(sheetContentPos);
    }

    String[] names = {"供应商","日期","航班号","飞机号","航段","发生机场","费用名称","数量","单价","手续费","金额","币种","汇率"};

    @Override
    void createHeadRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        // 第一行
        HSSFRow row1 = sheet.createRow(row);
        for (int i = 0,length = names.length; i < length; i++) {
            HSSFCell row1Cell = row1.createCell(i);
            row1Cell.setCellStyle(cellStyle);
            row1Cell.setCellValue(names[i]);
        }
    }

    @Override
    void createContentRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        for (RowContentPo rowContentPo : sheetContentPo.getRowContentPos()) {
            row++;
            HSSFRow rowContent = sheet.createRow(row);
            HSSFCell rowCell1 = rowContent.createCell(0);
            rowCell1.setCellStyle(cellStyle);
            rowCell1.setCellValue("云南空港百事特商务有限公司丽江营业部");
            HSSFCell rowCell2 = rowContent.createCell(1);
            rowCell2.setCellStyle(cellStyle);
            rowCell2.setCellValue(rowContentPo.getFlightDate());
            HSSFCell rowCell3 = rowContent.createCell(2);
            rowCell3.setCellStyle(cellStyle);
            rowCell3.setCellValue(rowContentPo.getFlightNo());
            HSSFCell rowCell4 = rowContent.createCell(3);
            rowCell4.setCellStyle(cellStyle);
            rowCell4.setCellValue(rowContentPo.getPlanNo());
            HSSFCell rowCell5 = rowContent.createCell(4);
            rowCell5.setCellStyle(cellStyle);
            rowCell5.setCellValue(rowContentPo.getLeg());
            HSSFCell rowCell6 = rowContent.createCell(5);
            rowCell6.setCellStyle(cellStyle);
            rowCell6.setCellValue(rowContentPo.getAirpotCode());
            HSSFCell rowCell7 = rowContent.createCell(6);
            rowCell7.setCellStyle(cellStyle);
            rowCell7.setCellValue("头等舱休息室费用");
            HSSFCell rowCell8 = rowContent.createCell(7);
            rowCell8.setCellStyle(cellStyle);
            if (rowContentPo.getServerPersonNum() == null) {
                rowCell8.setCellValue(new String());
            }else {
                rowCell8.setCellValue(rowContentPo.getServerPersonNum());
            }
            HSSFCell rowCell9 = rowContent.createCell(8);
            rowCell9.setCellStyle(cellStyle);
            if (rowContentPo.getPrice() == null) {
                rowCell9.setCellValue(new String());
            }else {
                rowCell9.setCellValue(rowContentPo.getPrice());
            }
            HSSFCell rowCell10 = rowContent.createCell(9);
            rowCell10.setCellStyle(cellStyle);
            rowCell10.setCellValue(new String());
            HSSFCell rowCell11 = rowContent.createCell(10);
            rowCell11.setCellStyle(cellStyle);
            if (rowContentPo.getAmout() == null) {
                rowCell11.setCellValue(new String());
            }else {
                rowCell11.setCellValue(rowContentPo.getAmout());
            }
            HSSFCell rowCell12 = rowContent.createCell(11);
            rowCell12.setCellStyle(cellStyle);
            rowCell12.setCellValue("RMB");
            HSSFCell rowCell13 = rowContent.createCell(12);
            rowCell3.setCellStyle(cellStyle);
            rowCell13.setCellValue("1");
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createTailRows(SheetContentPo sheetContentPo) {

    }

}
