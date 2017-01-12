package com.airportcloud.productmanage.APIUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: LXResult 
 * @Description: 封装系统统一的响应结构
 * @author zhangpengfei
 * @date 2016年12月21日 下午6:23:57
 * @version V1.0
 */
@ApiModel(value="响应结果")
public class LXResult {

	//响应状态码
	@ApiModelProperty(value="响应状态码",name="status", required=true)
	private Integer status;
	
	//响应消息
	@ApiModelProperty(value="响应消息",name="msg", required=true)
	private String msg;
	
	//响应数据
	@ApiModelProperty(value="响应数据",name="data", required=false)
	private Object data;
	
	//无参构造
	public LXResult() { }

	//带data构造
	public LXResult(Object data) {
		this.status = LZStatus.SUCCESS.value();
		this.msg = LZStatus.SUCCESS.display();
		this.data = data;
	}
	
	//带参构造
	public LXResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	//操作成功并返回数据
	public static LXResult success(Object data) {
		return new LXResult(data);
	}
	
	//操作成功不返回数据
	public static LXResult success() {
		return new LXResult(null);
	}
	
	//操作失败
	public static LXResult error(){
		return new LXResult(LZStatus.ERROR.value(), LZStatus.ERROR.display(), null);
	}
	
	//响应结果并返回数据
	public static LXResult build(Integer status, String msg, Object data) {
		return new LXResult(status, msg, data);
	}
	
	//响应结果不返回数据
	public static LXResult build(Integer status, String msg) {
		return new LXResult(status, msg, null);
	}
	
	//响应结果不返回数据(参数枚举类型)
	public static LXResult build(LZStatus lxStatus){
		return LXResult.build(lxStatus.value(), lxStatus.display());
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LXResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
