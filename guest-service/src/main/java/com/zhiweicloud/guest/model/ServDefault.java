/**
 * ServDefault.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-10 14:00:28 Created By Administrator
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ServDefault.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-10 14:00:28 Created By zhengyiyin
*/
@ApiModel(value="ServDefault",description="serv_default")
public class ServDefault extends BaseEntity{

    @ApiModelProperty(value="",name="servDefaultId", required=true)
    @NotEmpty
    private Long servDefaultId;

    @ApiModelProperty(value="",name="servId")
    private Long servId;

    @ApiModelProperty(value="",name="employeeId")
    private Long employeeId;


    /**
     * 
     * @return serv_default_id 
     */
    public Long getServDefaultId() {
        return servDefaultId;
    }

    /**
     * 
     * @param servDefaultId 
     */
    public void setServDefaultId(Long servDefaultId) {
        this.servDefaultId = servDefaultId;
    }

    /**
     * 
     * @return serv_id 
     */
    public Long getServId() {
        return servId;
    }

    /**
     * 
     * @param servId 
     */
    public void setServId(Long servId) {
        this.servId = servId;
    }

    /**
     * 
     * @return employee_id 
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 
     * @param employeeId 
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


}