/**
 * Employee.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 11:08:54 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * Employee.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 11:08:54 Created By zhangpengfei
 */
@ApiModel(value = "Employee", description = "employee")
public class Employee extends BaseEntity {
    @ApiModelProperty(value = "employeeId", name = "employeeId")
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    private Long employeeId;


    @ApiModelProperty(value = "账号", name = "account")
    private String account;

    @ApiModelProperty(value = "用户名", name = "name", required = true)
    private String name;

    @ApiModelProperty(value = "性别：0：男，1：女", name = "sex")
    private Short sex;

    @Transient
    @ApiModelProperty(value="角色id集合",name="roleIdList")
    private List<Long> roleIdList;

    @Transient
    @ApiModelProperty(value="判断新增的记录是否存在：0：新增，1：修改",name="isExist")
    private Integer isExist;

    /**
     * 账号
     * @return account 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 用户名
     * @return name 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 用户名
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * 性别：0：男，1：女
     * @return sex 性别：0：男，1：女
     */
    public Short getSex() {
        return sex;
    }

    /**
     * 性别：0：男，1：女
     * @param sex 性别：0：男，1：女
     */
    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public Integer getIsExist() {
        return isExist;
    }

    public void setIsExist(Integer isExist) {
        this.isExist = isExist;
    }
}