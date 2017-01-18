/**
 * ProductTypeAllocation.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 10:26:47 Created By zhangpengfei
*/
package com.zhiweicloud.productmanage.model;

import com.zhiweicloud.guest.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProductTypeAllocation.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 10:26:47 Created By zhangpengfei
*/
@ApiModel(value="ProductTypeAllocation",description="product_type_allocation")
public class ProductTypeAllocation extends BaseEntity {
    @ApiModelProperty(value="品类编号",name="categoryNo")
    private String categoryNo;

    @ApiModelProperty(value="产品品类",name="productCategory")
    private String productCategory;

    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    @ApiModelProperty(value="免费随员人数",name="freeRetinueNum")
    private Integer freeRetinueNum;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    /**
     * 品类编号
     * @return category_no 品类编号
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 品类编号
     * @param categoryNo 品类编号
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 产品品类
     * @return product_category 产品品类
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * 产品品类
     * @param productCategory 产品品类
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * 服务类型
     * @return service_type 服务类型
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 服务类型
     * @param serviceType 服务类型
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * 免费随员人数
     * @return free_retinue_num 免费随员人数
     */
    public Integer getFreeRetinueNum() {
        return freeRetinueNum;
    }

    /**
     * 免费随员人数
     * @param freeRetinueNum 免费随员人数
     */
    public void setFreeRetinueNum(Integer freeRetinueNum) {
        this.freeRetinueNum = freeRetinueNum;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}