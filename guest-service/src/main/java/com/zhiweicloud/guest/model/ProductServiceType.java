/**
 * ProductServiceType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-07 16:25:01 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * ProductServiceType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-07 16:25:01 Created By wzt
*/
@ApiModel(value="ProductServiceType",description="product_service_type")
public class ProductServiceType extends BaseEntity{
    @ApiModelProperty(value="主键id",name="productServiceTypeId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long productServiceTypeId;

    @ApiModelProperty(value="产品id",name="productId")
    private Long productId;

    @ApiModelProperty(value="服务分类id",name="serviceTypeId")
    private Long serviceTypeId;

    @Transient
    @ApiModelProperty(value="产品名称",name="productName")
    private String productName;

    @Transient
    @ApiModelProperty(value="产品编号",name="productNo")
    private String productNo;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Transient
    @ApiModelProperty(value="服务",name="serviceList")
    private List<Serv> serviceList;

    public List<Serv> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Serv> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * 主键id
     * @return product_service_type_id 主键id
     */
    public Long getProductServiceTypeId() {
        return productServiceTypeId;
    }

    /**
     * 主键id
     * @param productServiceTypeId 主键id
     */
    public void setProductServiceTypeId(Long productServiceTypeId) {
        this.productServiceTypeId = productServiceTypeId;
    }

    /**
     * 产品id
     * @return product_id 产品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 产品id
     * @param productId 产品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 服务分类id
     * @return service_type_id 服务分类id
     */
    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    /**
     * 服务分类id
     * @param serviceTypeId 服务分类id
     */
    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}