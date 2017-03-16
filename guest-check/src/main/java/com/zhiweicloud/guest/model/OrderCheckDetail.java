/**
 * OrderCheckDetail.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-15 11:04:10 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * OrderCheckDetail.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-15 11:04:10 Created By zhangpengfei
*/
@ApiModel(value="OrderCheckDetail",description="order_check_detail")
public class OrderCheckDetail {
    @ApiModelProperty(value="订单号",name="orderNo")
    private String orderNo;

    @ApiModelProperty(value="协议客户id",name="customerId")
    private Long customerId;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="产品id",name="productId")
    private Long productId;

    @ApiModelProperty(value="航班日期",name="flightDate")
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="航班号",name="planNo")
    private String planNo;

    @ApiModelProperty(value="航段",name="routeSegment")
    private String routeSegment;

    @ApiModelProperty(value="出发机场名",name="flightDepAirport")
    private String flightDepAirport;

    @ApiModelProperty(value="到达机场名",name="flightArrAirport")
    private String flightArrAirport;

    @ApiModelProperty(value="出港,进港",name="isInOrOut")
    private String isInOrOut;

    @ApiModelProperty(value="贵宾厅人次",name="vipPersonNum")
    private Integer vipPersonNum;

    @ApiModelProperty(value="贵宾厅费用",name="vipPrice")
    private Double vipPrice;

    @ApiModelProperty(value="陪同人次",name="accompanyPersonNum")
    private Integer accompanyPersonNum;

    @ApiModelProperty(value="陪同费用",name="accompanyPrice")
    private Double accompanyPrice;

    @ApiModelProperty(value="费用合计",name="feeTotal")
    private Double feeTotal;

    @ApiModelProperty(value="列头",name="column")
    private String column;

    @ApiModelProperty(value="查询列",name="selectFields")
    private String selectFields;

    @ApiModelProperty(value="总价",name="totalAmount")
    private String totalAmount;



    /**
     * 订单号
     * @return order_no 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 协议客户id
     * @return customer_id 协议客户id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 协议客户id
     * @param customerId 协议客户id
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
     * @return plan_no 航班号
     */
    public String getPlanNo() {
        return planNo;
    }

    /**
     * 航班号
     * @param planNo 航班号
     */
    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    /**
     * 出发机场名
     * @return flight_dep_airport 出发机场名
     */
    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    /**
     * 出发机场名
     * @param flightDepAirport 出发机场名
     */
    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    /**
     * 到达机场名
     * @return flight_arr_airport 到达机场名
     */
    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    /**
     * 到达机场名
     * @param flightArrAirport 到达机场名
     */
    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    /**
     * 贵宾厅人次
     * @return vip_person_num 贵宾厅人次
     */
    public Integer getVipPersonNum() {
        return vipPersonNum;
    }

    /**
     * 贵宾厅人次
     * @param vipPersonNum 贵宾厅人次
     */
    public void setVipPersonNum(Integer vipPersonNum) {
        this.vipPersonNum = vipPersonNum;
    }

    /**
     * 贵宾厅费用
     * @return vip_price 贵宾厅费用
     */
    public Double getVipPrice() {
        return vipPrice;
    }

    /**
     * 贵宾厅费用
     * @param vipPrice 贵宾厅费用
     */
    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    /**
     * 陪同人次
     * @return accompany_person_num 陪同人次
     */
    public Integer getAccompanyPersonNum() {
        return accompanyPersonNum;
    }

    /**
     * 陪同人次
     * @param accompanyPersonNum 陪同人次
     */
    public void setAccompanyPersonNum(Integer accompanyPersonNum) {
        this.accompanyPersonNum = accompanyPersonNum;
    }

    /**
     * 陪同费用
     * @return accompany_price 陪同费用
     */
    public Double getAccompanyPrice() {
        return accompanyPrice;
    }

    /**
     * 陪同费用
     * @param accompanyPrice 陪同费用
     */
    public void setAccompanyPrice(Double accompanyPrice) {
        this.accompanyPrice = accompanyPrice;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSelectFields() {
        return selectFields;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setSelectFields(String selectFields) {
        this.selectFields = selectFields;

    }

    public String getRouteSegment() {
        return routeSegment;
    }

    public void setRouteSegment(String routeSegment) {
        this.routeSegment = routeSegment;
    }

    public void setIsInOrOut(String isInOrOut) {
        this.isInOrOut = isInOrOut;
    }

    public Double getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Double feeTotal) {
        this.feeTotal = feeTotal;
    }


    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}