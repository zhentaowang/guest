package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * IbeFlightGroup.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/19 15:58
 * @author tiecheng
 */
@XmlRootElement(name = "IBE_FlightGroup")
public class IbeFlightGroup implements Serializable{

    private IbeFlights ibeFlights;

    @XmlElement(name = "IBE_Flights" )
    public IbeFlights getIbeFlights() {
        return ibeFlights;
    }

    public void setIbeFlights(IbeFlights ibeFlights) {
        this.ibeFlights = ibeFlights;
    }

}
