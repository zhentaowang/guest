package com.zhiweicloud.guest.thrift.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航班调度请求方法工具类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/9 20:33
 * @author tiecheng
 */
@Service
public class ScheduleEventUtils {

    private static final Log log = LogFactory.getLog(ScheduleEventUtils.class);

    @Autowired
    private ScheduleEventMapper scheduleEventMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightUtils flightUtils;

    /**
     * 调度管理 - 航班更新
     * 实际上和航班/订单中 航班更新 走的是同一个方法(重复方法)，因为页面调用不同的方法，固暂改后端
     * @param request
     * @return
     */
    public String flightUpdate(JSONObject request) {
        /*
        get request params
         */
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        JSONArray param = request.getJSONArray("data");
        if (log.isDebugEnabled()) {
            log.debug("Head Params airportCode: " + airportCode + "userId: " + userId);
        }
        /*
        do update operator
         */
        try {
            for (int i = 0; i < param.size(); i++) {
                Flight flight = param.getJSONObject(i).toJavaObject(Flight.class);
                flight.setAirportCode(airportCode);
                flight.setUpdateUser(userId);
                Long flightId = flight.getFlightId();
                flightUtils.updateFlight(flightUtils.queryFlightById(flightId,airportCode),flight);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 调度管理 - 航班信息
     * @param request
     * @return
     * @test
     */
    public String getFlightList(JSONObject request){
        /*
        get request params
         */
        String airportCode = request.getString("client_id");
        FlightInfoQuery flightInfoQuery = request.toJavaObject(FlightInfoQuery.class);
        flightInfoQuery.setAirportCode(airportCode);
        /*
        get flight list by page
         */
        BasePagination<FlightInfoQuery> queryCondition;
        List<Flight> flightList;
        PaginationResult<Flight> eqr;
        int count = flightMapper.getFlightListCountByOrderStatus(flightInfoQuery);
        System.out.println(count);
        queryCondition = new BasePagination<>(flightInfoQuery, new PageModel(flightInfoQuery.getPage(), flightInfoQuery.getRows()));
        flightList = flightMapper.getFlightListByOrderStatus(queryCondition);
        /*
        set flight scheduleEventName when serverComplete is 1
         */
        for(int i = 0; i < flightList.size(); i++){
            Flight flight = flightUtils.queryFlightById(flightList.get(i).getFlightId(), flightInfoQuery.getAirportCode());
            if(flight.getServerComplete() == 1){
                flightList.get(i).setScheduleTime(flight.getServerCompleteTime());
                flightList.get(i).setScheduleEventName("服务完成");
            }
        }
        eqr = new PaginationResult<>(count, flightList);
        return JSON.toJSONStringWithDateFormat(new LZResult<>(eqr), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     *
     * 航班管理 - 根据flightId查询航班详情
     * @position 订单管理 - 调度管理 - 详细信息 - 航班信息
     * @param request
     * @return
     * @test
     */
    public String getFlightView(JSONObject request){
        /*
        get request params
         */
        String airportCode = request.getString("client_id");
        Long flightId = request.getLong("flightId");
        return JSON.toJSONStringWithDateFormat(new LZResult<>( flightUtils.queryFlightById(flightId,airportCode)), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 调度事件管理 - 根据scheduleEventId查询事件详情
     * @param request
     * @return
     * @test
     */
    public String scheduleEventView(JSONObject request){
        /*
        get request params
         */
        Long scheduleEventId = request.getLong("scheduleEventId");
        String airportCode = request.getString("client_id");
        return JSON.toJSONString(new LZResult<>(queryScheduleEventById(scheduleEventId,airportCode)));
    }

    /**
     * 调度事件的更新或者保存
     * @param request
     * @return
     * @test
     */
    public String scheduleEventSaveOrUpdate(JSONObject request){
        /*
        get request params
         */
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        JSONArray data = request.getJSONArray("data");
        if (data ==null) {
            System.out.println("data is null");
        }
        ScheduleEvent scheduleEvent = data.getJSONObject(0).toJavaObject(ScheduleEvent.class);
        scheduleEvent.setAirportCode(airportCode);
        /*
        method operator
         */
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
     * @test
     */
    public String scheduleEventList(JSONObject request){
        String airportCode = request.getString("client_id");
        Integer page = request.getInteger("page");
        page = page == null ? 1 : page;
        Integer rows = request.getInteger("rows");
        rows = rows == null ? 10 : rows;
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
     * @test
     */
    public String getScheduleEventByFlightId(JSONObject request){
        Long scheduleEventId = request.getLong("scheduleEventId");
        String airportCode = request.getString("client_id");
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
     * @test
     */
    public String getScheduleEventDropDownBox(JSONObject request){
        String airportCode = request.getString("client_id");
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
     * @test
     */
    public String scheduleEventDelete(JSONObject request){
        String airportCode = request.getString("client_id");
        JSONArray data = request.getJSONArray("data");
        List<Long> ids = JSON.parseArray(data.toJSONString(), Long.class);
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

    /**
     * 根据调度时间ID查询调度事件对象
     * @param scheduleEventId
     * @param airportCode
     * @return
     */
    public ScheduleEvent queryScheduleEventById(Long scheduleEventId,String airportCode){
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("scheduleEventId",scheduleEventId);
        return scheduleEventMapper.selectByPrimaryKey(param);
    }

}
