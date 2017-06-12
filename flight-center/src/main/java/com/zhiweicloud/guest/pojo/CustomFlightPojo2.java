package com.zhiweicloud.guest.pojo;

import com.zhiweicloud.guest.po.FlightPo;

import java.util.List;

/**
 * Created by tc on 2017/6/6.
 */
public class CustomFlightPojo2 {

    private Long customerId;

    private String customUrl;

    private List<FlightPo> flightPos;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public List<FlightPo> getFlightPos() {
        return flightPos;
    }

    public void setFlightPos(List<FlightPo> flightPos) {
        this.flightPos = flightPos;
    }

}
