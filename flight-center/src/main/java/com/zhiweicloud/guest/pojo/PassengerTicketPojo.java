package com.zhiweicloud.guest.pojo;

import com.zhiweicloud.guest.po.PassengerTicketPo;

import java.util.Date;

/**
 * Created by tc on 2017/5/31.
 */
public class PassengerTicketPojo extends PassengerTicketPo {

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
    private Date infantBirthday;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别 0：女 1：男
     */
    private Short sex;

    @Override
    public Long getPassengerId() {
        return passengerId;
    }

    @Override
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
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
        this.idCard = idCard;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

}
