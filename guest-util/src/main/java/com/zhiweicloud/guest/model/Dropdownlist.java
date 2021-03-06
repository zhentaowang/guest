package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * Created by zhangpengfei on 2016/12/26.
 */
@ApiModel(value="Dropdownlist",description="下来框对象模型")
public class Dropdownlist {
    @ApiModelProperty(value="下拉框id",name="id")
    @Id
    private Long id;

    @ApiModelProperty(value="下拉框显示值",name="value")

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
