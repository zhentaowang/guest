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

package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightMatch;
import com.zhiweicloud.guest.model.ScheduleEvent;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * @author wzt
 * @since 2017-03-03 21:17
 */
public interface FlightMapper {
    //订单状态值为null时使用
    List<Flight> getFlightListByConidition(BasePagination<Map<String, Object>> queryCondition);
    Integer getFlightListCount(Map<String, Object> map);
    //订单状态值不为null时使用
    List<Flight> getFlightListByOrderStatus(BasePagination<Map<String, Object>> queryCondition);
    Integer getFlightListCountByOrderStatus(Map<String, Object> map);
    Flight selectByPrimaryKey(Map<String, Object> map);
    Integer updateByPrimaryKeySelective(Flight flight);

    Long isFlightExist(Flight flight);

    Long updateFlight(Flight flight);
}
