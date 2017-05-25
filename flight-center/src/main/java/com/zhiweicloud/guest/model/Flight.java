/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 航班信息
 * 
 * @author IronC
 * @version 1.0  2017-05-13
 */
public class Flight {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "flight_info";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long flightId;

    /**
     * 航班日期
     * 不能为空
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date flightDate;

    /**
     * 航班号
     * 不能为空
     */
    private String flightNo;

    /**
     * 出发地三字码
     * 不能为空
     */
    private String flightDepcode;

    /**
     * 目的地三字码
     * 不能为空
     */
    private String flightArrcode;

    /**
     * 机号
     */
    private String planNo;

    /**
     * 机位
     */
    private String flightPosition;

    /**
     * 国际国内：国外：0，国内：1
     */
    private Short boardInOut;

    /**
     * 出港：0，进港1
     */
    private Short isInOrOut;

    /**
     * 远机位：0，近机位：1
     */
    private Short isNearOrFar;

    /**
     * 忽视
     */
    private String fdId;

    /**
     * 航空公司名称
     */
    private String flightCompany;

    /**
     * 计划起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimePlanDate;

    /**
     * 计划到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimePlanDate;

    /**
     * 预计起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimeReadyDate;

    /**
     * 预计到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimeReadyDate;

    /**
     * 实际起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimeDate;

    /**
     * 实际到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimeDate;

    /**
     * 是否 经停 （0:不经停;1:经停 ）
     */
    private Short stopFlag;

    /**
     * 是否 共享 （0:不共享;1:共享 ）
     */
    private Short shareFlag;

    /**
     * 共享航班号
     */
    private String shareFlightNo;

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     */
    private String fillFlightNo;

    /**
     * 登机口
     */
    private String boardGate;

    /**
     * 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     */
    private String boardState;

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     */
    private String flightState;

    /**
     * 候机楼
     */
    private String flightHterminal;

    /**
     * 接机楼
     */
    private String flightTerminal;

    /**
     * 出发城市名
     */
    private String flightDep;

    /**
     * 到达城市名
     */
    private String flightArr;

    /**
     * 出发机场名
     */
    private String flightDepAirport;

    /**
     * 到达机场名
     */
    private String flightArrAirport;

    /**
     * 备降信息节点
     */
    private String alternateInfo;

    /**
     * 出发地时区
     */
    private String orgTimezone;

    /**
     * 目的地时区
     */
    private String dstTimezone;

    /**
     * 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     */
    private String fcategory;

    /**
     * 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     */
    private String boardGateTime;

    /**
     * 值机柜台
     */
    private String flightzj;

    /**
     * 行李盘
     */
    private String carousel;

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

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode == null ? null : flightDepcode.trim();
    }

    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode == null ? null : flightArrcode.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getFlightPosition() {
        return flightPosition;
    }

    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition == null ? null : flightPosition.trim();
    }

    public Short getBoardInOut() {
        return boardInOut;
    }

    public void setBoardInOut(Short boardInOut) {
        this.boardInOut = boardInOut;
    }

    public Short getIsInOrOut() {
        return isInOrOut;
    }

    public void setIsInOrOut(Short isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId == null ? null : fdId.trim();
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany == null ? null : flightCompany.trim();
    }

    public Date getFlightDeptimePlanDate() {
        return flightDeptimePlanDate;
    }

    public void setFlightDeptimePlanDate(Date flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    public Date getFlightArrtimePlanDate() {
        return flightArrtimePlanDate;
    }

    public void setFlightArrtimePlanDate(Date flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
    }

    public Date getFlightDeptimeReadyDate() {
        return flightDeptimeReadyDate;
    }

    public void setFlightDeptimeReadyDate(Date flightDeptimeReadyDate) {
        this.flightDeptimeReadyDate = flightDeptimeReadyDate;
    }

    public Date getFlightArrtimeReadyDate() {
        return flightArrtimeReadyDate;
    }

    public void setFlightArrtimeReadyDate(Date flightArrtimeReadyDate) {
        this.flightArrtimeReadyDate = flightArrtimeReadyDate;
    }

    public Date getFlightDeptimeDate() {
        return flightDeptimeDate;
    }

    public void setFlightDeptimeDate(Date flightDeptimeDate) {
        this.flightDeptimeDate = flightDeptimeDate;
    }

    public Date getFlightArrtimeDate() {
        return flightArrtimeDate;
    }

    public void setFlightArrtimeDate(Date flightArrtimeDate) {
        this.flightArrtimeDate = flightArrtimeDate;
    }

    public Short getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Short stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Short getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(Short shareFlag) {
        this.shareFlag = shareFlag;
    }

    public String getShareFlightNo() {
        return shareFlightNo;
    }

    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo == null ? null : shareFlightNo.trim();
    }

    public String getFillFlightNo() {
        return fillFlightNo;
    }

    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo == null ? null : fillFlightNo.trim();
    }

    public String getBoardGate() {
        return boardGate;
    }

    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate == null ? null : boardGate.trim();
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState == null ? null : boardState.trim();
    }

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState == null ? null : flightState.trim();
    }

    public String getFlightHterminal() {
        return flightHterminal;
    }

    public void setFlightHterminal(String flightHterminal) {
        this.flightHterminal = flightHterminal == null ? null : flightHterminal.trim();
    }

    public String getFlightTerminal() {
        return flightTerminal;
    }

    public void setFlightTerminal(String flightTerminal) {
        this.flightTerminal = flightTerminal == null ? null : flightTerminal.trim();
    }

    public String getFlightDep() {
        return flightDep;
    }

    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep == null ? null : flightDep.trim();
    }

    public String getFlightArr() {
        return flightArr;
    }

    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr == null ? null : flightArr.trim();
    }

    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport == null ? null : flightDepAirport.trim();
    }

    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport == null ? null : flightArrAirport.trim();
    }

    public String getAlternateInfo() {
        return alternateInfo;
    }

    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo == null ? null : alternateInfo.trim();
    }

    public String getOrgTimezone() {
        return orgTimezone;
    }

    public void setOrgTimezone(String orgTimezone) {
        this.orgTimezone = orgTimezone == null ? null : orgTimezone.trim();
    }

    public String getDstTimezone() {
        return dstTimezone;
    }

    public void setDstTimezone(String dstTimezone) {
        this.dstTimezone = dstTimezone == null ? null : dstTimezone.trim();
    }

    public String getFcategory() {
        return fcategory;
    }

    public void setFcategory(String fcategory) {
        this.fcategory = fcategory == null ? null : fcategory.trim();
    }

    public String getBoardGateTime() {
        return boardGateTime;
    }

    public void setBoardGateTime(String boardGateTime) {
        this.boardGateTime = boardGateTime == null ? null : boardGateTime.trim();
    }

    public String getFlightzj() {
        return flightzj;
    }

    public void setFlightzj(String flightzj) {
        this.flightzj = flightzj == null ? null : flightzj.trim();
    }

    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel == null ? null : carousel.trim();
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
        sb.append(", flightId=").append(flightId);
        sb.append(", flightDate=").append(flightDate);
        sb.append(", flightNo=").append(flightNo);
        sb.append(", flightDepcode=").append(flightDepcode);
        sb.append(", flightArrcode=").append(flightArrcode);
        sb.append(", planNo=").append(planNo);
        sb.append(", flightPosition=").append(flightPosition);
        sb.append(", boardInOut=").append(boardInOut);
        sb.append(", isInOrOut=").append(isInOrOut);
        sb.append(", isNearOrFar=").append(isNearOrFar);
        sb.append(", fdId=").append(fdId);
        sb.append(", flightCompany=").append(flightCompany);
        sb.append(", flightDeptimePlanDate=").append(flightDeptimePlanDate);
        sb.append(", flightArrtimePlanDate=").append(flightArrtimePlanDate);
        sb.append(", flightDeptimeReadyDate=").append(flightDeptimeReadyDate);
        sb.append(", flightArrtimeReadyDate=").append(flightArrtimeReadyDate);
        sb.append(", flightDeptimeDate=").append(flightDeptimeDate);
        sb.append(", flightArrtimeDate=").append(flightArrtimeDate);
        sb.append(", stopFlag=").append(stopFlag);
        sb.append(", shareFlag=").append(shareFlag);
        sb.append(", shareFlightNo=").append(shareFlightNo);
        sb.append(", fillFlightNo=").append(fillFlightNo);
        sb.append(", boardGate=").append(boardGate);
        sb.append(", boardState=").append(boardState);
        sb.append(", flightState=").append(flightState);
        sb.append(", flightHterminal=").append(flightHterminal);
        sb.append(", flightTerminal=").append(flightTerminal);
        sb.append(", flightDep=").append(flightDep);
        sb.append(", flightArr=").append(flightArr);
        sb.append(", flightDepAirport=").append(flightDepAirport);
        sb.append(", flightArrAirport=").append(flightArrAirport);
        sb.append(", alternateInfo=").append(alternateInfo);
        sb.append(", orgTimezone=").append(orgTimezone);
        sb.append(", dstTimezone=").append(dstTimezone);
        sb.append(", fcategory=").append(fcategory);
        sb.append(", boardGateTime=").append(boardGateTime);
        sb.append(", flightzj=").append(flightzj);
        sb.append(", carousel=").append(carousel);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}