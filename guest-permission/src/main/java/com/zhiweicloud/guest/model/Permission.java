/**
 * Permission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 16:59:34 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Permission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 16:59:34 Created By wzt
*/
@ApiModel(value="Permission",description="permission")
public class Permission extends BaseEntity {
    @ApiModelProperty(value="主键自增id",name="permissionId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long permissionId;

    @ApiModelProperty(value="接口url",name="url")
    private String url;

    @ApiModelProperty(value="权限名称",name="name")
    private String name;

    @Transient
    @ApiModelProperty(value="角色权限Id",name="rolePermissionId")
    private Long rolePermissionId;

    @Transient
    @ApiModelProperty(value="角色Id",name="roleId")
    private Long roleId;

    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 主键自增id
     * @return permission_id 主键自增id
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 主键自增id
     * @param permissionId 主键自增id
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 接口url
     * @return url 接口url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 接口url
     * @param url 接口url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 权限名称
     * @return name 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 权限名称
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }
}