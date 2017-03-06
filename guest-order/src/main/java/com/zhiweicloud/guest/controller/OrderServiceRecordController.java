package com.zhiweicloud.guest.controller;

import com.zhiweicloud.guest.service.OrderServiceRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created by Administrator on 2017/3/4.
 */
@Component
@Path("/")
@Api(value = "订单", description = "订单desc ", tags = {"订单管理"})
public class OrderServiceRecordController {
    @Autowired
    private OrderServiceRecordService orderServiceRecordService;


    
}
