package com.zhiweicloud.guest.common;

import java.util.List;

/**
 * Created by tc on 2017/5/15.
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
