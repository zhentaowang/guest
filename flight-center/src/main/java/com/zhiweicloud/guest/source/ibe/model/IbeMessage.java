package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by tc on 2017/6/14.
 */
@XmlRootElement(name = "IBE_Message")
public class IbeMessage {

    private ErrorRes errorRes;

    @XmlElement(name = "ErrorRes")
    public ErrorRes getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(ErrorRes errorRes) {
        this.errorRes = errorRes;
    }

}
