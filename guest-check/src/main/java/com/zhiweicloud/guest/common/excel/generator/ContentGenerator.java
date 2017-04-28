package com.zhiweicloud.guest.common.excel.generator;

import com.zhiweicloud.guest.common.excel.po.SheetContentPo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * 内容生成器
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * <p>
 * 2017/4/25 17:51
 *
 * @author tiecheng
 */
public abstract class ContentGenerator {

    /**
     * 生成的文件对象
     */
    HSSFWorkbook workbook;

    /**
     * 工作簿对象信息
     */
    List<SheetContentPo> sheetContentPos;

    /**
     * 全局单元格样式
     */
    HSSFCellStyle cellStyle;

    public ContentGenerator() {

    }

    public ContentGenerator(List<SheetContentPo> sheetContentPos) {
        this.sheetContentPos = sheetContentPos;
    }

    /**
     * 创建头部行
     */
    abstract void createHeadRows(SheetContentPo excelAssistPo);

    /**
     * 创建内容行
     */
    abstract void createContentRows(SheetContentPo excelAssistPo);

    /**
     * 创建尾部行
     */
    abstract void createTailRows(SheetContentPo excelAssistPo);

    /**
     * 创建文件 模版方法
     */
    public void createSheets() {
        for (SheetContentPo sheetContentPo : sheetContentPos) {
            createHeadRows(sheetContentPo);
            createContentRows(sheetContentPo);
            createTailRows(sheetContentPo);
        }
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public List<SheetContentPo> getSheetContentPos() {
        return sheetContentPos;
    }

    public void setSheetContentPos(List<SheetContentPo> sheetContentPos) {
        this.sheetContentPos = sheetContentPos;
    }

    public HSSFCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(HSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

}
