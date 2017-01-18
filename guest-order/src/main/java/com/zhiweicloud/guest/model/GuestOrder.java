/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "GuestOrder", description = "guest_order")
public class GuestOrder extends GuestOrderQuery {
    @ApiModelProperty(value = "协议id", name = "protocolId", required = true)
    @NotEmpty
    private Long protocolId;

    @ApiModelProperty(value = "协议名称", name = "protocolName")
    @Transient
    private String protocolName;

    @ApiModelProperty(value = "机构名称id", name = "orgCustomerId", required = true)
    @NotEmpty
    private Long orgCustomerId;

    @ApiModelProperty(value = "机构名称", name = "customerName")
    @Transient
    private String customerName;

    @ApiModelProperty(value = "服务名称", name = "serviceName")
    @Transient
    private String serviceName;

    @ApiModelProperty(value = "通道名称", name = "passageWayName")
    @Transient
    private String passageWayName;

    @ApiModelProperty(value = "协议备注", name = "protocolRemark")
    private String protocolRemark;

    @ApiModelProperty(value = "预约人", name = "bookingPerson")
    private Long bookingPerson;

    @ApiModelProperty(value = "是否重要：0：重要，1：不重要", name = "isImportant")
    private Short isImportant;

    @ApiModelProperty(value = "0:预约，1：消费,2 : 取消", name = "orderStatus")
    private Short orderStatus;

    @ApiModelProperty(value = "订单备注", name = "orderRemark")
    private String orderRemark;

    @ApiModelProperty(value = "签字人", name = "signerPerson")
    private String signerPerson;

    @ApiModelProperty(value = "订单编号", name = "orderNo")
    private String orderNo;

    @ApiModelProperty(value="服务时间",name="serverTime",hidden = true)
    @JsonFormat(timezone="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serverTime;

    @Transient
    @ApiModelProperty(value = "服务时间", name = "serverTime")
    private String strServerTime;

    @ApiModelProperty(value = "航班信息", name = "flight")
    @Transient
    private Flight flight;


    @ApiModelProperty(value = "冠名厅/休息室", name = "serviceId")
    private Long serviceId;

    @ApiModelProperty(value = "通道", name = "passagewayId")
    private Long passagewayId;

    @ApiModelProperty(value = "提前打登机牌 0:需要，1：不需要", name = "isPrintBoardingCheck")
    private Short isPrintBoardingCheck;

    @ApiModelProperty(value = "座位要求", name = "sitRequire")
    private String sitRequire;

    @ApiModelProperty(value = "行李件数", name = "packageNo")
    private Integer packageNo;

    @ApiModelProperty(value = "行李kg", name = "packageWeight")
    private Float packageWeight;

    @ApiModelProperty(value = "是否托运, 0:托运，1：不托运", name = "isConsign")
    private Short isConsign;

    @ApiModelProperty(value = "服务人数", name = "serverPersonNum")
    private Integer serverPersonNum;

    @ApiModelProperty(value = "随员人数", name = "companyPersonNum")
    private Integer companyPersonNum;

    @ApiModelProperty(value = "车辆数", name = "carNum")
    private Integer carNum;

    @ApiModelProperty(value = "车牌", name = "carNo")
    @Transient
    private String carNo;

    @ApiModelProperty(value = "订单类型： 0；VIP订单，1；CIP订单,1:头等舱订单，1：金银卡订单", name = "orderType",required = true)
    private Short orderType;

    @Transient
    @ApiModelProperty(value = "车辆", name = "orderCar")
    private List<OrderCar> orderCarList;

    @Transient
    @ApiModelProperty(value = "乘客信息", name = "bsPassenger")
    private List<Passenger> passengerList;

    @Transient
    @ApiModelProperty(value = "收费服务", name = "orderChargeList")
    private List<OrderCharge> orderChargeList;

    @Transient
    @ApiModelProperty(value = "确认消费，仅仅用于前台标志 是用来更改订单的状态,确认：1")
    private Integer changeOrderStatus;



    /**
     * 协议id
     *
     * @return protocol_id 协议id
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * 协议id
     *
     * @param protocolId 协议id
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * 协议备注
     *
     * @return protocol_remark 协议备注
     */
    public String getProtocolRemark() {
        return protocolRemark;
    }

    /**
     * 协议备注
     *
     * @param protocolRemark 协议备注
     */
    public void setProtocolRemark(String protocolRemark) {
        this.protocolRemark = protocolRemark;
    }

    /**
     * 预约人
     *
     * @return booking_person 预约人
     */
    public Long getBookingPerson() {
        return bookingPerson;
    }

    /**
     * 预约人
     *
     * @param bookingPerson 预约人
     */
    public void setBookingPerson(Long bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    /**
     * 是否重要：0：重要，1：不重要
     *
     * @return is_important 是否重要：0：重要，1：不重要
     */
    public Short getIsImportant() {
        return isImportant;
    }

    /**
     * 是否重要：0：重要，1：不重要
     *
     * @param isImportant 是否重要：0：重要，1：不重要
     */
    public void setIsImportant(Short isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * 0:预约，1：消费,2 : 取消
     *
     * @return order_status 0:预约，1：消费,2 : 取消
     */
    public Short getOrderStatus() {
        return orderStatus;
    }

    /**
     * 0:预约，1：消费,2 : 取消
     *
     * @param orderStatus 0:预约，1：消费,2 : 取消
     */
    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 订单备注
     *
     * @return order_remark 订单备注
     */
    public String getOrderRemark() {
        return orderRemark;
    }

    /**
     * 订单备注
     *
     * @param orderRemark 订单备注
     */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    /**
     * 签字人
     *
     * @return signer_person 签字人
     */
    public String getSignerPerson() {
        return signerPerson;
    }

    /**
     * 签字人
     *
     * @param signerPerson 签字人
     */
    public void setSignerPerson(String signerPerson) {
        this.signerPerson = signerPerson;
    }

    /**
     * 订单编号
     *
     * @return order_no 订单编号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单编号
     *
     * @param orderNo 订单编号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

   /* *//**
     * 航班日期
     *
     * @return flight_date 航班日期
     *//*
    public Date getFlightDate() {
        return flightDate;
    }

    *//**
     * 航班日期
     *
     * @param flightDate 航班日期
     *//*
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    *//**
     * 航班号
     *
     * @return flight_no 航班号
     *//*
    public String getFlightNo() {
        return flightNo;
    }

    *//**
     * 航班号
     *
     * @param flightNo 航班号
     *//*
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    *//**
     * 航段
     *
     * @return flight_segment 航段
     *//*
    public String getFlightSegment() {
        return flightSegment;
    }

    *//**
     * 航段
     *
     * @param flightSegment 航段
     *//*
    public void setFlightSegment(String flightSegment) {
        this.flightSegment = flightSegment;
    }

    *//**
     * 停机位
     *
     * @return flight_position 停机位
     *//*
    public String getFlightPosition() {
        return flightPosition;
    }

    *//**
     * 停机位
     *
     * @param flightPosition 停机位
     *//*
    public void setFlightPosition(String flightPosition) {
        this.flightPosition = flightPosition;
    }

    *//**
     * 计划起飞时间
     *
     * @return plan_take_off_time 计划起飞时间
     *//*
    public Date getPlanTakeOffTime() {
        return planTakeOffTime;
    }

    *//**
     * 计划起飞时间
     *
     * @param planTakeOffTime 计划起飞时间
     *//*
    public void setPlanTakeOffTime(Date planTakeOffTime) {
        this.planTakeOffTime = planTakeOffTime;
    }

    *//**
     * 计划降落时间
     *
     * @return plan_landing_time 计划降落时间
     *//*
    public Date getPlanLandingTime() {
        return planLandingTime;
    }

    *//**
     * 计划降落时间
     *
     * @param planLandingTime 计划降落时间
     *//*
    public void setPlanLandingTime(Date planLandingTime) {
        this.planLandingTime = planLandingTime;
    }

    *//**
     * 出港：0，进港1
     *
     * @return is_in_or_out 出港：0，进港1
     *//*
    public Short getIsInOrOut() {
        return isInOrOut;
    }

    *//**
     * 出港：0，进港1
     *
     * @param isInOrOut 出港：0，进港1
     *//*
    public void setIsInOrOut(Short isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    *//**
     * 登机口
     *
     * @return boarding_port 登机口
     *//*
    public String getBoardingPort() {
        return boardingPort;
    }

    *//**
     * 登机口
     *
     * @param boardingPort 登机口
     *//*
    public void setBoardingPort(String boardingPort) {
        this.boardingPort = boardingPort;
    }

    *//**
     * 远机位：0，近机位：1
     *
     * @return is_near_or_far 远机位：0，近机位：1
     *//*
    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    *//**
     * 远机位：0，近机位：1
     *
     * @param isNearOrFar 远机位：0，近机位：1
     *//*
    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }
*/
    /**
     * 冠名厅/休息室
     *
     * @return service_id 冠名厅/休息室
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * 冠名厅/休息室
     *
     * @param serviceId 冠名厅/休息室
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 通道
     *
     * @return passageway_id 通道
     */
    public Long getPassagewayId() {
        return passagewayId;
    }

    /**
     * 通道
     *
     * @param passagewayId 通道
     */
    public void setPassagewayId(Long passagewayId) {
        this.passagewayId = passagewayId;
    }

    /**
     * 提前打登机牌 0:需要，1：不需要
     *
     * @return is_print_boarding_check 提前打登机牌 0:需要，1：不需要
     */
    public Short getIsPrintBoardingCheck() {
        return isPrintBoardingCheck;
    }

    /**
     * 提前打登机牌 0:需要，1：不需要
     *
     * @param isPrintBoardingCheck 提前打登机牌 0:需要，1：不需要
     */
    public void setIsPrintBoardingCheck(Short isPrintBoardingCheck) {
        this.isPrintBoardingCheck = isPrintBoardingCheck;
    }

    /**
     * 座位要求
     *
     * @return sit_require 座位要求
     */
    public String getSitRequire() {
        return sitRequire;
    }

    /**
     * 座位要求
     *
     * @param sitRequire 座位要求
     */
    public void setSitRequire(String sitRequire) {
        this.sitRequire = sitRequire;
    }

    /**
     * 行李件数
     *
     * @return package_no 行李件数
     */
    public Integer getPackageNo() {
        return packageNo;
    }

    /**
     * 行李件数
     *
     * @param packageNo 行李件数
     */
    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    /**
     * 行李kg
     *
     * @return package_weight 行李kg
     */
    public Float getPackageWeight() {
        return packageWeight;
    }

    /**
     * 行李kg
     *
     * @param packageWeight 行李kg
     */
    public void setPackageWeight(Float packageWeight) {
        this.packageWeight = packageWeight;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     *
     * @return is_consign 是否托运, 0:托运，1：不托运
     */
    public Short getIsConsign() {
        return isConsign;
    }

    /**
     * 是否托运, 0:托运，1：不托运
     *
     * @param isConsign 是否托运, 0:托运，1：不托运
     */
    public void setIsConsign(Short isConsign) {
        this.isConsign = isConsign;
    }

    /**
     * 服务人数
     *
     * @return server_person_num 服务人数
     */
    public Integer getServerPersonNum() {
        return serverPersonNum;
    }

    /**
     * 服务人数
     *
     * @param serverPersonNum 服务人数
     */
    public void setServerPersonNum(Integer serverPersonNum) {
        this.serverPersonNum = serverPersonNum;
    }

    /**
     * 随员人数
     *
     * @return company_person_num 随员人数
     */
    public Integer getCompanyPersonNum() {
        return companyPersonNum;
    }

    /**
     * 随员人数
     *
     * @param companyPersonNum 随员人数
     */
    public void setCompanyPersonNum(Integer companyPersonNum) {
        this.companyPersonNum = companyPersonNum;
    }

    /**
     * 车辆数
     *
     * @return car_num 车辆数
     */
    public Integer getCarNum() {
        return carNum;
    }

    /**
     * 车辆数
     *
     * @param carNum 车辆数
     */
    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    /**
     * 订单类型： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单
     *
     * @return order_type 订单类型： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单
     */
    public Short getOrderType() {
        return orderType;
    }

    /**
     * 订单类型： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单
     *
     * @param orderType 订单类型： 0；VIP订单，1；CIP订单,2:头等舱订单，3：金银卡订单
     */
    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }


    /**
     * 机构名称id
     *
     * @return org_customer_id 机构名称id
     */
    public Long getOrgCustomerId() {
        return orgCustomerId;
    }

    /**
     * 机构名称id
     *
     * @param orgCustomerId 机构名称id
     */
    public void setOrgCustomerId(Long orgCustomerId) {
        this.orgCustomerId = orgCustomerId;
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

    public Integer getChangeOrderStatus() {
        return changeOrderStatus;
    }

    public void setChangeOrderStatus(Integer changeOrderStatus) {
        this.changeOrderStatus = changeOrderStatus;
    }

    public List<OrderCharge> getOrderChargeList() {
        return orderChargeList;
    }

    public void setOrderChargeList(List<OrderCharge> orderChargeList) {
        this.orderChargeList = orderChargeList;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPassageWayName() {
        return passageWayName;
    }

    public void setPassageWayName(String passageWayName) {
        this.passageWayName = passageWayName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * 服务时间
     * @return server_time 服务时间
     */
    public Date getServerTime() {
        return serverTime;
    }

    /**
     * 服务时间
     * @param serverTime 服务时间
     */
    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    public String getStrServerTime() {
        return strServerTime;
    }

    public void setStrServerTime(String strServerTime) {
        this.strServerTime = strServerTime;
    }
}