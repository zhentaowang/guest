package com.zhiweicloud.guest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 休息室账单 对象
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/27 20:10
 * @author tiecheng
 */
public class LoungeCheckPo implements Serializable {

    /**
     * 航班ID
     */
    private Long flightId;

    /**
     * 航班日期
     */
    private Date flightDate;

    /**
     * 客户名字
     */
    private String customerName;

    /**
     * 航班下订单
     */
    private List<OrderCheckPo> orderCheckPos;

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

    public List<OrderCheckPo> getOrderCheckPos() {
        return orderCheckPos;
    }

    public void setOrderCheckPos(List<OrderCheckPo> orderCheckPos) {
        this.orderCheckPos = orderCheckPos;
    }

}
