package com.zhiweicloud.guest.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by tc on 2017/5/18.
 */
public class TickPassenger {

    private String passengerName;

    private String depAirportCode;

    private String arrAirportCode;

    private String ticketNo;

    private String currencyType;

    private String exchangeInfo;

    private Double fare;

    private String fareCompute;

    private String followTicketNo;

    private Date infantBirthDay;

    private String ISI;

    private String originalString;

    private String originalIssue;

    private String payMethod;

    private String passengerType;

    private Boolean receiptPrinted;

    /**
     * 获取签注信息
     */
    private String signingInfo;

    /**
     * 税款金额
     */
    private Double tax;

    /**
     * 客票总金额
     */
    private Double totalAmount;

    /**
     * 旅游代码
     */
    private String tourCode;

    /**
     * 无人陪伴儿童年龄
     */
    private Integer unaccompaniedChildAge;

    private String airlineCode;

    private Integer baggageWeight;

    private String boardNo;

    private String cabinNo;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depScheduledDate;

    private String flightNo;

    /**
     * 旅客订座记录编号
     */
    private String pnrNo;

    /**
     * 适用运价（如YB为Y舱B类运价）
     */
    private String rate;

    private String legState;

    private String ticketState;

    private String legType;

    private String baggagePiece;

    private String remark;

    private String eTicketType;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getExchangeInfo() {
        return exchangeInfo;
    }

    public void setExchangeInfo(String exchangeInfo) {
        this.exchangeInfo = exchangeInfo;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getFareCompute() {
        return fareCompute;
    }

    public void setFareCompute(String fareCompute) {
        this.fareCompute = fareCompute;
    }

    public String getFollowTicketNo() {
        return followTicketNo;
    }

    public void setFollowTicketNo(String followTicketNo) {
        this.followTicketNo = followTicketNo;
    }

    public Date getInfantBirthDay() {
        return infantBirthDay;
    }

    public void setInfantBirthDay(Date infantBirthDay) {
        this.infantBirthDay = infantBirthDay;
    }

    public String getISI() {
        return ISI;
    }

    public void setISI(String ISI) {
        this.ISI = ISI;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getOriginalIssue() {
        return originalIssue;
    }

    public void setOriginalIssue(String originalIssue) {
        this.originalIssue = originalIssue;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public Boolean getReceiptPrinted() {
        return receiptPrinted;
    }

    public void setReceiptPrinted(Boolean receiptPrinted) {
        this.receiptPrinted = receiptPrinted;
    }

    public String getSigningInfo() {
        return signingInfo;
    }

    public void setSigningInfo(String signingInfo) {
        this.signingInfo = signingInfo;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public Integer getUnaccompaniedChildAge() {
        return unaccompaniedChildAge;
    }

    public void setUnaccompaniedChildAge(Integer unaccompaniedChildAge) {
        this.unaccompaniedChildAge = unaccompaniedChildAge;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Integer getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(Integer baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo;
    }

    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLegState() {
        return legState;
    }

    public void setLegState(String legState) {
        this.legState = legState;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public String getLegType() {
        return legType;
    }

    public void setLegType(String legType) {
        this.legType = legType;
    }

    public String getBaggagePiece() {
        return baggagePiece;
    }

    public void setBaggagePiece(String baggagePiece) {
        this.baggagePiece = baggagePiece;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String geteTicketType() {
        return eTicketType;
    }

    public void seteTicketType(String eTicketType) {
        this.eTicketType = eTicketType;
    }

}
