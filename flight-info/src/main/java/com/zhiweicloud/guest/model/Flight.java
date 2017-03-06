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

    @ApiModelProperty(value="调度事件id",name="scheduleEventId")
    private Long scheduleEventId;

    @ApiModelProperty(value="航班日期",name="flightDate")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="出发地三字码",name="flightDepcode")
    private String flightDepcode;

    @ApiModelProperty(value="目的地三字码",name="flightArrcode")
    private String flightArrcode;

    @ApiModelProperty(value="出发城市名",name="flightDep")
    private String flightDep;

    @ApiModelProperty(value="到达城市名",name="flightArr")
    private String flightArr;

    @ApiModelProperty(value="出发机场名",name="flightDepAirport")
    private String flightDepAirport;

    @ApiModelProperty(value="到达机场名",name="flightArrAirport")
    private String flightArrAirport;

    @ApiModelProperty(value="机号",name="planNo")
    private String planNo;

    @ApiModelProperty(value="机位",name="flightPosition")
    private String flightPosition;

    @ApiModelProperty(value="国际国内：国外：0，国内：1",name="boardInOut")
    private Short boardInOut;

    @ApiModelProperty(value="航班状态",name="flightStatus")
    private Short flightStatus;

    @ApiModelProperty(value="实际降落时间",name="realLandingTime")
    private String realLandingTime;

    @ApiModelProperty(value="实际起飞时间",name="realTakeOffTime")
    private String realTakeOffTime;

    @ApiModelProperty(value="计划起飞时间",name="planTakeOffTime")
    private String planTakeOffTime;

    @ApiModelProperty(value="计划降落时间",name="planLandingTime")
    private String planLandingTime;

    @ApiModelProperty(value="出港：0，进港1",name="isInOrOut")
    private Short isInOrOut;

    @ApiModelProperty(value="登机口",name="boardingPort")
    private String boardingPort;

    @ApiModelProperty(value="登机状态",name="boardStatus")
    private Short boardStatus;

    @ApiModelProperty(value="远机位：0，近机位：1",name="isNearOrFar")
    private Short isNearOrFar;

    @ApiModelProperty(value="服务时间",name="serverTime")
    private Date serverTime;

    @Transient
    @ApiModelProperty(value="调度时间",name="scheduleTime")
    private Date scheduleTime;

    @Transient
    @ApiModelProperty(value="调度事件名",name="scheduleEventName")
    private String scheduleEventName;

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
     * 调度事件id
     * @return schedule_event_id 调度事件id
     */
    public Long getScheduleEventId() {
        return scheduleEventId;
    }

    /**
     * 调度事件id
     * @param scheduleEventId 调度事件id
     */
    public void setScheduleEventId(Long scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
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
     * 航班状态
     * @return flight_status 航班状态
     */
    public Short getFlightStatus() {
        return flightStatus;
    }

    /**
     * 航班状态
     * @param flightStatus 航班状态
     */
    public void setFlightStatus(Short flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * 实际降落时间
     * @return real_landing_time 实际降落时间
     */
    public String getRealLandingTime() {
        return realLandingTime;
    }

    /**
     * 实际降落时间
     * @param realLandingTime 实际降落时间
     */
    public void setRealLandingTime(String realLandingTime) {
        this.realLandingTime = realLandingTime;
    }

    /**
     * 实际起飞时间
     * @return real_take_off_time 实际起飞时间
     */
    public String getRealTakeOffTime() {
        return realTakeOffTime;
    }

    /**
     * 实际起飞时间
     * @param realTakeOffTime 实际起飞时间
     */
    public void setRealTakeOffTime(String realTakeOffTime) {
        this.realTakeOffTime = realTakeOffTime;
    }

    /**
     * 计划起飞时间
     * @return plan_take_off_time 计划起飞时间
     */
    public String getPlanTakeOffTime() {
        return planTakeOffTime;
    }

    /**
     * 计划起飞时间
     * @param planTakeOffTime 计划起飞时间
     */
    public void setPlanTakeOffTime(String planTakeOffTime) {
        this.planTakeOffTime = planTakeOffTime;
    }

    /**
     * 计划降落时间
     * @return plan_landing_time 计划降落时间
     */
    public String getPlanLandingTime() {
        return planLandingTime;
    }

    /**
     * 计划降落时间
     * @param planLandingTime 计划降落时间
     */
    public void setPlanLandingTime(String planLandingTime) {
        this.planLandingTime = planLandingTime;
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
     * 登机口
     * @return boarding_port 登机口
     */
    public String getBoardingPort() {
        return boardingPort;
    }

    /**
     * 登机口
     * @param boardingPort 登机口
     */
    public void setBoardingPort(String boardingPort) {
        this.boardingPort = boardingPort;
    }

    /**
     * 登机状态：0，登机未开始；1，登机开始；2，登机结束
     * @return boardStatus 登机状态
     */
    public Short getBoardStatus() {
        return boardStatus;
    }

    /**
     * 登机状态：0，登机未开始；1，登机开始；2，登机结束
     * @param boardStatus 登机状态
     */
    public void setBoardStatus(Short boardStatus) {
        this.boardStatus = boardStatus;
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
}