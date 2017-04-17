package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;

/**
 * FlightInfoQuery.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * Created by wzt on 2017/4/11.
 */
@ApiModel(value = "FlightInfoQuery", description = "仅用航班条件查询使用")
public class FlightInfoQuery extends BaseEntity{

    @ApiModelProperty(value = "航班号", name = "flightNo")
    @QueryParam("flightNo")
    private String flightNo;

    @ApiModelProperty(value = "航班日期", name = "flightDate")
    @QueryParam("flightDate")
    private String flightDate;

    @ApiModelProperty(value = "航班状态", name = "flightState")
    @QueryParam("flightState")
    private String flightState;

    @ApiModelProperty(value = "进出港", name = "isInOrOut")
    @QueryParam("isInOrOut")
    private String isInOrOut;

    @ApiModelProperty(value = "调度事件id", name = "scheduleEventIds")
    @QueryParam("scheduleEventIds")
    private String scheduleEventIds;

    @ApiModelProperty(value = "服务id", name = "servIds")
    @QueryParam("servIds")
    private String servIds;

    @ApiModelProperty(value = "服务类型id", name = "typeId")
    @QueryParam("typeId")
    private Long typeId;

    @ApiModelProperty(value = "订单是否为服务完成状态", name = "serverComplete")
    @QueryParam("serverComplete")
    private Long serverComplete;

    @ApiModelProperty(value = "起始页", name = "page")
    @QueryParam("page")
    private Integer page;

    @ApiModelProperty(value = "每页显示数目", name = "rows")
    @QueryParam("rows")
    private Integer rows;

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

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    public String getIsInOrOut() {
        return isInOrOut;
    }

    public void setIsInOrOut(String isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    public String getScheduleEventIds() {
        return scheduleEventIds;
    }

    public void setScheduleEventIds(String scheduleEventIds) {
        this.scheduleEventIds = scheduleEventIds;
    }

    public String getServIds() {
        return servIds;
    }

    public void setServIds(String servIds) {
        this.servIds = servIds;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getServerComplete() {
        return serverComplete;
    }

    public void setServerComplete(Long serverComplete) {
        this.serverComplete = serverComplete;
    }

}
