package com.zhiweicloud.guest.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by tc on 2017/6/13.
 */
public class FlightPushPojo {

    private Long flightPushId;

    private String customerName;

    private Long customerId;

    private String invokeResult;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    public Long getFlightPushId() {
        return flightPushId;
    }

    public void setFlightPushId(Long flightPushId) {
        this.flightPushId = flightPushId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
