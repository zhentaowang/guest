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
@ApiModel(value="ExchangeDragon",description="exchange_dragon")
public class ExchangeDragon extends BaseEntity {

    /**
     表名
     */
    public transient static final String TABLE_NAME = "flight_exchange_dragon";

    @ApiModelProperty(value="主键自增id",name="logId", required=true)
    @NotEmpty
    private Long logId;

    @ApiModelProperty(value="航班日期",name="flightDate", required=true)
    @NotEmpty
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo", required=true)
    @NotEmpty
    private String flightNo;

    @ApiModelProperty(value="执行方法")
    private Short exchangeType;

    @ApiModelProperty(value="调用时间",name="invokeTime", required=true)
    @NotEmpty
    private Date invokeTime;

    @ApiModelProperty(value="成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4",name="invokeResult", required=true)
    @NotEmpty
    private Short invokeResult;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
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

    public Short getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Short exchangeType) {
        this.exchangeType = exchangeType;
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

}