package com.zhiweicloud.guest.common;

/**
 * PushStatus.java
 * 龙腾推送航班信息 状态枚举类
 * <p>
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * https://www.zhiweicloud.com
 * 2017/4/5 9:26
 *
 * @author tiecheng
 */
public enum PushStatus {

    ERROR(-1, "操作失败"),
    EMPTY(-2, "推送航班信息为空"),
    SUCCESS(1, "接收并处理成功"),
    REPEAT(2, "接收并处理成功");

    private int state;
    private String info;

    public int state() {
        return this.state;
    }

    public String info() {
        return this.info;
    }

    PushStatus(int value, String display) {
        this.state = value;
        this.info = display;
    }

    public static String getInfoByState(int value) {
        PushStatus[] dragonStatuses = values();
        int length = dragonStatuses.length;
        for (int i = 0; i < length; i++) {
            PushStatus state = dragonStatuses[i];
            if (value == state.state()) {
                return state.info();
            }
        }
        return null;
    }

}
