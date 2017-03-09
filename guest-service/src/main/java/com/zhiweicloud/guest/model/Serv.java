/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:58:17 Created By wzt
*/
package com.zhiweicloud.guest.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 20:58:17 Created By wzt
*/
@ApiModel(value="Serv",description="serv")
public class Serv extends BaseEntity{

    @ApiModelProperty(value="服务Id",name="servId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long servId;

    @ApiModelProperty(value="服务类型配置id",name="serviceTypeAllocationId")
    private Long serviceTypeAllocationId;

    @ApiModelProperty(value="服务编号",name="no")
    private String no;

    @ApiModelProperty(value="服务名称",name="name")
    private String name;

    @ApiModelProperty(value="服务详情",name="ServiceDetail")
    private String serviceDetail;

    @Transient
    @ApiModelProperty(value="计价规则",name="pricingRule")
    private String pricingRule;

    @Transient
    @ApiModelProperty(value="是否计价：0，计价；1，不计价",name="isPricing")
    private Boolean isPricing;

    @Transient
    @ApiModelProperty(value="是否优先：0，优先使用；1，不优先",name="isPrioritized")
    private Boolean isPrioritized;

    @Transient
    @ApiModelProperty(value="是否可用：0，可用；1，不可用",name="isAvailabled")
    private Boolean isAvailabled;

    /**
     * 作为返回字段：服务厅位置数量
     */
    @Transient
    private Integer positionNum;

    /**
     * 作为返回字段：服务厅已使用位置数量
     */
    @Transient
    private Integer serverNum;

    public String getPricingRule() {
        return pricingRule;
    }

    public void setPricingRule(String pricingRule) {
        this.pricingRule = pricingRule;
    }

    public Boolean getPricing() {
        return isPricing;
    }

    public void setPricing(Boolean pricing) {
        isPricing = pricing;
    }

    public Boolean getPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(Boolean prioritized) {
        isPrioritized = prioritized;
    }

    public Boolean getAvailabled() {
        return isAvailabled;
    }

    public void setAvailabled(Boolean availabled) {
        isAvailabled = availabled;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 服务详情
     * @return ServiceDetail 服务详情
     */
    public String getServiceDetail() {
        return serviceDetail;
    }

    /**
     * 服务详情
     * @param serviceDetail 服务详情
     */
    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }
   
    public Long getServId() {
        return servId;
    }

    public void setServId(Long servId) {
        this.servId = servId;
    }

    /**
     * 服务类型配置id
     * @return service_type_allocation_id 服务类型配置id
     */
    public Long getServiceTypeAllocationId() {
        return serviceTypeAllocationId;
    }

    /**
     * 服务类型配置id
     * @param serviceTypeAllocationId 服务类型配置id
     */
    public void setServiceTypeAllocationId(Long serviceTypeAllocationId) {
        this.serviceTypeAllocationId = serviceTypeAllocationId;
    }

    public Integer getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(Integer positionNum) {
        this.positionNum = positionNum;
    }

    public Integer getServerNum() {
        return serverNum;
    }

    public void setServerNum(Integer serverNum) {
        this.serverNum = serverNum;
    }
}