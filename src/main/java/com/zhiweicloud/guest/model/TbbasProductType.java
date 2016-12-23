/**
 * TbbasProductType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-22 16:32:39 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * TbbasProductType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-22 16:32:39 Created By zhangpengfei
*/
@ApiModel(value="TbbasProductType",description="tbbas_product_type")
public class TbbasProductType {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    private Long id;

    @ApiModelProperty(value="产品品类",name="productType", required=true)
    @NotEmpty
    private String productType;

    @ApiModelProperty(value="品类编号",name="sno", required=true)
    @NotEmpty
    private String sno;

    @ApiModelProperty(value="服务类型",name="serviceType")
    private String serviceType;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

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
     * 产品品类
     * @return product_type 产品品类
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 产品品类
     * @param productType 产品品类
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 品类编号
     * @return sno 品类编号
     */
    public String getSno() {
        return sno;
    }

    /**
     * 品类编号
     * @param sno 品类编号
     */
    public void setSno(String sno) {
        this.sno = sno;
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