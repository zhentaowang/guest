/**
 * Product.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 09:36:01 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Product.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 09:36:01 Created By Administrator
*/
@ApiModel(value="Product",description="product")
public class Product extends BaseEntity{
    @ApiModelProperty(value="产品id",name="productId", required=true)
    @NotEmpty
    private Long productId;

    @ApiModelProperty(value="产品名称",name="productName")
    private String productName;

    @ApiModelProperty(value="包含服务的id",name="serviceTypeIds")
    private String serviceTypeIds;

    @Transient
    @ApiModelProperty(value="产品编号",name="productNo")
    private String productNo;

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
     * 产品名称
     * @return product_name 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 产品名称
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 包含服务的id
     * @return service_type_ids 包含服务的id
     */
    public String getServiceTypeIds() {
        return serviceTypeIds;
    }

    /**
     * 包含服务的id
     * @param serviceTypeIds 包含服务的id
     */
    public void setServiceTypeIds(String serviceTypeIds) {
        this.serviceTypeIds = serviceTypeIds;
    }

    /**
     *
     * @return
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     *
     * @param productNo
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
}