package com.zhiweicloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by zhangpengfei on 2016/12/14.
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
