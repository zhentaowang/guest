package com.zhiweicloud.guest.common.excel.generator;

import com.zhiweicloud.guest.common.excel.po.RowContentPo;
import com.zhiweicloud.guest.common.excel.po.SheetContentPo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 创建文件内容 -- 头等舱账单
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/25 18:07
 * @author tiecheng
 */
public class FirstClassContentGenerator extends ContentGenerator {

    public FirstClassContentGenerator(List<SheetContentPo> sheetContentPos) {
        super(sheetContentPos);
    }

    @Override
    void createHeadRows(SheetContentPo sheetContentPo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        RowContentPo rowContentPo = sheetContentPo.getRowContentPos().get(0);
        // 第一行
        HSSFRow row1 = sheet.createRow(row);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellStyle(cellStyle);
        row1Cell1.setCellValue("航空公司");
        HSSFCell row1Cell2 = row1.createCell(1);
        row1Cell2.setCellStyle(cellStyle);
        row1Cell2.setCellValue(rowContentPo.getCustomerName());
        HSSFCell row1Cell3 = row1.createCell(2);
        row1Cell3.setCellStyle(cellStyle);
        row1Cell3.setCellValue("航    程");
        HSSFCell row1Cell4 = row1.createCell(3);
        row1Cell4.setCellStyle(cellStyle);
        row1Cell4.setCellValue(rowContentPo.getLeg());
        HSSFCell row1Cell5 = row1.createCell(4);
        row1Cell5.setCellStyle(cellStyle);
        row1Cell5.setCellValue("日    期");
        HSSFCell row1Cell6 = row1.createCell(5);
        row1Cell6.setCellStyle(cellStyle);
        row1Cell6.setCellValue(rowContentPo.getFlightDate());
        // 第二行
        row++;
        HSSFRow row2 = sheet.createRow(row);
        row2.setRowStyle(cellStyle);
        HSSFCell row2Cell1 = row2.createCell(0);
        row2Cell1.setCellValue("航 班 号");
        HSSFCell row2Cell2 = row2.createCell(1);
        row2Cell2.setCellValue(rowContentPo.getFlightNo());
        HSSFCell row2Cell3 = row2.createCell(2);
        row2Cell3.setCellValue("机    型");
        HSSFCell row2Cell4 = row2.createCell(3);
        row2Cell4.setCellValue(new String());
        HSSFCell row2Cell5 = row2.createCell(4);
        row2Cell5.setCellValue("机    号");
        HSSFCell row2Cell6 = row2.createCell(5);
        row2Cell6.setCellValue(rowContentPo.getPlanNo());
        // 把当前行号设置回去
        sheetContentPo.setRow(row);
    }

    @Override
    void createContentRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        for (RowContentPo rowContentPo : sheetContentPo.getRowContentPos()) {
            row++;
            HSSFRow contentRow = sheet.createRow(row);
            HSSFCell cell1 = contentRow.createCell(0);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue("姓名");
            HSSFCell cell2 = contentRow.createCell(1);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(rowContentPo.getName());
            HSSFCell cell3 = contentRow.createCell(2);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue("客票号码");
            CellRangeAddress cra = new CellRangeAddress(row, row, 3, 5);
            sheet.addMergedRegion(cra);
            HSSFCell cell4 = contentRow.createCell(3);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(rowContentPo.getTicketNo());
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createTailRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        row++;
        HSSFRow rowPenult = sheet.createRow(row);
        HSSFCell penultCell1 = rowPenult.createCell(0);
        penultCell1.setCellStyle(cellStyle);
        penultCell1.setCellValue("合计人数");
        HSSFCell penultCell2 = rowPenult.createCell(1);
        penultCell2.setCellStyle(cellStyle);
        penultCell2.setCellValue(sheetContentPo.getRowContentPos().size());
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

}
