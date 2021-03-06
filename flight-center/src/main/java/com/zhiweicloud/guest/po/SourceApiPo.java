/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 数据来源接口日志
 * 
 * @author IronC
 * @version 1.0  2017-05-18
 */
public class SourceApiPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "operator_source_api";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long sourceApiId;

    /**
     * 资源名字
     * 不能为空
     */
    private String sourceName;

    /**
     * source接口名字
     * 不能为空
     */
    private String apiName;

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
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getSourceApiId() {
        return sourceApiId;
    }

    public void setSourceApiId(Long sourceApiId) {
        this.sourceApiId = sourceApiId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public String getInvokeState() {
        return invokeState;
    }

    public void setInvokeState(String invokeState) {
        this.invokeState = invokeState == null ? null : invokeState.trim();
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
        sb.append(", sourceApiLogId=").append(sourceApiId);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", apiName=").append(apiName);
        sb.append(", invokeState=").append(invokeState);
        sb.append(", invokeResult=").append(invokeResult);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}