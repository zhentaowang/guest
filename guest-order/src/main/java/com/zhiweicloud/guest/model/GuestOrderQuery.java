package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.util.Date;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "GuestOrderQuery", description = "仅用订单条件查询使用")
public class GuestOrderQuery extends BaseEntity{

    @Transient
    @ApiModelProperty(value = "航班开始日期",hidden = true)//分页model，不会显示这个字段，用了hidden=true
    @JsonInclude(JsonInclude.Include.NON_NULL) //这个字段我们做分页查询的时候，不会查询出这个字段的结果集，所以其值就是null，那么就不会显示到分页列表的列中了。
    private Date queryFlightStartDate;

    @Transient
    @ApiModelProperty(value = "航班结束日期",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date queryFlightEndDate;

    @Transient
    @ApiModelProperty(value = "航班号",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String queryFlightNo;

    @Transient
    @ApiModelProperty(value = "进出港：出港：0，进港：1",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short queryIsInOrOut;

    @Transient
    @ApiModelProperty(value = "旅客姓名",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String queryPassengerName;

    @Transient
    @ApiModelProperty(value = "品  类： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short queryOrderType;

    @Transient
    @ApiModelProperty(value = "服务名称",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String queryServerName;

    @Transient
    @ApiModelProperty(value = "机构客户",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String queryCustomerName;

    @Transient
    @ApiModelProperty(value = "预定开始时间",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date queryBookingStartDate;

    @Transient
    @ApiModelProperty(value = "预定结束时间",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date queryBookingEndDate;

    @Transient
    @ApiModelProperty(value = "办票,0:是，1：否",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short queryIsPrintBoardingCheck;

    @Transient
    @ApiModelProperty(value = "托运, 0:是，1：否",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short queryIsConsign;

    @Transient
    @ApiModelProperty(value = "起始页",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;

    @Transient
    @ApiModelProperty(value = "每页显示数目",hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rows;

    public Date getQueryFlightStartDate() {
        return queryFlightStartDate;
    }

    public void setQueryFlightStartDate(Date queryFlightStartDate) {
        this.queryFlightStartDate = queryFlightStartDate;
    }

    public Date getQueryFlightEndDate() {
        return queryFlightEndDate;
    }

    public void setQueryFlightEndDate(Date queryFlightEndDate) {
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

    public Date getQueryBookingStartDate() {
        return queryBookingStartDate;
    }

    public void setQueryBookingStartDate(Date queryBookingStartDate) {
        this.queryBookingStartDate = queryBookingStartDate;
    }

    public Date getQueryBookingEndDate() {
        return queryBookingEndDate;
    }

    public void setQueryBookingEndDate(Date queryBookingEndDate) {
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
}
