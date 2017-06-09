/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import java.util.Date;

/**
 * 航班信息推送日志表
 * 
 * @author IronC
 * @version 1.0  2017-06-07
 */
public class FlightPushPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "f_flight_push";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long flightPushId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 执行结果
     * 不能为空
     */
    private String invokeResult;

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

    public Long getFlightPushId() {
        return flightPushId;
    }

    public void setFlightPushId(Long flightPushId) {
        this.flightPushId = flightPushId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getInvokeResult() {
        return invokeResult;
    }

    public void setInvokeResult(String invokeResult) {
        this.invokeResult = invokeResult == null ? null : invokeResult.trim();
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
        sb.append(", flightPushId=").append(flightPushId);
        sb.append(", customerId=").append(customerId);
        sb.append(", invokeResult=").append(invokeResult);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}