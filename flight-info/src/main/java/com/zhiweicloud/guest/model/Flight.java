/**
 * Flight.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-04 16:46:06 Created By wzt
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Flight.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-04 16:46:06 Created By wzt
*/
@ApiModel(value="Flight",description="flight")
public class Flight extends BaseEntity{
    @ApiModelProperty(value="主键自增id",name="flightId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long flightId;

    @ApiModelProperty(value="航班日期",name="flightDate")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="出发地三字码",name="flightDepcode")
    private String flightDepcode;

    @ApiModelProperty(value="目的地三字码",name="flightArrcode")
    private String flightArrcode;

    @ApiModelProperty(value="机号",name="planNo")
    private String planNo;

    @ApiModelProperty(value="机位",name="flightPosition")
    private String flightPosition;

    @ApiModelProperty(value="国际国内：国外：0，国内：1",name="boardInOut")
    private Short boardInOut;

    @ApiModelProperty(value="出港：0，进港1",name="isInOrOut")
    private Short isInOrOut;

    @ApiModelProperty(value="远机位：0，近机位：1",name="isNearOrFar")
    private Short isNearOrFar;

    @ApiModelProperty(value="服务时间",name="serverTime")
    private Date serverTime;

    @ApiModelProperty(value="忽视",name="fdId")
    private String fdId;

    @ApiModelProperty(value="航空公司名称",name="flightCompany")
    private String flightCompany;

    @ApiModelProperty(value="计划起飞时间（yyyy-mm-dd hh-mm-ss格式）",name="flightDeptimePlanDate")
    private Date flightDeptimePlanDate;

    @ApiModelProperty(value="计划到达时间（yyyy-mm-dd hh-mm-ss格式）",name="flightArrtimePlanDate")
    private Date flightArrtimePlanDate;

    @ApiModelProperty(value="预计起飞时间（yyyy-mm-dd hh-mm-ss格式）",name="flightDeptimeReadyDate")
    private Date flightDeptimeReadyDate;

    @ApiModelProperty(value="预计到达时间（yyyy-mm-dd hh-mm-ss格式）",name="flightArrtimeReadyDate")
    private Date flightArrtimeReadyDate;

    @ApiModelProperty(value="实际起飞时间（yyyy-mm-dd hh-mm-ss格式）",name="flightDeptimeDate")
    private Date flightDeptimeDate;

    @ApiModelProperty(value="实际到达时间（yyyy-mm-dd hh-mm-ss格式）",name="flightArrtimeDate")
    private Date flightArrtimeDate;

    @ApiModelProperty(value="是否 经停 （0:不经停;1:经停 ）",name="stopFlag")
    private Short stopFlag;

    @ApiModelProperty(value="是否 共享 （0:不共享;1:共享 ）",name="shareFlag")
    private Short shareFlag;

    @ApiModelProperty(value="共享航班号",name="shareFlightNo")
    private String shareFlightNo;

    @ApiModelProperty(value="补班 航班号 （取消 的航班才有此字段）",name="fillFlightNo")
    private String fillFlightNo;

    @ApiModelProperty(value="登机口",name="boardGate")
    private String boardGate;

    @ApiModelProperty(value="乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）",name="boardState")
    private String boardState;

    @ApiModelProperty(value="航班状态（计划，起飞，到达，延误，取消，备降，返航）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return)",name="flightState")
    private String flightState;

    @ApiModelProperty(value="候机楼",name="flightHterminal")
    private String flightHterminal;

    @ApiModelProperty(value="接机楼",name="flightTerminal")
    private String flightTerminal;

    @ApiModelProperty(value="出发城市名",name="flightDep")
    private String flightDep;

    @ApiModelProperty(value="到达城市名",name="flightArr")
    private String flightArr;

    @ApiModelProperty(value="出发机场名",name="flightDepAirport")
    private String flightDepAirport;

    @ApiModelProperty(value="到达机场名",name="flightArrAirport")
    private String flightArrAirport;

    @ApiModelProperty(value="备降信息节点",name="alternateInfo")
    private String alternateInfo;

    @ApiModelProperty(value="出发地时区",name="orgTimezone")
    private String orgTimezone;

    @ApiModelProperty(value="目的地时区",name="dstTimezone")
    private String dstTimezone;

    @ApiModelProperty(value="航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）",name="fcategory")
    private String fcategory;

    @ApiModelProperty(value="按商户传递原值返回",name="fid")
    private String fid;

    @ApiModelProperty(value="安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）",name="boardGateTime")
    private String boardGateTime;

    @Transient
    @ApiModelProperty(value="调度时间",name="scheduleTime")
    private Date scheduleTime;

    @Transient
    @ApiModelProperty(value="调度事件名",name="scheduleEventName")
    private String scheduleEventName;

    @Transient
    @ApiModelProperty(value="调度事件id",name="scheduleEventId", required=true)
    private Long scheduleEventId;

    @Transient
    @ApiModelProperty(value="服务名称",name="serviceName")
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getScheduleEventId() {
        return scheduleEventId;
    }

    public void setScheduleEventId(Long scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleEventName() {
        return scheduleEventName;
    }

    public void setScheduleEventName(String scheduleEventName) {
        this.scheduleEventName = scheduleEventName;
    }

    /**
     * 主键自增id
     * @return flight_id 主键自增id
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * 主键自增id
     * @param flightId 主键自增id
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * 航班日期
     * @return flight_date 航班日期
     */
    public Date getFlightDate() {
        return flightDate;
    }

    /**
     * 航班日期
     * @param flightDate 航班日期
     */
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * 航班号
     * @return flight_no 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 航班号
     * @param flightNo 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 出发地三字码
     * @return flight_depcode 出发地三字码
     */
    public String getFlightDepcode() {
        return flightDepcode;
    }

    /**
     * 出发地三字码
     * @param flightDepcode 出发地三字码
     */
    public void setFlightDepcode(String flightDepcode) {
        this.flightDepcode = flightDepcode;
    }

    /**
     * 目的地三字码
     * @return flight_arrcode 目的地三字码
     */
    public String getFlightArrcode() {
        return flightArrcode;
    }

    /**
     * 目的地三字码
     * @param flightArrcode 目的地三字码
     */
    public void setFlightArrcode(String flightArrcode) {
        this.flightArrcode = flightArrcode;
    }

    /**
     * 机号
     * @return plan_no 机号
     */
    public String getPlanNo() {
        return planNo;
    }

    /**
     * 机号
     * @param planNo 机号
     */
    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    /**
     * 机位
     * @return flight_position 机位
     */
    public String getFlightPosition() {
        return flightPosition;
    }

    /**
     * 机位
     * @param flightPosition 机位
     */
    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition;
    }

    /**
     * 国际国内：国外：0，国内：1
     * @return board_in_out 国际国内：国外：0，国内：1
     */
    public Short getBoardInOut() {
        return boardInOut;
    }

    /**
     * 国际国内：国外：0，国内：1
     * @param boardInOut 国际国内：国外：0，国内：1
     */
    public void setBoardInOut(Short boardInOut) {
        this.boardInOut = boardInOut;
    }

    /**
     * 出港：0，进港1
     * @return is_in_or_out 出港：0，进港1
     */
    public Short getIsInOrOut() {
        return isInOrOut;
    }

    /**
     * 出港：0，进港1
     * @param isInOrOut 出港：0，进港1
     */
    public void setIsInOrOut(Short isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    /**
     * 远机位：0，近机位：1
     * @return is_near_or_far 远机位：0，近机位：1
     */
    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    /**
     * 远机位：0，近机位：1
     * @param isNearOrFar 远机位：0，近机位：1
     */
    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }

    /**
     * 服务时间
     * @return server_time 服务时间
     */
    public Date getServerTime() {
        return serverTime;
    }

    /**
     * 服务时间
     * @param serverTime 服务时间
     */
    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    /**
     * 忽视
     * @return fd_id 忽视
     */
    public String getFdId() {
        return fdId;
    }

    /**
     * 忽视
     * @param fdId 忽视
     */
    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    /**
     * 航空公司名称
     * @return flight_company 航空公司名称
     */
    public String getFlightCompany() {
        return flightCompany;
    }

    /**
     * 航空公司名称
     * @param flightCompany 航空公司名称
     */
    public void setFlightCompany(String flightCompany) {
        this.flightCompany = flightCompany;
    }

    /**
     * 计划起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_deptime_plan_date 计划起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightDeptimePlanDate() {
        return flightDeptimePlanDate;
    }

    /**
     * 计划起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightDeptimePlanDate 计划起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightDeptimePlanDate(Date flightDeptimePlanDate) {
        this.flightDeptimePlanDate = flightDeptimePlanDate;
    }

    /**
     * 计划到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_arrtime_plan_date 计划到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightArrtimePlanDate() {
        return flightArrtimePlanDate;
    }

    /**
     * 计划到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightArrtimePlanDate 计划到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightArrtimePlanDate(Date flightArrtimePlanDate) {
        this.flightArrtimePlanDate = flightArrtimePlanDate;
    }

    /**
     * 预计起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_deptime_ready_date 预计起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightDeptimeReadyDate() {
        return flightDeptimeReadyDate;
    }

    /**
     * 预计起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightDeptimeReadyDate 预计起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightDeptimeReadyDate(Date flightDeptimeReadyDate) {
        this.flightDeptimeReadyDate = flightDeptimeReadyDate;
    }

    /**
     * 预计到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_arrtime_ready_date 预计到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightArrtimeReadyDate() {
        return flightArrtimeReadyDate;
    }

    /**
     * 预计到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightArrtimeReadyDate 预计到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightArrtimeReadyDate(Date flightArrtimeReadyDate) {
        this.flightArrtimeReadyDate = flightArrtimeReadyDate;
    }

    /**
     * 实际起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_deptime_date 实际起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightDeptimeDate() {
        return flightDeptimeDate;
    }

    /**
     * 实际起飞时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightDeptimeDate 实际起飞时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightDeptimeDate(Date flightDeptimeDate) {
        this.flightDeptimeDate = flightDeptimeDate;
    }

    /**
     * 实际到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @return flight_arrtime_date 实际到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public Date getFlightArrtimeDate() {
        return flightArrtimeDate;
    }

    /**
     * 实际到达时间（yyyy-mm-dd hh-mm-ss格式）
     * @param flightArrtimeDate 实际到达时间（yyyy-mm-dd hh-mm-ss格式）
     */
    public void setFlightArrtimeDate(Date flightArrtimeDate) {
        this.flightArrtimeDate = flightArrtimeDate;
    }

    /**
     * 是否 经停 （0:不经停;1:经停 ）
     * @return stop_flag 是否 经停 （0:不经停;1:经停 ）
     */
    public Short getStopFlag() {
        return stopFlag;
    }

    /**
     * 是否 经停 （0:不经停;1:经停 ）
     * @param stopFlag 是否 经停 （0:不经停;1:经停 ）
     */
    public void setStopFlag(Short stopFlag) {
        this.stopFlag = stopFlag;
    }

    /**
     * 是否 共享 （0:不共享;1:共享 ）
     * @return share_flag 是否 共享 （0:不共享;1:共享 ）
     */
    public Short getShareFlag() {
        return shareFlag;
    }

    /**
     * 是否 共享 （0:不共享;1:共享 ）
     * @param shareFlag 是否 共享 （0:不共享;1:共享 ）
     */
    public void setShareFlag(Short shareFlag) {
        this.shareFlag = shareFlag;
    }

    /**
     * 共享航班号
     * @return share_flight_no 共享航班号
     */
    public String getShareFlightNo() {
        return shareFlightNo;
    }

    /**
     * 共享航班号
     * @param shareFlightNo 共享航班号
     */
    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     * @return fill_flight_no 补班 航班号 （取消 的航班才有此字段）
     */
    public String getFillFlightNo() {
        return fillFlightNo;
    }

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     * @param fillFlightNo 补班 航班号 （取消 的航班才有此字段）
     */
    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo;
    }

    /**
     * 登机口
     * @return board_gate 登机口
     */
    public String getBoardGate() {
        return boardGate;
    }

    /**
     * 登机口
     * @param boardGate 登机口
     */
    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate;
    }

    /**
     * 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     * @return board_state 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     */
    public String getBoardState() {
        return boardState;
    }

    /**
     * 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     * @param boardState 乘机状态（ 开始值机，值机结束，开始登机，催促登机，登机结束 ）
     */
    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return)
     * @return flight_state 航班状态（计划，起飞，到达，延误，取消，备降，返航）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return)
     */
    public String getFlightState() {
        return flightState;
    }

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return)
     * @param flightState 航班状态（计划，起飞，到达，延误，取消，备降，返航）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return)
     */
    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    /**
     * 候机楼
     * @return flight_hterminal 候机楼
     */
    public String getFlightHterminal() {
        return flightHterminal;
    }

    /**
     * 候机楼
     * @param flightHterminal 候机楼
     */
    public void setFlightHterminal(String flightHterminal) {
        this.flightHterminal = flightHterminal;
    }

    /**
     * 接机楼
     * @return flight_terminal 接机楼
     */
    public String getFlightTerminal() {
        return flightTerminal;
    }

    /**
     * 接机楼
     * @param flightTerminal 接机楼
     */
    public void setFlightTerminal(String flightTerminal) {
        this.flightTerminal = flightTerminal;
    }

    /**
     * 出发城市名
     * @return flight_dep 出发城市名
     */
    public String getFlightDep() {
        return flightDep;
    }

    /**
     * 出发城市名
     * @param flightDep 出发城市名
     */
    public void setFlightDep(String flightDep) {
        this.flightDep = flightDep;
    }

    /**
     * 到达城市名
     * @return flight_arr 到达城市名
     */
    public String getFlightArr() {
        return flightArr;
    }

    /**
     * 到达城市名
     * @param flightArr 到达城市名
     */
    public void setFlightArr(String flightArr) {
        this.flightArr = flightArr;
    }

    /**
     * 出发机场名
     * @return flight_dep_airport 出发机场名
     */
    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    /**
     * 出发机场名
     * @param flightDepAirport 出发机场名
     */
    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    /**
     * 到达机场名
     * @return flight_arr_airport 到达机场名
     */
    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    /**
     * 到达机场名
     * @param flightArrAirport 到达机场名
     */
    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    /**
     * 备降信息节点
     * @return alternate_info 备降信息节点
     */
    public String getAlternateInfo() {
        return alternateInfo;
    }

    /**
     * 备降信息节点
     * @param alternateInfo 备降信息节点
     */
    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo;
    }

    /**
     * 出发地时区
     * @return org_timezone 出发地时区
     */
    public String getOrgTimezone() {
        return orgTimezone;
    }

    /**
     * 出发地时区
     * @param orgTimezone 出发地时区
     */
    public void setOrgTimezone(String orgTimezone) {
        this.orgTimezone = orgTimezone;
    }

    /**
     * 目的地时区
     * @return dst_timezone 目的地时区
     */
    public String getDstTimezone() {
        return dstTimezone;
    }

    /**
     * 目的地时区
     * @param dstTimezone 目的地时区
     */
    public void setDstTimezone(String dstTimezone) {
        this.dstTimezone = dstTimezone;
    }

    /**
     * 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     * @return fcategory 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     */
    public String getFcategory() {
        return fcategory;
    }

    /**
     * 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     * @param fcategory 航班属性（0:国内-国内;1国内-国际;2国内-地区;3:地区-国际;4:国际-国际;5:未知）
     */
    public void setFcategory(String fcategory) {
        this.fcategory = fcategory;
    }

    /**
     * 按商户传递原值返回
     * @return fid 按商户传递原值返回
     */
    public String getFid() {
        return fid;
    }

    /**
     * 按商户传递原值返回
     * @param fid 按商户传递原值返回
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     * @return board_gate_time 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     */
    public String getBoardGateTime() {
        return boardGateTime;
    }

    /**
     * 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     * @param boardGateTime 安检口 到登机口 的步行时间 （0 代表交通方式为步行；1 代表交通方式为机场小火车 ）
     */
    public void setBoardGateTime(String boardGateTime) {
        this.boardGateTime = boardGateTime;
    }
}