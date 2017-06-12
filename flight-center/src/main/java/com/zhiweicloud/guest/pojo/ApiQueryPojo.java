package com.zhiweicloud.guest.pojo;

import java.util.Date;

/**
 * ApiQueryPojo.java
 * 接口访问情况查询的对象
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/12 14:11
 * @author tiecheng
 */
public class ApiQueryPojo {

    private Long customerId;

    private String customerName;

    private String sourceName;

    private String apiName;

    private String invokeState;

    private String invokeResult;

    private Date startDate;

    private Date endDate;

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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
