/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:58:17 Created By wzt
*/
package com.zhiweicloud.productmanage.model;

import com.zhiweicloud.guest.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:58:17 Created By wzt
*/
@ApiModel(value="Serv",description="serv")
public class Serv extends BaseEntity{
    @ApiModelProperty(value="产品类型配置id",name="productTypeAllocationId")
    private Long productTypeAllocationId;

    @ApiModelProperty(value="服务编号",name="no")
    private String no;

    @ApiModelProperty(value="服务名称",name="name")
    private String name;

    @Transient
    @ApiModelProperty(value="产品品类",name="productCategory")
    private String productCategory;

    @Transient
    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    @ApiModelProperty(value="出发类型",name="departType")
    private String departType;

    @ApiModelProperty(value="航站楼",name="terminal")
    private String terminal;

    @ApiModelProperty(value="是否安检：默认为0，0：安检前，1：安检后",name="isSecurityCheck", required=true)
    @NotEmpty
    private Short isSecurityCheck;

    @ApiModelProperty(value="适用登机口",name="availableGate")
    private String availableGate;

    @ApiModelProperty(value="区域",name="region")
    private String region;

    @ApiModelProperty(value="详细地点",name="addressDetail")
    private String addressDetail;

    @Transient
    @ApiModelProperty(value="单价，单位：元",name="price")
    private BigDecimal price;

    @Transient
    @ApiModelProperty(value="免费随员人数",name="freeRetinueNum")
    private Integer freeRetinueNum;

    @Transient
    @ApiModelProperty(value="超员单价，单位：元",name="overStaffUnitPrice")
    private BigDecimal overStaffUnitPrice;

    @Transient
    @ApiModelProperty(value="价格说明",name="description")
    private String description;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Transient
    @ApiModelProperty(value="服务名称",name="serviceName")
    private String serviceName;

    public String getDescription() {
        return description;
    }

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
     * 服务编号
     * @return no 服务编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 服务编号
     * @param no 服务编号
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 服务名称
     * @return name 服务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 服务名称
     * @param name 服务名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 出发类型
     * @return depart_type 出发类型
     */
    public String getDepartType() {
        return departType;
    }

    /**
     * 出发类型
     * @param departType 出发类型
     */
    public void setDepartType(String departType) {
        this.departType = departType;
    }

    /**
     * 航站楼
     * @return terminal 航站楼
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * 航站楼
     * @param terminal 航站楼
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * 是否安检：默认为0，0：安检前，1：安检后
     * @return is_security_check 是否安检：默认为0，0：安检前，1：安检后
     */
    public Short getIsSecurityCheck() {
        return isSecurityCheck;
    }

    /**
     * 是否安检：默认为0，0：安检前，1：安检后
     * @param isSecurityCheck 是否安检：默认为0，0：安检前，1：安检后
     */
    public void setIsSecurityCheck(Short isSecurityCheck) {
        this.isSecurityCheck = isSecurityCheck;
    }

    /**
     * 适用登机口
     * @return available_gate 适用登机口
     */
    public String getAvailableGate() {
        return availableGate;
    }

    /**
     * 适用登机口
     * @param availableGate 适用登机口
     */
    public void setAvailableGate(String availableGate) {
        this.availableGate = availableGate;
    }

    /**
     * 区域
     * @return region 区域
     */
    public String getRegion() {
        return region;
    }

    /**
     * 区域
     * @param region 区域
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 详细地点
     * @return address_detail 详细地点
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 详细地点
     * @param addressDetail 详细地点
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}