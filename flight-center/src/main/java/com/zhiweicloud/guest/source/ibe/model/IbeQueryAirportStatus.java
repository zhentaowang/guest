package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by tc on 2017/6/8.
 */
@XmlRootElement(name = "IBE_QueryAirportStatus")
public class IbeQueryAirportStatus {

    private ErrorRes errorRes;

    private AirportA airportA;

    @XmlElement(name = "ErrorRes")
    public ErrorRes getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(ErrorRes errorRes) {
        this.errorRes = errorRes;
    }

    @XmlElement(name = "airportA")
    public AirportA getAirportA() {
        return airportA;
    }

    public void setAirportA(AirportA airportA) {
        this.airportA = airportA;
    }
}
