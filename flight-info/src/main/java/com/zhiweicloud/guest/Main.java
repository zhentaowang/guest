package com.zhiweicloud.guest;

import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.Dictionary;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by tc on 2017/5/5.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.getEnvironment().setActiveProfiles("production");
        context.load("classpath:spring.xml", "classpath:mybatis.xml","classpath:spring-client.xml");
        context.refresh();
        Server server = new Server(8080);
        server.startSingleServer(SpringBeanUtil.getBean("businessService"),"businessService");
        while (true) {
            System.out.println(Dictionary.START_MESSAGE);
            Thread.sleep(1000000);
        }
    }
}
