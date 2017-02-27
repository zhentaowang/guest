package com.zhiweicloud.guest.model;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzh on 2016/8/15.
 */
public enum HttpStatus {
    DELETE_OK("success", "删除成功"), ADD_OK("success", "添加成功"),
    DELETE_FAIL("fail", "删除失败"), ADD_FAIL("fail", "添加失败"),
    UPDATE_FAIL("fail", "更新失败"), UPDATE_OK("success", "更新成功"),
    NO_DATA_FAIL("fail", "获取数据失败"), REPEAT_DATA_ERROR("fail", "请不要重复添加！"),
    NO_USER_FAIL("fail", "用户不存在"), HANDLE_OK("success", "操作成功"),HANDLE_FAIL("fail", "操作失败"),
    DATA_OK("success","获取数据成功"), LIST_TOO_LAST("fail","对不起，访问列表数量受限，开通vip访问访问更多"),
    LIST_TOO_LARGE("fail","对不起，您访问的数据量过大"), TAG_CHAR_ERROR("fail", "标签只能包含中文、英文、数字或符号(仅含（）().)"),
    TAG_LENGTH_ERROR("fail","标签字数长度控制在2-10个字"), TAG_COUNT_ERROR("fail","标签总个数控制在100个以为，请删除标签后再添加"),
    NOT_VIP_FAIL("fail","对不起，您不是vip用户，请开通vip查看后续数据",101), NO_AUTHORITY("fail","对不起，您没有权限",100),
    INVALID_PARAM_ERROR("fail","对不起，参数有误"),DATA_EMPTY_FAIL("fail", "暂无此项数据");

    private String status;
    private String display_message;
    private int code;

    HttpStatus(String status, String dispaly_message) {
        this.status = status;
        this.display_message = dispaly_message;
    }



    HttpStatus(String status, String message, int code) {
        this.status = status;
        this.display_message = message;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplay_message() {
        return display_message;
    }

    public void setDisplay_message(String display_message) {
        this.display_message = display_message;
    }

    public String toJSONString() {
        Map map = new HashMap<>();
        map.put("status",status);
        map.put("display_message",display_message);
        map.put("code",code);
        return JSON.toJSONString(map);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
