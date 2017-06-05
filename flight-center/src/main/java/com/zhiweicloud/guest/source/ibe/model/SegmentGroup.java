package com.zhiweicloud.guest.source.ibe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by tc on 2017/5/18.
 */
@XmlRootElement(name = "segmentGroup")
public class SegmentGroup implements Serializable{

    private IbeDetrTktSegment ibeDetrTktSegment;

    @XmlElement(name = "IBE_DETR_TKT_Segment")
    public IbeDetrTktSegment getIbeDetrTktSegment() {
        return ibeDetrTktSegment;
    }

    public void setIbeDetrTktSegment(IbeDetrTktSegment ibeDetrTktSegment) {
        this.ibeDetrTktSegment = ibeDetrTktSegment;
    }

}
