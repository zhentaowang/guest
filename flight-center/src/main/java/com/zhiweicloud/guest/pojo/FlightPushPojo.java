package com.zhiweicloud.guest.pojo;

import java.util.Map;

/**
 * Created by tc on 2017/6/7.
 */
public class FlightPushPojo {

    private Long customerId;

    private String url;

    private Map<String, String> params;

    private boolean pushResult;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean isPushResult() {
        return pushResult;
    }

    public void setPushResult(boolean pushResult) {
        this.pushResult = pushResult;
    }

}
