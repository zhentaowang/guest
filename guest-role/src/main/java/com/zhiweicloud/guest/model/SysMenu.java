/**
 * SysMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-07 09:27:44 Created By zhangpengfei
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
 * SysMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-07 09:27:44 Created By zhangpengfei
*/
@ApiModel(value="SysMenu",description="sys_menu")
public class SysMenu extends BaseEntity{
    @ApiModelProperty(value="父id",name="pid")
    private Long pid;

    @ApiModelProperty(value="菜单名称",name="name", required=true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value="菜单链接地址",name="url")
    private String url;

    @ApiModelProperty(value="菜单类型",name="type")
    private String type;

    @ApiModelProperty(value="子菜单",name="children")
    @Transient
    private List<SysMenu> children;

    /**
     * 父id
     * @return pid 父id
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 父id
     * @param pid 父id
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 菜单名称
     * @return name 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 菜单名称
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 菜单链接地址
     * @return url 菜单链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 菜单链接地址
     * @param url 菜单链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 菜单类型
     * @return type 菜单类型
     */
    public String getType() {
        return type;
    }

    /**
     * 菜单类型
     * @param type 菜单类型
     */
    public void setType(String type) {
        this.type = type;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}