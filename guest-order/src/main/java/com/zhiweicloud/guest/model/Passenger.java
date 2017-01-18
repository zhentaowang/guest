/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:19:25 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import java.util.Date;

/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:19:25 Created By zhangpengfei
 */
@ApiModel(value = "Passenger", description = "passenger")
public class Passenger extends  BaseEntity{
    @ApiModelProperty(value = "订单id", name = "orderId", required = true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value = "旅客姓名", name = "name")
    private String name;

    @ApiModelProperty(value = "联系方式", name = "phone")
    private Long phone;

    @ApiModelProperty(value = "身份证", name = "identityCard")
    private String identityCard;

    @ApiModelProperty(value = "单位", name = "workUnit")
    private String workUnit;

    @ApiModelProperty(value = "贵宾卡", name = "vipCard")
    private String vipCard;

    @ApiModelProperty(value = "座位号", name = "sitNo")
    private String sitNo;

    @ApiModelProperty(value = "舱位", name = "cabinNo")
    private String cabinNo;

    @ApiModelProperty(value = "客票号", name = "ticketNo")
    private String ticketNo;

    @ApiModelProperty(value = "卡号", name = "cardNo")
    private String cardNo;

    @ApiModelProperty(value = "0：金卡，1：银卡", name = "cardType")
    private Short cardType;

    @ApiModelProperty(value = "有效期", name = "expireTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

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
     * 旅客姓名
     * @return name 旅客姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 旅客姓名
     * @param name 旅客姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 联系方式
     * @return phone 联系方式
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * 联系方式
     * @param phone 联系方式
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * 身份证
     * @return identity_card 身份证
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 身份证
     * @param identityCard 身份证
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 单位
     * @return work_unit 单位
     */
    public String getWorkUnit() {
        return workUnit;
    }

    /**
     * 单位
     * @param workUnit 单位
     */
    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    /**
     * 贵宾卡
     * @return vip_card 贵宾卡
     */
    public String getVipCard() {
        return vipCard;
    }

    /**
     * 贵宾卡
     * @param vipCard 贵宾卡
     */
    public void setVipCard(String vipCard) {
        this.vipCard = vipCard;
    }

    /**
     * 座位号
     * @return sit_no 座位号
     */
    public String getSitNo() {
        return sitNo;
    }

    /**
     * 座位号
     * @param sitNo 座位号
     */
    public void setSitNo(String sitNo) {
        this.sitNo = sitNo;
    }

    /**
     * 舱位
     * @return cabin_no 舱位
     */
    public String getCabinNo() {
        return cabinNo;
    }

    /**
     * 舱位
     * @param cabinNo 舱位
     */
    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo;
    }

    /**
     * 客票号
     * @return ticket_no 客票号
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * 客票号
     * @param ticketNo 客票号
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * 卡号
     * @return card_no 卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 卡号
     * @param cardNo 卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 0：金卡，1：银卡
     * @return card_type 0：金卡，1：银卡
     */
    public Short getCardType() {
        return cardType;
    }

    /**
     * 0：金卡，1：银卡
     * @param cardType 0：金卡，1：银卡
     */
    public void setCardType(Short cardType) {
        this.cardType = cardType;
    }

    /**
     * 有效期
     * @return expire_time 有效期
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 有效期
     * @param expireTime 有效期
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}