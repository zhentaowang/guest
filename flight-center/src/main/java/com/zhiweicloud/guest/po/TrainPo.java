/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 基础火车信息表
 * 
 * @author IronC
 * @version 1.0  2017-05-24
 */
public class TrainPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "base_train";

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
    @JSONField(name = "starttime")
    private String startTime;

    /**
     * 到达时间
     */
    @JSONField(name = "endtime")
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
        this.trainType = trainType == null ? null : trainType.trim();
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
        this.name = name == null ? null : name.trim();
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start == null ? null : start.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage == null ? null : mileage.trim();
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

    /**
     * out method.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", trainId=").append(trainId);
        sb.append(", trainType=").append(trainType);
        sb.append(", trainDate=").append(trainDate);
        sb.append(", name=").append(name);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", mileage=").append(mileage);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}