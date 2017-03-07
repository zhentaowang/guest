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
@ApiModel(value = "OrderInfoQuery", description = "仅用订单条件查询使用")
public class OrderInfoQuery extends BaseEntity{

    @ApiModelProperty(value = "乘客身份证号码", name = "queryIdentityCard")
    @Transient
    private String queryIdentityCard;

    @ApiModelProperty(value = "乘客姓名", name = "queryPassengerName")
    @Transient
    private String queryPassengerName;

    @ApiModelProperty(value = "航班日期", name = "queryFlightDate")
    @Transient
    private String queryFlightDate;

    @ApiModelProperty(value = "航班号", name = "queryFlightNo")
    @Transient
    private String queryFlightNo;

    @ApiModelProperty(value = "协议id", name = "queryProtocolId")
    @Transient
    private String queryProtocolIds;

    @ApiModelProperty(value = "产品id", name = "queryProductId")
    @Transient
    private String queryProductId;

    @ApiModelProperty(value = "是否重要订单：0：重要，1：不重要", name = "queryIsImportant")
    @Transient
    private String queryIsImportant;

    @ApiModelProperty(value = "提前一天预约", name = "queryBookingOneDayBefore")
    @Transient
    private String queryBookingOneDayBefore;

    @ApiModelProperty(value = "按照航班起飞时间或者降落时间排序：0：起飞顺序，1：起飞倒序，2：降落顺序，3：降落倒序", name = "queryOrderBy")
    @Transient
    private String queryOrderBy;

    @ApiModelProperty(value = "订单状态", name = "queryOrderStatus")
    @Transient
    private String queryOrderStatus;

    @ApiModelProperty(value = "预约人id", name = "queryBookingIds")
    @Transient
    private String queryBookingIds;


    @ApiModelProperty(value = "客户名称/协议名/预约号/预约人信息", name = "queryCustomerInfo")
    @Transient
    private String queryCustomerInfo;

    @ApiModelProperty(value = "订单类型：0：预约订单，1：服务订单", name = "queryOrderType")
    @Transient
    private String queryOrderType;


    public String getQueryPassengerName() {
        return queryPassengerName;
    }

    public void setQueryPassengerName(String queryPassengerName) {
        this.queryPassengerName = queryPassengerName;
    }

    public String getQueryFlightDate() {
        return queryFlightDate;
    }

    public void setQueryFlightDate(String queryFlightDate) {
        this.queryFlightDate = queryFlightDate;
    }

    public String getQueryFlightNo() {
        return queryFlightNo;
    }

    public void setQueryFlightNo(String queryFlightNo) {
        this.queryFlightNo = queryFlightNo;
    }

    public String getQueryProtocolIds() {
        return queryProtocolIds;
    }

    public void setQueryProtocolIds(String queryProtocolIds) {
        this.queryProtocolIds = queryProtocolIds;
    }

    public String getQueryProductId() {
        return queryProductId;
    }

    public void setQueryProductId(String queryProductId) {
        this.queryProductId = queryProductId;
    }

    public String getQueryIsImportant() {
        return queryIsImportant;
    }

    public void setQueryIsImportant(String queryIsImportant) {
        this.queryIsImportant = queryIsImportant;
    }

    public String getQueryBookingOneDayBefore() {
        return queryBookingOneDayBefore;
    }

    public void setQueryBookingOneDayBefore(String queryBookingOneDayBefore) {
        this.queryBookingOneDayBefore = queryBookingOneDayBefore;
    }

    public String getQueryOrderBy() {
        return queryOrderBy;
    }

    public void setQueryOrderBy(String queryOrderBy) {
        this.queryOrderBy = queryOrderBy;
    }

    public String getQueryOrderStatus() {
        return queryOrderStatus;
    }

    public void setQueryOrderStatus(String queryOrderStatus) {
        this.queryOrderStatus = queryOrderStatus;
    }

    public String getQueryBookingIds() {
        return queryBookingIds;
    }

    public void setQueryBookingIds(String queryBookingIds) {
        this.queryBookingIds = queryBookingIds;
    }

    public String getQueryCustomerInfo() {
        return queryCustomerInfo;
    }

    public void setQueryCustomerInfo(String queryCustomerInfo) {
        this.queryCustomerInfo = queryCustomerInfo;
    }

    public String getQueryOrderType() {
        return queryOrderType;
    }

    public void setQueryOrderType(String queryOrderType) {
        this.queryOrderType = queryOrderType;
    }

    public String getQueryIdentityCard() {
        return queryIdentityCard;
    }

    public void setQueryIdentityCard(String queryIdentityCard) {
        this.queryIdentityCard = queryIdentityCard;
    }
}
