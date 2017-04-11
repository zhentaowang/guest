package com.zhiweicloud.guest.APIUtil;

/**
 * @ClassName: LZResult
 * @Description: 封装系统统一的响应结构
 * @author zhangpengfei
 * @date 2016年12月14日 下午6:23:57
 * @version V1.0
 */
public enum LZStatus {
	// HTTP STATUS
	// 400 - Bad Reques
	BAD_REQUEST(400, "提交参数不匹配"),
	
	// 404 - not found
	NOT_FOUND(404, "请求结果未找到"),
	
	// 405 - Method Not Allowed
	METHOD_NOT_ALLOWED(405, "请求方法类型不匹配"),
	
	// 415 - Unsupported Media Type
	UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

	// 2XX成功
	SUCCESS(200, "操作成功"),
	ENBNAM(2002, "名称可用"),
	
	// 5xx失败
	ERROR(500, "操作失败"),
	REPNAM(5001, "有重复名称，请检查"),
	DATA_EMPTY(5003, "参数为空"),
	DATA_REF_ERROR(5004, "该项已被其他功能引用，无法删除；如需帮助请联系开发者"),
    ORDER_STATUS_FLOW_ERROR(5005,"订单状态更新异常，请检查正常的订单流转流程");

	private int value;

	private String display;

	public int value() {
		return value;
	}

	public String display() {
		return display;
	}

	private LZStatus(int value, String display) {
		this.value = value;
		this.display = display;
	}

	public static String getDisplayByValue(int value) {
		for (LZStatus state : LZStatus.values()) {
			if (value == state.value()) {
				return state.display();
			}
		}
		return null;
	}

}
