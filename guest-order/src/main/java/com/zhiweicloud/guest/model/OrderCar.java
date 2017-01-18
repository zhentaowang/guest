/**
 * OrderCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:29:23 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import java.util.Date;

/**
 * OrderCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:29:23 Created By zhangpengfei
 */
@ApiModel(value = "OrderCar", description = "order_car")
public class OrderCar extends  BaseEntity{
    @ApiModelProperty(value = "预约订单id", name = "orderId", required = true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value = "车牌", name = "carNo")
    private String carNo;

    /**
     * 预约订单id
     * @return order_id 预约订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 预约订单id
     * @param orderId 预约订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 车牌
     * @return car_no 车牌
     */
    public String getCarNo() {
        return carNo;
    }

    /**
     * 车牌
     * @param carNo 车牌
     */
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

}