/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户接口订单记录表
 * 
 * @author IronC
 * @version 1.0  2017-05-18
 */
public class ApiCustomerOrder {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "api_customer_order";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long orderId;

    /**
     * 客户ID
     * 不能为空
     */
    private Long customerId;

    /**
     * 购买次数(总次数)
     */
    private Long apiTotal;

    /**
     * 过期日期
     */
    private String expiryDate;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getApiTotal() {
        return apiTotal;
    }

    public void setApiTotal(Long apiTotal) {
        this.apiTotal = apiTotal;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate == null ? null : expiryDate.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * out method.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", orderId=").append(orderId);
        sb.append(", customerId=").append(customerId);
        sb.append(", apiTotal=").append(apiTotal);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}