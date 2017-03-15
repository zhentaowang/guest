/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-13 20:46:33 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-13 20:46:33 Created By Administrator
*/
@ApiModel(value="Passenger",description="passenger")
public class Passenger extends BaseEntity{
    @ApiModelProperty(value="主键自增id",name="passengerId", required=true)
    @NotEmpty
    private Long passengerId;

    @ApiModelProperty(value="订单id",name="orderId", required=true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value="航班id",name="flightId")
    private Long flightId;

    @ApiModelProperty(value="协议客户id",name="clientId")
    private Long clientId;

    @ApiModelProperty(value="协议客户名称",name="clientName")
    private String clientName;

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

    @ApiModelProperty(value="卡类别:0：金卡，1：银卡",name="cardType")
    private Short cardType;

    @ApiModelProperty(value="有效期",name="expireTime")
    private Date expireTime;

    /**
     * 返回参数，使用次数
     */
    @Transient
    private int buyTimes;

    /**
     * 返回参数，协议类型（不重复）
     */
    @Transient
    private String types;

    /**
     * 返回参数，标签名
     */
    @Transient
    private List<String> labalsName;


    public List<String> getLabalsName() {
        List<String> list = new ArrayList<>();
        if(!StringUtils.isEmpty(this.types)){
            String[] typeArr = types.split(",");
            for(int i=0; i <typeArr.length; i++){
                switch (typeArr[i]){
                    case "10" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("头等舱");
                                }
                                break;
                    case "9" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("金银卡");
                                }
                                break;
                    case "4" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("地方政要");
                                }
                                break;
                    case "5" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("地方政要");
                                }
                                break;
                    case "6" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("副部级vip");
                                }
                                break;
                    case "1" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("银行领导");
                                }
                                break;
                    case "7" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("持卡用户");
                                }
                                break;
                    case "2" : if(list.toString().indexOf("头等舱") == -1){
                                    list.add("持卡用户");
                                }
                                break;
                }
            }
        }
        return list;
    }

    public void setLabalsName(List<String> labalsName) {
        this.labalsName = labalsName;
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
     * 航班id
     * @return flight_id 航班id
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * 航班id
     * @param flightId 航班id
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * 协议客户id
     * @return client_id 协议客户id
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * 协议客户id
     * @param clientId 协议客户id
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * 协议客户名称
     * @return client_name 协议客户名称
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 协议客户名称
     * @param clientName 协议客户名称
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
     * 卡类别:0：金卡，1：银卡
     * @return card_type 卡类别:0：金卡，1：银卡
     */
    public Short getCardType() {
        return cardType;
    }

    /**
     * 卡类别:0：金卡，1：银卡
     * @param cardType 卡类别:0：金卡，1：银卡
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

    public int getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(int buyTimes) {
        this.buyTimes = buyTimes;
    }
}