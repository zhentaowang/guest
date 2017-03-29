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


import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.OrderInfoQuery;
import com.zhiweicloud.guest.model.Passenger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangpengfei on 2016/12/26.
 */
public interface PassengerMapper{

    /**
     * 订单列表，身份证号模糊匹配下拉框
     * 返回不重复的身份证号码
     * @return
     */
    List<Dropdownlist> getIdentityCardDropdownList(@Param("identityCard") String identityCard, @Param("airportCode") String airportCode);

    void updateByPassengerIdAndAirportCodeKeySelective(Passenger p);

    void insertSelective(Passenger p);

    void markAsDeleted(Passenger passenger);
    /**
     * 返回航班信息中的 旅客信息，与订单状态等信息结合
     * @param flightId
     * @param typeId
     * @param servId
     * @param airportCode
     * @return
     */
    List<Passenger> getPassengerlistByFlightId(@Param("flightId")Long flightId, @Param("typeId") Long typeId,
                                               @Param("servId") Long servId, @Param("airportCode") String airportCode);

}
