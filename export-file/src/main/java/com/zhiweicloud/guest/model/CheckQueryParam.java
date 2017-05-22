package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "CheckQueryParam", description = "仅用对账单条件查询使用")
public class CheckQueryParam extends BaseEntity{
    @ApiModelProperty(value = "航班开始日期", name = "queryFlightDateBegin")
    @QueryParam("queryFlightDateBegin")
    private String queryFlightDateBegin;

    @ApiModelProperty(value = "航班结束日期", name = "queryFlightDateEnd")
    @QueryParam("queryFlightDateEnd")
    private String queryFlightDateEnd;

    @ApiModelProperty(value = "登记开始日期", name = "queryCreateDateBegin")
    @QueryParam("queryCreateDateBegin")
    private String queryCreateDateBegin;

    @ApiModelProperty(value = "登记结束日期", name = "queryCreateDateEnd")
    @QueryParam("queryCreateDateEnd")
    private String queryCreateDateEnd;

    @ApiModelProperty(value = "客户名称", name = "queryCustomerName")
    @QueryParam("queryCustomerName")
    private String queryCustomerName;

    @ApiModelProperty(value = "协议名称", name = "queryProtocolName")
    @QueryParam("queryProtocolName")
    private String queryProtocolName;

    @ApiModelProperty(value = "协议类型,支持多选", name = "queryProtocolType")
    @QueryParam("queryProtocolType")
    private String queryProtocolType;

    @ApiModelProperty(value = "协议id", name = "queryProtocolId")
    @QueryParam("queryProtocolId")
    private String queryProtocolId;

    @ApiModelProperty(value = "产品", name = "queryProductId")
    @QueryParam("queryProductId")
    private String queryProductId;

    @ApiModelProperty(value = "类型", name = "type")
    @QueryParam("type")
    private String type;

    public String getQueryFlightDateBegin() {
        return queryFlightDateBegin;
    }

    public void setQueryFlightDateBegin(String queryFlightDateBegin) {
        this.queryFlightDateBegin = queryFlightDateBegin;
    }

    public String getQueryFlightDateEnd() {
        return queryFlightDateEnd;
    }

    public void setQueryFlightDateEnd(String queryFlightDateEnd) {
        this.queryFlightDateEnd = queryFlightDateEnd;
    }

    public String getQueryCreateDateBegin() {
        return queryCreateDateBegin;
    }

    public void setQueryCreateDateBegin(String queryCreateDateBegin) {
        this.queryCreateDateBegin = queryCreateDateBegin;
    }

    public String getQueryCreateDateEnd() {
        return queryCreateDateEnd;
    }

    public void setQueryCreateDateEnd(String queryCreateDateEnd) {
        this.queryCreateDateEnd = queryCreateDateEnd;
    }

    public String getQueryCustomerName() {
        return queryCustomerName;
    }

    public void setQueryCustomerName(String queryCustomerName) {
        this.queryCustomerName = queryCustomerName;
    }

    public String getQueryProtocolName() {
        return queryProtocolName;
    }

    public void setQueryProtocolName(String queryProtocolName) {
        this.queryProtocolName = queryProtocolName;
    }

    public String getQueryProtocolType() {
        return queryProtocolType;
    }

    public void setQueryProtocolType(String queryProtocolType) {
        this.queryProtocolType = queryProtocolType;
    }

    public String getQueryProductId() {
        return queryProductId;
    }

    public void setQueryProductId(String queryProductId) {
        this.queryProductId = queryProductId;
    }

    public String getQueryProtocolId() {
        return queryProtocolId;
    }

    public void setQueryProtocolId(String queryProtocolId) {
        this.queryProtocolId = queryProtocolId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
