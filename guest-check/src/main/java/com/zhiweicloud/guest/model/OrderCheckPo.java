package com.zhiweicloud.guest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tc on 2017/5/17.
 */
public class OrderCheckPo implements Serializable{

    /**
     * 航班ID
     */
    private Long flightId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 服务人次
     */
    private Integer serverNum;

    /**
     * 主宾数量
     */
    private Integer guestNum;

    /**
     * 订单下乘客
     */
    private List<PassengerCheckPo> checkPassengerPos;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getServerNum() {
        return serverNum;
    }

    public void setServerNum(Integer serverNum) {
        this.serverNum = serverNum;
    }

    public Integer getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(Integer guestNum) {
        this.guestNum = guestNum;
    }

    public List<PassengerCheckPo> getCheckPassengerPos() {
        return checkPassengerPos;
    }

    public void setCheckPassengerPos(List<PassengerCheckPo> checkPassengerPos) {
        this.checkPassengerPos = checkPassengerPos;
    }

}
