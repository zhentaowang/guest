package com.zhiweicloud.guest;

import com.zhiweicloud.guest.common.util.ApplicationContextUtils;

/**
 * test for localhost
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/5 20:25
 * @author tiecheng
 */
public class LocalMain {

    public static void main(String[] args) throws InterruptedException {
        String[] strings = {"local", "thriftLocal"};
//        ApplicationContextUtils.createContext("local");
        ApplicationContextUtils.createContext(strings);
    }

}
