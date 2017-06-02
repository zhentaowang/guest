package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tc on 2017/5/10.
 */
@XmlRootElement(name = "FlightStatus")
public class FlightStatus implements Serializable{

    private String flightNo;

    private String depAirportCode;

    private String arrAirportCode;

    private String depCity;

    private String arrCity;

    private String depTerminal;

    private String arrTerminal;

    private Date depScheduledDate;

    private Date depEstimatedDate;

    private Date depActualDate;

    private Date arrScheduledDate;

    private Date arrEstimatedDate;

    private Date arrActualDate;

    private String flightState;

    private String arrRate;

    private String boardGate;

    private String carousel;

    private String checkInCounter;

    /**
     * 航班号
     */
    @XmlElement(name = "FlightNo")
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 出发城市三字码
     */
    @XmlElement(name = "DepCode")
    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    /**
     * 到达城市三字码
     */
    @XmlElement(name = "ArrCode")
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    /**
     * 出发城市名
     */
    @XmlElement(name = "DepCity")
    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String flightDep) {
        this.depCity = depCity;
    }

    /**
     * 到达城市名
     */
    @XmlElement(name = "ArrCity")
    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String flightArr) {
        this.arrCity = arrCity;
    }

    /**
     * 候机楼（始发机场航站楼）
     */
    @XmlElement(name = "DepTerminal")
    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    /**
     * 接机楼（到达机场航站楼）
     */
    @XmlElement(name = "ArrTerminal")
    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    /**
     * 航班计划起飞时间
     */
    @XmlElement(name = "DepScheduled")
    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    /**
     * 航班计划到达时间
     */
    @XmlElement(name = "ArrScheduled")
    public Date getArrScheduledDate() {
        return arrScheduledDate;
    }

    public void setArrScheduledDate(Date arrScheduledDate) {
        this.arrScheduledDate = arrScheduledDate;
    }

    /**
     * 航班预计起飞时间
     */
    @XmlElement(name = "DepEstimated")
    public Date getDepEstimatedDate() {
        return depEstimatedDate;
    }

    public void setDepEstimatedDate(Date depEstimatedDate) {
        this.depEstimatedDate = depEstimatedDate;
    }

    /**
     * 航班预计到达时间
     */
    @XmlElement(name = "ArrEstimated")
    public Date getArrEstimatedDate() {
        return arrEstimatedDate;
    }

    public void setArrEstimatedDate(Date arrEstimatedDate) {
        this.arrEstimatedDate = arrEstimatedDate;
    }

    /**
     * 航班实际起飞时间
     */
    @XmlElement(name = "DepActual")
    public Date getDepActualDate() {
        return depActualDate;
    }

    public void setDepActualDate(Date depActualDate) {
        this.depActualDate = depActualDate;
    }

    /**
     * 航班实际到达时间
     */
    @XmlElement(name = "ArrActual")
    public Date getArrActualDate() {
        return arrActualDate;
    }

    public void setArrActualDate(Date arrActualDate) {
        this.arrActualDate = arrActualDate;
    }

    /**
     * 航班状态
     */
    @XmlElement(name = "FlightState")
    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    /**
     * 准点率
     */
    @XmlElement(name = "arrRate",nillable = true)
    public String getArrRate() {
        return arrRate;
    }

    public void setArrRate(String arrRate) {
        this.arrRate = arrRate;
    }

    @XmlElement(name = "boardingGate")
    public String getBoardGate() {
        return boardGate;
    }

    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate;
    }

    @XmlElement(name = "carousel")
    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel;
    }

    @XmlElement(name = "flightzj")
    public String getCheckInCounter() {
        return checkInCounter;
    }

    public void setCheckInCounter(String checkInCounter) {
        this.checkInCounter = checkInCounter;
    }

}
