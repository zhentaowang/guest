/**
 * OrderCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:29:23 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * OrderCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-27 19:29:23 Created By zhangpengfei
*/
@ApiModel(value="OrderCar",description="order_car")
public class OrderCar {
    @ApiModelProperty(value="主键自增id",name="id", required=true)
    @NotEmpty
    @Id
    private Long id;

    @ApiModelProperty(value="预约订单id",name="orderId", required=true)
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value="车牌",name="carNo")
    private String carNo;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted")
    private Short isDeleted;

    @ApiModelProperty(value="机场id",name="ariportId", required=true)
    @NotEmpty
    private Long ariportId;
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
     * 预约订单id
     * @return order_id 预约订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 预约订单id
     * @param orderId 预约订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 车牌
     * @return car_no 车牌
     */
    public String getCarNo() {
        return carNo;
    }

    /**
     * 车牌
     * @param carNo 车牌
     */
    public void setCarNo(String carNo) {
        this.carNo = carNo;
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

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @return is_deleted 是否删除：默认为0，0：不删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     * @param isDeleted 是否删除：默认为0，0：不删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 机场id
     * @return ariport_id 机场id
     */
    public Long getAriportId() {
        return ariportId;
    }

    /**
     * 机场id
     * @param ariportId 机场id
     */
    public void setAriportId(Long ariportId) {
        this.ariportId = ariportId;
    }
}