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

    private String flightDepcode;

    private String flightArrcode;

    private String flightDep;

    private String flightArr;

    private String flightHterminal;

    private String flightTerminal;

    private Date flightDeptimePlanDate;

    private Date flightArrtimePlanDate;

    private Date flightDeptimeReadyDate;

    private Date flightArrtimeReadyDate;

    private Date flightDeptimeDate;

    private Date flightArrtimeDate;

    private String flightState;

    private String arrRate;

    private String boardGate;

    private String carousel;

    private String flightzj;

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
    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    /**
     * 到达城市三字码
     */
    @XmlElement(name = "ArrCode")
    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    /**
     * 出发城市名
     */
    @XmlElement(name = "DepCity")
    public String getFlightDep() {
        return flightDep;
    }

    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep;
    }

    /**
     * 到达城市名
     */
    @XmlElement(name = "ArrCity")
    public String getFlightArr() {
        return flightArr;
    }

    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr;
    }

    /**
     * 候机楼（始发机场航站楼）
     */
    @XmlElement(name = "DepTerminal")
    public String getFlightHterminal() {
        return flightHterminal;
    }

    public void setFlightHterminal(String flightHterminal) {
        this.flightHterminal = flightHterminal;
    }

    /**
     * 接机楼（到达机场航站楼）
     */
    @XmlElement(name = "ArrTerminal")
    public String getFlightTerminal() {
        return flightTerminal;
    }

    public void setFlightTerminal(String flightTerminal) {
        this.flightTerminal = flightTerminal;
    }

    /**
     * 航班计划起飞时间
     */
    @XmlElement(name = "DepScheduled")
    public Date getFlightDeptimePlanDate() {
        return flightDeptimePlanDate;
    }

    public void setFlightDeptimePlanDate(Date flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    /**
     * 航班计划到达时间
     */
    @XmlElement(name = "ArrScheduled")
    public Date getFlightArrtimePlanDate() {
        return flightArrtimePlanDate;
    }

    public void setFlightArrtimePlanDate(Date flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
    }

    /**
     * 航班预计起飞时间
     */
    @XmlElement(name = "DepEstimated")
    public Date getFlightDeptimeReadyDate() {
        return flightDeptimeReadyDate;
    }

    public void setFlightDeptimeReadyDate(Date flightDeptimeReadyDate) {
        this.flightDeptimeReadyDate = flightDeptimeReadyDate;
    }

    /**
     * 航班预计到达时间
     */
    @XmlElement(name = "ArrEstimated")
    public Date getFlightArrtimeReadyDate() {
        return flightArrtimeReadyDate;
    }

    public void setFlightArrtimeReadyDate(Date flightArrtimeReadyDate) {
        this.flightArrtimeReadyDate = flightArrtimeReadyDate;
    }

    /**
     * 航班实际起飞时间
     */
    @XmlElement(name = "DepActual")
    public Date getFlightDeptimeDate() {
        return flightDeptimeDate;
    }

    public void setFlightDeptimeDate(Date flightDeptimeDate) {
        this.flightDeptimeDate = flightDeptimeDate;
    }

    /**
     * 航班实际到达时间
     */
    @XmlElement(name = "ArrActual")
    public Date getFlightArrtimeDate() {
        return flightArrtimeDate;
    }

    public void setFlightArrtimeDate(Date flightArrtimeDate) {
        this.flightArrtimeDate = flightArrtimeDate;
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
    public String getFlightzj() {
        return flightzj;
    }

    public void setFlightzj(String flightzj) {
        this.flightzj = flightzj;
    }

}
