package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 *
 * ProtocolInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:19:25 Created By zhangpengfei
 */
@ApiModel(value="ProtocolInfo",description="协议相关信息")
public class ProtocolInfo {
    @ApiModelProperty(value="协议:id,value的形式",name="protocol")
    private Dropdownlist protocol;

    @ApiModelProperty(value="协议编号",name="no")
    private String no;

    @ApiModelProperty(value="机构客户:id,value的形式",name="orgCustomer")
    private Dropdownlist orgCustomer;

    @ApiModelProperty(value="协议备注",name="remark")
    private String remark;

    @ApiModelProperty(value="预约人",name="personDropDownList")
    private List<Dropdownlist> personDropDownList;

    @ApiModelProperty(value = "机场code", name = "airportCode", required = true)
    @NotEmpty
    private String airportCode;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Dropdownlist getOrgCustomer() {
        return orgCustomer;
    }

    public void setOrgCustomer(Dropdownlist orgCustomer) {
        this.orgCustomer = orgCustomer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Dropdownlist> getPersonDropDownList() {
        return personDropDownList;
    }

    public void setPersonDropDownList(List<Dropdownlist> personDropDownList) {
        this.personDropDownList = personDropDownList;
    }

    public Dropdownlist getProtocol() {
        return protocol;
    }

    public void setProtocol(Dropdownlist protocol) {
        this.protocol = protocol;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
