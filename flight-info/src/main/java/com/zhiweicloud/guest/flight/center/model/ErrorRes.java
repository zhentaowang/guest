package com.zhiweicloud.guest.flight.center.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by tc on 2017/5/10.
 */
@XmlRootElement(name = "ErrorRes")
public class ErrorRes implements Serializable {

    private String errCode;

    private String errContent;

    @XmlElement(name = "Err_code")
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @XmlElement(name = "Err_content")
    public String getErrContent() {
        return errContent;
    }

    public void setErrContent(String errContent) {
        this.errContent = errContent;
    }

}
