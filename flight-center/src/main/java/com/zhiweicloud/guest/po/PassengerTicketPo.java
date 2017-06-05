/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 乘客客票表
 * 
 * @author IronC
 * @version 1.0  2017-05-31
 */
public class PassengerTicketPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "o_passenger_ticket";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long passengerTicketId;

    /**
     * 乘客ID
     * 不能为空
     */
    private Long passengerId;

    /**
     * 客票号
     */
    private String ticketNo;

    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 货币类型
     */
    private String currencyType;

    /**
     * 改签信息
     */
    private String exchangeInfo;

    /**
     * 票价
     */
    private BigDecimal fare;

    /**
     * 适用运价（如YB为Y舱B类运价）
     */
    private String rate;

    /**
     * 票价计算信息
     */
    private String fareCompute;

    /**
     * 后续票号
     */
    private String followTicketNo;

    /**
     * ISI信息
     */
    private String isi;

    /**
     * 原始信息（OI信息）
     */
    private String originalIssue;

    /**
     * 原始字符信息
     */
    private String originalString;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 乘客类型
     */
    private String passengerType;

    /**
     * 是否已打印T4(发票)联
     */
    private Short receiptPrinted;

    /**
     * 获取签注信息
     */
    private String signingInfo;

    /**
     * 税款金额
     */
    private BigDecimal tax;

    /**
     * 客票总金额
     */
    private BigDecimal totalAmount;

    /**
     * 旅游代码
     */
    private String tourCode;

    /**
     * 无人陪伴儿童年龄
     */
    private Integer unaccompaniedChildAge;

    /**
     * 航空公司编号
     */
    private String airlineCode;

    /**
     * 行李重量
     */
    private Integer baggageWeight;

    /**
     * 行李价格
     */
    private String baggagePiece;

    /**
     * 登机牌编号
     */
    private String boardNo;

    /**
     * 舱位
     */
    private String cabinNo;

    /**
     * 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date depScheduledDate;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 旅客订座记录编号
     */
    private String pnrNo;

    /**
     * 客票状态
     */
    private String ticketState;

    /**
     * 航段状态
     */
    private String legState;

    /**
     * 航段类型
     */
    private String legType;

    /**
     * 票类型
     */
    private String eTicketType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getPassengerTicketId() {
        return passengerTicketId;
    }

    public void setPassengerTicketId(Long passengerTicketId) {
        this.passengerTicketId = passengerTicketId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo == null ? null : ticketNo.trim();
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode == null ? null : depAirportCode.trim();
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode == null ? null : arrAirportCode.trim();
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public String getExchangeInfo() {
        return exchangeInfo;
    }

    public void setExchangeInfo(String exchangeInfo) {
        this.exchangeInfo = exchangeInfo == null ? null : exchangeInfo.trim();
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getFareCompute() {
        return fareCompute;
    }

    public void setFareCompute(String fareCompute) {
        this.fareCompute = fareCompute == null ? null : fareCompute.trim();
    }

    public String getFollowTicketNo() {
        return followTicketNo;
    }

    public void setFollowTicketNo(String followTicketNo) {
        this.followTicketNo = followTicketNo == null ? null : followTicketNo.trim();
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi == null ? null : isi.trim();
    }

    public String getOriginalIssue() {
        return originalIssue;
    }

    public void setOriginalIssue(String originalIssue) {
        this.originalIssue = originalIssue == null ? null : originalIssue.trim();
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString == null ? null : originalString.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType == null ? null : passengerType.trim();
    }

    public Short getReceiptPrinted() {
        return receiptPrinted;
    }

    public void setReceiptPrinted(Short receiptPrinted) {
        this.receiptPrinted = receiptPrinted;
    }

    public String getSigningInfo() {
        return signingInfo;
    }

    public void setSigningInfo(String signingInfo) {
        this.signingInfo = signingInfo == null ? null : signingInfo.trim();
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode == null ? null : tourCode.trim();
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
        this.airlineCode = airlineCode == null ? null : airlineCode.trim();
    }

    public Integer getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(Integer baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    public String getBaggagePiece() {
        return baggagePiece;
    }

    public void setBaggagePiece(String baggagePiece) {
        this.baggagePiece = baggagePiece == null ? null : baggagePiece.trim();
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo == null ? null : boardNo.trim();
    }

    public String getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo == null ? null : cabinNo.trim();
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
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo == null ? null : pnrNo.trim();
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState == null ? null : ticketState.trim();
    }

    public String getLegState() {
        return legState;
    }

    public void setLegState(String legState) {
        this.legState = legState == null ? null : legState.trim();
    }

    public String getLegType() {
        return legType;
    }

    public void setLegType(String legType) {
        this.legType = legType == null ? null : legType.trim();
    }

    public String geteTicketType() {
        return eTicketType;
    }

    public void seteTicketType(String eTicketType) {
        this.eTicketType = eTicketType == null ? null : eTicketType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * out method.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", passengerTicketId=").append(passengerTicketId);
        sb.append(", passengerId=").append(passengerId);
        sb.append(", ticketNo=").append(ticketNo);
        sb.append(", depAirportCode=").append(depAirportCode);
        sb.append(", arrAirportCode=").append(arrAirportCode);
        sb.append(", currencyType=").append(currencyType);
        sb.append(", exchangeInfo=").append(exchangeInfo);
        sb.append(", fare=").append(fare);
        sb.append(", rate=").append(rate);
        sb.append(", fareCompute=").append(fareCompute);
        sb.append(", followTicketNo=").append(followTicketNo);
        sb.append(", isi=").append(isi);
        sb.append(", originalIssue=").append(originalIssue);
        sb.append(", originalString=").append(originalString);
        sb.append(", payMethod=").append(payMethod);
        sb.append(", passengerType=").append(passengerType);
        sb.append(", receiptPrinted=").append(receiptPrinted);
        sb.append(", signingInfo=").append(signingInfo);
        sb.append(", tax=").append(tax);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", tourCode=").append(tourCode);
        sb.append(", unaccompaniedChildAge=").append(unaccompaniedChildAge);
        sb.append(", airlineCode=").append(airlineCode);
        sb.append(", baggageWeight=").append(baggageWeight);
        sb.append(", baggagePiece=").append(baggagePiece);
        sb.append(", boardNo=").append(boardNo);
        sb.append(", cabinNo=").append(cabinNo);
        sb.append(", depScheduledDate=").append(depScheduledDate);
        sb.append(", flightNo=").append(flightNo);
        sb.append(", pnrNo=").append(pnrNo);
        sb.append(", ticketState=").append(ticketState);
        sb.append(", legState=").append(legState);
        sb.append(", legType=").append(legType);
        sb.append(", eTicketType=").append(eTicketType);
        sb.append(", remark=").append(remark);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}