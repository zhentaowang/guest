package com.zhiweicloud.guest.thrift.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.ScheduleEventMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightInfoQuery;
import com.zhiweicloud.guest.model.ScheduleEvent;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tc on 2017/5/5.
 */
@Service
public class ScheduleEventUtil {

    @Autowired
    private ScheduleEventMapper scheduleEventMapper;

    @Autowired
    private FlightMapper flightMapper;

    public String flightUpdate(JSONObject request) {
//        request
        return null;
    }

    /**
     * 调度管理 - 航班信息
     * @param request
     * @return
     */
    public String getFlightList(JSONObject request){
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        String flightDate = request.getString("flightDate");
        String flightState = request.getString("flightState");
        String isInorOut = request.getString("isInorOut");
        String page = request.getString("page");

        FlightInfoQuery flightInfoQuery = null;
        BasePagination<FlightInfoQuery> queryCondition;
        List<Flight> flightList;
        PaginationResult<Flight> eqr;
        int count = flightMapper.getFlightListCountByOrderStatus(flightInfoQuery);
        queryCondition = new BasePagination<>(flightInfoQuery, new PageModel(flightInfoQuery.getPage(), flightInfoQuery.getRows()));
        flightList = flightMapper.getFlightListByOrderStatus(queryCondition);
        for(int i = 0; i < flightList.size(); i++){
            Map<String,Object> params = new HashMap<>();
            params.put("flightId",flightList.get(i).getFlightId());
            params.put("airportCode",flightInfoQuery.getAirportCode());
            Flight flight = flightMapper.selectByPrimaryKey(params);
            if(flight.getServerComplete() == 1){
                flightList.get(i).setScheduleTime(flight.getServerCompleteTime());
                flightList.get(i).setScheduleEventName("服务完成");
            }
        }
        eqr = new PaginationResult<>(count, flightList);
        return JSON.toJSONStringWithDateFormat(new LZResult<>(eqr), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 航班管理 - 根据flightId查询航班详情
     * @param request
     * @return
     */
    public String getFlightView(JSONObject request){
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        Long flightId = request.getLong("flightId");
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        return JSON.toJSONStringWithDateFormat(new LZResult<>( flightMapper.selectByPrimaryKey(param)), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 调度事件管理 - 根据scheduleEventId查询事件详情
     * @param request
     * @return
     */
    public String scheduleEventView(JSONObject request){
        Long scheduleEventId = request.getLong("scheduleEventId");
        String airportCode = request.getString("client_id");
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("scheduleEventId",scheduleEventId);
        ScheduleEvent scheduleEvent = scheduleEventMapper.selectByPrimaryKey(param);
        return JSON.toJSONString(new LZResult<>(scheduleEvent));
    }

    /**
     * 调度时间的更新或者保存
     * @param request
     * @return
     */
    public String scheduleEventSaveOrUpdate(JSONObject request){
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        ScheduleEvent scheduleEvent = request.getObject("data", ScheduleEvent.class);
        scheduleEvent.setAirportCode(airportCode);
        if (scheduleEvent.getScheduleEventId() != null) {
            scheduleEvent.setUpdateUser(userId);
            scheduleEventMapper.updateByPrimaryKeySelective(scheduleEvent);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } else if(scheduleEvent.getName() == null || scheduleEvent.getType() == null
                || scheduleEvent.getIsApproach() == null || scheduleEvent.getAirportCode() == null){
            return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
        }else {
            scheduleEvent.setCreateTime(new Date());
            scheduleEvent.setUpdateTime(new Date());
            scheduleEvent.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            scheduleEvent.setCreateUser(userId);
            scheduleEventMapper.insertSelective(scheduleEvent);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        }
    }

    /**
     * 调度事件集合
     * @param request
     * @return
     */
    public String scheduleEventList(JSONObject request){
        String airportCode = request.getString("client_id");
        Integer page = request.getInteger("page");
        Integer rows = request.getInteger("rows");
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        int count = scheduleEventMapper.getListCount(param);
        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ScheduleEvent> protocolList = scheduleEventMapper.getListByConidition(queryCondition);
        PaginationResult<ScheduleEvent> eqr = new PaginationResult<>(count, protocolList);
        return JSON.toJSONStringWithDateFormat(new LZResult<>(eqr), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 调度事件管理 - 根据flightId和scheduleEventId查询
     * @param request
     * @return
     */
    public String getScheduleEventByFlightId(JSONObject request){
        Long scheduleEventId = request.getLong("scheduleEventId");
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        Long flightId = request.getLong("flightId");
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        param.put("scheduleEventId",scheduleEventId);
        List<ScheduleEvent> scheduleEventList = scheduleEventMapper.selectByFlightId(param);
        Flight flight = flightMapper.selectByPrimaryKey(param);
        if(flight.getServerComplete() == 1){
            ScheduleEvent scheduleEvent = new ScheduleEvent();
            scheduleEvent.setScheduleTime(flight.getServerCompleteTime());
            scheduleEvent.setScheduleUpdateUserName(flight.getServerCompleteName());
            scheduleEvent.setName("服务完成");
            scheduleEventList.add(scheduleEvent);
        }
        return JSON.toJSONStringWithDateFormat(new LZResult<>(scheduleEventList), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 调度事件管理 - 获取调度事件下拉框
     * @param request
     * @return
     */
    public String getScheduleEventDropDownBox(JSONObject request){
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        Long flightId = request.getLong("flightId");
        Long isInOrOut = request.getLong("isInOrOut");
        String scheduleType = request.getString("scheduleType");
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        param.put("isInOrOut",isInOrOut);
        param.put("scheduleType",scheduleType);
        List<ScheduleEvent> scheduleEvent = scheduleEventMapper.getScheduleEventBox(param);
        return JSON.toJSONStringWithDateFormat(new LZResult<>(scheduleEvent), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 调度事件管理 - 删除
     * @param request
     * @return
     */
    public String scheduleEventDelete(JSONObject request){
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        List<Long> ids = request.getObject("data", List.class);
            for(int i = 0; i< ids.size();i++){
                Map<String, Object> params = new HashMap<>();
                params.put("airportCode", airportCode);
                params.put("scheduleEventId", ids.get(i));
                Long count = scheduleEventMapper.selectFlightByScheduleEventId(params);
                if (count < 0) {//count大于0，说明有航班已经引用该调度事件
                    JSON.toJSONString(LXResult.build(LZStatus.DATA_REF_ERROR));
                }
                ScheduleEvent scheduleEvent = new ScheduleEvent();
                scheduleEvent.setAirportCode(airportCode);
                scheduleEvent.setScheduleEventId(ids.get(i));
                scheduleEvent.setIsDeleted(Constant.MARK_AS_DELETED);
                scheduleEventMapper.updateByPrimaryKeySelective(scheduleEvent);
            }
            return JSON.toJSONString(LXResult.success());
    }

}
