/**
 * EmployeePermission.java
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
 * EmployeePermission.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-23 18:06:14 Created By wzt
*/
@ApiModel(value="EmployeePermission",description="employee_permission")
public class EmployeePermission extends BaseEntity{
    @ApiModelProperty(value="员工id",name="employeeId")
    private Long employeeId;

    @ApiModelProperty(value="权限id",name="permissionId")
    private Long permissionId;

    /**
     * 员工id
     * @return employee_id 员工id
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 员工id
     * @param employeeId 员工id
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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