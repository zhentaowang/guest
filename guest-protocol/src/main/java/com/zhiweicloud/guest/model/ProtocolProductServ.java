/**
 * ProtocolProductServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-27 21:06:37 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * ProtocolProductServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-27 21:06:37 Created By wzt
*/
@ApiModel(value="ProtocolProductServ",description="protocol_product_service")
public class ProtocolProductServ extends BaseEntity{
    @ApiModelProperty(value="主键自增Id",name="protocolProductServiceId", required=true)
    @NotEmpty
    private Long protocolProductServiceId;

    @ApiModelProperty(value="协议产品Id",name="protocolProductId")
    private Long protocolProductId;

    @ApiModelProperty(value="服务类型配置Id",name="serviceTypeAllocationId")
    private Long serviceTypeAllocationId;

    @ApiModelProperty(value="服务Id",name="serviceId")
    private Long serviceId;

    @ApiModelProperty(value="计价规则",name="pricingRule")
    private String pricingRule;

    @ApiModelProperty(value="是否计价：0，不计价；1，计价",name="isPricing")
    private Boolean isPricing;

    @ApiModelProperty(value="是否优先：0，不优先；1，优先使用",name="isPrioritized")
    private Boolean isPrioritized;

    @ApiModelProperty(value="是否可用：0，不可用；1，可用",name="isAvailabled")
    private Boolean isAvailabled;

    @Transient
    @ApiModelProperty(value="服务大类",name="category")
    private String category;

    @Transient
    @ApiModelProperty(value="服务类别",name="type")
    private String type;

    @Transient
    @ApiModelProperty(value="服务编号",name="no")
    private String no;

    @Transient
    @ApiModelProperty(value="服务名称",name="name")
    private String name;

    @Transient
    @ApiModelProperty(value = "服务类别列表",name="serviceTypeList")
    private List<ProtocolProductServ> serviceTypeList;

    public List<ProtocolProductServ> getServiceTypeList() {
        return serviceTypeList;
    }
    public void setServiceTypeList(List<ProtocolProductServ> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 服务大类
     * @return category 服务大类
     */
    public String getCategory() {
        return category;
    }

    /**
     * 服务大类
     * @param category 服务大类
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 服务类别
     * @return type 服务类别
     */
    public String getType() {
        return type;
    }

    /**
     * 服务类别
     * @param type 服务类别
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 主键自增Id
     * @return protocol_product_service_id 主键自增Id
     */
    public Long getProtocolProductServiceId() {
        return protocolProductServiceId;
    }

    /**
     * 主键自增Id
     * @param protocolProductServiceId 主键自增Id
     */
    public void setProtocolProductServiceId(Long protocolProductServiceId) {
        this.protocolProductServiceId = protocolProductServiceId;
    }

    /**
     * 协议产品Id
     * @return protocol_product_id 协议产品Id
     */
    public Long getProtocolProductId() {
        return protocolProductId;
    }

    /**
     * 协议产品Id
     * @param protocolProductId 协议产品Id
     */
    public void setProtocolProductId(Long protocolProductId) {
        this.protocolProductId = protocolProductId;
    }

    public Long getServiceTypeAllocationId() {
        return serviceTypeAllocationId;
    }

    public void setServiceTypeAllocationId(Long serviceTypeAllocationId) {
        this.serviceTypeAllocationId = serviceTypeAllocationId;
    }

    /**
     * 服务Id
     * @return service_id 服务Id
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * 服务Id
     * @param serviceId 服务Id
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 
     * @return pricing_rule 
     */
    public String getPricingRule() {
        return pricingRule;
    }

    /**
     * 
     * @param pricingRule 
     */
    public void setPricingRule(String pricingRule) {
        this.pricingRule = pricingRule;
    }

    /**
     * 是否计价：0，计价；1，不计价
     * @return is_pricing 是否计价：0，计价；1，不计价
     */
    public Boolean getIsPricing() {
        return isPricing;
    }

    /**
     * 是否计价：0，计价；1，不计价
     * @param isPricing 是否计价：0，计价；1，不计价
     */
    public void setIsPricing(Boolean isPricing) {
        this.isPricing = isPricing;
    }

    /**
     * 是否优先：0，优先使用；1，不优先
     * @return is_prioritized 是否优先：0，优先使用；1，不优先
     */
    public Boolean getIsPrioritized() {
        return isPrioritized;
    }

    /**
     * 是否优先：0，优先使用；1，不优先
     * @param isPrioritized 是否优先：0，优先使用；1，不优先
     */
    public void setIsPrioritized(Boolean isPrioritized) {
        this.isPrioritized = isPrioritized;
    }

    /**
     * 是否可用：0，可用；1，不可用
     * @return is_availabled 是否可用：0，可用；1，不可用
     */
    public Boolean getIsAvailabled() {
        return isAvailabled;
    }

    /**
     * 是否可用：0，可用；1，不可用
     * @param isAvailabled 是否可用：0，可用；1，不可用
     */
    public void setIsAvailabled(Boolean isAvailabled) {
        this.isAvailabled = isAvailabled;
    }
}