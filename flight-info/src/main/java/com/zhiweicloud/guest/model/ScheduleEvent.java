/**
 * ScheduleEvent.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-03 20:47:22 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * ScheduleEvent.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-03 20:47:22 Created By wzt
*/
@ApiModel(value="ScheduleEvent",description="schedule_event")
public class ScheduleEvent extends BaseEntity{
    @ApiModelProperty(value="自增id",name="scheduleEventId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long scheduleEventId;

    @ApiModelProperty(value="调度事件编号",name="no")
    private String no;

    @ApiModelProperty(value="调度事件类型",name="type")
    private String type;

    @ApiModelProperty(value="调度事件描述信息",name="name")
    private String name;

    @ApiModelProperty(value="进港：1，出港：0",name="isApproach")
    private Short isApproach;

    @Transient
    @ApiModelProperty(value="调度时间",name="scheduleTime")
    private Date scheduleTime;

    @Transient
    @ApiModelProperty(value="调度事件更新人",name="scheduleUpdateUserName")
    private String scheduleUpdateUserName;

    @Transient
    @ApiModelProperty(value="调度事件备注说明",name="remark")
    private String remark;

    @Transient
    @ApiModelProperty(value="航班调度事件更新时间",name="flightScheduleUpdateTime")
    private Date flightScheduleUpdateTime;

    public Date getFlightScheduleUpdateTime() {
        return flightScheduleUpdateTime;
    }

    public void setFlightScheduleUpdateTime(Date flightScheduleUpdateTime) {
        this.flightScheduleUpdateTime = flightScheduleUpdateTime;
    }

    public String getScheduleUpdateUserName() {
        return scheduleUpdateUserName;
    }

    public void setScheduleUpdateUserName(String scheduleUpdateUserName) {
        this.scheduleUpdateUserName = scheduleUpdateUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    /**
     * 自增id
     * @return schedule_event_id 自增id
     */
    public Long getScheduleEventId() {
        return scheduleEventId;
    }

    /**
     * 自增id
     * @param scheduleEventId 自增id
     */
    public void setScheduleEventId(Long scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }

    /**
     * 调度事件编号
     * @return no 调度事件编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 调度事件编号
     * @param no 调度事件编号
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 调度事件类型
     * @return type 调度事件类型
     */
    public String getType() {
        return type;
    }

    /**
     * 调度事件类型
     * @param type 调度事件类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 调度事件描述信息
     * @return name 调度事件描述信息
     */
    public String getName() {
        return name;
    }

    /**
     * 调度事件描述信息
     * @param name 调度事件描述信息
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 进港：1，出港：0
     * @return is_approach 进港：1，出港：0
     */
    public Short getIsApproach() {
        return isApproach;
    }

    /**
     * 进港：1，出港：0
     * @param isApproach 进港：1，出港：0
     */
    public void setIsApproach(Short isApproach) {
        this.isApproach = isApproach;
    }
}