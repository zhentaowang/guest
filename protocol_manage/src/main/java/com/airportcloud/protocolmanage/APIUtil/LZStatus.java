package com.airportcloud.protocolmanage.APIUtil;

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
	
	SSO_LOGIN_EMPTY(5002, "用户名或密码不能为空"),
	SSO_LOGIN_USER_ERROR(5004, "用户名或密码错误"),
	REQUIRE_DATE_EMPTY(5005, "请检查必填字段是否填写完毕"),
	SSO_TICKET_EMPTY(5006, "ticket为空"),
	SSO_LOGIN_LX2OA_FAIL(5007, "登录OA链接失败"),
	
	TREE_TABLE_ERROR(5008, "tableName对应值不存在"),
	MAX_UPLOAD_SIZE(5009, "单文件的大小不能超过20M"),
	COMPANY(5010,"当前页面单位重复"),
	BID(5011,"没有创建招标不能继续"),
	FLOW_STARTED(5013, "流程已启动，请勿重复发起流程"),
	FLOW_DID_NOT_STARTED(5015, "流程还未启动"),
	FLOW_CONFIG_FIRST(5014, "请先配置流程，然后再发起流程"),
	FLOW_ANALY_ERROR(5016, "流程解析错误，请检查"),
	FLOW_OA_USERNAME_NULL(5017, "系统所匹配的OA登录名为空，请检查"),
	GLOBAL_PERMISSION(5012,"模块菜单不能删除"),
	ORG_DEPARTMENT_PARENT_CHOICE_ERROR(5018,"不能选择子部门/自身作为父部门"),
	ORG_COMPANY_PARENT_CHOICE_ERROR(5019, "不能选择子公司/自身作为父公司"),
	PARENT_SUB_NODE_CHOICE(5020,"不能选择子节点/自身作为父节点"),
	PROJECT_NODE(5021,"不可以违反规则移动项目"),
	PROJECT_DELETE(5022,"请删除分类下面的项目之后进行"),
	PROJECT_REMOVE(5024,"不可以创建与所有项目平级的节点"),
	POSITION_REFED(5023,"此岗位已经被引用了，无法删除"),
	PROJECT_TREE(5025,"项目分类不可以与项目平级"),
	NEWS_DATA(5026,"不可以在审核状态和未通过状态下时发布"),
	NEWS_DATA_DRAFT(5027,"添加草稿成功"),
	NEWS_DATA_REVIEW(5028,"送审成功"),
	NEWS_DATA_PASS(5029,"通过"),
	NEWS_DATA_NO_PASS(5030,"不通过"),
	NEWS_DATA_ISSUE(5031,"发布成功"),
	NEWS_DATA_RETURN_DRAFT(5032,"返回草稿状态"),
	PROJECT_EXIST_SUB_PROJECT(5033,"当前项目下存在子项目,请先删除子项目"),
	REPCODE(5034,"编号重复");
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
