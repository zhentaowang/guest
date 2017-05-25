package com.zhiweicloud.guest;

import com.zhiweicloud.guest.common.Dictionary;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

    public static void main( String[] args ) throws InterruptedException {
        createContext();
    }

    private static void createContext() throws InterruptedException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("production");
        context.setValidating(false);
        context.load( "classpath:spring.xml", "classpath:mybatis.xml");
        context.refresh();
        while (true) {
            System.out.println(Dictionary.START_MESSAGE);
            Thread.sleep(1000000);
        }
    }

}
