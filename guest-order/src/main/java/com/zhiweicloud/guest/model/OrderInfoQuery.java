package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;

/**
 *
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "OrderInfoQuery", description = "仅用订单条件查询使用")
public class OrderInfoQuery extends BaseEntity{

    @ApiModelProperty(value = "乘客身份证号码", name = "queryIdentityCard")
    @QueryParam("queryIdentityCard")
    private String queryIdentityCard;

    @ApiModelProperty(value = "乘客姓名", name = "queryPassengerName")
    @QueryParam("queryPassengerName")
    private String queryPassengerName;

    @ApiModelProperty(value = "航班日期", name = "queryFlightDate")
    @QueryParam("queryFlightDate")
    private String queryFlightDate;

    @ApiModelProperty(value = "航班号", name = "queryFlightNo")
    @QueryParam("queryFlightNo")
    private String queryFlightNo;

    @ApiModelProperty(value = "协议id", name = "queryProtocolId")
    @QueryParam("queryProtocolIds")
    private String queryProtocolIds;

    @ApiModelProperty(value = "产品id", name = "queryProductId")
    @QueryParam("queryProductId")
    private String queryProductId;

    @ApiModelProperty(value = "是否重要订单：0：重要，1：不重要", name = "queryIsImportant")
    @QueryParam("queryIsImportant")
    private String queryIsImportant;

    @ApiModelProperty(value = "提前一天预约", name = "queryBookingOneDayBefore")
    @QueryParam("queryBookingOneDayBefore")
    private String queryBookingOneDayBefore;

    @ApiModelProperty(value = "按照航班起飞时间或者降落时间排序：0：起飞顺序，1：起飞倒序，2：降落顺序，3：降落倒序", name = "queryOrderBy")
    @QueryParam("queryOrderBy")
    private Integer queryOrderBy;

    @ApiModelProperty(value = "订单状态:预约草稿，已预约，预约取消，已使用，服务草稿，服务取消", name = "queryOrderStatus")
    @QueryParam("queryOrderStatus")
    private String queryOrderStatus;

    @ApiModelProperty(value = "预约人id", name = "queryBookingIds")
    @QueryParam("queryBookingIds")
    private String queryBookingIds;


    @ApiModelProperty(value = "客户名称/协议名/预约号/预约人信息", name = "queryCustomerInfo")
    @QueryParam("queryCustomerInfo")
    private String queryCustomerInfo;

    @ApiModelProperty(value = "订单类型：0：预约订单，1：服务订单", name = "queryOrderType")
    @QueryParam("queryOrderType")
    private Integer queryOrderType;

    @ApiModelProperty(value = "出港：0，进港1", name = "queryIsInOrOut")
    @QueryParam("queryIsInOrOut")
    private String queryIsInOrOut;

    @ApiModelProperty(value = "附加服务单:1：代表查询的是勾选了代办登机牌，或者代托行李的订单", name = "queryAttServerOrderList")
    @QueryParam("queryAttServerOrderList")
    private String queryAttServerOrderList;

    @ApiModelProperty(value = "附加服务单 订单是否被安排，0：未安排，1：已安排，2：已完成", name = "queryAgentPerson")
    @QueryParam("queryAgentPerson")
    private String queryAgentPerson;

    @ApiModelProperty(value = "订单号", name = "queryOrderNo")
    @QueryParam("queryOrderNo")
    private String queryOrderNo;

    /**
     * 格式 "1,2,3"
     */
    @ApiModelProperty(value = "服务类别: 1:贵宾厅,5:休息室", name = "serviceId")
    @QueryParam("serviceId")
    private String serviceId;

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

    public Integer getQueryOrderBy() {
        return queryOrderBy;
    }

    public void setQueryOrderBy(Integer queryOrderBy) {
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

    public Integer getQueryOrderType() {
        return queryOrderType;
    }

    public void setQueryOrderType(Integer queryOrderType) {
        this.queryOrderType = queryOrderType;
    }

    public String getQueryIdentityCard() {
        return queryIdentityCard;
    }

    public void setQueryIdentityCard(String queryIdentityCard) {
        this.queryIdentityCard = queryIdentityCard;
    }

    public String getQueryIsInOrOut() {
        return queryIsInOrOut;
    }

    public void setQueryIsInOrOut(String queryIsInOrOut) {
        this.queryIsInOrOut = queryIsInOrOut;
    }

    public String getQueryAttServerOrderList() {
        return queryAttServerOrderList;
    }

    public void setQueryAttServerOrderList(String queryAttServerOrderList) {
        this.queryAttServerOrderList = queryAttServerOrderList;
    }

    public String getQueryAgentPerson() {
        return queryAgentPerson;
    }

    public void setQueryAgentPerson(String queryAgentPerson) {
        this.queryAgentPerson = queryAgentPerson;
    }

    public String getQueryOrderNo() {
        return queryOrderNo;
    }

    public void setQueryOrderNo(String queryOrderNo) {
        this.queryOrderNo = queryOrderNo;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
