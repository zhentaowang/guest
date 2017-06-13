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
    private FlightService flightService;

    @Autowired
    private DetrService detrService;

    @Autowired
    private FlightPushService flightPushService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ApiService sourceService;

    @Override
    public JSONObject handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }
        if (log.isDebugEnabled()) {
            log.debug("【 ************ request method params: " + request.toJSONString() +" ************ 】");
        }
        switch (operation) {
            // for outer net
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
                success = detrService.queryPassengerByTickNo(request);
                break;
            case "flightPush":
                flightPushService.flightPush(request);
                break;
            case "customFlight":
                success = flightService.customFlight(request);
                break;
            // for test
            case "customer1":
                success = flightPushService.testCustom1(request);
                break;
            case "customer2":
                success = flightPushService.testCustom2(request);
                break;
            // for inner net
            case "queryCustomerDropDownList":
                success = customerService.queryCustomerDropDownList(request);
                break;
            case "queryCustomerDetail":
                success = customerService.queryCustomerDetail(request);
                break;
            case "querySourceDropDownList":
                success = sourceService.querySourceDropDownList(request);
                break;
            case "querySourceApiByNameAndDate": // 未使用
                success = sourceService.querySourceApiByNameAndDate(request);
                break;
            case "queryFlightCenterApis":
                success = sourceService.queryFlightCenterApis(request);
                break;
            case "querySourceApis":
                success = sourceService.querySourceApis(request);
                break;
            case "countForFlightCenterApi":
                success = sourceService.countForFlightCenterApi(request);
                break;
            case "countForSourceApi":
                success = sourceService.countForSourceApi(request);
                break;
            case "queryPageFlightCenterApis":
                success = sourceService.queryFlightCenterApisPage(request);
                break;
            case "queryPageSourceApis":
                success = sourceService.querySourceApisPage(request);
                break;
            case "queryPageFlightPushs":
                success = flightPushService.queryFlightPushsPage(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

}
