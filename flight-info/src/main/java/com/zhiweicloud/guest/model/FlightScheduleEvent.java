/**
 * FlightScheduleEvent.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-09 09:06:03 Created By wzt
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * FlightScheduleEvent.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-09 09:06:03 Created By wzt
*/
@ApiModel(value="FlightScheduleEvent",description="flight_schedule_event")
public class FlightScheduleEvent extends BaseEntity{
    @ApiModelProperty(value="自增id",name="flightScheduleEventId", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long flightScheduleEventId;

    @ApiModelProperty(value="航班id",name="flightId")
    private Long flightId;

    @ApiModelProperty(value="调度事件id",name="scheduleEventId")
    private Long scheduleEventId;

    @ApiModelProperty(value="调度时间",name="scheduleTime")
    private Date scheduleTime;

    /**
     * 调度时间
     * @return scheduleTime 调度时间
     */
    public Date getScheduleTime() {
        return scheduleTime;
    }

    /**
     * 调度时间
     * @param scheduleTime 调度时间
     */
    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    /**
     * 自增id
     * @return flight_schedule_event_id 自增id
     */
    public Long getFlightScheduleEventId() {
        return flightScheduleEventId;
    }

    /**
     * 自增id
     * @param flightScheduleEventId 自增id
     */
    public void setFlightScheduleEventId(Long flightScheduleEventId) {
        this.flightScheduleEventId = flightScheduleEventId;
    }

    /**
     * 航班id
     * @return flight_id 航班id
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * 航班id
     * @param flightId 航班id
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * 调度事件id
     * @return schedule_event_id 调度事件id
     */
    public Long getScheduleEventId() {
        return scheduleEventId;
    }

    /**
     * 调度事件id
     * @param scheduleEventId 调度事件id
     */
    public void setScheduleEventId(Long scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }
}