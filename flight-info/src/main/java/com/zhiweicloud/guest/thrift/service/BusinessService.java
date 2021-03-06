package com.zhiweicloud.guest.thrift.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.thrift.util.FlightUtils;
import com.zhiweicloud.guest.thrift.util.ScheduleEventUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 航班服务接口实现类
 * 根据方法名分配执行方法
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/9 20:25
 * @author tiecheng
 */
@Service
public class BusinessService implements IBusinessService {

    private static final Log log = LogFactory.getLog(BusinessService.class);

    @Autowired
    private FlightUtils flightUtil;

    @Autowired
    private ScheduleEventUtils scheduleEventUtil;

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;
        if (log.isDebugEnabled()) {
            log.debug("request name: " + JSON.toJSONString(request));
        }
        if (log.isDebugEnabled()) {
            log.debug("request method name: " + operation);
        }
        switch (operation) {
            case "flightInfo":
                success = flightUtil.flightInfo(request);
                break;
            case "getFlightForOrderDetail":
                success = flightUtil.getFlightForOrderDetail(request);
                break;
            case "flightInfoDropdownList":
                success = flightUtil.flightInfoDropdownList(request);
                break;
            case "flightNoDropdownList":
                success = flightUtil.flightNoDropdownList(request);
                break;
            case "updateFlight":
                success = flightUtil.updateFlight(request);
                break;
            case "saveOrUpdateFlightScheduleEvent":
                success = flightUtil.saveOrUpdateFlightScheduleEvent(request);
                break;
            case "customFlight":
                success = flightUtil.customFlight(request);
                break;
            case "flightLog":
                success = flightUtil.flightLog(request);
                break;
            case "updateFlightInfo":
                success = flightUtil.updateFlightInfo(request);
                break;
            case "flight-update":
                success = scheduleEventUtil.flightUpdate(request);
                break;
            case "get-flight-list":
                success = scheduleEventUtil.getFlightList(request);
                break;
            case "get-flight-view":
                success = scheduleEventUtil.getFlightView(request);
                break;
            case "schedule-event-list":
                success = scheduleEventUtil.scheduleEventList(request);
                break;
            case "schedule-event-save-or-update":
                success = scheduleEventUtil.scheduleEventSaveOrUpdate(request);
                break;
            case "schedule-event-view":
                success = scheduleEventUtil.scheduleEventView(request);
                break;
            case "get-schedule-event-by-flight-id":
                success = scheduleEventUtil.getScheduleEventByFlightId(request);
                break;
            case "get-schedule-event-drop-down-box":
                success = scheduleEventUtil.getScheduleEventDropDownBox(request);
                break;
            case "schedule-event-delete":
                success = scheduleEventUtil.scheduleEventDelete(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

}
