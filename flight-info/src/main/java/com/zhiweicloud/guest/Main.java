package com.zhiweicloud.guest;

import com.zhiweicloud.guest.common.Dictionary;

import com.zhiweicloud.guest.server.Server;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tc on 2017/5/5.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server.SERVER_PORT = 8080;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("production");
        context.setValidating(false);
        context.load( "classpath:spring.xml", "classpath:mybatis.xml");
        context.refresh();

        executor.execute(() -> Server.startServer());

        while (true) {
            System.out.println(Dictionary.START_MESSAGE);
            Thread.sleep(1000000);
        }
    }
}
