/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.ScheduleEventMapper;
import com.zhiweicloud.guest.model.Flight;
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
 * 2017-03-03 20:47:22 Created By wzt
 */
@Service
public class ScheduleEventService {
    @Autowired
    private ScheduleEventMapper scheduleEventMapper;

    @Autowired
    private FlightMapper flightMapper;

    /**
     * 分页获取调度事件列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<ScheduleEvent>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = scheduleEventMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ScheduleEvent> protocolList = scheduleEventMapper.getListByConidition(queryCondition);
        PaginationResult<ScheduleEvent> eqr = new PaginationResult<>(count, protocolList);
        return new LZResult<>(eqr);
    }

    /**
     * 分页条件获取航班列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<Flight>> getFlightList(Map<String,Object> param, Integer page, Integer rows) {

        int count;
        BasePagination<Map<String,Object>> queryCondition;
        List<Flight> flightList;
        PaginationResult<Flight> eqr;
        count = flightMapper.getFlightListCountByOrderStatus(param);
        queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        flightList = flightMapper.getFlightListByOrderStatus(queryCondition);
        for(int i = 0; i < flightList.size(); i++){
            Map<String,Object> params = new HashMap<>();
            params.put("flightId",flightList.get(i).getFlightId());
            params.put("airportCode",param.get("airportCode"));
            Date serverCompleteTime = flightMapper.selectByPrimaryKey(params).getServerCompleteTime();
            if(serverCompleteTime != null){
                flightList.get(i).setScheduleTime(serverCompleteTime);
                flightList.get(i).setScheduleEventName("服务完成");
            }
        }
        eqr = new PaginationResult<>(count, flightList);
        return new LZResult<>(eqr);
    }

    /**
     * 获取调度事件详情
     * @param param
     */
    public ScheduleEvent getByScheduleEventId(Map<String,Object> param) {
        return scheduleEventMapper.selectByPrimaryKey(param);
    }

    /**
     * 获取航班详情
     * @param param
     */
    public Flight getByFlightId(Map<String,Object> param) {
        return flightMapper.selectByPrimaryKey(param);
    }

    /**
     * 获取调度时间和类型
     * @param param
     */
    public List<ScheduleEvent> getScheduleEventByFlightId(Map<String,Object> param) {
        List<ScheduleEvent> scheduleEventList = scheduleEventMapper.selectByFlightId(param);
        Flight flight = flightMapper.selectByPrimaryKey(param);
        if(flight.getServerCompleteTime() != null){
            ScheduleEvent scheduleEvent = new ScheduleEvent();
            scheduleEvent.setScheduleTime(flight.getServerCompleteTime());
            scheduleEvent.setScheduleUpdateUserName(flight.getServerCompleteName());
//            scheduleEvent.setScheduleUpdateUserName("超级管理员");
            scheduleEvent.setName("服务完成");
            scheduleEventList.add(scheduleEvent);
        }
        return scheduleEventList;
    }

    /**
     * 获取调度事件下拉框
     * @param param
     */
    public  List<ScheduleEvent> getScheduleEventBox(Map<String,Object> param) {
        return scheduleEventMapper.getScheduleEventBox(param);
    }

    /**
     * 调度事件添加与修改
     * @param scheduleEvent
     */
    public void saveOrUpdate(ScheduleEvent scheduleEvent) {
        if (scheduleEvent.getScheduleEventId() != null) {
            scheduleEventMapper.updateByPrimaryKeySelective(scheduleEvent);
        } else {
            scheduleEvent.setCreateTime(new Date());
            scheduleEvent.setUpdateTime(new Date());
            scheduleEvent.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            scheduleEventMapper.insertSelective(scheduleEvent);
        }
    }

    /**
     * 修改航班
     * @param flight
     */
    public void flightUpdate(Flight flight) {
        flightMapper.updateByPrimaryKeySelective(flight);
    }

    /**
     * 删除调度事件
     * @param airportCode
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            ScheduleEvent scheduleEvent = new ScheduleEvent();
            scheduleEvent.setAirportCode(airportCode);
            scheduleEvent.setScheduleEventId(ids.get(i));
            scheduleEvent.setIsDeleted(Constant.MARK_AS_DELETED);
            scheduleEventMapper.updateByPrimaryKeySelective(scheduleEvent);
        }
    }

    /**
     * 删除调度事件时判断是否有航班已经引用
     * @param scheduleEventId
     * @return boolean
     */
    public boolean selectFlightByScheduleEventId(Long scheduleEventId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("scheduleEventId",scheduleEventId);
        Long count = scheduleEventMapper.selectFlightByScheduleEventId(params);
        if(count > 0){//count大于0，说明有航班已经引用该调度事件
            return true;
        }
        else{
            return false;
        }
    }
}
