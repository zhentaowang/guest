/**
 * InstitutionClient.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * InstitutionClient.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
*/
@ApiModel(value="InstitutionClient",description="institution_client")
public class InstitutionClient {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    private Long id;

    @ApiModelProperty(value="机构客户编号",name="no")
    private String no;

    @ApiModelProperty(value="机构客户名称",name="name")
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

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted")
    private Short isDeleted;

    /**
     * 主键自增id
     * @return id 主键自增id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键自增id
     * @param id 主键自增id
     */
    public void setId(Long id) {
        this.id = id;
    }

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

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @return is_deleted 是否删除：默认为0，0：不删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @param isDeleted 是否删除：默认为0，0：不删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}