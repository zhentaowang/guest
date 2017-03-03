package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.dragon.sign.DragonSignature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.Global;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.service.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 2017-03-03 20:47:22 Created By wzt
 */
@Component
@Path("/")
@Api(value = "调度管理", description = "调度管理desc ", tags = {"flight-info"})
public class ScheduleEventController {
    
}
