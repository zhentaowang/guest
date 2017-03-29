/**
 * CardType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-29 10:38:57 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * CardType.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-29 10:38:57 Created By zhangpengfei
*/
@ApiModel(value="CardType",description="card_type")
public class CardType {
    @ApiModelProperty(value="协议客户id",name="cardId")
    private Long cardId;

    @ApiModelProperty(value="卡类别",name="cardName")
    private String cardName;

    @ApiModelProperty(value="是否删除：默认为0，0：不删除，1：删除",name="isDeleted")
    private Short isDeleted;

    @ApiModelProperty(value="机场code",name="airportCode", required=true)
    @NotEmpty
    private String airportCode;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="创建人",name="createUser")
    private Long createUser;

    @ApiModelProperty(value="修改时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="修改人",name="updateUser")
    private Long updateUser;

    /**
     * 协议客户id
     * @return card_id 协议客户id
     */
    public Long getCardId() {
        return cardId;
    }

    /**
     * 协议客户id
     * @param cardId 协议客户id
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    /**
     * 卡类别
     * @return card_name 卡类别
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * 卡类别
     * @param cardName 卡类别
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
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
     * 机场code
     * @return airport_code 机场code
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 机场code
     * @param airportCode 机场code
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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
     * 创建人
     * @return create_user 创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
     * 修改人
     * @return update_user 修改人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     * @param updateUser 修改人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}