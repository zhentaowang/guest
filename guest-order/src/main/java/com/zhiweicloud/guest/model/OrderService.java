/**
 * OrderService.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-28 17:55:21 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * OrderService.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-28 17:55:21 Created By zhangpengfei
*/
@ApiModel(value="OrderService",description="order_service")
public class OrderService extends BaseEntity{
    @ApiModelProperty(value="主键自增id",name="orderServiceId", required=true)
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long orderServiceId;

    @ApiModelProperty(value="主键自增id",name="orderId")
    private Long orderId;

    @ApiModelProperty(value="服务详情",name="serviceDetail")
    private String serviceDetail;

    @ApiModelProperty(value="价格详情",name="serviceDetail")
    private String priceRule;

    /**
     * 主键自增id
     * @return order_service_id 主键自增id
     */
    public Long getOrderServiceId() {
        return orderServiceId;
    }

    /**
     * 主键自增id
     * @param orderServiceId 主键自增id
     */
    public void setOrderServiceId(Long orderServiceId) {
        this.orderServiceId = orderServiceId;
    }

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
     * 主键自增id
     * @return service_detail 主键自增id
     */
    public String getServiceDetail() {
        return serviceDetail;
    }

    /**
     * 主键自增id
     * @param serviceDetail 主键自增id
     */
    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getPriceRule() {
        return priceRule;
    }

    public void setPriceRule(String priceRule) {
        this.priceRule = priceRule;
    }
}