package com.zhiweicloud.guest.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhiweicloud.guest.po.StationPo;
import com.zhiweicloud.guest.po.TrainPo;

import java.util.Date;
import java.util.List;

/**
 * TrainPojo.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/24 21:19
 * @author tiecheng
 */
public class TrainPojo{

    List<StationPojo> stations;

    /**
     * 主键自增id
     * 不能为空
     */
    @JSONField(serialize = false)
    private Long trainId;

    /**
     * 火车类型
     */
    private String trainType;

    /**
     * 火车日期（2099-01-01）
     * 不能为空
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date trainDate;

    /**
     * 列次名称
     * 不能为空
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
     * 里程（km）
     */
    private String mileage;

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public List<StationPojo> getStations() {
        return stations;
    }

    public void setStations(List<StationPojo> stations) {
        this.stations = stations;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
