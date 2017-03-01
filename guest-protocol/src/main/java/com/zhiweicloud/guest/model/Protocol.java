/**
 * Protocol.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-30 15:34:40 Created By wzt
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Protocol.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-30 15:34:40 Created By wzt
*/
@ApiModel(value="Protocol",description="protocol")
public class Protocol extends BaseEntity{

    @ApiModelProperty(value="主键自增id",name="protocolId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long protocolId;

    @ApiModelProperty(value="机构客户id",name="institutionClientId")
    private Long institutionClientId;

    @Transient
    @ApiModelProperty(value="机构客户名称",name="institutionClientName")
    private String institutionClientName;

    @Transient
    @ApiModelProperty(value="机构客户编号",name="institutionClientNo")
    private String institutionClientNo;

    @Transient
    @ApiModelProperty(value="机构客户类型",name="institutionClientType")
    private String institutionClientType;

    @ApiModelProperty(value="协议编号",name="no")
    private String no;

    @ApiModelProperty(value="协议名称",name="name")
    private String name;

    @ApiModelProperty(value="协议类型",name="type")
    private String type;

    @ApiModelProperty(value="开始时间",name="startTime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value="结束时间",name="endTime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value="结算方式，0：预付，1：月结，2：包量，3：接待",name="clearForm")
    private Short clearForm;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    @Transient
    @ApiModelProperty(value = "授权人",name="authorizer")
    private List<Authorizer> authorizerList;

    @Transient
    @ApiModelProperty(value = "协议服务",name="protocolServ")
    private List<ProtocolServ> protocolServList;

    @Transient
    @ApiModelProperty(value="服务名称",name="serviceName")
    private String serviceName;

    @Transient
    @ApiModelProperty(value="产品品类",name="productCategory")
    private String productCategory;

    @Transient
    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
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

    public List<ProtocolServ> getProtocolServList() {
        return protocolServList;
    }

    public void setProtocolServList(List<ProtocolServ> protocolServList) {
        this.protocolServList = protocolServList;
    }

    public List<Authorizer> getAuthorizerList() {
        return authorizerList;
    }

    public void setAuthorizerList(List<Authorizer> authorizerList) {
        this.authorizerList = authorizerList;
    }

    /**
     * 机构客户id
     * @return institution_client_id 机构客户id
     */
    public Long getInstitutionClientId() {
        return institutionClientId;
    }

    /**
     * 机构客户id
     * @param institutionClientId 机构客户id
     */
    public void setInstitutionClientId(Long institutionClientId) {
        this.institutionClientId = institutionClientId;
    }

    /**
     * 机构客户名称
     * @return institutionClientName 机构客户名称
     */
    public String getInstitutionClientName() {
        return institutionClientName;
    }

    /**
     * 机构客户名称
     * @param institutionClientName 机构客户名称
     */
    public void setInstitutionClientName(String institutionClientName) {
        this.institutionClientName = institutionClientName;
    }

    /**
     * 机构客户编号
     * @return institutionClientNo 机构客户编号
     */
    public String getInstitutionClientNo() {
        return institutionClientNo;
    }

    /**
     * 机构客户编号
     * @param institutionClientNo 机构客户编号
     */
    public void setInstitutionClientNo(String institutionClientNo) {
        this.institutionClientNo = institutionClientNo;
    }

    /**
     * 机构客户类型
     * @return institutionClientType 机构客户类型
     */
    public String getInstitutionClientType() {
        return institutionClientType;
    }

    /**
     * 机构客户类型
     * @param institutionClientType 机构客户类型
     */
    public void setInstitutionClientType(String institutionClientType) {
        this.institutionClientType = institutionClientType;
    }

    /**
     * 协议编号
     * @return no 协议编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 协议编号
     * @param no 协议编号
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 协议名称
     * @return name 协议名称
     */
    public String getName() {
        return name;
    }

    /**
     * 协议名称
     * @param name 协议名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 协议类型
     * @return type 协议类型
     */
    public String getType() {
        return type;
    }

    /**
     * 协议类型
     * @param type 协议类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 开始时间
     * @return start_time 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 开始时间
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 结束时间
     * @return end_time 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 结束时间
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 结算方式，0：预付，1：月结，2：包量，3：接待
     * @return clear_form 结算方式，0：预付，1：月结，2：包量，3：接待
     */
    public Short getClearForm() {
        return clearForm;
    }

    /**
     * 结算方式，0：预付，1：月结，2：包量，3：接待
     * @param clearForm 结算方式，0：预付，1：月结，2：包量，3：接待
     */
    public void setClearForm(Short clearForm) {
        this.clearForm = clearForm;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}