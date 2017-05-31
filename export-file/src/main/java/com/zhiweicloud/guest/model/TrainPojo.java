package com.zhiweicloud.guest.model;

import javax.ws.rs.QueryParam;

/**
 * Created by tc on 2017/5/24.
 */
public class TrainPojo {

    @QueryParam("clientName")
    private String clientName;

    @QueryParam("trainName")
    private String trainName;

    @QueryParam("productName")
    private String productName;

    @QueryParam("startTime")
    private String startTime;

    @QueryParam("endTime")
    private String endTime;

    @QueryParam("type")
    private Integer type;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
