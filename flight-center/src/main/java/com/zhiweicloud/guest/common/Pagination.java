package com.zhiweicloud.guest.common;

import java.util.List;

/**
 * Pagination.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/13 10:59
 * @author tiecheng
 */
public class Pagination<T> {

    int total;

    List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
