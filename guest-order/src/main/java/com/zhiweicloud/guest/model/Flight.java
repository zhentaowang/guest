/**
 * Flight.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-17 11:34:33 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Flight.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-17 11:34:33 Created By zhangpengfei
 */
@ApiModel(value="Flight",description="flight")
public class Flight extends BaseEntity{
    @ApiModelProperty(value="订单id",name="orderId", required=true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value="航班日期",name="flightDate",hidden = true)
    @JsonFormat(timezone="GMT+8",pattern = "yyyy-MM-dd") //这里一定要加上时区，否则时间会少一天:比如数据库是　2017-01-18 查出来会是：2017-01-17　
    private Date flightDate;

    @Transient
    @ApiModelProperty(value = "航班日期", name = "flightDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String strFlightDate;

    @ApiModelProperty(value="航班号",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="航段",name="flightSegment")
    private String flightSegment;

    @ApiModelProperty(value="停机位",name="flightPosition")
    private String flightPosition;

    @ApiModelProperty(value="计划起飞时间",name="planTakeOffTime",hidden = true)
    @JsonFormat(timezone="GMT+8",pattern = "HH:mm:ss")
    private Date planTakeOffTime;

    @Transient
    @ApiModelProperty(value = "计划起飞时间", name = "planTakeOffTime")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(timezone="GMT+8",pattern = "hh:mm:ss")
    private String strPlanTakeOffTime;

    @ApiModelProperty(value="计划降落时间",name="planLandingTime",hidden = true)
    @JsonFormat(timezone="GMT+8",pattern = "hh:mm:ss")
    private Date planLandingTime;

    @Transient
    @ApiModelProperty(value = "计划降落时间", name = "planLandingTime")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(timezone="GMT+8",pattern = "HH:mm:ss")
    private String strPlanLandingTime;

    @ApiModelProperty(value="出港：0，进港：1",name="isInOrOut")
    private Short isInOrOut;

    @ApiModelProperty(value="登机口",name="boardingPort")
    private String boardingPort;

    @ApiModelProperty(value="远机位：0，近机位：1",name="isNearOrFar")
    private Short isNearOrFar;



    @ApiModelProperty(value="机号",name="planNo")
    private String planNo;

    /*@ApiModelProperty(value="服务时间",name="serverTime",hidden = true)
    @JsonFormat(timezone="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serverTime;

    @Transient
    @ApiModelProperty(value = "服务时间", name = "serverTime")
    private String strServerTime;*/

    @Transient
    @ApiModelProperty(value = "起飞时段,0:上午 6:00-12:00,1:下午 12：00-18：00,2:晚上 18：00-24：00", name = "takeOffTimeFlag" )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer takeOffTimeFlag;


    @Transient
    @ApiModelProperty(value = "降落时段,0:上午 6:00-12:00,1:下午 12：00-18：00,2:晚上 18：00-24：00", name = "landingTimeFlag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer landingTimeFlag;
    /**
     * 订单id
     * @return order_id 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 订单id
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
     * 航段
     * @return flight_segment 航段
     */
    public String getFlightSegment() {
        return flightSegment;
    }

    /**
     * 航段
     * @param flightSegment 航段
     */
    public void setFlightSegment(String flightSegment) {
        this.flightSegment = flightSegment;
    }

    /**
     * 停机位
     * @return flight_position 停机位
     */
    public String getFlightPosition() {
        return flightPosition;
    }

    /**
     * 停机位
     * @param flightPosition 停机位
     */
    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition;
    }

    /**
     * 计划起飞时间
     * @return plan_take_off_time 计划起飞时间
     */
    public Date getPlanTakeOffTime() {
        return planTakeOffTime;
    }

    /**
     * 计划起飞时间
     * @param planTakeOffTime 计划起飞时间
     */
    public void setPlanTakeOffTime(Date planTakeOffTime) {
        this.planTakeOffTime = planTakeOffTime;
    }

    /**
     * 计划降落时间
     * @return plan_landing_time 计划降落时间
     */
    public Date getPlanLandingTime() {
        return planLandingTime;
    }

    /**
     * 计划降落时间
     * @param planLandingTime 计划降落时间
     */
    public void setPlanLandingTime(Date planLandingTime) {
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

    public String getStrFlightDate() {
        return strFlightDate;
    }

    public void setStrFlightDate(String strFlightDate) {
        this.strFlightDate = strFlightDate;
    }

    public String getStrPlanTakeOffTime() {
        return strPlanTakeOffTime;
    }

    public void setStrPlanTakeOffTime(String strPlanTakeOffTime) {
        this.strPlanTakeOffTime = strPlanTakeOffTime;
    }

    public String getStrPlanLandingTime() {
        return strPlanLandingTime;
    }

    public void setStrPlanLandingTime(String strPlanLandingTime) {
        this.strPlanLandingTime = strPlanLandingTime;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Integer getTakeOffTimeFlag() {
        return takeOffTimeFlag;
    }

    public void setTakeOffTimeFlag(Integer takeOffTimeFlag) {
        this.takeOffTimeFlag = takeOffTimeFlag;
    }

    public Integer getLandingTimeFlag() {
        return landingTimeFlag;
    }

    public void setLandingTimeFlag(Integer landingTimeFlag) {
        this.landingTimeFlag = landingTimeFlag;
    }
}