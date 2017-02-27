package com.zhiweicloud.guest.model;

import java.util.Date;

/**
 * Created by twist on 2016-10-21.
 */
public class HttpDataLog {
    private String url;
    private String ip;
    private String query;
    private String method;
    private int userId;
    private String params;
    private Date requestTime;
    private Date responseTime;
    private String responseData;
    private int esTime;
    private long backendTime;
    private Integer vipLevel;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public int getEsTime() {
        return esTime;
    }

    public void setEsTime(int esTime) {
        this.esTime = esTime;
    }

    public long getBackendTime() {
        return backendTime;
    }

    public void setBackendTime(long backendTime) {
        this.backendTime = backendTime;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
}
