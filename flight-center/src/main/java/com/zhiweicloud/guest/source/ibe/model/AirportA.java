package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AirportA.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 20:13
 * @author tiecheng
 */
@XmlRootElement(name = "airportA")
public class AirportA {

    private String depDelay;

    private String arrDelay;

    private String depCount;

    private String arrCount;

    private String weather;

    @XmlElement(name = "depdelay")
    public String getDepDelay() {
        return depDelay;
    }

    public void setDepDelay(String depDelay) {
        this.depDelay = depDelay;
    }

    @XmlElement(name = "arrdelay")
    public String getArrDelay() {
        return arrDelay;
    }

    public void setArrDelay(String arrDelay) {
        this.arrDelay = arrDelay;
    }

    @XmlElement(name = "depcount")
    public String getDepCount() {
        return depCount;
    }

    public void setDepCount(String depCount) {
        this.depCount = depCount;
    }

    @XmlElement(name = "arrcount")
    public String getArrCount() {
        return arrCount;
    }

    public void setArrCount(String arrCount) {
        this.arrCount = arrCount;
    }

    @XmlElement(name = "weather")
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
