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
    private Integer type;

    @ApiModelProperty(value="预约号",name="reservationNum")
    private String reservationNum;

    @ApiModelProperty(value="开始日期",name="startDate")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value="结束日期",name="endDate")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value="结算方式，0：预付，1：月结，2：包量，3：接待",name="clearForm")
    private Short clearForm;

    @ApiModelProperty(value="协议说明",name="remark")
    private String remark;

    @Transient
    @ApiModelProperty(value = "授权人",name="authorizer")
    private List<Authorizer> authorizerList;

    @Transient
    @ApiModelProperty(value = "协议产品",name="protocolProduct")
    private List<ProtocolProduct> protocolProductList;

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    public List<ProtocolProduct> getProtocolProductList() {
        return protocolProductList;
    }

    public void setProtocolProductList(List<ProtocolProduct> protocolProductList) {
        this.protocolProductList = protocolProductList;
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
    public Integer getType() {
        return type;
    }

    /**
     * 协议类型
     * @param type 协议类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 开始时间
     * @return startDate 开始日期
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     * @return endDate 结束日期
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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