/**
 * InstitutionClient.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Transient;

/**
 * InstitutionClient.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
*/
@ApiModel(value="InstitutionClient",description="institution_client")
public class InstitutionClient extends BaseEntity{
    @ApiModelProperty(value="机构客户id",name="institutionClientId")
    private Long institutionClientId;

    @ApiModelProperty(value="机构客户编号",name="no")
    private String no;

    @ApiModelProperty(value="机构客户名称",name="name",required = true)
    private String name;

    @ApiModelProperty(value="机构客户类型",name="type")
    private String type;

    @ApiModelProperty(value="跟进人id",name="employeeId")
    private Long employeeId;

    @Transient
    @ApiModelProperty(value="跟进人名称",name="employeeId")
    private String employeeName;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    /**
     * 机构客户编号
     * @return no 机构客户编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 机构客户编号
     * @param no 机构客户编号
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 机构客户名称
     * @return name 机构客户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 机构客户名称
     * @param name 机构客户名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 机构客户类型
     * @return type 机构客户类型
     */
    public String getType() {
        return type;
    }

    /**
     * 机构客户类型
     * @param type 机构客户类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 跟进人
     * @return employee_id 跟进人
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 跟进人
     * @param employeeId 跟进人
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getInstitutionClientId() {
        return institutionClientId;
    }

    public void setInstitutionClientId(Long institutionClientId) {
        this.institutionClientId = institutionClientId;
    }
}