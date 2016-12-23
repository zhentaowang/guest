/**
 * SysMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 20:53:00 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * SysMenu.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 20:53:00 Created By zhangpengfei
*/
@ApiModel(value="SysMenu",description="sys_menu")
public class SysMenu {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    private Long id;

    @ApiModelProperty(value="父id",name="pid")
    private Long pid;

    @ApiModelProperty(value="菜单名称",name="name", required=true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value="菜单链接地址",name="url")
    private String url;

    @ApiModelProperty(value="菜单类型",name="type")
    private String type;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

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
}