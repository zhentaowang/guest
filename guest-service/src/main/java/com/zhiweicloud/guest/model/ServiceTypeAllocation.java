/**
 * ServiceTypeAllocation.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 10:26:47 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * ServiceTypeAllocation.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 10:26:47 Created By wzt
*/
@ApiModel(value="ServiceTypeAllocation",description="service_type_allocation")
public class ServiceTypeAllocation extends BaseEntity {

    @ApiModelProperty(value="服务类型配置Id",name="serviceTypeAllocationId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long serviceTypeAllocationId;

    @ApiModelProperty(value="大类编号",name="categoryNo")
    private String categoryNo;

    @ApiModelProperty(value="服务大类",name="category")
    private String category;

    @ApiModelProperty(value="服务类别",name="type")
    private String type;

    @ApiModelProperty(value="说明",name="description")
    private String description;

    @Transient
    @ApiModelProperty(value = "服务类别列表",name="serviceTypeList")
    private List<Dropdownlist> serviceTypeList;

    public List<Dropdownlist> getServiceTypeList() {
        return serviceTypeList;
    }
    public void setServiceTypeList(List<Dropdownlist> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }



    public Long getTypeId() {
        return serviceTypeAllocationId;
    }

    public void setTypeId(Long typeId) {
        this.serviceTypeAllocationId = typeId;
    }

    /**
     * 大类编号
     * @return category_no 大类编号
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 大类编号
     * @param categoryNo 大类编号
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 服务大类
     * @return category 服务大类
     */
    public String getCategory() {
        return category;
    }

    /**
     * 服务大类
     * @param category 服务大类
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 服务类别
     * @return type 服务类别
     */
    public String getType() {
        return type;
    }

    /**
     * 服务类别
     * @param type 服务类别
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 说明
     * @return description 说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 说明
     * @param description 说明
     */
    public void setDescription(String description) {
        this.description = description;
    }
}