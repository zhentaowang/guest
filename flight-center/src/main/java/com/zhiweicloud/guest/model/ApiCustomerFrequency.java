/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.model;

import java.util.Date;

/**
 * 客户接口次数表
 * 
 * @author IronC
 * @version 1.0  2017-05-18
 */
public class ApiCustomerFrequency {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "api_customer_frequency";

    /**
     * 客户ID
     * 不能为空
     */
    private Long customerId;

    /**
     * 接口名
     * 不能为空
     */
    private String apiName;

    /**
     * 接口剩余次数
     */
    private Long apiFrequency;

    /**
     * 过期日期
     */
    private Date expiryDate;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public Long getApiFrequency() {
        return apiFrequency;
    }

    public void setApiFrequency(Long apiFrequency) {
        this.apiFrequency = apiFrequency;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
        sb.append(", customerId=").append(customerId);
        sb.append(", apiName=").append(apiName);
        sb.append(", apiFrequency=").append(apiFrequency);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}