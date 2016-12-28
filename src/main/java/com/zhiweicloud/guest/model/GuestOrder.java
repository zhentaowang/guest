/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:28:28 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:28:28 Created By zhangpengfei
*/
@ApiModel(value="GuestOrder",description="guest_order")
public class GuestOrder {
    @ApiModelProperty(value="主键自增id",name="id", required=true,hidden = true) //不想显示在前端UI接口中的字段，添加hidden=true属性
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ApiModelProperty(value="机场编码 LJG:丽江机场，HQG：虹桥机场",name="airportCode",required=true)
    @NotEmpty
    private String airportCode;

    @ApiModelProperty(value="协议id",name="protocolId", required=true)
    @NotEmpty
    private Long protocolId;

    @ApiModelProperty(value="预约人",name="bookingPerson")
    private String bookingPerson;

    @ApiModelProperty(value="是否重要：0：重要，1：不重要",name="isImportant")
    private Short isImportant;

    @ApiModelProperty(value="0:预约订单，1：消费订单",name="orderStatus")
    private Short orderStatus;

    @ApiModelProperty(value="订单备注",name="orderRemark")
    private String orderRemark;

    @ApiModelProperty(value="签字人",name="signerPerson")
    private String signerPerson;

    @ApiModelProperty(value="航班日期",name="flightDate")
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="航段",name="flightSegment")
    private String flightSegment;

    @ApiModelProperty(value="停机位",name="flightPosition")
    private String flightPosition;

    @ApiModelProperty(value="计划起飞时间",name="planTakeOffTime")
    private Date planTakeOffTime;

    @ApiModelProperty(value="计划降落时间",name="planLandingTime")
    private Date planLandingTime;

    @ApiModelProperty(value="出港：0，进港1",name="isInOrOut")
    private Short isInOrOut;

    @ApiModelProperty(value="登机口",name="boardingPort")
    private String boardingPort;

    @ApiModelProperty(value="远机位：0，近机位：1",name="isNearOrFar")
    private Short isNearOrFar;

    @ApiModelProperty(value="冠名厅",name="serviceId")
    private Long serviceId;

    @ApiModelProperty(value="提前打登机牌 0:需要，1：不需要",name="isPrintBoardingCheck")
    private Short isPrintBoardingCheck;

    @ApiModelProperty(value="座位要求",name="sitRequire")
    private String sitRequire;

    @ApiModelProperty(value="行李件数",name="packageNo")
    private Integer packageNo;

    @ApiModelProperty(value="行李kg",name="packageWeight")
    private Float packageWeight;

    @ApiModelProperty(value="是否托运, 0:托运，1：不托运",name="isConsign")
    private Short isConsign;

    @ApiModelProperty(value="服务人数",name="serverPersonNum")
    private Integer serverPersonNum;

    @ApiModelProperty(value="随员人数",name="companyPersonNum")
    private Integer companyPersonNum;

    @ApiModelProperty(value="车辆数",name="carNum")
    private Integer carNum;

    @ApiModelProperty(value="订单类型： 0；VIP预约订单，1；CIP预约订单,2:头等舱消费订单，3：金银卡消费订单",name="orderType")
    private Short orderType;

    @ApiModelProperty(value="收费服务",name="chargeService")
    private String chargeService;

    @ApiModelProperty(value="收费方式,0:现金,1:刷卡,2:其他",name="chargeBy")
    private Short chargeBy;

    @ApiModelProperty(value="收费内容",name="chargeContent")
    private String chargeContent;

    @ApiModelProperty(value="收费金额",name="chargeAmount")
    private Double chargeAmount;

    @ApiModelProperty(value="创建时间",name="createTime",hidden = true)
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime",hidden = true)
    private Date updateTime;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted",hidden = true)
    private Short isDeleted;

    @ApiModelProperty(value="订单编号",name="order_no",hidden = true)
    private String orderNo;


    @Transient
    @ApiModelProperty(value = "车辆",name="orderCar")
    private List<OrderCar> orderCarList;

    @Transient
    @ApiModelProperty(value = "乘客信息",name="bsPassenger")
    private List<Passenger> passengerList;

    /**
     * 主键自增id
     * @return id 主键自增id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键自增id
     * @param id 主键自增id
     */
    public void setId(Long id) {
        this.id = id;
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
    public String getBookingPerson() {
        return bookingPerson;
    }

    /**
     * 预约人
     * @param bookingPerson 预约人
     */
    public void setBookingPerson(String bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    /**
     * 是否重要：0：重要，1：不重要
     * @return is_important 是否重要：0：重要，1：不重要
     */
    public Short getIsImportant() {
        return isImportant;
    }

    /**
     * 是否重要：0：重要，1：不重要
     * @param isImportant 是否重要：0：重要，1：不重要
     */
    public void setIsImportant(Short isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * 0:预约订单，1：消费订单
     * @return order_status 0:预约订单，1：消费订单
     */
    public Short getOrderStatus() {
        return orderStatus;
    }

    /**
     * 0:预约订单，1：消费订单
     * @param orderStatus 0:预约订单，1：消费订单
     */
    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 订单备注
     * @return order_remark 订单备注
     */
    public String getOrderRemark() {
        return orderRemark;
    }

    /**
     * 订单备注
     * @param orderRemark 订单备注
     */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    /**
     * 签字人
     * @return signer_person 签字人
     */
    public String getSignerPerson() {
        return signerPerson;
    }

    /**
     * 签字人
     * @param signerPerson 签字人
     */
    public void setSignerPerson(String signerPerson) {
        this.signerPerson = signerPerson;
    }

    /**
     * 航班日期
     * @return flight_date 航班日期
     */
    public Date getFlightDate() {
        return flightDate;
    }

    /**
     * 航班日期
     * @param flightDate 航班日期
     */
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * 航班号
     * @return flight_no 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 航班号
     * @param flightNo 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 航段
     * @return flight_segment 航段
     */
    public String getFlightSegment() {
        return flightSegment;
    }

    /**
     * 航段
     * @param flightSegment 航段
     */
    public void setFlightSegment(String flightSegment) {
        this.flightSegment = flightSegment;
    }

    /**
     * 停机位
     * @return flight_position 停机位
     */
    public String getFlightPosition() {
        return flightPosition;
    }

    /**
     * 停机位
     * @param flightPosition 停机位
     */
    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition;
    }

    /**
     * 计划起飞时间
     * @return plan_take_off_time 计划起飞时间
     */
    public Date getPlanTakeOffTime() {
        return planTakeOffTime;
    }

    /**
     * 计划起飞时间
     * @param planTakeOffTime 计划起飞时间
     */
    public void setPlanTakeOffTime(Date planTakeOffTime) {
        this.planTakeOffTime = planTakeOffTime;
    }

    /**
     * 计划降落时间
     * @return plan_landing_time 计划降落时间
     */
    public Date getPlanLandingTime() {
        return planLandingTime;
    }

    /**
     * 计划降落时间
     * @param planLandingTime 计划降落时间
     */
    public void setPlanLandingTime(Date planLandingTime) {
        this.planLandingTime = planLandingTime;
    }

    /**
     * 出港：0，进港1
     * @return is_in_or_out 出港：0，进港1
     */
    public Short getIsInOrOut() {
        return isInOrOut;
    }

    /**
     * 出港：0，进港1
     * @param isInOrOut 出港：0，进港1
     */
    public void setIsInOrOut(Short isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    /**
     * 登机口
     * @return boarding_port 登机口
     */
    public String getBoardingPort() {
        return boardingPort;
    }

    /**
     * 登机口
     * @param boardingPort 登机口
     */
    public void setBoardingPort(String boardingPort) {
        this.boardingPort = boardingPort;
    }

    /**
     * 远机位：0，近机位：1
     * @return is_near_or_far 远机位：0，近机位：1
     */
    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    /**
     * 远机位：0，近机位：1
     * @param isNearOrFar 远机位：0，近机位：1
     */
    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }

    /**
     * 冠名厅
     * @return service_id 冠名厅
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * 冠名厅
     * @param serviceId 冠名厅
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 提前打登机牌 0:需要，1：不需要
     * @return is_print_boarding_check 提前打登机牌 0:需要，1：不需要
     */
    public Short getIsPrintBoardingCheck() {
        return isPrintBoardingCheck;
    }

    /**
     * 提前打登机牌 0:需要，1：不需要
     * @param isPrintBoardingCheck 提前打登机牌 0:需要，1：不需要
     */
    public void setIsPrintBoardingCheck(Short isPrintBoardingCheck) {
        this.isPrintBoardingCheck = isPrintBoardingCheck;
    }

    /**
     * 座位要求
     * @return sit_require 座位要求
     */
    public String getSitRequire() {
        return sitRequire;
    }

    /**
     * 座位要求
     * @param sitRequire 座位要求
     */
    public void setSitRequire(String sitRequire) {
        this.sitRequire = sitRequire;
    }

    /**
     * 行李件数
     * @return package_no 行李件数
     */
    public Integer getPackageNo() {
        return packageNo;
    }

    /**
     * 行李件数
     * @param packageNo 行李件数
     */
    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    /**
     * 行李kg
     * @return package_weight 行李kg
     */
    public Float getPackageWeight() {
        return packageWeight;
    }

    /**
     * 行李kg
     * @param packageWeight 行李kg
     */
    public void setPackageWeight(Float packageWeight) {
        this.packageWeight = packageWeight;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     * @return is_consign 是否托运, 0:托运，1：不托运
     */
    public Short getIsConsign() {
        return isConsign;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     * @param isConsign 是否托运, 0:托运，1：不托运
     */
    public void setIsConsign(Short isConsign) {
        this.isConsign = isConsign;
    }

    /**
     * 服务人数
     * @return server_person_num 服务人数
     */
    public Integer getServerPersonNum() {
        return serverPersonNum;
    }

    /**
     * 服务人数
     * @param serverPersonNum 服务人数
     */
    public void setServerPersonNum(Integer serverPersonNum) {
        this.serverPersonNum = serverPersonNum;
    }

    /**
     * 随员人数
     * @return company_person_num 随员人数
     */
    public Integer getCompanyPersonNum() {
        return companyPersonNum;
    }

    /**
     * 随员人数
     * @param companyPersonNum 随员人数
     */
    public void setCompanyPersonNum(Integer companyPersonNum) {
        this.companyPersonNum = companyPersonNum;
    }

    /**
     * 车辆数
     * @return car_num 车辆数
     */
    public Integer getCarNum() {
        return carNum;
    }

    /**
     * 车辆数
     * @param carNum 车辆数
     */
    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    /**
     * 订单类型： 0；VIP预约订单，1；CIP预约订单,2:头等舱消费订单，3：金银卡消费订单
     * @return order_type 订单类型： 0；VIP预约订单，1；CIP预约订单,2:头等舱消费订单，3：金银卡消费订单
     */
    public Short getOrderType() {
        return orderType;
    }

    /**
     * 订单类型： 0；VIP预约订单，1；CIP预约订单,2:头等舱消费订单，3：金银卡消费订单
     * @param orderType 订单类型： 0；VIP预约订单，1；CIP预约订单,2:头等舱消费订单，3：金银卡消费订单
     */
    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    /**
     * 收费服务
     * @return charge_service 收费服务
     */
    public String getChargeService() {
        return chargeService;
    }

    /**
     * 收费服务
     * @param chargeService 收费服务
     */
    public void setChargeService(String chargeService) {
        this.chargeService = chargeService;
    }

    /**
     * 收费方式,0:现金,1:刷卡,2:其他
     * @return charge_by 收费方式,0:现金,1:刷卡,2:其他
     */
    public Short getChargeBy() {
        return chargeBy;
    }

    /**
     * 收费方式,0:现金,1:刷卡,2:其他
     * @param chargeBy 收费方式,0:现金,1:刷卡,2:其他
     */
    public void setChargeBy(Short chargeBy) {
        this.chargeBy = chargeBy;
    }

    /**
     * 收费内容
     * @return charge_content 收费内容
     */
    public String getChargeContent() {
        return chargeContent;
    }

    /**
     * 收费内容
     * @param chargeContent 收费内容
     */
    public void setChargeContent(String chargeContent) {
        this.chargeContent = chargeContent;
    }

    /**
     * 收费金额
     * @return charge_amount 收费金额
     */
    public Double getChargeAmount() {
        return chargeAmount;
    }

    /**
     * 收费金额
     * @param chargeAmount 收费金额
     */
    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @return is_deleted 是否删除：默认为0，0：不删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @param isDeleted 是否删除：默认为0，0：不删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 机场id
     * @return ariport_code 机场code
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 机场id
     * @param airportCode 机场id
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public List<OrderCar> getOrderCarList() {
        return orderCarList;
    }

    public void setOrderCarList(List<OrderCar> orderCarList) {
        this.orderCarList = orderCarList;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }
    /**
     * 订单编号
     * @return order_no 订单编号
     */
    public String getOrderNo() {
        return orderNo;
    }
    /**
     * 订单编号
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}