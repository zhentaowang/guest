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


import com.zhiweicloud.guest.common.FlightException;
import com.zhiweicloud.guest.mapper.AirportInfoMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.FlightScheduleEventMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightScheduleEvent;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2017-02-04 11:09
 */
@Service
public class FlightService {

    private static final Log log = LogFactory.getLog(FlightService.class);

    @Autowired
    private AirportInfoMapper airportInfoMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightScheduleEventMapper flightScheduleEventMapper;

    public List<Map<String, String>> flightInfoDropdownList(String airportNameOrCode) {
        return airportInfoMapper.queryFlightInfoDropdownList(airportNameOrCode);
    }

    public List<Map<String, String>> flightNoDropdownList(String flightNo, String airportCode) {
        return airportInfoMapper.queryFlightNoDropdownList(flightNo, airportCode);
    }

    public void updateFlight(Flight flight) throws Exception {
        Flight queryFlight = flightMapper.isFlightExist(flight);
        log.info("查询出来的flight: " + queryFlight.toString());
        if (queryFlight == null || queryFlight.getFlightId() == 0) {
            throw new FlightException("没有找到对应的航班信息");
        } else {
            Long flightId = queryFlight.getFlightId();
            String fdId = queryFlight.getFdId();
            fdId = fdId == null ? "-1" : fdId;
            log.info("当前的fdId:" + fdId);
            log.info("推送的fdId:" + flight.getFdId());
            if (Long.valueOf(fdId) <= Long.valueOf(flight.getFdId())) {
                flight.setFlightId(flightId);
                log.info("更新航班");
                flightMapper.updateFlight(flight);
                log.info("更新结束");
            } else {
                throw new FlightException("无需更新");
            }
        }
    }

    public void saveOrUpdateFlightScheduleEvent(FlightScheduleEvent flightScheduleEvent, Long userId) throws Exception {

        if (flightScheduleEvent.getFlightScheduleEventId() == null) {
            flightScheduleEvent.setCreateUser(userId);
            flightScheduleEventMapper.insertSelective(flightScheduleEvent);
        } else {
            flightScheduleEvent.setUpdateUser(userId);
            flightScheduleEventMapper.updateByPrimaryKeySelective(flightScheduleEvent);
        }

        Map<String, Object> param = new HashedMap();
        param.put("airportCode", flightScheduleEvent.getAirportCode());
        param.put("userId", userId);
        param.put("flightScheduleEventId", flightScheduleEvent.getFlightScheduleEventId());
        FlightScheduleEvent flightScheduleEvent0 = flightScheduleEventMapper.selectByPrimaryKey(param);
        flightScheduleEvent0.setAirportCode(flightScheduleEvent.getAirportCode());
        flightScheduleEventMapper.updateByPrimaryKeySelective(flightScheduleEvent0);
    }

    public Flight queryFlightById(Long id, String airportCode) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("flightId", id);
        params.put("airportCode", airportCode);
        return flightMapper.selectByPrimaryKey(params);
    }

}
