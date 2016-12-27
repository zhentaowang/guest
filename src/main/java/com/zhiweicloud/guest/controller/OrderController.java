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

import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.GuestOrder;
import com.zhiweicloud.guest.service.OrderService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@RestController
@RequestMapping("/order")
@Api(value="订单",description="订单desc ", tags={"order"})
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value ="/list")
    @ApiOperation(value = "订单 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = false, paramType = "query")})
    public LZResult<PaginationResult<GuestOrder>> list(
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "name", required = false) String name) {
        List<GuestOrder> list = new ArrayList<GuestOrder>();

        PaginationResult<GuestOrder> eqr = new PaginationResult<GuestOrder>(1, list);
        LZResult<PaginationResult<GuestOrder>> result = new LZResult<PaginationResult<GuestOrder>>(eqr);
        return result;
    }

    /**
     * 员工管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/saveOrUpdate", method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="订单 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "employee", required = true) @RequestBody RequsetParams<GuestOrder> params){
        try {
            //保存订单
            GuestOrder order = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                order = params.getData().get(0);
            }

            if (order == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            orderService.saveOrUpdate(order);
            LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
            //保存乘客

            //保存车辆

            LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
        return null;
    }


    /**
     * 订单管理 - 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单 - 根据id查询 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工id", dataType = "Long", required = true, paramType = "query")
    })
    public LZResult<GuestOrder> view(@RequestParam(value = "id", required = true) Long id) {
        return new LZResult<GuestOrder>(new GuestOrder());
    }

    /**
     * 订单管理 - 删除
     * {
        "data": [
        6,7,8
        ]
    }
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public LXResult delete(@RequestBody RequsetParams<Long> params) {
        try {
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return LXResult.error();
        }
    }



}
