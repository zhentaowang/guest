package com.zhiweicloud.guest;

import com.wyun.zookeeper.conf.AppProperties;
import com.zhiweicloud.guest.common.Dictionary;
import com.zhiweicloud.guest.common.util.ApplicationContextUtils;
import com.zhiweicloud.guest.conf.DataManager;

import java.util.Map;

/**
 * Main.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/12 21:24
 * @author tiecheng
 */
public class Main {

    public static void main( String[] args ) throws InterruptedException {
        // 测试配置中心
        DataManager dataChange = new DataManager();
        AppProperties appProperties = new AppProperties("flight_center");
        appProperties.registerDataChangeHandler(dataChange);
        appProperties.init();
        String env = System.getenv("LOCAL_ENV");
        if ("dev".equals(env)) {
            Map<String, String> map = appProperties.getProperties("jdbc_local");
            System.out.println("properties: "+map.toString());
            ApplicationContextUtils.createContext(env, Dictionary.testPort);
        }else {
            Map<String, String> map = appProperties.getProperties("jdbc_test");
            System.out.println("properties: "+map.toString());
            ApplicationContextUtils.createContext("production");
        }
    }

}
