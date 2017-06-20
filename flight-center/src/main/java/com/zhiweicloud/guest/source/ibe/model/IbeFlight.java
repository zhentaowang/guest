package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * IbeFlight.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/19 15:57 
 * @author tiecheng
 */
@XmlRootElement(name = "IBE_Flight")
public class IbeFlight implements Serializable{

    private String flightNo;

    private String depAirportCode;

    private String arrAirportCode;

    private Date depDate;

    private Date arrDate;

    private String depTime;

    private String arrTime;

    private String depTimeModify;

    private String arrTimeModify;

    private String flightTime;

    private String flightType;

    private int stopNumber;

    private boolean meal;

    private String carrier;

    private String depTerminal;

    private String arrTerminal;

    @XmlElement(name = "FlightNO" )
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @XmlElement(name = "orgCity" )
    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    @XmlElement(name = "dstcity" )
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    @XmlElement(name = "depDate" )
    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    @XmlElement(name = "arrDate" )
    public Date getArrDate() {
        return arrDate;
    }

    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    @XmlElement(name = "depTime" )
    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    @XmlElement(name = "arrtime" )
    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    @XmlElement(name = "depTimeModify" )
    public String getDepTimeModify() {
        return depTimeModify;
    }

    public void setDepTimeModify(String depTimeModify) {
        this.depTimeModify = depTimeModify;
    }

    @XmlElement(name = "arrTimeModify" )
    public String getArrTimeModify() {
        return arrTimeModify;
    }

    public void setArrTimeModify(String arrTimeModify) {
        this.arrTimeModify = arrTimeModify;
    }

    @XmlElement(name = "flightTime" )
    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    @XmlElement(name = "planeStyle" )
    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    @XmlElement(name = "stopNumber" )
    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    @XmlElement(name = "meal" )
    public boolean isMeal() {
        return meal;
    }

    public void setMeal(boolean meal) {
        this.meal = meal;
    }

    @XmlElement(name = "carrier" )
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @XmlElement(name = "OrgAirportTerminal" )
    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    @XmlElement(name = "DstAirportTerminal" )
    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

}
