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
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.OrderService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@RestController
@Api(value = "订单", description = "订单desc ", tags = {"order"})
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/list")
    @ApiOperation(value = "订单 - 分页查询", notes = "返回分页结果", httpMethod = "POST", produces = "application/json")
    public LZResult<PaginationResult<GuestOrder>> list(@RequestBody RequsetParams<GuestOrderQueryParam> param) {
        try {
            GuestOrderQueryParam guestOrderQueryParam = null;
            if (!CollectionUtils.isEmpty(param.getData())) {
                guestOrderQueryParam = param.getData().get(0);
            }
            LZResult<PaginationResult<GuestOrder>> result = orderService.getAll(guestOrderQueryParam);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }


    /**
     * 订单管理 - 新增or更新
     * 需要判断name是否重复
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult save(@ApiParam(value = "GuestOrder", required = true) @RequestBody RequsetParams<GuestOrder> params) {
        try {
            GuestOrder order = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                order = params.getData().get(0);
            }
            if (order == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            orderService.saveOrUpdate(order);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 订单管理 - 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单 - 根据id查询 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<GuestOrder> view(@RequestParam(value = "id", required = true) Long id,
                                     @RequestParam(value = "airportCode", required = true) String airportCode) {
        GuestOrder guestOrder = orderService.getById(id, airportCode);
        return new LZResult<>(guestOrder);
    }

    /**
     * 订单管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public LXResult delete(@RequestBody RequsetParams<Long> params, @RequestParam(value = "airportCode", required = true) String airportCode) {
        try {
            List<Long> ids = params.getData();
            orderService.deleteById(ids, airportCode);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return LXResult.error();
        }
    }

    /**
     * 订单管理 - 根据协议查询客户名称，预约人，备注信息
     *
     * @param protocolId
     * @param protocolNo
     * @return
     */
    @RequestMapping(value = "/protocolInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单 - 根据协议查询客户名称，预约人，备注信息 ", notes = "返回协议相关信息", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "protocolNo", value = "协议编号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编码", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<ProtocolInfo> protocolInfo(
            @RequestParam(value = "protocolId", required = false) Long protocolId,
            @RequestParam(value = "protocolNo", required = false) String protocolNo,
            @RequestParam(value = "airportCode", required = true) String airportCode) {
        ProtocolInfo param = new ProtocolInfo();
        Dropdownlist d = new Dropdownlist();
        d.setId(protocolId);
        d.setValue(protocolNo);
        param.setProtocol(d);
        param.setAirportCode(airportCode);
        return new LZResult<>(orderService.getProtocolInfoBy(param));
    }

    /**
     * 服务下拉框 数据
     * VIP
     * 冠名
     * CIP
     * 休息室
     * 通道
     * 头等舱
     * 金银卡
     *
     * @return
     */
    @RequestMapping(value = "/getServerList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单 - 根据协议查询服务,休息室，通道 ", notes = "返回协议相关信息", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCategory", value = "CIP/VIP", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "serviceType", value = "冠名厅，休息室，通道", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编码", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServerList(
            @RequestParam(value = "productCategory", required = true) String productCategory,
            @RequestParam(value = "serviceType", required = true) String serviceType,
            @RequestParam(value = "protocolId", required = true) String protocolId,
            @RequestParam(value = "airportCode", required = true) String airportCode) {
        List<Dropdownlist> list = orderService.getServerList(productCategory, serviceType, protocolId,airportCode);
        return new LZResult<>(list);
    }

    @RequestMapping(value ="/flightList")
    @ApiOperation(value = "航班信息列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "flightDate", value = "航班日期", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "takeOffTimeFlag", value = "起飞时段", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "landingTimeFlag", value = "降落时段", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "flightNo", value = "航班号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "isInOrOut", value = "进出港。 出港：0，进港1", dataType = "String", required = false, paramType = "query"),

            })
    public LZResult<PaginationResult<Flight>> flightList(
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value="airportCode",required = true) String airportCode,
            @RequestParam(value="flightDate",required = true) String flightDate,
            @RequestParam(value="takeOffTimeFlag",required = false) String takeOffTimeFlag,
            @RequestParam(value="landingTimeFlag",required = false) String landingTimeFlag,
            @RequestParam(value="flightNo",required = false) String flightNo,
            @RequestParam(value="isInOrOut",required = false) String isInOrOut) {
        try {
            Flight flight = new Flight();
            flight.setAirportCode(airportCode);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            flight.setFlightDate(sdf.parse(flightDate));


            if(flightNo != null && !flightNo.equals("")){
                flight.setFlightNo(flightNo);
            }

            if(isInOrOut != null && !isInOrOut.equals("")){
                flight.setIsNearOrFar(Short.valueOf(isInOrOut));
            }

            if(takeOffTimeFlag != null && !takeOffTimeFlag.equals("")){
                flight.setTakeOffTimeFlag(Integer.valueOf(takeOffTimeFlag));
            }

            if(landingTimeFlag != null && !landingTimeFlag.equals("")){
                flight.setLandingTimeFlag(Integer.valueOf(landingTimeFlag));
            }


            LZResult<PaginationResult<Flight>> result  = orderService.getFlightList(flight,page,rows);
            return result;
        }catch(Exception e) {
            return null;
        }
    }




}
