package com.zhiweicloud.guest.source.juhe.model;

import java.util.List;

/**
 * TrainInfo.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/24 19:56
 * @author tiecheng
 */
public class TrainInfo {

    /**
     * 列车名次
     */
    private String name;

    /**
     * 起点站
     */
    private String start;

    /**
     * 终点站
     */
    private String end;

    /**
     * 发车时间
     */
    private String startTime;

    /**
     * 到达时间
     */
    private String endTime;

    /**
     * 里程
     */
    private String mileage;

    /**
     * 站点信息
     */
    private List<Station> stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

}
