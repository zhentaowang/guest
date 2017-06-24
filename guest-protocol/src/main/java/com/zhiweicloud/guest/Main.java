package com.zhiweicloud.guest;

import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by luojing@wyunbank.com on 02/05/2017.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.getEnvironment().setActiveProfiles("production");
        context.load( "classpath:spring.xml", "classpath:mybatis.xml","classpath:spring-client.xml");
        context.refresh();
        Server server=new Server(8080);
        server.startSingleServer(SpringBeanUtil.getBean("businessService"),"businessService");
        while (true) {
            System.out.println("start");
            Thread.sleep(1000000);
        }
    }
}
