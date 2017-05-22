/*
 * Copyright (c) 2016. Hangzhou Quantum Finance Co., Ltd. All rights reserved.
 * Created by luojing@wyunbank.com.
 */

package com.zhiweicloud.guest.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan({"com.zhiweicloud.guest","com.wyun.thrift"})
public class SpringConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

