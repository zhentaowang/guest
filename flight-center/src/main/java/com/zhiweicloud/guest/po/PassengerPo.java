/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 基础乘客表
 * 
 * @author IronC
 * @version 1.0  2017-05-31
 */
public class PassengerPo {

    /**
    * 表名
    */
    public static final transient String TABLE_NAME = "base_passenger";

    /**
     * 主键自增id
     * 不能为空
     */
    private Long passengerId;

    /**
     * 乘客名字
     * 不能为空
     */
    private String passengerName;

    /**
     * 婴儿的生日（年月）
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date infantBirthday;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别 0：女 1：男
     */
    private Short sex;

    /**
     * 是否删除：默认为0，0：不删除，1：删除
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName == null ? null : passengerName.trim();
    }

    public Date getInfantBirthday() {
        return infantBirthday;
    }

    public void setInfantBirthday(Date infantBirthday) {
        this.infantBirthday = infantBirthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * out method.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", passengerId=").append(passengerId);
        sb.append(", passengerName=").append(passengerName);
        sb.append(", infantBirthday=").append(infantBirthday);
        sb.append(", idCard=").append(idCard);
        sb.append(", sex=").append(sex);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}