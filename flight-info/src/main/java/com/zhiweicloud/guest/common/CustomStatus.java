package com.zhiweicloud.guest.common;

/**
 * CustomStatus.java
 * 向龙腾定制航班信息 状态枚举类
 * <p>
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * https://www.zhiweicloud.com
 * 2017/4/5 10:40
 *
 * @author tiecheng
 */
public enum CustomStatus {

    ERROR(-1, "定制航班异常"),
    EMPTY(-2, "无法定制不存在航班"),
    FAIL(-3, "定制航班失败"),
    SUCCESS(1, "定制航班成功");

    private int state;
    private String info;

    public int state() {
        return this.state;
    }

    public String info() {
        return this.info;
    }

    CustomStatus(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public static String getInfoByState(int state) {
        CustomStatus[] customStatuses = values();
        int length = customStatuses.length;
        for (int i = 0; i < length; i++) {
            CustomStatus status = customStatuses[i];
            if (state == status.state()) {
                return status.info();
            }
        }
        return null;
    }

}
