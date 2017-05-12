package com.zhiweicloud.guest.conf;

import com.zhiweicloud.guest.flight.center.DragonService;
import com.zhiweicloud.guest.flight.center.FlightCenterService;
import com.zhiweicloud.guest.flight.center.IbeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * 航班中心配置类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/11 16:59
 * @author tiecheng
 */
@Configuration
@Import({SpringConfiguration.class})
public class FlightCenterConfig {

    @Bean(name = "flightCoreService")
    public FlightCenterService serivceLoad(Environment environment){
        String center = environment.getProperty("flight.center");
        if ("ibe".equals(center)) {
            return new IbeService();
        }else {
            return new DragonService();
        }
    }

}
