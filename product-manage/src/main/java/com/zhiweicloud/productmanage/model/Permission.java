/**
 * Permission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-23 18:06:14 Created By wzt
*/
package com.zhiweicloud.productmanage.model;

import com.zhiweicloud.guest.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Permission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-23 18:06:14 Created By wzt
*/
@ApiModel(value="Permission",description="permission")
public class Permission extends BaseEntity{
    @ApiModelProperty(value="接口url",name="url")
    private String url;

    @ApiModelProperty(value="url权限",name="authorizer")
    private String authorizer;

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

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }
}