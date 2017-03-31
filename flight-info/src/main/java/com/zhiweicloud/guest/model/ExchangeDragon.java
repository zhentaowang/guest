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
 * ExchangeDragon.java
 * 龙腾接口对接操作信息表
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

    @ApiModelProperty(value="主键自增id",name="exchangeId", required=true)
    @NotEmpty
    private Long exchangeId;

    @ApiModelProperty(value="航班日期",name="flightDate", required=true)
    @NotEmpty
    private Date flightDate;

    @ApiModelProperty(value="航班号",name="flightNo", required=true)
    @NotEmpty
    private String flightNo;

    @ApiModelProperty(value="时刻表：1，定制：2，动态表：3",name="exchangeType", required=true)
    @NotEmpty
    private Short exchangeType;

    @ApiModelProperty(value="调用时间",name="invokeTime", required=true)
    @NotEmpty
    private Date invokeTime;

    @ApiModelProperty(value="成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4",name="invokeResult", required=true)
    @NotEmpty
    private Short invokeResult;

    /**
     * 主键自增id
     * @return exchange_id 主键自增id
     */
    public Long getExchangeId() {
        return exchangeId;
    }

    /**
     * 主键自增id
     * @param exchangeId 主键自增id
     */
    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    /**
     * 航班日期
     * @return flight_date 航班日期
     */
    public Date getFlightDate() {
        return flightDate;
    }

    /**
     * 航班日期
     * @param flightDate 航班日期
     */
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * 航班号
     * @return flight_no 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 航班号
     * @param flightNo 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 时刻表：1，定制：2，动态表：3
     * @return exchange_type 时刻表：1，定制：2，动态表：3
     */
    public Short getExchangeType() {
        return exchangeType;
    }

    /**
     * 时刻表：1，定制：2，动态表：3
     * @param exchangeType 时刻表：1，定制：2，动态表：3
     */
    public void setExchangeType(Short exchangeType) {
        this.exchangeType = exchangeType;
    }

    /**
     * 调用时间
     * @return invoke_time 调用时间
     */
    public Date getInvokeTime() {
        return invokeTime;
    }

    /**
     * 调用时间
     * @param invokeTime 调用时间
     */
    public void setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
    }

    /**
     * 成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4
     * @return invoke_result 成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4
     */
    public Short getInvokeResult() {
        return invokeResult;
    }

    /**
     * 成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4
     * @param invokeResult 成功：1，航班中心程序异常：-1，航班中心签名失败：-2，航班中心参数有误：-3，非常准无数据：-4
     */
    public void setInvokeResult(Short invokeResult) {
        this.invokeResult = invokeResult;
    }

}