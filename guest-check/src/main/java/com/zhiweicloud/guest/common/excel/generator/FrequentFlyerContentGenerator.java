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
 * 创建文件内容 -- 常旅客账单
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/25 18:08
 * @author tiecheng
 */
public class FrequentFlyerContentGenerator extends ContentGenerator {

    public FrequentFlyerContentGenerator(List<SheetContentPo> sheetContentPos) {
        super(sheetContentPos);
    }

    String[] names = {"姓名","航班号","机号","客票号码","航程","卡类别","舱位","卡号","有效期","随行"};


    @Override
    void createHeadRows(SheetContentPo sheetContentPo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        RowContentPo rowContentPo = sheetContentPo.getRowContentPos().get(0);
        // 第一行
        CellRangeAddress cra1 = new CellRangeAddress(row, row, 1, 6);
        sheet.addMergedRegion(cra1);
        HSSFRow row1 = sheet.createRow(row);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellStyle(cellStyle);
        row1Cell1.setCellValue("航空公司");
        HSSFCell row1Cell2 = row1.createCell(1);
        row1Cell2.setCellStyle(cellStyle);
        row1Cell2.setCellValue(rowContentPo.getCustomerName());
        HSSFCell row1Cell3 = row1.createCell(7);
        row1Cell3.setCellStyle(cellStyle);
        row1Cell3.setCellValue("日期");
        CellRangeAddress cra2 = new CellRangeAddress(row, row, 8, 9);
        sheet.addMergedRegion(cra2);
        HSSFCell row1Cell4 = row1.createCell(8);
        row1Cell4.setCellStyle(cellStyle);
        row1Cell4.setCellValue(rowContentPo.getFlightDate());
        row++;
        // 第二行
        HSSFRow row2 = sheet.createRow(row);
        int i = 0;
        for (String name : names) {
            HSSFCell textcell = row2.createCell(i);
            textcell.setCellStyle(cellStyle);
            textcell.setCellValue(name);
            i++;
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createContentRows(SheetContentPo sheetContentPo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        for (RowContentPo rowContentPo : sheetContentPo.getRowContentPos()) {
            row++;
            HSSFRow rowContent = sheet.createRow(row);
            HSSFCell cell1 = rowContent.createCell(0);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(rowContentPo.getName());
            HSSFCell cell2 = rowContent.createCell(1);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(rowContentPo.getFlightNo());
            HSSFCell cell3 = rowContent.createCell(2);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(rowContentPo.getPlanNo());
            HSSFCell cell4 = rowContent.createCell(3);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(rowContentPo.getTicketNo());
            HSSFCell cell5 = rowContent.createCell(4);
            cell5.setCellStyle(cellStyle);
            cell5.setCellValue(rowContentPo.getLeg());
            HSSFCell cell6 = rowContent.createCell(5);
            cell6.setCellStyle(cellStyle);
            cell6.setCellValue(rowContentPo.getCardType());
            HSSFCell cell7 = rowContent.createCell(6);
            cell7.setCellStyle(cellStyle);
            cell7.setCellValue(rowContentPo.getCabinNo());
            HSSFCell cell8 = rowContent.createCell(7);
            cell8.setCellStyle(cellStyle);
            cell8.setCellValue(rowContentPo.getCardNo());
            HSSFCell cell9 = rowContent.createCell(8);
            cell9.setCellStyle(cellStyle);
            if (rowContentPo.getExpireTime() == null) {
                cell9.setCellValue(new String());
            }else {
                cell9.setCellValue(simpleDateFormat.format(rowContentPo.getExpireTime()));
            }
            HSSFCell cell10 = rowContent.createCell(9);
            cell10.setCellStyle(cellStyle);
            if (rowContentPo.getAlongTotal() ==null) {
                cell10.setCellValue(new String());
            }else {
                cell10.setCellValue(String.valueOf(rowContentPo.getAlongTotal()));
            }
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createTailRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
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
        penultCell2.setCellValue(sheetContentPo.getRowContentPos().size());
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

    @Override
    void setWidthHelp(SheetContentPo sheetContentPo) {
        HSSFSheet sheet = sheetContentPo.getSheet();
        sheet.setColumnWidth(3,"1970-01-01 12:00:00".getBytes().length * 256);
        sheet.setColumnWidth(4,12 * 256);
        sheet.setColumnWidth(8,"1970-01-01 12:00:00".getBytes().length * 256);
    }

}
