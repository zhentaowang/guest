/**
 * ProtocolServ.java
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
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * ProtocolServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-04 14:50:19 Created By wzt
*/
@ApiModel(value="ProtocolServ",description="protocol_serv")
public class ProtocolServ extends BaseEntity{

    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="服务id",name="serviceId")
    private Long serviceId;

    @ApiModelProperty(value="产品类型配置id",name="productTypeAllocationId")
    private Long productTypeAllocationId;

    @ApiModelProperty(value="单价，单位：元",name="price")
    private BigDecimal price;

    @ApiModelProperty(value="免费随员人数",name="freeRetinueNum")
    private Integer freeRetinueNum;

    @ApiModelProperty(value="超员单价，单位：元",name="overStaffUnitPrice")
    private BigDecimal overStaffUnitPrice;

    @ApiModelProperty(value="价格说明",name="description")
    private String description;

    @Transient
    @ApiModelProperty(value="服务名称",name="serviceName")
    private String serviceName;

    @Transient
    @ApiModelProperty(value="产品品类",name="productCategory")
    private String productCategory;

    @Transient
    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    public List<ProtocolServ> getProtocolServList() {
        return protocolServList;
    }

    public void setProtocolServList(List<ProtocolServ> protocolServList) {
        this.protocolServList = protocolServList;
    }

    @Transient
    @ApiModelProperty(value = "协议服务",name="protocolServList")
    private List<ProtocolServ> protocolServList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
     * 单价，单位：元
     * @return price 单价，单位：元
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 单价，单位：元
     * @param price 单价，单位：元
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 价格说明
     * @return description 价格说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 价格说明
     * @param description 价格说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOverStaffUnitPrice() {
        return overStaffUnitPrice;
    }

    public void setOverStaffUnitPrice(BigDecimal overStaffUnitPrice) {
        this.overStaffUnitPrice = overStaffUnitPrice;
    }

    public Integer getFreeRetinueNum() {
        return freeRetinueNum;
    }

    public void setFreeRetinueNum(Integer freeRetinueNum) {
        this.freeRetinueNum = freeRetinueNum;
    }
}

