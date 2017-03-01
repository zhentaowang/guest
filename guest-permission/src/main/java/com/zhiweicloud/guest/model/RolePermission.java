/**
 * RolePermission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 16:59:34 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RolePermission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-22 16:59:34 Created By wzt
*/
@ApiModel(value="RolePermission",description="role_permission")
public class RolePermission extends BaseEntity {
    @ApiModelProperty(value="主键自增id",name="rolePermissionId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long rolePermissionId;

    @ApiModelProperty(value="角色id",name="roleId")
    private Long roleId;

    @ApiModelProperty(value="权限id",name="permissionId")
    private Long permissionId;

    /**
     * 主键自增id
     * @return role_permission_id 主键自增id
     */
    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    /**
     * 主键自增id
     * @param rolePermissionId 主键自增id
     */
    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 权限id
     * @return permission_id 权限id
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 权限id
     * @param permissionId 权限id
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}