/**
 * SysRole.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-04 10:23:24 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * SysRole.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-04 10:23:24 Created By zhangpengfei
*/
@ApiModel(value="sysRoleParam",description="用于接收前台参数")
public class SysRoleParam{
    @ApiModelProperty(
            value = "角色id",
            name = "id",
            required = true
    )
    private Long id;


    @ApiModelProperty(
            value = "菜单对象",
            name = "sysMenu",
            required = true
    )
    private List<SysMenuParam> sysMenuList;

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String airportCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SysMenuParam> getSysMenuList() {
        return sysMenuList;
    }

    public void setSysMenuList(List<SysMenuParam> sysMenuList) {
        this.sysMenuList = sysMenuList;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}