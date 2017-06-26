package com.zhiweicloud.guest;

import com.zhiweicloud.guest.common.Dictionary;
import com.zhiweicloud.guest.common.util.ApplicationContextUtils;

/**
 * Main.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/12 21:24
 * @author tiecheng
 */
public class Main {

    public static void main( String[] args ) throws InterruptedException {
        String env = System.getenv("LOCAL_ENV");
        if ("dev".equals(env)) {
            ApplicationContextUtils.createContext(env, Dictionary.testPort);
        }else {
            ApplicationContextUtils.createContext("production");
        }
    }

}
