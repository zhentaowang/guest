/**
 * OrderInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-24 17:28:24 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * OrderInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-24 17:28:24 Created By zhangpengfei
*/
@ApiModel(value="OrderInfo",description="order_info")
public class OrderInfo extends BaseEntity{
    @ApiModelProperty(value="主键自增id",name="orderId", required=true)
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long orderId;

    @ApiModelProperty(value="机构名称id",name="customerId")
    private Long customerId;

    @ApiModelProperty(value="协议id",name="protocolId", required=true)
    private Long protocolId;

    @ApiModelProperty(value="预约人",name="bookingPerson",hidden = true)
    private Long bookingPerson;

    @ApiModelProperty(value="通知人",name="noticePerson")
    private String noticePerson;

    @ApiModelProperty(value="预约方式：0：电话，1：邮件，2：传真，3：短信，4：其他",name="bookingWay")
    private Short bookingWay;

    @ApiModelProperty(value="是否重要订单：0：重要，1：不重要",name="isImportant")
    private Short isImportant;

    @ApiModelProperty(value="产品id",name="productId")
    private Long productId;

    @ApiModelProperty(value="代办登机牌 0:需要，1：不需要",name="printCheck")
    private Short printCheck;

    @ApiModelProperty(value="座位要求: 靠窗 靠走道 不要安全门 前排 后排",name="printCheckRemark")
    private String printCheckRemark;

    @ApiModelProperty(value="代办托运 说明",name="consign")
    private Short consign;

    @ApiModelProperty(value="是否托运, 0:托运，1：不托运",name="consignRemark")
    private String consignRemark;

    @ApiModelProperty(value="服务说明",name="otherRemark")
    private String otherRemark;

    @ApiModelProperty(value="0:预约成功，1：消费,2 : 取消，3：使用，4：草稿",name="orderStatus")
    private Short orderStatus;

    @ApiModelProperty(value="订单类型：0：预约订单，1：服务订单",name="orderType")
    private Short orderType;

    @ApiModelProperty(value="安排代办人",name="agentPerson")
    private Long agentPerson;

    @ApiModelProperty(value="代办完成:1：是，0：否",name="agentPerson")
    private Short agentComplete;

    @ApiModelProperty(value="证件",name="serverCardNo")
    private String serverCardNo;

    @ApiModelProperty(value="贵宾卡",name="vipCard")
    private String vipCard;

    @ApiModelProperty(value="现金",name="cash")
    private BigDecimal cash;

    @ApiModelProperty(value="座位号",name="sitNo")
    private String sitNo;

    @ApiModelProperty(value="服务完成：0：是，1：否",name="serverComplete")
    private Short serverComplete;

    @ApiModelProperty(value = "服务人数", name = "serverPersonNum")
    private Integer serverPersonNum;


    @Transient
    @ApiModelProperty(value="订单详细服务",name="serviceList")
    private List<OrderService> serviceList;

    @Transient
    @ApiModelProperty(value = "乘客信息", name = "passengerList")
    private List<Passenger> passengerList;

    @Transient
    @ApiModelProperty(value = "确认消费，仅仅用于前台标志 是用来更改订单的状态,确认：1")
    private Integer changeOrderStatus;


    @ApiModelProperty(value = "航班信息", name = "flight")
    @Transient
    private Flight flight;

    /**
     * 主键自增id
     * @return order_id 主键自增id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 主键自增id
     * @param orderId 主键自增id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 机构名称id
     * @return customer_id 机构名称id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 机构名称id
     * @param customerId 机构名称id
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 协议id
     * @return protocol_id 协议id
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * 协议id
     * @param protocolId 协议id
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * 预约人
     * @return booking_person 预约人
     */
    public Long getBookingPerson() {
        return bookingPerson;
    }

    /**
     * 预约人
     * @param bookingPerson 预约人
     */
    public void setBookingPerson(Long bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    /**
     * 通知人
     * @return notice_person 通知人
     */
    public String getNoticePerson() {
        return noticePerson;
    }

    /**
     * 通知人
     * @param noticePerson 通知人
     */
    public void setNoticePerson(String noticePerson) {
        this.noticePerson = noticePerson;
    }

    /**
     * 预约方式：0：电话，1：邮件，2：传真，3：短信，4：其他
     * @return booking_way 预约方式：0：电话，1：邮件，2：传真，3：短信，4：其他
     */
    public Short getBookingWay() {
        return bookingWay;
    }

    /**
     * 预约方式：0：电话，1：邮件，2：传真，3：短信，4：其他
     * @param bookingWay 预约方式：0：电话，1：邮件，2：传真，3：短信，4：其他
     */
    public void setBookingWay(Short bookingWay) {
        this.bookingWay = bookingWay;
    }

    /**
     * 是否重要订单：0：重要，1：不重要
     * @return is_important 是否重要订单：0：重要，1：不重要
     */
    public Short getIsImportant() {
        return isImportant;
    }

    /**
     * 是否重要订单：0：重要，1：不重要
     * @param isImportant 是否重要订单：0：重要，1：不重要
     */
    public void setIsImportant(Short isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * 产品id
     * @return product_id 产品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 产品id
     * @param productId 产品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 代办登机牌 0:需要，1：不需要
     * @return print_check 代办登机牌 0:需要，1：不需要
     */
    public Short getPrintCheck() {
        return printCheck;
    }

    /**
     * 代办登机牌 0:需要，1：不需要
     * @param printCheck 代办登机牌 0:需要，1：不需要
     */
    public void setPrintCheck(Short printCheck) {
        this.printCheck = printCheck;
    }

    /**
     * 座位要求: 靠窗 靠走道 不要安全门 前排 后排
     * @return print_check_remark 座位要求: 靠窗 靠走道 不要安全门 前排 后排
     */
    public String getPrintCheckRemark() {
        return printCheckRemark;
    }

    /**
     * 座位要求: 靠窗 靠走道 不要安全门 前排 后排
     * @param printCheckRemark 座位要求: 靠窗 靠走道 不要安全门 前排 后排
     */
    public void setPrintCheckRemark(String printCheckRemark) {
        this.printCheckRemark = printCheckRemark;
    }

    /**
     * 代办托运 说明
     * @return consign 代办托运 说明
     */
    public Short getConsign() {
        return consign;
    }

    /**
     * 代办托运 说明
     * @param consign 代办托运 说明
     */
    public void setConsign(Short consign) {
        this.consign = consign;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     * @return consign_remark 是否托运, 0:托运，1：不托运
     */
    public String getConsignRemark() {
        return consignRemark;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     * @param consignRemark 是否托运, 0:托运，1：不托运
     */
    public void setConsignRemark(String consignRemark) {
        this.consignRemark = consignRemark;
    }

    /**
     * 服务说明
     * @return other_remark 服务说明
     */
    public String getOtherRemark() {
        return otherRemark;
    }

    /**
     * 服务说明
     * @param otherRemark 服务说明
     */
    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    /**
     * 0:预约成功，1：消费,2 : 取消，3：使用，4：草稿
     * @return order_status 0:预约成功，1：消费,2 : 取消，3：使用，4：草稿
     */
    public Short getOrderStatus() {
        return orderStatus;
    }

    /**
     * 0:预约成功，1：消费,2 : 取消，3：使用，4：草稿
     * @param orderStatus 0:预约成功，1：消费,2 : 取消，3：使用，4：草稿
     */
    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 订单类型：0：预约订单，1：服务订单
     * @return order_type 订单类型：0：预约订单，1：服务订单
     */
    public Short getOrderType() {
        return orderType;
    }

    /**
     * 订单类型：0：预约订单，1：服务订单
     * @param orderType 订单类型：0：预约订单，1：服务订单
     */
    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    /**
     * 安排代办人
     * @return agent_person 安排代办人
     */
    public Long getAgentPerson() {
        return agentPerson;
    }

    /**
     * 安排代办人
     * @param agentPerson 安排代办人
     */
    public void setAgentPerson(Long agentPerson) {
        this.agentPerson = agentPerson;
    }

    /**
     * 证件
     * @return server_card_no 证件
     */
    public String getServerCardNo() {
        return serverCardNo;
    }

    /**
     * 证件
     * @param serverCardNo 证件
     */
    public void setServerCardNo(String serverCardNo) {
        this.serverCardNo = serverCardNo;
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
     * 现金
     * @return cash 现金
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * 现金
     * @param cash 现金
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
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
     * 服务完成：0：是，1：否
     * @return server_complete 服务完成：0：是，1：否
     */
    public Short getServerComplete() {
        return serverComplete;
    }

    /**
     * 服务完成：0：是，1：否
     * @param serverComplete 服务完成：0：是，1：否
     */
    public void setServerComplete(Short serverComplete) {
        this.serverComplete = serverComplete;
    }

    public List<OrderService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<OrderService> serviceList) {
        this.serviceList = serviceList;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public Integer getChangeOrderStatus() {
        return changeOrderStatus;
    }

    public void setChangeOrderStatus(Integer changeOrderStatus) {
        this.changeOrderStatus = changeOrderStatus;
    }

    public Integer getServerPersonNum() {
        return serverPersonNum;
    }

    public void setServerPersonNum(Integer serverPersonNum) {
        this.serverPersonNum = serverPersonNum;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Short getAgentComplete() {
        return agentComplete;
    }

    public void setAgentComplete(Short agentComplete) {
        this.agentComplete = agentComplete;
    }
}