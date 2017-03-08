/**
 * ProtocolProduct.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-01 14:22:00 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * ProtocolProduct.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-01 14:22:00 Created By wzt
*/
@ApiModel(value="ProtocolProduct",description="protocol_product")
public class ProtocolProduct extends BaseEntity{
    @ApiModelProperty(value="主键id",name="protocolProductId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long protocolProductId;

    @ApiModelProperty(value="协议id",name="protocolId")
    private Long protocolId;

    @ApiModelProperty(value="产品id",name="productId")
    private Long productId;

    @ApiModelProperty(value="产品说明",name="productDesc")
    private String productDesc;

    @Transient
    @ApiModelProperty(value="产品名称",name="productName")
    private String productName;

    @Transient
    @ApiModelProperty(value="产品编号",name="productNo")
    private String productNo;

    @Transient
    @ApiModelProperty(value = "协议产品服务",name="protocolProductService")
    private List<ProtocolProductServ> protocolProductServList;

    public List<ProtocolProductServ> getProtocolProductServList() {
        return protocolProductServList;
    }

    public void setProtocolProductServList(List<ProtocolProductServ> protocolProductServList) {
        this.protocolProductServList = protocolProductServList;
    }

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

    /**
     * 主键id
     * @return protocol_product_id 主键id
     */
    public Long getProtocolProductId() {
        return protocolProductId;
    }

    /**
     * 主键id
     * @param protocolProductId 主键id
     */
    public void setProtocolProductId(Long protocolProductId) {
        this.protocolProductId = protocolProductId;
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
     * 产品说明
     * @return product_desc 产品说明
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * 产品说明
     * @param productDesc 产品说明
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}