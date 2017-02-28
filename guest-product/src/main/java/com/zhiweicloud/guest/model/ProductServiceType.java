/**
 * ProductServiceType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-27 09:32:11 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProductServiceType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-27 09:32:11 Created By zhengyiyin
*/
@ApiModel(value="ProductServiceType",description="product_service_type")
public class ProductServiceType extends BaseEntity{

    @ApiModelProperty(value="主键id",name="productServiceTypeId", required=true)
    @NotEmpty
    private Long productServiceTypeId;

    @ApiModelProperty(value="产品id",name="productId")
    private Long productId;

    @ApiModelProperty(value="服务分类id",name="serviceTypeId")
    private Long serviceTypeId;


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