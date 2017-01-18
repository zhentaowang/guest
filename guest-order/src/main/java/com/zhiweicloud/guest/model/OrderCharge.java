/**
 * OrderCharge.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 16:44:41 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * OrderCharge.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 16:44:41 Created By zhangpengfei
*/
@ApiModel(value="OrderCharge",description="order_charge")
public class OrderCharge extends  BaseEntity{
    @ApiModelProperty(value="预约订单id",name="orderId", required=true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value="收费服务",name="chargeService")
    private String chargeService;

    @ApiModelProperty(value="收费方式,0:现金,1:刷卡,2:其他",name="chargeBy")
    private Short chargeBy;

    @ApiModelProperty(value="收费数量",name="chargeNum")
    private String chargeNum;

    @ApiModelProperty(value="收费单价",name="chargePrice")
    private Double chargePrice;

    @ApiModelProperty(value="收费金额",name="chargeAmount")
    private Double chargeAmount;


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
     * 收费数量
     * @return charge_num 收费数量
     */
    public String getChargeNum() {
        return chargeNum;
    }

    /**
     * 收费数量
     * @param chargeNum 收费数量
     */
    public void setChargeNum(String chargeNum) {
        this.chargeNum = chargeNum;
    }

    /**
     * 收费单价
     * @return charge_price 收费单价
     */
    public Double getChargePrice() {
        return chargePrice;
    }

    /**
     * 收费单价
     * @param chargePrice 收费单价
     */
    public void setChargePrice(Double chargePrice) {
        this.chargePrice = chargePrice;
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


}