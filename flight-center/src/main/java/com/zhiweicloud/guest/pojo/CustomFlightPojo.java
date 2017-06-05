package com.zhiweicloud.guest.pojo;

import com.zhiweicloud.guest.po.FlightPo;

import java.util.List;

/**
 * Created by tc on 2017/6/1.
 */
public class CustomFlightPojo {

    private Long flightId;

    private FlightPo flightPo;

    private List<String> customUrls;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public FlightPo getFlightPo() {
        return flightPo;
    }

    public void setFlightPo(FlightPo flightPo) {
        this.flightPo = flightPo;
    }

    public List<String> getCustomUrls() {
        return customUrls;
    }

    public void setCustomUrls(List<String> customUrls) {
        this.customUrls = customUrls;
    }

}
