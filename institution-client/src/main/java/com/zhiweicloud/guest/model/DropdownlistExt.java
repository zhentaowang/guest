package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * Created by thd on 2017/2/15 0015.
 */

@ApiModel(
    value = "DropdownlistExt",
    description = "下来框对象模型扩展"
)
public class DropdownlistExt extends Dropdownlist{

    @ApiModelProperty(
        value = "下拉框no",
        name = "no"
    )

    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
