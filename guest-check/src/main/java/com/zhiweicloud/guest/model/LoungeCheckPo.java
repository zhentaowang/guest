package com.zhiweicloud.guest.model;

import java.util.Date;
import java.util.List;

/**
 * 休息室账单 对象
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/27 20:10
 * @author tiecheng
 */
public class LoungeCheckPo {

    private Long flightId;

    private Date flightDate;

    private String customerName;

    private List<CheckPassengerPo> checkPassengerPos;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CheckPassengerPo> getCheckPassengerPos() {
        return checkPassengerPos;
    }

    public void setCheckPassengerPos(List<CheckPassengerPo> checkPassengerPos) {
        this.checkPassengerPos = checkPassengerPos;
    }

}
