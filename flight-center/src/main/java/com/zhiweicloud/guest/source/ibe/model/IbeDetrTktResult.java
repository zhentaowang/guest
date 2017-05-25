package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DETR结果类
 * Created by tc on 2017/5/18.
 */
@XmlRootElement(name = "IBE_DETETKTResult",namespace = "http://ws.ibeservice.com/")
public class IbeDetrTktResult {

    private TktGroup tktGroup;

    private ErrorRes errorRes;

    @XmlElement(name = "TKTGroup")
    public TktGroup getTktGroup() {
        return tktGroup;
    }

    public void setTktGroup(TktGroup tktGroup) {
        this.tktGroup = tktGroup;
    }

    @XmlElement(name = "ErrorRes")
    public ErrorRes getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(ErrorRes errorRes) {
        this.errorRes = errorRes;
    }

}
