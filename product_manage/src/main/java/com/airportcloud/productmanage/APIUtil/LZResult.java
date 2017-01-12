package com.airportcloud.productmanage.APIUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: LZResult
 * @Description: 封装系统统一的响应结构
 * @author zhangpengfei
 * @date 2016年12月14日 下午6:23:57
 * @version V1.0
 */
@ApiModel(value = "响应结果", description = "响应结果")
public class LZResult<DATA> {

	// 响应状态码
	@ApiModelProperty(value = "响应状态码", name = "status", required = true)
	private Integer status;

	// 响应消息
	@ApiModelProperty(value = "响应消息", name = "msg", required = true)
	private String msg;

	// 响应数据
	@ApiModelProperty(value = "响应数据", name = "data", required = false)
	private DATA data;

	// 无参构造
	public LZResult() {
	}

	// 带data构造
	public LZResult(DATA data) {
		this.status = LZStatus.SUCCESS.value();
		this.msg = LZStatus.SUCCESS.display();
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DATA getData() {
		return data;
	}

	public void setData(DATA data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "APIResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

}
