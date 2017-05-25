package com.zhiweicloud.guest.common.model;

/**
 * 航班中心结果对象
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/13 13:29
 * @author tiecheng
 */
public class FlightCenterResult<DATA> extends BaseResult {

    private DATA data;

    public FlightCenterResult() {
    }

    public FlightCenterResult(DATA t) {
        this.data = t;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

}
