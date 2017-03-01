/**
 * SysRole.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-04 10:23:24 Created By zhangpengfei
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
 * SysRole.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-04 10:23:24 Created By zhangpengfei
*/
@ApiModel(value="SysRole",description="sys_role")
public class SysRole extends BaseEntity{
    @ApiModelProperty(value="角色id",name="roleId")
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long roleId;

    @ApiModelProperty(value="角色名称",name="name", required=true)
    private String name;

    @ApiModelProperty(value="角色描述",name="description")
    private String description;

    @Transient
    @ApiModelProperty(value="菜单id集合",name="menuIdList")
    private List<Long> menuIdList;


    /**
     * 角色名称
     * @return name 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 角色名称
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}