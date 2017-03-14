/**
 * OrderServiceRecord.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-04 14:30:12 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * OrderServiceRecord.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-04 14:30:12 Created By zhengyiyin
*/
@ApiModel(value="OrderServiceRecord",description="order_service_record")
public class OrderServiceRecord extends BaseEntity{

    @ApiModelProperty(value="主键id",name="orderServiceRecordId", required=true)
    @NotEmpty
    private Long orderServiceRecordId;

    @ApiModelProperty(value="订单id",name="orderId")
    private Long orderId;

    @ApiModelProperty(value="服务动态",name="recordDesc")
    private String recordDesc;


    //以下是订单里的信息，插入服务动态时，作为参数
    @Transient
    @ApiModelProperty(value="安排代办人",name="agentPerson")
    private Long agentPerson;

    @Transient
    @ApiModelProperty(value="安排代办人",name="agentPersonName")
    private String agentPersonName;

    @Transient
    @ApiModelProperty(value="代办完成:1：是，0：否",name="agentComplete")
    private Short agentComplete;

    @Transient
    @ApiModelProperty(value="证件",name="serverCardNo")
    private String serverCardNo;

    @Transient
    @ApiModelProperty(value="贵宾卡",name="vipCard")
    private String vipCard;

    @Transient
    @ApiModelProperty(value="现金",name="cash")
    private BigDecimal cash;

    @Transient
    @ApiModelProperty(value="座位号",name="sitNo")
    private String sitNo;

    @Transient
    @ApiModelProperty(value="是否托运",name="consign")
    private Short consign;

    @Transient
    @ApiModelProperty(value="代办登机牌 0:需要，1：不需要",name="printCheck")
    private Short printCheck;


    /**
     * 主键id
     * @return order_service_record_id 主键id
     */
    public Long getOrderServiceRecordId() {
        return orderServiceRecordId;
    }

    /**
     * 主键id
     * @param orderServiceRecordId 主键id
     */
    public void setOrderServiceRecordId(Long orderServiceRecordId) {
        this.orderServiceRecordId = orderServiceRecordId;
    }

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
     * 服务动态
     * @return record_desc 服务动态
     */
    public String getRecordDesc() {
        return recordDesc;
    }

    /**
     * 服务动态
     * @param recordDesc 服务动态
     */
    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public Long getAgentPerson() {
        return agentPerson;
    }

    public void setAgentPerson(Long agentPerson) {
        this.agentPerson = agentPerson;
    }

    public Short getAgentComplete() {
        return agentComplete;
    }

    public void setAgentComplete(Short agentComplete) {
        this.agentComplete = agentComplete;
    }

    public String getServerCardNo() {
        return serverCardNo;
    }

    public void setServerCardNo(String serverCardNo) {
        this.serverCardNo = serverCardNo;
    }

    public String getVipCard() {
        return vipCard;
    }

    public void setVipCard(String vipCard) {
        this.vipCard = vipCard;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getSitNo() {
        return sitNo;
    }

    public void setSitNo(String sitNo) {
        this.sitNo = sitNo;
    }

    public Short getConsign() {
        return consign;
    }

    public void setConsign(Short consign) {
        this.consign = consign;
    }

    public Short getPrintCheck() {
        return printCheck;
    }

    public void setPrintCheck(Short printCheck) {
        this.printCheck = printCheck;
    }

    public String getAgentPersonName() {
        return agentPersonName;
    }

    public void setAgentPersonName(String agentPersonName) {
        this.agentPersonName = agentPersonName;
    }
}