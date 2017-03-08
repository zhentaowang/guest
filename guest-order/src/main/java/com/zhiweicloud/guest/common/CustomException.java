package com.zhiweicloud.guest.common;

/**
 * Created by zhangpengfei on 2017/3/8.
 */
public class CustomException extends Exception{
    public CustomException(){}

    public CustomException(String message) {        //用来创建指定参数对象
        super(message);                             //调用超类构造器
    }
}
