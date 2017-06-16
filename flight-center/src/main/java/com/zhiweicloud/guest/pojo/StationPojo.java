package com.zhiweicloud.guest.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by tc on 2017/6/15.
 */
public class StationPojo {

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
    private Integer stationOrdinal;

    /**
     * 站点名称
     * 不能为空
     */
    private String stationName;

    /**
     * 到达时间
     */
    private String arrivedTime;

    /**
     * 发车时间
     */
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
    private String firstClass;

    /**
     * 二等座价格
     */
    private String economyClass;

    /**
     * 商务座价格
     */
    private String businessSeat;

    /**
     * 硬座
     */
    private String hardSeat;

    /**
     * 软座
     */
    private String softSeat;

    /**
     * 硬卧
     */
    private String hardBerthSleeper;

    /**
     * 软卧
     */
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
        this.stationType = stationType;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(String waitingRoom) {
        this.waitingRoom = waitingRoom;
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
        this.stationName = stationName;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
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
        this.wuzuo = wuzuo;
    }

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz;
    }

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz;
    }

    public String getGjrw() {
        return gjrw;
    }

    public void setGjrw(String gjrw) {
        this.gjrw = gjrw;
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
