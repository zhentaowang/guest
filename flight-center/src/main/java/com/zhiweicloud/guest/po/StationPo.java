/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 基础火车站点表
 * 
 * @author IronC
 * @version 1.0  2017-05-24
 */
public class StationPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "base_station";

    /**
     * 主键自增id
     * 不能为空
     */
    @JSONField(serialize = false)
    private Long stationId;

    /**
     * 火车ID
     * 不能为空
     */
    @JSONField(serialize = false)
    private Long trainId;

    /**
     * 检票口
     */
    private String ticketEntrance;

    /**
     * 站点类型
     * 不能为空
     */
    private String stationType;

    /**
     * 站台号
     */
    private String stationNo;

    /**
     * 候车室
     */
    private String waitingRoom;

    /**
     * 站点序号
     * 不能为空
     */
    @JSONField(name = "train_id")
    private Integer stationOrdinal;

    /**
     * 站点名称
     * 不能为空
     */
    @JSONField(name = "station_name")
    private String stationName;

    /**
     * 到达时间
     */
    @JSONField(name = "arrived_time")
    private String arrivedTime;

    /**
     * 发车时间
     */
    @JSONField(name = "leave_time")
    private String leaveTime;

    /**
     * 停留
     */
    private String stay;

    /**
     * 里程（km）
     */
    private String mileage;

    /**
     * 一等座价格
     */
    @JSONField(name = "fsoftSeat")
    private String firstClass;

    /**
     * 二等座价格
     */
    @JSONField(name = "ssoftSeat")
    private String economyClass;

    /**
     * 商务座价格
     */
    private String businessSeat;

    /**
     * 硬座
     */
    @JSONField(name = "hardSead")
    private String hardSeat;

    /**
     * 软座
     */
    @JSONField(name = "softSeat")
    private String softSeat;

    /**
     * 硬卧
     */
    @JSONField(name = "hardSleep")
    private String hardBerthSleeper;

    /**
     * 软卧
     */
    @JSONField(name = "softSleep")
    private String softBerthSleeper;

    /**
     * 站票
     */
    private String standingRoom;

    /**
     * XX
     */
    private String wuzuo;

    /**
     * XX
     */
    private String swz;

    /**
     * XX
     */
    private String tdz;

    /**
     * XX
     */
    private String gjrw;

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

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTicketEntrance() {
        return ticketEntrance;
    }

    public void setTicketEntrance(String ticketEntrance) {
        this.ticketEntrance = ticketEntrance;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType == null ? null : stationType.trim();
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo == null ? null : stationNo.trim();
    }

    public String getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(String waitingRoom) {
        this.waitingRoom = waitingRoom == null ? null : waitingRoom.trim();
    }

    public Integer getStationOrdinal() {
        return stationOrdinal;
    }

    public void setStationOrdinal(Integer stationOrdinal) {
        this.stationOrdinal = stationOrdinal;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime == null ? null : arrivedTime.trim();
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime == null ? null : leaveTime.trim();
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay == null ? null : stay.trim();
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage == null ? null : mileage.trim();
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(String economyClass) {
        this.economyClass = economyClass;
    }

    public String getBusinessSeat() {
        return businessSeat;
    }

    public void setBusinessSeat(String businessSeat) {
        this.businessSeat = businessSeat;
    }

    public String getHardSeat() {
        return hardSeat;
    }

    public void setHardSeat(String hardSeat) {
        this.hardSeat = hardSeat;
    }

    public String getSoftSeat() {
        return softSeat;
    }

    public void setSoftSeat(String softSeat) {
        this.softSeat = softSeat;
    }

    public String getHardBerthSleeper() {
        return hardBerthSleeper;
    }

    public void setHardBerthSleeper(String hardBerthSleeper) {
        this.hardBerthSleeper = hardBerthSleeper;
    }

    public String getSoftBerthSleeper() {
        return softBerthSleeper;
    }

    public void setSoftBerthSleeper(String softBerthSleeper) {
        this.softBerthSleeper = softBerthSleeper;
    }

    public String getStandingRoom() {
        return standingRoom;
    }

    public void setStandingRoom(String standingRoom) {
        this.standingRoom = standingRoom;
    }

    public String getWuzuo() {
        return wuzuo;
    }

    public void setWuzuo(String wuzuo) {
        this.wuzuo = wuzuo == null ? null : wuzuo.trim();
    }

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz == null ? null : swz.trim();
    }

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz == null ? null : tdz.trim();
    }

    public String getGjrw() {
        return gjrw;
    }

    public void setGjrw(String gjrw) {
        this.gjrw = gjrw == null ? null : gjrw.trim();
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
        sb.append(", stationId=").append(stationId);
        sb.append(", stationType=").append(stationType);
        sb.append(", stationNo=").append(stationNo);
        sb.append(", waitingRoom=").append(waitingRoom);
        sb.append(", stationOrdinal=").append(stationOrdinal);
        sb.append(", stationName=").append(stationName);
        sb.append(", arrivedTime=").append(arrivedTime);
        sb.append(", leaveTime=").append(leaveTime);
        sb.append(", stay=").append(stay);
        sb.append(", mileage=").append(mileage);
        sb.append(", firstClass=").append(firstClass);
        sb.append(", economyClass=").append(economyClass);
        sb.append(", businessSeat=").append(businessSeat);
        sb.append(", hardSeat=").append(hardSeat);
        sb.append(", softSeat=").append(softSeat);
        sb.append(", hardBerthSleeper=").append(hardBerthSleeper);
        sb.append(", softBerthSleeper=").append(softBerthSleeper);
        sb.append(", standingRoom=").append(standingRoom);
        sb.append(", wuzuo=").append(wuzuo);
        sb.append(", swz=").append(swz);
        sb.append(", tdz=").append(tdz);
        sb.append(", gjrw=").append(gjrw);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}