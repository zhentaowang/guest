/**
 * Product.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 09:36:01 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


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
    @GeneratedValue(generator = "JDBC")
    private Long productId;

    @ApiModelProperty(value="产品名称",name="productName")
    private String productName;


    @Transient
    private String productNo;

    @Transient
    private List<Long> serviceTypeIds;


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

    public List<Long> getServiceTypeIds() {
        return serviceTypeIds;
    }

    public void setServiceTypeIds(List<Long> serviceTypeIds) {
        this.serviceTypeIds = serviceTypeIds;
    }

}