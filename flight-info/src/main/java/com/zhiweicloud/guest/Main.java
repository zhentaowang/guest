package com.zhiweicloud.guest;

import com.wyun.thrift.server.business.BusinessServiceMap;
import com.wyun.thrift.server.processor.WyunServiceImpl;
import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.Dictionary;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tc on 2017/5/5.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
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
