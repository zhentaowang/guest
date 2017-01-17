/**
 * Authorizer.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
package com.zhiweicloud.protocolmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import java.util.Date;

/**
 * Authorizer.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
@ApiModel(value="Authorizer",description="authorizer")
public class Authorizer {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    private Long id;

    @ApiModelProperty(value="机场code",name="airportCode", required=true)
    @NotEmpty
    private String airportCode;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="姓名",name="name")
    private String name;

    @ApiModelProperty(value="手机号",name="cellphone")
    private String cellphone;

    @ApiModelProperty(value="电话",name="telephone")
    private String telephone;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted")
    private Short isDeleted;

    /**
     * 主键自增id
     * @return id 主键自增id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键自增id
     * @param id 主键自增id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 机场code
     * @return airport_code 机场code
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 机场code
     * @param airportCode 机场code
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @return is_deleted 是否删除：默认为0，0：不删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @param isDeleted 是否删除：默认为0，0：不删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}