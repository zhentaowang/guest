package com.zhiweicloud.guest.pojo;

import java.util.Date;
import java.util.Map;

/**
 * Created by tc on 2017/6/9.
 */
public class CustomerDetailPojo {

    private Long customerId;

    private String customerName;

    private String customUrl;

    private Long total;

    private String sysCode;

    private Date createTime;

    private Map<String,Integer> requestTimes;

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

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, Integer> getRequestTimes() {
        return requestTimes;
    }

    public void setRequestTimes(Map<String, Integer> requestTimes) {
        this.requestTimes = requestTimes;
    }

}
