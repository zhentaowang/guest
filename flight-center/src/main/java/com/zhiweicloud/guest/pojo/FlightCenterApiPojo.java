package com.zhiweicloud.guest.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * FlightCenterApiPojo.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/12 14:07
 * @author tiecheng
 */
public class FlightCenterApiPojo{

    private Long flightCenterApiId;

    private String apiName;

    private Long customerId;

    private String customerName;

    private String invokeState;

    private String invokeResult;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    public Long getFlightCenterApiId() {
        return flightCenterApiId;
    }

    public void setFlightCenterApiId(Long flightCenterApiId) {
        this.flightCenterApiId = flightCenterApiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInvokeState() {
        return invokeState;
    }

    public void setInvokeState(String invokeState) {
        this.invokeState = invokeState;
    }

    public String getInvokeResult() {
        return invokeResult;
    }

    public void setInvokeResult(String invokeResult) {
        this.invokeResult = invokeResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
