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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.OrderConstant;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.OrderInfoService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

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


    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功",response = OrderInfo.class)
    })
    public String list(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @BeanParam final OrderInfoQuery orderInfoQuery,
            @Context final HttpHeaders headers) {
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            orderInfoQuery.setAirportCode(airportCode);
            LZResult<PaginationResult<OrderInfo>> result = orderInfoService.getOrderInfoList(page, rows,orderInfoQuery,userId);
            return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
        }catch (Exception e){
            e.printStackTrace();
            LZResult result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return  JSON.toJSONString(result);
        }
    }

    /**
     * 订单管理 - 新增or更新
     * 需要判断name是否重复
     *
    // * @param orderInfo
     * @return
     */
    @POST
    @Path(value = "saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "订单 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String saveOrUpdate(@ApiParam(value = "OrderInfo", required = true) String orderInfo,@Context final HttpHeaders headers) {
        LZResult<String> result = new LZResult<>();
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));

            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            JSONObject param = JSON.parseObject(orderInfo);
            JSONObject orderObject = param.getJSONArray("data").getJSONObject(0);
            JSONArray serviceListArray = orderObject.getJSONArray("serviceList");
            JSONArray passengerListArray = orderObject.getJSONArray("passengerList");
            orderObject.remove("serviceList");
            orderObject.remove("passengerList");

            List<OrderService> orderServiceList = JSON.parseArray(JSON.toJSONString(serviceListArray), OrderService.class);
            List<Passenger> passengerList = JSON.parseArray(JSON.toJSONString(passengerListArray), Passenger.class);

            OrderInfo order = JSON.toJavaObject(param.getJSONArray("data").getJSONObject(0), OrderInfo.class);

            if (order == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{
                orderInfoService.saveOrUpdate(order,passengerList,orderServiceList,userId,airportCode);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(e.toString());
        }
        return JSON.toJSONString(result);
    }


    /**
     * 员工管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     *
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @Context final HttpHeaders headers,
            @RequestBody RequsetParams<Long> params) {
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            List<Long> ids = params.getData();
            orderInfoService.deleteById(ids, userId,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }



    /**
     * 员工管理 - 根据id查询员工
     * @param orderId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 根据id查询订单 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功",response = OrderInfo.class)
    })
    public String view(
            @QueryParam("orderId") Long orderId,
            @Context final HttpHeaders headers) {
        LZResult<OrderInfo> result = new LZResult();
        try {
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            OrderInfo orderInfo = orderInfoService.getById(orderId,userId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderInfo);
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }


    /**
     * 订单管理 - 修改订单服务状态
     * @return
     */
    @POST
    @Path("updateServerComplete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "修改订单服务状态", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String updateServerComplete(
            @Context final HttpHeaders headers,
            @QueryParam("flightId") Long flightId,
            @QueryParam("serverComplete") Short serverComplete) {
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");

            orderInfoService.updateServerComplete(flightId,serverComplete, userId,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("updateServerComplete error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 根据订单状态/详细服务id 获取服务人次
     * @return
     */
    @GET
    @Path("getServerNumByServiceDetailId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据订单状态/详细服务id 获取服务人次 ", notes = "返回服务人次", httpMethod = "GET", produces = "application/json")
    public String getServerNumByServiceDetailId(
            @QueryParam("serviceDetailId") Long serviceDetailId,
            @Context final HttpHeaders headers) {
        LZResult<Integer> result = new LZResult();
        try {
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            Integer orderCount = orderInfoService.getServerNumByServiceDetailId(OrderConstant.ORDER_STATUS_USED,serviceDetailId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }

    @GET
    @Path("getOrderCountByProtocolId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 判断协议是否被订单引用 ", notes = "返回被引用数量", httpMethod = "GET", produces = "application/json")
    public String getOrderCountByProtocolId(
            @QueryParam("protocolId") Long protocolId,
            @Context final HttpHeaders headers) {
        LZResult<Integer> result = new LZResult();
        try {
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            int orderCount = orderInfoService.getOrderCountByProtocolId(protocolId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }


}
