package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.util.Date;

/**
 * GuestOrder.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 17:01:53 Created By zhangpengfei
 */
@ApiModel(value = "OrderInfoQuery", description = "仅用订单条件查询使用")
public class OrderInfoQuery extends BaseEntity{

    @ApiModelProperty(value = "乘客id", name = "queryPassengerId")
    @Transient
    private String queryPassengerId;

    @ApiModelProperty(value = "乘客姓名", name = "queryPassengerName")
    @Transient
    private String queryPassengerName;

    @ApiModelProperty(value = "航班日期", name = "queryFlightDate")
    @Transient
    private String queryFlightDate;

    @ApiModelProperty(value = "航班号", name = "queryFlightNo")
    @Transient
    private String queryFlightNo;

    @ApiModelProperty(value = "协议id", name = "queryProtocolId")
    @Transient
    private String queryProtocolId;

    @ApiModelProperty(value = "产品id", name = "queryProductId")
    @Transient
    private String queryProductId;

    @ApiModelProperty(value = "是否重要订单", name = "queryIsImportant")
    @Transient
    private String queryIsImportant;

    @ApiModelProperty(value = "提前一天预约", name = "queryBookingOneDayBefore")
    @Transient
    private String queryBookingOneDayBefore;

    @ApiModelProperty(value = "订单状态", name = "queryOrderStatus")
    @Transient
    private String queryOrderStatus;

    @ApiModelProperty(value = "按照航班起飞时间或者降落时间排序：", name = "queryOrderBy")
    @Transient
    private String queryOrderBy;




    public String getQueryPassengerId() {
        return queryPassengerId;
    }

    public void setQueryPassengerId(String queryPassengerId) {
        this.queryPassengerId = queryPassengerId;
    }

    public String getQueryPassengerName() {
        return queryPassengerName;
    }

    public void setQueryPassengerName(String queryPassengerName) {
        this.queryPassengerName = queryPassengerName;
    }

    public String getQueryFlightDate() {
        return queryFlightDate;
    }

    public void setQueryFlightDate(String queryFlightDate) {
        this.queryFlightDate = queryFlightDate;
    }

    public String getQueryFlightNo() {
        return queryFlightNo;
    }

    public void setQueryFlightNo(String queryFlightNo) {
        this.queryFlightNo = queryFlightNo;
    }
}
