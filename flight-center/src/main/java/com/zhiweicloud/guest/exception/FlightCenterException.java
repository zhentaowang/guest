package com.zhiweicloud.guest.exception;

/**
 * 航班中心异常
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/13 10:52
 * @author tiecheng
 */
public class FlightCenterException extends RuntimeException {

    public FlightCenterException() {
    }

    public FlightCenterException(String message) {
        super(message);
    }

}
