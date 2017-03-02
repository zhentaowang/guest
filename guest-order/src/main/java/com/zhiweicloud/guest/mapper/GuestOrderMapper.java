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


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.GuestOrder;
import com.zhiweicloud.guest.model.ProtocolInfo;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangpengfei on 2016/12/26.
 */
public interface GuestOrderMapper{
    /**
     * 获取协议备注，协议客户名称，协议编号等信息
     *
     * @param protocolInfo 协议id
     * @param protocolInfo 协议编号
     * @return
     */
    public ProtocolInfo getProtocolInfo(ProtocolInfo protocolInfo);

    /**
     * 获取协议备注，协议客户名称，协议编号等信息
     *
     * @param protocolInfo 协议id
     * @param protocolInfo 协议编号
     * @return
     */
    public List<Dropdownlist> getProtocolPersonInfo(ProtocolInfo protocolInfo);

    List<Dropdownlist> getServerListInfo(Map<String, Object> map);

    List<GuestOrder> selectByComplexQuery(BasePagination<GuestOrder> queryCondition);

    /**
     * 根据主键id和机场编码获取单条记录详情
     * @param map
     * @return
     */
    GuestOrder selectByIdAndAirCode(Map<String, Object> map);

    /**
     * 根据条件删除当前订单下的乘客，车辆，收费服务
     * @param map
     */
    void markChildRowsAsDeleted(Map<String,Object> map);


    int selectByComplexQueryCount(BasePagination<GuestOrder> queryCondition);
}
