package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tc on 2017/6/1.
 */
@XmlRootElement(name = "IBE_DETR_TKT_Segment")
public class IbeDetrTktSegment implements Serializable{

    /**
     * 航空公司编号
     */
    private String airlineCode;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 行李重量
     */
    private Integer baggageWeight;

    /**
     * 登机牌编号
     */
    private String boardNo;

    /**
     * 舱位
     */
    private String cabinNo;

    /**
     * 起飞机场三字码
     */
    private String depAirportCode;

    /**
     * 起飞时间（年月日时分）
     */
    private Date depScheduledDate;

    /**
     * 航班编号
     */
    private String flightNo;

    /**
     * 旅客订座记录编号
     */
    private String pnrNo;

    /**
     * 适用运价（如YB为Y舱B类运价）
     */
    private String rate;

    /**
     * 航段状态
     */
    private String legState;

    /**
     * 客票状态
     */
    private String ticketState;

    /**
     * 航段类型
     */
    private String legType;

    /**
     * 行李价格
     */
    private String baggagePiece;

    @XmlElement(name = "airline")
    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    @XmlElement(name = "arrAirportCode")
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    @XmlElement(name = "baggageWeight")
    public Integer getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(Integer baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    @XmlElement(name = "boardingNo")
    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    @XmlElement(name = "cabin")
    public String getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo;
    }

    @XmlElement(name = "depAirportCode")
    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    @XmlElement(name = "depTime")
    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    @XmlElement(name = "flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @XmlElement(name = "pnrNo")
    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    @XmlElement(name = "rate")
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @XmlElement(name = "segmentStatus")
    public String getLegState() {
        return legState;
    }

    public void setLegState(String legState) {
        this.legState = legState;
    }

    @XmlElement(name = "ticketStatus")
    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    @XmlElement(name = "type")
    public String getLegType() {
        return legType;
    }

    public void setLegType(String legType) {
        this.legType = legType;
    }

    @XmlElement(name = "baggagePiece")
    public String getBaggagePiece() {
        return baggagePiece;
    }

    public void setBaggagePiece(String baggagePiece) {
        this.baggagePiece = baggagePiece;
    }

}
