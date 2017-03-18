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


import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.model.ServiceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/2/23.
 */
public interface PassengerMapper {
    /**
     * crm列表
     * @return
     */
    List<Passenger> queryPassengerList(@Param("passengerQuery") PassengerQuery passengerQuery, @Param("begin") int begin, @Param("rows") int rows);

    /**
     * 根据 手机号或身份证 查询用户使用次数
     */
    int queryBuyTimes(@Param("phone") Long phone, @Param("identityCard") String identityCard, @Param("airportCode") String airportCode);

    /**
     * 分页总条数
     * @param passengerQuery
     * @return
     */
    int getListCount(@Param("passengerQuery") PassengerQuery passengerQuery);

    /**
     * 根据id查询，为防止结果集出现重复id，返回list
     * @param passengerId
     * @return
     */
    List<Passenger> queryById(@Param("passengerId") Long passengerId, @Param("airportCode") String airportCode);

    /**
     * 获取手机或者身份证匹配的 服务信息
     * @param phone
     * @param identityCard
     * @param airportCode
     * @return
     */
    List<ServiceInfo> queryServiceInfoList(Map<String,Object> map);
}
