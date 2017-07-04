package com.zhiweicloud.guest.pojo;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * 休息室账单 一个工作簿对应实体
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * <p>
 * 2017/4/27 15:52
 *
 * @author tiecheng
 */
public class SheetContentPo {

    /**
     * 初始化行数
     */
    private int row = 0;

    /**
     * 工作簿对象
     */
    private HSSFSheet sheet;

    /**
     * 行数据
     */
    private List<RowContentPo> rowContentPos;

    /**
     * 显示人数
     */
    public int total;

    /**
     * 服务人员
     */
    private String employeeName;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    public List<RowContentPo> getRowContentPos() {
        return rowContentPos;
    }

    public void setRowContentPos(List<RowContentPo> rowContentPos) {
        this.rowContentPos = rowContentPos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

}
