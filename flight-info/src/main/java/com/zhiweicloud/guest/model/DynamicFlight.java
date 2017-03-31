package com.zhiweicloud.guest.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * DynamicFlight.java
 * 航班动态信息json串映射实体类
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/28 16:30
 * @author tiecheng
 */
public class DynamicFlight extends FlightMatch {

    @JSONField(name = "Fcategory")
    private String fcategory;

    @JSONField(name = "FD_ID")
    private String fdId;

    @JSONField(name = "StopFlag")
    private Short stopFlag;

    @JSONField(name = "ShareFlag")
    private Short shareFlag;

    @Override
    public String getFcategory() {
        return fcategory;
    }

    @Override
    public void setFcategory(String fcategory) {
        this.fcategory = fcategory;
    }

    @Override
    public String getFdId() {
        return fdId;
    }

    @Override
    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    @Override
    public Short getStopFlag() {
        return stopFlag;
    }

    @Override
    public void setStopFlag(Short stopFlag) {
        this.stopFlag = stopFlag;
    }

    @Override
    public Short getShareFlag() {
        return shareFlag;
    }

    @Override
    public void setShareFlag(Short shareFlag) {
        this.shareFlag = shareFlag;
    }

}
