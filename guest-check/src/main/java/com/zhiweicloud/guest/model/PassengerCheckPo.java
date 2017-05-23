package com.zhiweicloud.guest.model;

import java.util.Date;

/**
 * 账单乘客 对象
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/27 20:11
 * @author tiecheng
 */
public class PassengerCheckPo {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 座位号
     */
    private String sitNo;

    /**
     * 名字
     */
    private String name;

    /**
     * 客票号码
     */
    private String ticketNo;

    /**
     * 卡类别
     */
    private String cardType;

    /**
     * 卡号码
     */
    private String cardNo;

    /**
     * 舱位
     */
    private String cabinNo;

    /**
     * 机号
     */
    private String planNo;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 航段（航程）
     */
    private String leg;

    /**
     * 机型 目前没有
     */
    private String flightType;

    /**
     * 有效期
     */
    private Date expireTime;

    /**
     * 随从人数
     */
    private Integer alongTotal;

    /**
     * 发生机场
     */
    private String airpotCode;

    /**
     * 起飞航站
     */
    private String flightDepcode;

    /**
     * 达到航站
     */
    private String flightArrcode;

    /**
     * 客户类型
     */
    private Short passengerType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSitNo() {
        return sitNo;
    }

    public void setSitNo(String sitNo) {
        this.sitNo = sitNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getAlongTotal() {
        return alongTotal;
    }

    public void setAlongTotal(Integer alongTotal) {
        this.alongTotal = alongTotal;
    }

    public String getAirpotCode() {
        return airpotCode;
    }

    public void setAirpotCode(String airpotCode) {
        this.airpotCode = airpotCode;
    }

    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    public Short getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(Short passengerType) {
        this.passengerType = passengerType;
    }

}
