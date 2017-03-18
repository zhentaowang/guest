package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户详情，服务信息/标签信息 返回类型
 * Created by zhengyiyin on 2017/3/16.
 */
@ApiModel(value="ServiceInfo",description="serviceInfo")
public class ServiceInfo {

    @ApiModelProperty(value="用户id",name="crmPassengerId")
    private Long crmPassengerId;

    @ApiModelProperty(value="航班时间",name="flightDate")
    private Date flightDate;

    @ApiModelProperty(value="订单号",name="orderNo")
    private String orderNo;

    @ApiModelProperty(value="产品名",name="productName")
    private String productName;

    @ApiModelProperty(value="客户名",name="clientName")
    private String clientName;

    @ApiModelProperty(value="协议号",name="protocolNo")
    private String protocolNo;

    @ApiModelProperty(value="协议类型",name="protocolType")
    private Short protocolType;


    /***********************标签信息的返回字段*********************************/

    @ApiModelProperty(value="公司名称",name="companyName")
    private String companyName;//暂时没有来源

    @ApiModelProperty(value="卡类别:0：金卡，1：银卡",name="cardType")
    private Short cardType;

    @ApiModelProperty(value="卡号",name="cardNo")
    private String cardNo;

    @ApiModelProperty(value="有效期",name="expireTime")
    private Date expireTime;

    @ApiModelProperty(value="单位",name="workUnit")
    private String workUnit;

    @ApiModelProperty(value="职位",name="workUnit")
    private String position;//暂时没有来源


    /************************************************************************/

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public Short getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(Short protocolType) {
        this.protocolType = protocolType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Short getCardType() {
        return cardType;
    }

    public void setCardType(Short cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getCrmPassengerId() {
        return crmPassengerId;
    }

    public void setCrmPassengerId(Long crmPassengerId) {
        this.crmPassengerId = crmPassengerId;
    }
}
