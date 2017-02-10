/**
 * Employee.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 11:08:54 Created By zhangpengfei
 */
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Employee.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-09 11:08:54 Created By zhangpengfei
 */
@ApiModel(value = "Employee", description = "employee")
public class Employee extends BaseEntity {
    @ApiModelProperty(value = "账号", name = "account")
    private String account;

    @ApiModelProperty(value = "用户名", name = "name", required = true)
    private String name;

    @ApiModelProperty(value = "密码", name = "password", required = true)
    private String password;

    @ApiModelProperty(value = "性别：0：男，1：女", name = "sex")
    private Short sex;

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
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
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


}