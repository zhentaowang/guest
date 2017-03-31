/**
 * FlightUpdateLog.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-29 14:38:37 Created By tc
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * FlightUpdateLog.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-29 14:38:37 Created By tc
*/
@ApiModel(value="FlightUpdateLog",description="flight_update_log")
public class FlightUpdateLog extends BaseEntity {

    @ApiModelProperty(value="主键自增id",name="flightLogId", required=true)
    @NotEmpty
    private Long flightLogId;

    @ApiModelProperty(value="航班id",name="flightId", required=true)
    @NotEmpty
    private Long flightId;

    @ApiModelProperty(value="操作员名字",name="operatorName", required=true)
    @NotEmpty
    private String operatorName;

    @ApiModelProperty(value="航班更新内容",name="updateMessage", required=true)
    @NotEmpty
    private String updateMessage;

    /**
     * 主键自增id
     * @return flight_log_id 主键自增id
     */
    public Long getFlightLogId() {
        return flightLogId;
    }

    /**
     * 主键自增id
     * @param flightLogId 主键自增id
     */
    public void setFlightLogId(Long flightLogId) {
        this.flightLogId = flightLogId;
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
     * 操作员名字
     * @return operator_name 操作员名字
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 操作员名字
     * @param operatorName 操作员名字
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 航班更新内容
     * @return update_message 航班更新内容
     */
    public String getUpdateMessage() {
        return updateMessage;
    }

    /**
     * 航班更新内容
     * @param updateMessage 航班更新内容
     */
    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

}