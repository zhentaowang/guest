package com.zhiweicloud.guest.thrift.service;

import com.alibaba.fastjson.JSONObject;

import com.zhiweicloud.guest.thrift.util.FlightUtil;
import com.zhiweicloud.guest.thrift.util.ScheduleEventUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tc on 2017/5/5.
 */
@Service
public class FlightModuleService implements IFlightSerivce {

    private static final Log log = LogFactory.getLog(FlightModuleService.class);

    @Autowired
    private FlightUtil flightUtil;

    @Autowired
    private ScheduleEventUtil scheduleEventUtil;

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
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
        return success;
    }

}
