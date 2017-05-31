/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 基础航班信息表
 * 
 * @author IronC
 * @version 1.0  2017-05-31
 */
public class FlightPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "base_flight";

    /**
     * 主键自增id
     * 不能为空
     */
    @JSONField(serialize = false)
    private Long flightId;

    /**
     * 更新标识字段
     */
    private Integer fdId;

    /**
     * 航空公司编号
     */
    private String airlineCode;

    /**
     * 航空公司英语名
     */
    private String airlineEnName;

    /**
     * 航空公司名称
     */
    private String airlineName;

    /**
     * 中转机场
     */
    private String transferAirport;

    /**
     * 中转机场英文名
     */
    private String transferAirportCodeEnName;

    /**
     * 中转机场三字码
     * 不能为空
     */
    private String transferAirportCode;

    /**
     * 登机口
     */
    private String boardGate;

    /**
     * 登机时间
     */
    private Date boardTime;

    /**
     * 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     */
    private String boardState;

    /**
     * 航班号
     * 不能为空
     */
    private String flightNo;

    /**
     * 机型
     * 不能为空
     */
    private String flightType;

    /**
     * 机号
     */
    private String planNo;

    /**
     * 航班状态代码
     * 不能为空
     */
    private String flightStateCode;

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     */
    private String flightState;

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
     * 值机柜台
     * 不能为空
     */
    private String checkInCounter;

    /**
     * 行李盘
     * 不能为空
     */
    private String carousel;

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
     * 备降信息节点
     */
    private String alternateInfo;

    /**
     * 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     */
    private String fcategory;

    /**
     * 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     */
    private String boardGateTime;

    /**
     * 出发地英文
     */
    private String depEnName;

    /**
     * 出发城市名
     */
    private String depCity;

    /**
     * 出发日期
     * 不能为空
     */
    private Date depDate;

    /**
     * 出发机场
     */
    private String depAirport;

    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 出发机场名
     */
    private String depAirportName;

    /**
     * 出发地时区
     */
    private String depTimeZone;

    /**
     * 出发机场候机楼
     */
    private String depTerminal;

    /**
     * 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date depScheduledDate;

    /**
     * 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date depEstimatedDate;

    /**
     * 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date depActualDate;

    /**
     * 到达地英文
     */
    private String arrEnName;

    /**
     * 到达城市
     */
    private String arrCity;

    /**
     * 到达日期
     * 不能为空
     */
    private Date arrDate;

    /**
     * 到达机场
     */
    private String arrAirport;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 到达机场名
     */
    private String arrAirportName;

    /**
     * 到达地时区
     */
    private String arrTimeZone;

    /**
     * 到达地机场候机楼（接机楼）
     */
    private String arrTerminal;

    /**
     * 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date arrScheduledDate;

    /**
     * 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date arrEstimatedDate;

    /**
     * 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    private Date arrActualDate;

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

    public Integer getFdId() {
        return fdId;
    }

    public void setFdId(Integer fdId) {
        this.fdId = fdId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode == null ? null : airlineCode.trim();
    }

    public String getAirlineEnName() {
        return airlineEnName;
    }

    public void setAirlineEnName(String airlineEnName) {
        this.airlineEnName = airlineEnName == null ? null : airlineEnName.trim();
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName == null ? null : airlineName.trim();
    }

    public String getTransferAirport() {
        return transferAirport;
    }

    public void setTransferAirport(String transferAirport) {
        this.transferAirport = transferAirport == null ? null : transferAirport.trim();
    }

    public String getTransferAirportCodeEnName() {
        return transferAirportCodeEnName;
    }

    public void setTransferAirportCodeEnName(String transferAirportCodeEnName) {
        this.transferAirportCodeEnName = transferAirportCodeEnName == null ? null : transferAirportCodeEnName.trim();
    }

    public String getTransferAirportCode() {
        return transferAirportCode;
    }

    public void setTransferAirportCode(String transferAirportCode) {
        this.transferAirportCode = transferAirportCode == null ? null : transferAirportCode.trim();
    }

    public String getBoardGate() {
        return boardGate;
    }

    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate == null ? null : boardGate.trim();
    }

    public Date getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(Date boardTime) {
        this.boardTime = boardTime;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState == null ? null : boardState.trim();
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType == null ? null : flightType.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getFlightStateCode() {
        return flightStateCode;
    }

    public void setFlightStateCode(String flightStateCode) {
        this.flightStateCode = flightStateCode == null ? null : flightStateCode.trim();
    }

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState == null ? null : flightState.trim();
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

    public String getCheckInCounter() {
        return checkInCounter;
    }

    public void setCheckInCounter(String checkInCounter) {
        this.checkInCounter = checkInCounter == null ? null : checkInCounter.trim();
    }

    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel == null ? null : carousel.trim();
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

    public String getAlternateInfo() {
        return alternateInfo;
    }

    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo == null ? null : alternateInfo.trim();
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

    public String getDepEnName() {
        return depEnName;
    }

    public void setDepEnName(String depEnName) {
        this.depEnName = depEnName == null ? null : depEnName.trim();
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity == null ? null : depCity.trim();
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport == null ? null : depAirport.trim();
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode == null ? null : depAirportCode.trim();
    }

    public String getDepAirportName() {
        return depAirportName;
    }

    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName == null ? null : depAirportName.trim();
    }

    public String getDepTimeZone() {
        return depTimeZone;
    }

    public void setDepTimeZone(String depTimeZone) {
        this.depTimeZone = depTimeZone == null ? null : depTimeZone.trim();
    }

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal == null ? null : depTerminal.trim();
    }

    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    public Date getDepEstimatedDate() {
        return depEstimatedDate;
    }

    public void setDepEstimatedDate(Date depEstimatedDate) {
        this.depEstimatedDate = depEstimatedDate;
    }

    public Date getDepActualDate() {
        return depActualDate;
    }

    public void setDepActualDate(Date depActualDate) {
        this.depActualDate = depActualDate;
    }

    public String getArrEnName() {
        return arrEnName;
    }

    public void setArrEnName(String arrEnName) {
        this.arrEnName = arrEnName == null ? null : arrEnName.trim();
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity == null ? null : arrCity.trim();
    }

    public Date getArrDate() {
        return arrDate;
    }

    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport == null ? null : arrAirport.trim();
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode == null ? null : arrAirportCode.trim();
    }

    public String getArrAirportName() {
        return arrAirportName;
    }

    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName == null ? null : arrAirportName.trim();
    }

    public String getArrTimeZone() {
        return arrTimeZone;
    }

    public void setArrTimeZone(String arrTimeZone) {
        this.arrTimeZone = arrTimeZone == null ? null : arrTimeZone.trim();
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal == null ? null : arrTerminal.trim();
    }

    public Date getArrScheduledDate() {
        return arrScheduledDate;
    }

    public void setArrScheduledDate(Date arrScheduledDate) {
        this.arrScheduledDate = arrScheduledDate;
    }

    public Date getArrEstimatedDate() {
        return arrEstimatedDate;
    }

    public void setArrEstimatedDate(Date arrEstimatedDate) {
        this.arrEstimatedDate = arrEstimatedDate;
    }

    public Date getArrActualDate() {
        return arrActualDate;
    }

    public void setArrActualDate(Date arrActualDate) {
        this.arrActualDate = arrActualDate;
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
        sb.append(", fdId=").append(fdId);
        sb.append(", airlineCode=").append(airlineCode);
        sb.append(", airlineEnName=").append(airlineEnName);
        sb.append(", airlineName=").append(airlineName);
        sb.append(", transferAirport=").append(transferAirport);
        sb.append(", transferAirportCodeEnName=").append(transferAirportCodeEnName);
        sb.append(", transferAirportCode=").append(transferAirportCode);
        sb.append(", boardGate=").append(boardGate);
        sb.append(", boardTime=").append(boardTime);
        sb.append(", boardState=").append(boardState);
        sb.append(", flightNo=").append(flightNo);
        sb.append(", flightType=").append(flightType);
        sb.append(", planNo=").append(planNo);
        sb.append(", flightStateCode=").append(flightStateCode);
        sb.append(", flightState=").append(flightState);
        sb.append(", flightPosition=").append(flightPosition);
        sb.append(", boardInOut=").append(boardInOut);
        sb.append(", isInOrOut=").append(isInOrOut);
        sb.append(", isNearOrFar=").append(isNearOrFar);
        sb.append(", checkInCounter=").append(checkInCounter);
        sb.append(", carousel=").append(carousel);
        sb.append(", stopFlag=").append(stopFlag);
        sb.append(", shareFlag=").append(shareFlag);
        sb.append(", shareFlightNo=").append(shareFlightNo);
        sb.append(", fillFlightNo=").append(fillFlightNo);
        sb.append(", alternateInfo=").append(alternateInfo);
        sb.append(", fcategory=").append(fcategory);
        sb.append(", boardGateTime=").append(boardGateTime);
        sb.append(", depEnName=").append(depEnName);
        sb.append(", depCity=").append(depCity);
        sb.append(", depDate=").append(depDate);
        sb.append(", depAirport=").append(depAirport);
        sb.append(", depAirportCode=").append(depAirportCode);
        sb.append(", depAirportName=").append(depAirportName);
        sb.append(", depTimeZone=").append(depTimeZone);
        sb.append(", depTerminal=").append(depTerminal);
        sb.append(", depScheduledDate=").append(depScheduledDate);
        sb.append(", depEstimatedDate=").append(depEstimatedDate);
        sb.append(", depActualDate=").append(depActualDate);
        sb.append(", arrEnName=").append(arrEnName);
        sb.append(", arrCity=").append(arrCity);
        sb.append(", arrDate=").append(arrDate);
        sb.append(", arrAirport=").append(arrAirport);
        sb.append(", arrAirportCode=").append(arrAirportCode);
        sb.append(", arrAirportName=").append(arrAirportName);
        sb.append(", arrTimeZone=").append(arrTimeZone);
        sb.append(", arrTerminal=").append(arrTerminal);
        sb.append(", arrScheduledDate=").append(arrScheduledDate);
        sb.append(", arrEstimatedDate=").append(arrEstimatedDate);
        sb.append(", arrActualDate=").append(arrActualDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}