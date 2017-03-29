/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-28 17:39:03 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import com.zhiweicloud.guest.common.OrderConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-28 17:39:03 Created By zhangpengfei
*/
@ApiModel(value="Passenger",description="passenger")
public class Passenger extends BaseEntity{
    @ApiModelProperty(value="主键自增id",name="passengerId", required=true)
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long passengerId;

    @ApiModelProperty(value="订单id",name="orderId", required=true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value="航班id",name="orderId")
    private Long flightId;

    @ApiModelProperty(value="旅客姓名",name="name")
    private String name;

    @ApiModelProperty(value="联系方式",name="phone")
    private Long phone;

    @ApiModelProperty(value="身份证",name="identityCard")
    private String identityCard;

    @ApiModelProperty(value="单位",name="workUnit")
    private String workUnit;

    @ApiModelProperty(value="贵宾卡",name="vipCard")
    private String vipCard;

    @ApiModelProperty(value="座位号",name="sitNo")
    private String sitNo;

    @ApiModelProperty(value="舱位",name="cabinNo")
    private String cabinNo;

    @ApiModelProperty(value="客票号",name="ticketNo")
    private String ticketNo;

    @ApiModelProperty(value="卡号",name="cardNo")
    private String cardNo;

    @ApiModelProperty(value="0：金卡，1：银卡",name="cardType")
    private Short cardType;

    @ApiModelProperty(value="乘客类型：0，乘客，1：随行",name="passengerType")
    private Short passengerType;


    @ApiModelProperty(value="有效期",name="expireTime")
    private Date expireTime;

    /**
     * 返回字段。详细服务
     */
    @Transient
    private String serviceDetail;

    /**
     * 返回字段。订单状态
     */
    @Transient
    private String orderStatus;

    /**
     * 返回字段。厅名
     */
    @Transient
    private String roomName;

    /**
     * 返回字段。旅客姓名
     */
    @Transient
    private List<String> passengerList;

    /**
     * 返回字段，订单编号
     */
    @Transient
    private String orderNo;

    /**
     * 返回字段，服务完成时间
     */
    @Transient
    private Date serverCreateTime;

    /**
     * 返回字段，客户名称，==乘客表中的 clientName
     */
    @Transient
    private String customerName;

    @Transient
    private Short isImportant;

    @Transient
    private boolean booleanImportant;

    /**
     * 没有服务时间，返回预定时间
     * @return
     */
    public Date getServerCreateTime() {
        return serverCreateTime == null ? this.getCreateTime() : serverCreateTime;
    }

    /**
     * 返回是否重要客户
     * @return
     */
    public boolean isBooleanImportant() {
        return this.isImportant == OrderConstant.ORDER_IS_IMPORTANT ? true : false;
    }

    public Short getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Short isImportant) {
        this.isImportant = isImportant;
    }

    public void setBooleanImportant(boolean booleanImportant) {
        this.booleanImportant = booleanImportant;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    /**
     * 主键自增id
     * @return passenger_id 主键自增id
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * 主键自增id
     * @param passengerId 主键自增id
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
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

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<String> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<String> passengerList) {
        this.passengerList = passengerList;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Short getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(Short passengerType) {
        this.passengerType = passengerType;
    }
}