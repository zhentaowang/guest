/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import java.util.Date;

/**
 * 航班中心接口日志
 * 
 * @author IronC
 * @version 1.0  2017-05-18
 */
public class FlightCenterApiPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "operator_flight_center_api";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long flightCenterApiId;

    /**
     * flightCenter接口名字
     * 不能为空
     */
    private String apiName;

    /**
     * 客户ID
     * 不能为空
     */
    private Long customerId;

    /**
     * 执行状态
     * 不能为空
     */
    private String invokeState;

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

    public Long getFlightCenterApiId() {
        return flightCenterApiId;
    }

    public void setFlightCenterApiId(Long flightCenterApiId) {
        this.flightCenterApiId = flightCenterApiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getInvokeState() {
        return invokeState;
    }

    public void setInvokeState(String invokeState) {
        this.invokeState = invokeState;
    }

    public String getInvokeResult() {
        return invokeResult;
    }

    public void setInvokeResult(String invokeResult) {
        this.invokeResult = invokeResult;
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
        sb.append(", flightCenterApiId=").append(flightCenterApiId);
        sb.append(", apiName=").append(apiName);
        sb.append(", customerId=").append(customerId);
        sb.append(", invokeState=").append(invokeState);
        sb.append(", invokeResult=").append(invokeResult);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}