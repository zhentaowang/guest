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
import org.hibernate.validator.constraints.NotEmpty;

/**
 * SysRole.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-02-04 10:23:24 Created By zhangpengfei
*/
@ApiModel(value="SysRole",description="sys_role")
public class SysRole extends BaseEntity{
    @ApiModelProperty(value="角色名称",name="name", required=true)
    @NotEmpty
    private String name;

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
}