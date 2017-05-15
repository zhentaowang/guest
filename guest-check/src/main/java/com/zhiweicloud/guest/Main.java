package com.zhiweicloud.guest;

import com.zhiweicloud.guest.server.Server;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by zhengyiyin on 2017/5/12.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server.SERVER_PORT = 8080;
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("production");
        context.setValidating(false);
        context.load( "classpath:spring.xml", "classpath:mybatis.xml");
        context.refresh();

        while (true) {
            System.out.println("start");
            Thread.sleep(1000000);
        }
    }
}
