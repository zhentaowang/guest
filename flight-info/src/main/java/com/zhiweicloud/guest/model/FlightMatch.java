package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by tc on 2017/3/15.
 */
public class FlightMatch extends BaseEntity {

    private Long flightId;

    @JSONField(name = "FlightDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date flightDate;

    @JSONField(name = "FlightNo")
    private String flightNo;

    @JSONField(name = "FlightDepcode")
    private String flightDepcode;

    @JSONField(name = "FlightArrcode")
    private String flightArrcode;

    private String planNo;

    private String flightPosition;

    private Short boardInOut;

    private Short isInOrOut;

    private Short isNearOrFar;

    @JSONField(name = "FD_ID")
    private String fdId;

    @JSONField(name = "FlightCompany")
    private String flightCompany;

    @JSONField(name = "FlightDeptimePlanDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimePlanDate;

    @JSONField(name = "FlightArrtimePlanDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimePlanDate;

    @JSONField(name = "FlightDeptimeReadyDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimeReadyDate;

    @JSONField(name = "FlightArrtimeReadyDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimeReadyDate;

    @JSONField(name = "FlightDeptimeDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightDeptimeDate;

    @JSONField(name = "FlightArrtimeDate")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date flightArrtimeDate;

    @JSONField(name = "stopFlag")
    private Short stopFlag;

    @JSONField(name = "shareFlag")
    private Short shareFlag;

    @JSONField(name = "ShareFlightNo")
    private String shareFlightNo;

    @JSONField(name = "FillFlightNo")
    private String fillFlightNo;

    @JSONField(name = "BoardGate")
    private String boardGate;

    @JSONField(name = "BoardState")
    private String boardState;

    @JSONField(name = "FlightState")
    private String flightState;

    @JSONField(name = "FlightHTerminal")
    private String flightHterminal;

    @JSONField(name = "FlightTerminal")
    private String flightTerminal;

    @JSONField(name = "FlightDep")
    private String flightDep;

    @JSONField(name = "FlightArr")
    private String flightArr;

    @JSONField(name = "FlightDepAirport")
    private String flightDepAirport;

    @JSONField(name = "FlightArrAirport")
    private String flightArrAirport;

    @JSONField(name = "alternate_info")
    private String alternateInfo;

    @JSONField(name = "org_timezone")
    private String orgTimezone;

    @JSONField(name = "dst_timezone")
    private String dstTimezone;

    @JSONField(name = "fcategory")
    private String fcategory;

    @JSONField(name = "fid")
    private String fid;

    @JSONField(name = "BoardGateTime")
    private String boardGateTime;

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
        this.flightNo = flightNo;
    }

    public String getFlightDepcode() {
        return flightDepcode;
    }

    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    public String getFlightArrcode() {
        return flightArrcode;
    }

    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getFlightPosition() {
        return flightPosition;
    }

    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition;
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
        this.fdId = fdId;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany;
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
        this.shareFlightNo = shareFlightNo;
    }

    public String getFillFlightNo() {
        return fillFlightNo;
    }

    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo;
    }

    public String getBoardGate() {
        return boardGate;
    }

    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    public String getFlightHterminal() {
        return flightHterminal;
    }

    public void setFlightHterminal(String flightHterminal) {
        this.flightHterminal = flightHterminal;
    }

    public String getFlightTerminal() {
        return flightTerminal;
    }

    public void setFlightTerminal(String flightTerminal) {
        this.flightTerminal = flightTerminal;
    }

    public String getFlightDep() {
        return flightDep;
    }

    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep;
    }

    public String getFlightArr() {
        return flightArr;
    }

    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr;
    }

    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    public String getAlternateInfo() {
        return alternateInfo;
    }

    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo;
    }

    public String getOrgTimezone() {
        return orgTimezone;
    }

    public void setOrgTimezone(String orgTimezone) {
        this.orgTimezone = orgTimezone;
    }

    public String getDstTimezone() {
        return dstTimezone;
    }

    public void setDstTimezone(String dstTimezone) {
        this.dstTimezone = dstTimezone;
    }

    public String getFcategory() {
        return fcategory;
    }

    public void setFcategory(String fcategory) {
        this.fcategory = fcategory;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBoardGateTime() {
        return boardGateTime;
    }

    public void setBoardGateTime(String boardGateTime) {
        this.boardGateTime = boardGateTime;
    }

    @Override
    public String toString() {
        return "FlightMatch{" +
                "flightId=" + flightId +
                ", flightDate=" + flightDate +
                ", flightNo='" + flightNo + '\'' +
                ", flightDepcode='" + flightDepcode + '\'' +
                ", flightArrcode='" + flightArrcode + '\'' +
                ", planNo='" + planNo + '\'' +
                ", flightPosition='" + flightPosition + '\'' +
                ", boardInOut=" + boardInOut +
                ", isInOrOut=" + isInOrOut +
                ", isNearOrFar=" + isNearOrFar +
                ", fdId='" + fdId + '\'' +
                ", flightCompany='" + flightCompany + '\'' +
                ", flightDeptimePlanDate=" + flightDeptimePlanDate +
                ", flightArrtimePlanDate=" + flightArrtimePlanDate +
                ", flightDeptimeReadyDate=" + flightDeptimeReadyDate +
                ", flightArrtimeReadyDate=" + flightArrtimeReadyDate +
                ", flightDeptimeDate=" + flightDeptimeDate +
                ", flightArrtimeDate=" + flightArrtimeDate +
                ", stopFlag=" + stopFlag +
                ", shareFlag=" + shareFlag +
                ", shareFlightNo='" + shareFlightNo + '\'' +
                ", fillFlightNo='" + fillFlightNo + '\'' +
                ", boardGate='" + boardGate + '\'' +
                ", boardState='" + boardState + '\'' +
                ", flightState='" + flightState + '\'' +
                ", flightHterminal='" + flightHterminal + '\'' +
                ", flightTerminal='" + flightTerminal + '\'' +
                ", flightDep='" + flightDep + '\'' +
                ", flightArr='" + flightArr + '\'' +
                ", flightDepAirport='" + flightDepAirport + '\'' +
                ", flightArrAirport='" + flightArrAirport + '\'' +
                ", alternateInfo='" + alternateInfo + '\'' +
                ", orgTimezone='" + orgTimezone + '\'' +
                ", dstTimezone='" + dstTimezone + '\'' +
                ", fcategory='" + fcategory + '\'' +
                ", fid='" + fid + '\'' +
                ", boardGateTime='" + boardGateTime + '\'' +
                '}';
    }
    
}
