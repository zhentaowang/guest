package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTER票面结果类
 * Created by tc on 2017/5/18.
 */
@XmlRootElement(name = "TKTGroup")
public class TktGroup {

    private IbeDetrTkt ibeDetrTkt;

    @XmlElement(name = "IBE_DETR_TKT")
    public IbeDetrTkt getIbeDetrTkt() {
        return ibeDetrTkt;
    }

    public void setIbeDetrTkt(IbeDetrTkt ibeDetrTkt) {
        this.ibeDetrTkt = ibeDetrTkt;
    }

}
