/**
 * ProtocolServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
package com.airportcloud.protocolmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * ProtocolServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
@ApiModel(value="ProtocolServ",description="protocol_serv")
public class ProtocolServ {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    private Long id;

    @ApiModelProperty(value="机场code",name="airportCode", required=true)
    @NotEmpty
    private String airportCode;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="服务id",name="serviceId")
    private Long serviceId;

    @ApiModelProperty(value="产品类型配置id",name="productTypeAllocationId")
    private Long productTypeAllocationId;

    @ApiModelProperty(value="价格说明",name="price")
    private String price;

    @ApiModelProperty(value="说明",name="description")
    private String description;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted")
    private Short isDeleted;

    @Transient
    @ApiModelProperty(value="服务名称",name="serviceName")
    private String serviceName;

    @Transient
    @ApiModelProperty(value="产品品类",name="productCategory")
    private String productCategory;

    @Transient
    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    @Transient
    @ApiModelProperty(value="协议名称",name="protocolName")
    private String protocolName;

    /**
     * 协议名称
     * @return protocolName 协议名称
     */
    public String getProtocolName() {
        return protocolName;
    }

    /**
     * 协议名称
     * @param protocolName 协议名称
     */
    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    /**
     * 服务名称
     * @return serviceName 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 服务名称
     * @param serviceName 服务名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 产品品类
     * @return product_category 产品品类
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * 产品品类
     * @param productCategory 产品品类
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * 服务类型
     * @return service_type 服务类型
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 服务类型
     * @param serviceType 服务类型
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

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
     * 服务id
     * @return service_id 服务id
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * 服务id
     * @param serviceId 服务id
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 产品类型配置id
     * @return product_type_allocation_id 产品类型配置id
     */
    public Long getProductTypeAllocationId() {
        return productTypeAllocationId;
    }

    /**
     * 产品类型配置id
     * @param productTypeAllocationId 产品类型配置id
     */
    public void setProductTypeAllocationId(Long productTypeAllocationId) {
        this.productTypeAllocationId = productTypeAllocationId;
    }

    /**
     * 价格说明
     * @return price 价格说明
     */
    public String getPrice() {
        return price;
    }

    /**
     * 价格说明
     * @param price 价格说明
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 说明
     * @return description 说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 说明
     * @param description 说明
     */
    public void setDescription(String description) {
        this.description = description;
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