/**
 * SysRoleMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-06 14:53:27 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * SysRoleMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-06 14:53:27 Created By zhangpengfei
*/
@ApiModel(value="SysRoleMenu",description="sys_role_menu")
public class SysRoleMenu extends BaseEntity{
    @ApiModelProperty(value="角色id",name="roleId", required=true)
    private Long roleId;

    @ApiModelProperty(value="菜单id",name="menuId", required=true)
    private Long menuId;


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
     * 菜单id
     * @return menu_id 菜单id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 菜单id
     * @param menuId 菜单id
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}