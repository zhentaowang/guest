/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-13 20:46:33 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Transient;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * Passenger.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-13 20:46:33 Created By Administrator
*/
@ApiModel(value="PassengerQuery",description="仅用crm条件查询使用")
public class PassengerQuery extends BaseEntity{

    @QueryParam("passengerNo")
    private String passengerNo;

    @ApiModelProperty(value="旅客姓名",name="name")
    @QueryParam("name")
    private String name;

    @ApiModelProperty(value="联系方式",name="phone")
    @QueryParam("phone")
    private Long phone;

    @ApiModelProperty(value="身份证",name="identityCard")
    @QueryParam("identityCard")
    private String identityCard;

    @ApiModelProperty(value="协议客户名称",name="clientName")
    @QueryParam("clientName")
    private String clientName;

    @ApiModelProperty(value="协议名称",name="protocolName")
    @QueryParam("protocolName")
    private String protocolName;


    @ApiModelProperty(value="协议类型",name="protocolTypes")
    @QueryParam("protocolTypes")
    private String protocolTypes;

    @ApiModelProperty(value="标签",name="labels")
    @QueryParam("labels")
    private String labels;

    @ApiModelProperty(value="查询开始日期",name="queryDateBegin")
    @QueryParam("queryDateBegin")
    private String queryDateBegin;

    @ApiModelProperty(value="查询结束日期",name="queryDateEnd")
    @QueryParam("queryDateEnd")
    private String queryDateEnd;

    @ApiModelProperty(value="查询协议类型",name="types")
    @QueryParam("types")
    private String types;




    /**
     * 协议客户名称
     * @return client_name 协议客户名称
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 协议客户名称
     * @param clientName 协议客户名称
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 旅客姓名
     * @return name 旅客姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 旅客姓名
     * @param name 旅客姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 联系方式
     * @return phone 联系方式
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * 联系方式
     * @param phone 联系方式
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * 身份证
     * @return identity_card 身份证
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 身份证
     * @param identityCard 身份证
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolTypes() {
        return protocolTypes;
    }

    public void setProtocolTypes(String protocolTypes) {
        this.protocolTypes = protocolTypes;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getQueryDateBegin() {
        return queryDateBegin;
    }

    public void setQueryDateBegin(String queryDateBegin) {
        this.queryDateBegin = queryDateBegin;
    }

    public String getQueryDateEnd() {
        return queryDateEnd;
    }

    public void setQueryDateEnd(String queryDateEnd) {
        this.queryDateEnd = queryDateEnd;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(String passengerNo) {
        this.passengerNo = passengerNo;
    }
}