/**
 * ExchangeDragon.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-28 13:53:00 Created By tc
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 对外接口日志类
 * 记录航班的对外接口日志
 *
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-28 13:53:00 Created By tc
*/
@ApiModel(value="ExternalInterfaceLog",description="external_interface_log")
public class ExternalInterfaceLog extends BaseEntity {

    /**
     表名
     */
    public transient static final String TABLE_NAME = "flight_external_interface_log";

    @ApiModelProperty(value="主键自增id",name="logId", required=true)
    @NotEmpty
    private Long logId;

    /**
     * 对接源 龙腾（非常准）/IBE
     */
    private String dockingSource ;

    @ApiModelProperty(value="航班日期",name="flightDate", required=true)
    @NotEmpty
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo", required=true)
    @NotEmpty
    private String flightNo;

    @ApiModelProperty(value="执行方法")
    private String methodName;

    @ApiModelProperty(value="调用时间",name="invokeTime", required=true)
    @NotEmpty
    private Date invokeTime;

    @ApiModelProperty(value="成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4",name="invokeResult", required=true)
    @NotEmpty
    private Short invokeResult;

    @ApiModelProperty(value="调用结果信息")
    private String invokeResultInfo;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getDockingSource() {
        return dockingSource;
    }

    public void setDockingSource(String dockingSource) {
        this.dockingSource = dockingSource;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
    }

    public Short getInvokeResult() {
        return invokeResult;
    }

    public void setInvokeResult(Short invokeResult) {
        this.invokeResult = invokeResult;
    }

    public String getInvokeResultInfo() {
        return invokeResultInfo;
    }

    public void setInvokeResultInfo(String invokeResultInfo) {
        this.invokeResultInfo = invokeResultInfo;
    }

}