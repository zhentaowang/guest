package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * IbeAvResult.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/19 15:58
 * @author tiecheng
 */
@XmlRootElement(name = "IBE_AVResult",namespace = "http://ws.ibeservice.com/")
public class IbeAvResult implements Serializable{

    private ErrorRes errorRes;

    private List<IbeFlightGroup> ibeFlightGroups;

    @XmlElement(name = "ErrorRes")
    public ErrorRes getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(ErrorRes errorRes) {
        this.errorRes = errorRes;
    }

    @XmlElementWrapper(name = "AVResult")
    @XmlElement(name = "IBE_FlightGroup")
    public List<IbeFlightGroup> getIbeFlightGroups() {
        return ibeFlightGroups;
    }

    public void setIbeFlightGroups(List<IbeFlightGroup> ibeFlightGroups) {
        this.ibeFlightGroups = ibeFlightGroups;
    }

}
