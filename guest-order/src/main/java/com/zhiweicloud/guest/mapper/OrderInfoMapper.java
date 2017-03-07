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


import com.zhiweicloud.guest.model.OrderInfo;
import com.zhiweicloud.guest.model.OrderInfoQuery;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by zhangpengfei on 2016/12/26.
 */
public interface OrderInfoMapper{


    void markChildRowsAsDeleted(
            @Param("orderId") Long orderId,
            @Param("ids") String ids,
            @Param("airportCode") String airportCode,
            @Param("idColumn") String idColumn,
            @Param("tableName")String tableName);

    void insertSelective(OrderInfo orderInfo);

    void updateByPrimaryKeySelective(OrderInfo orderInfo);

    int selectOrderInfoTotal(BasePagination<OrderInfoQuery> orderInfoQuery);

    List<OrderInfo> selectOrderInfoList(BasePagination<OrderInfoQuery> queryCondition);

    OrderInfo getDetailById(@Param("orderId") Long orderId, @Param("airportCode") String airportCode);

    void insertIntoOrderStatusRecord(OrderInfo orderInfo);
}
