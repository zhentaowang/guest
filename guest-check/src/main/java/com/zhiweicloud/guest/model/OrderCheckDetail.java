/**
 * OrderCheckDetail.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-15 11:04:10 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.ws.rs.QueryParam;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * OrderCheckDetail.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-15 11:04:10 Created By zhangpengfei
*/
@ApiModel(value="OrderCheckDetail",description="order_check_detail")
public class OrderCheckDetail extends BaseEntity{
    @ApiModelProperty(value="列头",name="column")
    private String column;

    @ApiModelProperty(value="查询列",name="selectFields")
    private String selectFields;

    @ApiModelProperty(value="总价",name="totalAmount")
    private String totalAmount;

    @ApiModelProperty(value="产品名称",name="productName")
    @QueryParam("queryProductName")
    private String queryProductName;

    @ApiModelProperty(value="客户id",name="queryCustomerId")
    @QueryParam("queryCustomerId")
    private Long queryCustomerId;

    @ApiModelProperty(value="协议id",name="queryProtocolId")
    @QueryParam("queryProtocolId")
    private Long queryProtocolId;

    @ApiModelProperty(value="协议类型",name="queryProtocolType")
    @QueryParam("queryProtocolType")
    private Long queryProtocolType;

    @ApiModelProperty(value="查询条件",name="queryProductType")
    private String queryWhere;






    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSelectFields() {
        return selectFields;
    }

    public void setSelectFields(String selectFields) {
        this.selectFields = selectFields;
    }

    public String getQueryProductName() {
        return queryProductName;
    }

    public void setQueryProductName(String queryProductName) {
        this.queryProductName = queryProductName;
    }

    public Long getQueryCustomerId() {
        return queryCustomerId;
    }

    public Long getQueryProtocolType() {
        return queryProtocolType;
    }

    public void setQueryProtocolType(Long queryProtocolType) {
        this.queryProtocolType = queryProtocolType;
    }

    public void setQueryCustomerId(Long queryCustomerId) {
        this.queryCustomerId = queryCustomerId;
    }

    public String getQueryWhere() {
        return queryWhere;
    }

    public void setQueryWhere(String queryWhere) {
        this.queryWhere = queryWhere;
    }

    public Long getQueryProtocolId() {
        return queryProtocolId;
    }

    public void setQueryProtocolId(Long queryProtocolId) {
        this.queryProtocolId = queryProtocolId;
    }
}