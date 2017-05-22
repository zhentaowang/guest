package com.zhiweicloud.guest;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luojing@wyunbank.com on 02/05/2017.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Server.SERVER_PORT = 8080;
//        ExecutorService executor = Executors.newSingleThreadExecutor();
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("production");
        context.setValidating(false);
        context.load( "classpath:spring.xml", "classpath:mybatis.xml","classpath:spring-client.xml");
        context.refresh();
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                Server.startServer();
//            }
//        });

        while (true) {
            System.out.println("start");
            Thread.sleep(1000000);
        }
    }
}
