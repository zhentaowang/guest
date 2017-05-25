package com.zhiweicloud.guest.common;

import java.util.List;

/**
 * Created by tc on 2017/5/15.
 */
public class Pagination<T> {

    Page page;

    List<T> t;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getT() {
        return t;
    }

    public void setT(List<T> t) {
        this.t = t;
    }

}
