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
        System.out.println(System.getenv("LOCAL_ENV"));
//        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
//            System.out.println(entry.getKey() + "   " + entry.getValue());
//            System.out.println();
//        }
//
//        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
//            System.out.println(entry.getKey() + "   " + entry.getValue());
//            System.out.println();
//        }
        ApplicationContextUtils.createContext("local",8901);
    }

}
