package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * IbeFlights.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/19 15:58
 * @author tiecheng
 */
@XmlRootElement(name = "IBE_Flights")
public class IbeFlights implements Serializable{

    private IbeFlight ibeFlight;

    @XmlElement(name = "IBE_Flight")
    public IbeFlight getIbeFlight() {
        return ibeFlight;
    }

    public void setIbeFlight(IbeFlight ibeFlight) {
        this.ibeFlight = ibeFlight;
    }

}
