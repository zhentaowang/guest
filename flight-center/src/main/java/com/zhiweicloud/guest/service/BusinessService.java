package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BusinessService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/24 19:35 
 * @author tiecheng
 */
@Service()
public class BusinessService implements IBusinessService {

    private static final Log log = LogFactory.getLog(BusinessService.class);

    @Autowired
    private TrainService trainService;

    @Autowired
    private TestService testService;

    @Autowired
    private FlightService flightService;

    @Override
    public JSONObject handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }
        if (log.isDebugEnabled()) {
            log.debug("request method name: " + operation);
        }
        switch (operation) {
            case "queryFlightInfo":
                success = flightService.queryFlightInfo(request);
                break;
            case "queryDynamicFlightInfo":
                success = flightService.queryDynamicFlightInfo(request);
                break;
            case "queryTrainByCondition":
                success = trainService.queryTrainByCondition(request);
                break;
            case "queryPassengerByTickNo":
                success = flightService.queryPassengerByTickNo(request);
                break;
            case "flightPush":
                flightService.flightPush(request);
                break;
            case "customFlight":
                flightService.customFlight(request);
                break;
            case "test":
                success = testService.testMethod(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

}
