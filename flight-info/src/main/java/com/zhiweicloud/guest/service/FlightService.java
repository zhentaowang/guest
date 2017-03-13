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
import com.zhiweicloud.guest.model.Flight;
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

    @Autowired
    private AirportInfoMapper airportInfoMapper;

    @Autowired
    private FlightMapper flightMapper;

    public List<Map<String,String>> flightInfoDropdownList(String airportNameOrCode) {
        return airportInfoMapper.queryFlightInfoDropdownList(airportNameOrCode);
    }

    public List<Map<String,String>> flightNoDropdownList(String flightNo,String airportCode) {
        return airportInfoMapper.queryFlightNoDropdownList(flightNo,airportCode);
    }

    public Long updateFlight(Flight flight)throws Exception{
        Long flightId = flightMapper.isFlightExist(flight);
        if (flightId == null || flightId.equals("") || flightId == 0){
            throw new FlightException("没有找到对应的航班信息");
        }else{
            flight.setFlightId(flightId);
            flightMapper.updateFlight(flight);
        }
        return flight.getFlightId();
    }

}
