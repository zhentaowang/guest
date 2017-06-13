package com.zhiweicloud.guest;

import com.wyun.thrift.server.business.BusinessServiceMap;
import com.wyun.thrift.server.processor.WyunServiceImpl;
import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.util.ApplicationContextUtils;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Main.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/12 21:24
 * @author tiecheng
 */
public class Main {

    public static void main( String[] args ) throws InterruptedException {
        /*String[] strings = {"production", "thriftProduction"};
        ApplicationContextUtils.createContext(strings);*/


        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.getEnvironment().setActiveProfiles("production");
        context.load("classpath:spring.xml","classpath:mybatis.xml");
        context.refresh();
        Server server=new Server(8080);
        BusinessServiceMap businessServiceMap = new BusinessServiceMap();
        businessServiceMap.registerService("fundService", SpringBeanUtil.getBean("fundService"));
        WyunServiceImpl wyunServiceImpl = new WyunServiceImpl(businessServiceMap);
        server.setWyunServiceImpl(wyunServiceImpl);
        server.startServer();
        while (true) {
            System.out.println("start");
            Thread.sleep(1000000);
        }
    }

}
