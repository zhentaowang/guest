package com.zhiweicloud.guest.common.util;

import com.wyun.thrift.server.business.BusinessServiceMap;
import com.wyun.thrift.server.processor.WyunServiceImpl;
import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.Dictionary;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by tc on 2017/6/5.
 */
public class ApplicationContextUtils {

    public static void createContext(String[] profileName,int port) throws InterruptedException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.getEnvironment().setActiveProfiles("production");
        context.load("classpath:spring.xml","classpath:mybatis.xml");
        context.refresh();
        Server server=new Server(port);
        BusinessServiceMap businessServiceMap = new BusinessServiceMap();
        businessServiceMap.registerService("fundService", SpringBeanUtil.getBean("fundService"));
        WyunServiceImpl wyunServiceImpl = new WyunServiceImpl(businessServiceMap);
        server.setWyunServiceImpl(wyunServiceImpl);
        server.startServer();
        while (true) {
            System.out.println(Dictionary.START_MESSAGE);
            Thread.sleep(1000000);
        }
    }

    public static void createContext(String profileName) throws InterruptedException {
        createContext(new String[]{profileName}, 8080);
    }

    public static void createContext(String profileName, int port) throws InterruptedException {
        createContext(new String[]{profileName}, port);
    }

}
