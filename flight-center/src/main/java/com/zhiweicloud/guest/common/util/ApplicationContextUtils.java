package com.zhiweicloud.guest.common.util;

import com.zhiweicloud.guest.common.Dictionary;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by tc on 2017/6/5.
 */
public class ApplicationContextUtils {

    public static void createContext(String[] profileName) throws InterruptedException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles(profileName);
        context.setValidating(false);
        context.load( "classpath:spring.xml", "classpath:mybatis.xml");
        context.refresh();
        while (true) {
            System.out.println(Dictionary.START_MESSAGE);
            Thread.sleep(1000000);
        }
    }

    public static void createContext(String profileName) throws InterruptedException {
        createContext(new String[]{profileName});
    }

}
