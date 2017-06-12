package com.zhiweicloud.guest.exception;

/**
 * SignatureException.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/31 17:00
 * @author tiecheng
 */
public class SignatureException extends RuntimeException {

    public SignatureException() {
    }

    public SignatureException(String message) {
        super(message);
    }

}
