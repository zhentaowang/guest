/**
 * Authorizer.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authorizer.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
@ApiModel(value="Authorizer",description="authorizer")
public class Authorizer extends BaseEntity{

    @ApiModelProperty(value="主键自增id",name="authorizerId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long authorizerId;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="姓名",name="name")
    private String name;

    @ApiModelProperty(value="手机号",name="cellphone")
    private String cellphone;

    @ApiModelProperty(value="电话",name="telephone")
    private String telephone;

    public Long getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Long authorizerId) {
        this.authorizerId = authorizerId;
    }

    /**
     * 协议id
     * @return protocol_id 协议id
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * 协议id
     * @param protocolId 协议id
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 手机号
     * @return cellphone 手机号
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * 手机号
     * @param cellphone 手机号
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 电话
     * @return telephone 电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 电话
     * @param telephone 电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}