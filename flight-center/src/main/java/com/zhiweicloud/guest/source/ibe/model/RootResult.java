package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 解析XMl 根节点
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/10 17:35
 * @author tiecheng
 */
@XmlRootElement(name = "IBE_QueryByFlightNO",namespace = "http://ws.ibeservice.com/")
public class RootResult implements Serializable{

    private ErrorRes errorRes;

    private List<FlightStatus> flightStatuses;

    @XmlElement(name = "ErrorRes")
    public ErrorRes getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(ErrorRes errorRes) {
        this.errorRes = errorRes;
    }

    @XmlElementWrapper(name = "StatusResult")
    @XmlElement(name = "FlightStatus")
    public List<FlightStatus> getFlightStatuses() {
        return flightStatuses;
    }

    public void setFlightStatuses(List<FlightStatus> flightStatuses) {
        this.flightStatuses = flightStatuses;
    }

}
