package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by tc on 2017/5/18.
 */
@XmlRootElement(name = "IBE_DETR_TKT")
public class IbeDetrTkt {

    /**
     * 货币类型
     */
    private String currencyType;

    /**
     * 终点城市三字码
     */
    private String arrAirportCode;

    /**
     * 改签信息
     */
    private String exchangeInfo;

    /**
     * 票价
     */
    private Double fare;

    /**
     * 票价计算信息
     */
    private String fareCompute;

    /**
     * 后续票号
     */
    private String followTicketNo;

    /**
     * 婴儿的生日（年月）
     */
    private Date infantBirthDay;

    /**
     * ISI信息
     */
    private String ISI;

    /**
     * 出票航空公司
     */
    private String airlineCode;

    /**
     * 始发地城市三字码
     */
    private String depAirportCode;

    /**
     * 原始字符信息
     */
    private String orginalString;

    /**
     * OI信息
     */
    private String originalIssue;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 旅客姓名
     */
    private String passengerName;

    /**
     * 旅客类型 -- IBE是枚举类
     */
    private String passengerType;

    /**
     * 是否已打印T4(发票)联
     */
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
     * 票号
     */
    private String ticketNo;

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

    /**
     * 票面航段信息
     */
    private SegmentGroup segmentGroup;

    /*
    XML中出现的，接口文档没有的字段
     */

    /**
     * 标注
     */
    private String remark;

    /**
     * ??
     */
    private String eTicketType;

    @XmlElement(name = "currencyType")
    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @XmlElement(name = "dstCity")
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    @XmlElement(name = "exchangeInfo")
    public String getExchangeInfo() {
        return exchangeInfo;
    }

    public void setExchangeInfo(String exchangeInfo) {
        this.exchangeInfo = exchangeInfo;
    }

    @XmlElement(name = "fare")
    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    @XmlElement(name = "fareCompute")
    public String getFareCompute() {
        return fareCompute;
    }

    public void setFareCompute(String fareCompute) {
        this.fareCompute = fareCompute;
    }

    @XmlElement(name = "followTicketNo")
    public String getFollowTicketNo() {
        return followTicketNo;
    }

    public void setFollowTicketNo(String followTicketNo) {
        this.followTicketNo = followTicketNo;
    }

    @XmlElement(name = "infantBirthDay")
    public Date getInfantBirthDay() {
        return infantBirthDay;
    }

    public void setInfantBirthDay(Date infantBirthDay) {
        this.infantBirthDay = infantBirthDay;
    }

    @XmlElement(name = "ISI")
    public String getISI() {
        return ISI;
    }

    public void setISI(String ISI) {
        this.ISI = ISI;
    }

    @XmlElement(name = "issueAirline")
    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    @XmlElement(name = "orgCity")
    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    @XmlElement(name = "orginalString")
    public String getOrginalString() {
        return orginalString;
    }

    public void setOrginalString(String orginalString) {
        this.orginalString = orginalString;
    }

    @XmlElement(name = "originalIssue")
    public String getOriginalIssue() {
        return originalIssue;
    }

    public void setOriginalIssue(String originalIssue) {
        this.originalIssue = originalIssue;
    }

    @XmlElement(name = "payMethod")
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    @XmlElement(name = "psgrName")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @XmlElement(name = "psgrType")
    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    @XmlElement(name = "receiptPrinted")
    public Boolean getReceiptPrinted() {
        return receiptPrinted;
    }

    public void setReceiptPrinted(Boolean receiptPrinted) {
        this.receiptPrinted = receiptPrinted;
    }

    @XmlElement(name = "signingInfo")
    public String getSigningInfo() {
        return signingInfo;
    }

    public void setSigningInfo(String signingInfo) {
        this.signingInfo = signingInfo;
    }

    @XmlElement(name = "tax")
    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @XmlElement(name = "ticketNo")
    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    @XmlElement(name = "totalAmount")
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @XmlElement(name = "tourCode")
    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    @XmlElement(name = "unaccompaniedChildAge")
    public Integer getUnaccompaniedChildAge() {
        return unaccompaniedChildAge;
    }

    public void setUnaccompaniedChildAge(Integer unaccompaniedChildAge) {
        this.unaccompaniedChildAge = unaccompaniedChildAge;
    }

    @XmlElement(name = "segmentGroup")
    public SegmentGroup getSegmentGroup() {
        return segmentGroup;
    }

    public void setSegmentGroup(SegmentGroup segmentGroup) {
        this.segmentGroup = segmentGroup;
    }

    @XmlElement(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement(name = "eTicketType")
    public String geteTicketType() {
        return eTicketType;
    }

    public void seteTicketType(String eTicketType) {
        this.eTicketType = eTicketType;
    }

}
