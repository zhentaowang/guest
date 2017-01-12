package com.airportcloud.productmanage.common;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: RequsetParamCollection 
 * @Description: 全局统一接受参数
 * @author zhangpengfei
 * @date 2016年12月15日 下午2:30:45
 * @version V1.0
 */
@ApiModel(value="全局统一接受参数",description="全局统一接受参数")
public class RequsetParams<T> implements Serializable {
	
	private static final long serialVersionUID = -672025261630909141L;
	
	@ApiModelProperty(value="数据",name="data")
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
