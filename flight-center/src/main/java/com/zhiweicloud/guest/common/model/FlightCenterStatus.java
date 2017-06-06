package com.zhiweicloud.guest.common.model;

import com.zhiweicloud.guest.APIUtil.LZStatus;

/**
 * Created by tc on 2017/5/18.
 */
public enum  FlightCenterStatus {

    // HTTP STATUS
    // 400 - Bad Reques
    BAD_REQUEST(400, "提交参数不匹配"),

    // 404 - not found
    NOT_FOUND(404, "请求结果未找到"),

    // 405 - Method Not Allowed
    METHOD_NOT_ALLOWED(405, "请求方法类型不匹配"),

    // 415 - Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

    NON_TRAIN(202,"查询不到高铁信息记录"),

    FLIGHT_CENTER_ERROR(303,"航班中心异常"),

    // 缺少参数
    NONE_SYS_CODE(309,"缺少参数sysCode"),
    NONE_FLIGHT_DATE(310,"缺少参数depDate"),
    NONE_FLIGHT_NO(311,"缺少参数flightNo"),
    NONE_DEP_AIRPORT_CODE(312,"缺少参数depAirportCode"),
    NONE_ARR_AIRPORT_CODE(313,"缺少参数arrAirportCode"),

    NONE_FLIGHT(318,"没有此航班"),

    NONE_CUSTOMER(320,"此客户不存在，请先购买"),
    NONE_TIME_ENOUGH(330,"次数不足，请续费购买"),

    ILLEGAL_DATE(380,"非法的日期"),

    SIGN_INVALID(339,"签名无效"),
    NOT_AUTH(340,"签名未通过"),
    FLIGHT_DATE_INVALID(351,"只能查询今日或未来的航班"),

    // 2XX成功
    SUCCESS(200, "操作成功"),
    ENBNAM(2002, "名称可用"),

    LOCAL_OK(201, "航班中心查询成功"),

    // 5xx失败
    ERROR(500, "操作失败"),
    REPNAM(5001, "有重复名称，请检查"),
    DATA_EMPTY(5003, "参数为空"),
    DATA_REF_ERROR(5004, "该项已被其他功能引用，无法删除；如需帮助请联系开发者"),
    DATA_TRANSFER_ERROR(4995, "传输数据字段错误"),
    ORDER_STATUS_FLOW_ERROR(5005, "订单状态更新异常，请检查正常的订单流转流程");

    private int value;

    private String display;

    public int value() {
        return value;
    }

    public String display() {
        return display;
    }

    FlightCenterStatus(int value, String display) {
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
