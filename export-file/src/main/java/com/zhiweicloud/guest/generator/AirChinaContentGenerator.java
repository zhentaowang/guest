package com.zhiweicloud.guest.generator;

import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.common.utils.StringUtils;
import com.zhiweicloud.guest.pojo.RowContentPo;
import com.zhiweicloud.guest.pojo.SheetContentPo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 创建文件内容 -- 国际航空账单
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/25 18:09
 * @author tiecheng
 */
public class AirChinaContentGenerator extends ContentGenerator {

    public AirChinaContentGenerator(List<SheetContentPo> sheetContentPos) {
        super(sheetContentPos);
    }

    String[] names = {"供应商名称","账单编号","账单日期","结算币种","汇率","业务单据编号","业务发生地点（航站）","业务单据日期","费用明细（供应商物料）","计量单位","结算数量","结算单价","单据行币种","单据行汇率","结算金额","航班日期","航班号","飞机号","起飞航站","到达航站"};

    @Override
    void createHeadRows(SheetContentPo sheetContentPo) {
        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        // 第一行
        HSSFRow row1 = sheet.createRow(row);
        CellRangeAddress cra1 = new CellRangeAddress(row,row,0,4);
        CellRangeAddress cra2 = new CellRangeAddress(row,row,5,14);
        CellRangeAddress cra3 = new CellRangeAddress(row,row,15,19);
        sheet.addMergedRegion(cra1);
        sheet.addMergedRegion(cra2);
        sheet.addMergedRegion(cra3);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellStyle(cellStyle);
        row1Cell1.setCellValue("账单汇总信息");
        HSSFCell row1Cell2 = row1.createCell(5);
        row1Cell2.setCellStyle(cellStyle);
        row1Cell2.setCellValue("账单费用明细信息");
        HSSFCell row1Cell3 = row1.createCell(15);
        row1Cell3.setCellStyle(cellStyle);
        row1Cell3.setCellValue("航班明细信息");
        // 第二行
        row++;
        HSSFRow row2 = sheet.createRow(row);
        row2.setRowStyle(cellStyle);
        for (int i = 0,length=names.length; i < length; i++) {
            HSSFCell cell = row2.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(names[i]);
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createContentRows(SheetContentPo sheetContentPo) {

        int row = sheetContentPo.getRow();
        HSSFSheet sheet = sheetContentPo.getSheet();
        for (RowContentPo rowContentPo : sheetContentPo.getRowContentPos()) {
            row++;
            HSSFRow rowContent = sheet.createRow(row);
            HSSFCell cell1 = rowContent.createCell(0);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue("云南空港百事特商务有限公司丽江营业部");
            HSSFCell cell2 = rowContent.createCell(1);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(new String());
            HSSFCell cell3 = rowContent.createCell(2);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(new String());
            HSSFCell cell4 = rowContent.createCell(3);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue("RMB");
            HSSFCell cell5 = rowContent.createCell(4);
            cell5.setCellStyle(cellStyle);
            cell5.setCellValue(1);
            HSSFCell cell6 = rowContent.createCell(5);
            cell6.setCellStyle(cellStyle);
            cell6.setCellValue(new String());
            HSSFCell cell7 = rowContent.createCell(6);
            cell7.setCellStyle(cellStyle);
            cell7.setCellValue(rowContentPo.getAirpotCode());
            HSSFCell cell8 = rowContent.createCell(7);
            cell8.setCellStyle(cellStyle);
            cell8.setCellValue(rowContentPo.getFlightDate());
            HSSFCell cell9 = rowContent.createCell(8);
            cell9.setCellStyle(cellStyle);
            cell9.setCellValue("头等舱旅客服务费");
            HSSFCell cell10 = rowContent.createCell(9);
            cell10.setCellStyle(cellStyle);
            cell10.setCellValue("人次");
            HSSFCell cell11 = rowContent.createCell(10);
            cell11.setCellStyle(cellStyle);
            if (rowContentPo.getServerPersonNum() == null) {
                cell11.setCellValue(new String());
            }else {
                cell11.setCellValue(rowContentPo.getServerPersonNum());
            }
            HSSFCell cell12 = rowContent.createCell(11);
            cell12.setCellStyle(numCellStyle);
            cell12.setCellValue(new String());
//            if (rowContentPo.getPrice() == null) {
//                cell12.setCellValue(new String());
//            }else {
//                cell12.setCellValue(rowContentPo.getPrice());
//            }
            HSSFCell cell13 = rowContent.createCell(12);
            cell13.setCellStyle(cellStyle);
            cell13.setCellValue(new String());
            HSSFCell cell14 = rowContent.createCell(13);
            cell14.setCellStyle(cellStyle);
            cell14.setCellValue(new String());
            HSSFCell cell15 = rowContent.createCell(14);
            cell15.setCellStyle(numCellStyle);
            cell15.setCellValue(new String());
//            if (rowContentPo.getAmout() == null) {
//                cell15.setCellValue(new String());
//            }else {
//                cell15.setCellValue(rowContentPo.getAmout());
//            }
            HSSFCell cell16 = rowContent.createCell(15);
            cell16.setCellStyle(cellStyle);
            cell16.setCellValue(rowContentPo.getFlightDate());
            HSSFCell cell17 = rowContent.createCell(16);
            cell17.setCellStyle(cellStyle);
            cell17.setCellValue(rowContentPo.getFlightNo());
            HSSFCell cell18 = rowContent.createCell(17);
            cell18.setCellStyle(numCellStyle);
            if (StringUtils.isNotNone(rowContentPo.getPlanNo()) && ExcelUtils.isInteger(rowContentPo.getPlanNo())) {
                cell18.setCellValue(Double.parseDouble(rowContentPo.getPlanNo()));
            } else {
                cell18.setCellValue(rowContentPo.getPlanNo());
            }
            HSSFCell cell19 = rowContent.createCell(18);
            cell19.setCellStyle(cellStyle);
            cell19.setCellValue(rowContentPo.getFlightDepcode());
            HSSFCell cell20 = rowContent.createCell(19);
            cell20.setCellStyle(cellStyle);
            cell20.setCellValue(rowContentPo.getFlightArrcode());
        }
        sheetContentPo.setRow(row);
    }

    @Override
    void createTailRows(SheetContentPo sheetContentPo) {

    }

    @Override
    void setWidthHelp(SheetContentPo sheetContentPo) {
        HSSFSheet sheet = sheetContentPo.getSheet();
        sheet.setColumnWidth(0,"云南空港百事特商务有限公司丽江".getBytes().length * 256);
        sheet.setColumnWidth(6,21 * 256);
        sheet.setColumnWidth(7,18 * 256);
        sheet.setColumnWidth(8,24 * 256);
        sheet.setColumnWidth(15,"业务单据日期".getBytes().length * 256);
    }

}
