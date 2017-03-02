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

package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.OrderInfoService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value = "订单", description = "订单desc ", tags = {"订单管理"})
public class OrderInfoController {
    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private OrderInfoService orderInfoService;


    /**
     * 订单管理 - 新增or更新
     * 需要判断name是否重复
     *
     * @param params
     * @return
     */
    @POST
    @Path(value = "saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "订单 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String save(@ApiParam(value = "OrderInfo", required = true) @RequestBody RequsetParams<OrderInfo> params) {
        LZResult<String> result = new LZResult<>();
        try {
            OrderInfo order = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                order = params.getData().get(0);
            }
            if (order == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }
            orderInfoService.saveOrUpdate(order);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }





}
