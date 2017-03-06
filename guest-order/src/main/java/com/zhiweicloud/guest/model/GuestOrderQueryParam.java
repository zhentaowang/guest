package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Transient;
import java.util.Date;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "OrderInfoQuery", description = "仅用订单条件查询使用")
public class GuestOrderQueryParam {

    @Transient
    @ApiModelProperty(value = "航班开始日期")
    private String queryFlightStartDate;

    @Transient
    @ApiModelProperty(value = "航班结束日期")
    private String queryFlightEndDate;

    @Transient
    @ApiModelProperty(value = "航班号")
    private String queryFlightNo;

    @Transient
    @ApiModelProperty(value = "进出港：出港：0，进港：1")
    private Short queryIsInOrOut;

    @Transient
    @ApiModelProperty(value = "旅客姓名")
    private String queryPassengerName;

    @Transient
    @ApiModelProperty(value = "品  类： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单")
    private Short queryOrderType;

    @Transient
    @ApiModelProperty(value = "服务名称")
    private String queryServerName;

    @Transient
    @ApiModelProperty(value = "机构客户")
    private String queryCustomerName;

    @Transient
    @ApiModelProperty(value = "预定开始时间")
    private String queryBookingStartDate;

    @Transient
    @ApiModelProperty(value = "预定结束时间")
    private String queryBookingEndDate;

    @Transient
    @ApiModelProperty(value = "办票,0:是，1：否")
    private Short queryIsPrintBoardingCheck;

    @Transient
    @ApiModelProperty(value = "托运, 0:是，1：否")
    private Short queryIsConsign;

    @Transient
    @ApiModelProperty(value = "起始页",required = true)
    private Integer page;

    @Transient
    @ApiModelProperty(value = "每页显示数目",required = true)
    private Integer rows;

    @ApiModelProperty(value = "机场code", name = "airportCode", required = true)
    @NotEmpty
    private String airportCode;

    public String getQueryFlightStartDate() {
        return queryFlightStartDate;
    }

    public void setQueryFlightStartDate(String queryFlightStartDate) {
        this.queryFlightStartDate = queryFlightStartDate;
    }

    public String getQueryFlightEndDate() {
        return queryFlightEndDate;
    }

    public void setQueryFlightEndDate(String queryFlightEndDate) {
        this.queryFlightEndDate = queryFlightEndDate;
    }

    public String getQueryFlightNo() {
        return queryFlightNo;
    }

    public void setQueryFlightNo(String queryFlightNo) {
        this.queryFlightNo = queryFlightNo;
    }

    public Short getQueryIsInOrOut() {
        return queryIsInOrOut;
    }

    public void setQueryIsInOrOut(Short queryIsInOrOut) {
        this.queryIsInOrOut = queryIsInOrOut;
    }

    public String getQueryPassengerName() {
        return queryPassengerName;
    }

    public void setQueryPassengerName(String queryPassengerName) {
        this.queryPassengerName = queryPassengerName;
    }

    public Short getQueryOrderType() {
        return queryOrderType;
    }

    public void setQueryOrderType(Short queryOrderType) {
        this.queryOrderType = queryOrderType;
    }

    public String getQueryServerName() {
        return queryServerName;
    }

    public void setQueryServerName(String queryServerName) {
        this.queryServerName = queryServerName;
    }

    public String getQueryCustomerName() {
        return queryCustomerName;
    }

    public void setQueryCustomerName(String queryCustomerName) {
        this.queryCustomerName = queryCustomerName;
    }

    public String getQueryBookingStartDate() {
        return queryBookingStartDate;
    }

    public void setQueryBookingStartDate(String queryBookingStartDate) {
        this.queryBookingStartDate = queryBookingStartDate;
    }

    public String getQueryBookingEndDate() {
        return queryBookingEndDate;
    }

    public void setQueryBookingEndDate(String queryBookingEndDate) {
        this.queryBookingEndDate = queryBookingEndDate;
    }

    public Short getQueryIsPrintBoardingCheck() {
        return queryIsPrintBoardingCheck;
    }

    public void setQueryIsPrintBoardingCheck(Short queryIsPrintBoardingCheck) {
        this.queryIsPrintBoardingCheck = queryIsPrintBoardingCheck;
    }

    public Short getQueryIsConsign() {
        return queryIsConsign;
    }

    public void setQueryIsConsign(Short queryIsConsign) {
        this.queryIsConsign = queryIsConsign;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
