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
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.OrderConstant;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.common.ThriftClientUtils;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.CopyProperties;
import com.zhiweicloud.guest.service.ListUtil;
import com.zhiweicloud.guest.service.OrderInfoService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @author zhangpengfei for thrift
     * @param request
     * @return
     */
    public String list(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        try {
            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");

            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
            }

            Long userId = Long.valueOf(request.getString("user_id"));
            String airportCode = request.getString("client_id");

            String createRole = request.getString("role-ids");


            System.out.println("[userId:" + userId + "---createRole:" + createRole + "---airportCode:" + airportCode + "]");
            //判断有无数据权限，没有直接返回
            if(StringUtils.isEmpty(createRole)){
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
            logger.debug("test - log - position");


            OrderInfoQuery orderInfoQuery = JSON.toJavaObject(request, OrderInfoQuery.class);

            //orderType传过来是-1 的话，查询全部
            if(orderInfoQuery != null && orderInfoQuery.getQueryOrderType() == -1){
                orderInfoQuery.setQueryOrderType(null);
            }

            orderInfoQuery.setAirportCode(airportCode);
            //数据角色权限
            orderInfoQuery.setQueryCreateRole(createRole);
            LZResult<PaginationResult<OrderInfo>> res = orderInfoService.getOrderInfoList(page, rows, orderInfoQuery, userId);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(res);
            return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = OrderInfo.class)
    })
    public String list(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @BeanParam final OrderInfoQuery orderInfoQuery,
            @HeaderParam("user-id") Long userId,
            @HeaderParam("role-ids") String createRole,
            @HeaderParam("client-id") String airportCode
            ) {
        try {
            System.out.println("[userId:" + userId + "---createRole:" + createRole + "---airportCode:" + airportCode + "]");
            //判断有无数据权限，没有直接返回
            if(StringUtils.isEmpty(createRole)){
                LZResult result = new LZResult<>();
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
            logger.debug("test - log - position");

            //orderType传过来是-1 的话，查询全部
            if(orderInfoQuery != null && orderInfoQuery.getQueryOrderType() == -1){
                orderInfoQuery.setQueryOrderType(null);
            }

            orderInfoQuery.setAirportCode(airportCode);
            //数据角色权限
            orderInfoQuery.setQueryCreateRole(createRole);
            LZResult<PaginationResult<OrderInfo>> result = orderInfoService.getOrderInfoList(page, rows, orderInfoQuery, userId);
            return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }


    /**
     * 订单管理 - 新增or更新
     * 需要判断name是否重复
     * <p>
     * // * @param orderInfo
     * @author zhangpengfei for thrift
     * @return
     */
    public String saveOrUpdate(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        //远程调用参数

        Long userId = request.getLong("user_id");
        String airportCode = request.getString("client_id");


        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("client_id", airportCode);
        params.put("employeeId", userId);

        try {
            JSONObject orderObject = request.getJSONArray("data").getJSONObject(0);
            JSONArray serviceListArray = orderObject.getJSONArray("serviceList");
            JSONArray passengerListArray = orderObject.getJSONArray("passengerList");
            JSONArray flightListArray = orderObject.getJSONArray("flightList");
            orderObject.remove("serviceList");
            orderObject.remove("passengerList");
            orderObject.remove("flightList");

            List<OrderService> orderServiceList = JSON.parseArray(JSON.toJSONString(serviceListArray), OrderService.class);
            List<Passenger> passengerList = JSON.parseArray(JSON.toJSONString(passengerListArray), Passenger.class);

            List<Flight> flightList = JSON.parseArray(JSON.toJSONString(flightListArray), Flight.class);

            Flight targetFlight = null;//全部参数

            OrderInfo order = JSON.toJavaObject(orderObject, OrderInfo.class);
            OrderInfo oldOrder = null;
            if (order.getOrderId() != null) {
                oldOrder = orderInfoService.getById(order.getOrderId(), userId, airportCode);
            }


            if (flightList != null && flightList.get(0) != null && flightList.get(1) != null) {
                Flight source = flightList.get(0);//修改后的部分参数
                targetFlight = flightList.get(1);//全部参数
                CopyProperties.copy(source, targetFlight);
            } else {
                targetFlight = oldOrder.getFlight();
            }


            if (order == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
                return JSON.toJSONString(result);
            } else {
                if (targetFlight.getFlightArrcode() == null || targetFlight.getFlightDepcode() == null || targetFlight.getFlightArrcode().equals("") || targetFlight.getFlightDepcode().equals("")) {
                    result.setMsg("出发地三字码或者目的地三字码为空");
                    result.setStatus(5006);
                    result.setData(null);
                    return JSON.toJSONString(result);
                } else {
                    if (order.getOrderId() != null) {
                        boolean flag = canUpdateOrderStatus(oldOrder.getOrderStatus(), order.getOrderStatus());
                        if (!flag) {
                            result.setMsg(LZStatus.ORDER_STATUS_FLOW_ERROR.display());
                            result.setStatus(LZStatus.ORDER_STATUS_FLOW_ERROR.value());
                            result.setData("错误的状态更新");
                            return JSON.toJSONString(result);
                        }
                    }
                    order.setFlight(targetFlight);

                    //查询当前用户角色
                    //JSONObject userRoleObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/getRoleByUserId", headerMap, paramMap));
                    JSONObject userRoleObject = JSON.parseObject(ThriftClientUtils.invokeRemoteMethodCallBack(params,"guest-employee"));
                    if (userRoleObject != null) {
                        JSONArray jsonArray = userRoleObject.getJSONArray("data");
                        List<Map<Object,Object>> list = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<List<Map<Object,Object>>>(){});
                        order.setCreateRole(ListUtil.Map2String(list));
                    }
                    Long orderId = orderInfoService.saveOrUpdate(order, passengerList, orderServiceList, userId, airportCode);
                    result.setMsg(LZStatus.SUCCESS.display());
                    result.setStatus(LZStatus.SUCCESS.value());
                    result.setData(orderId);
                    return JSON.toJSONString(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(e.toString());
            return JSON.toJSONString(result);
        }

    }


    /**
     * 订单管理 - 新增or更新
     * 需要判断name是否重复
     * <p>
     * // * @param orderInfo
     *
     * @return
     */
    @POST
    @Path(value = "saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "订单 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String saveOrUpdate(@ApiParam(value = "OrderInfo", required = true) String orderInfo,  @HeaderParam("client-id") String airportCode,
                               @HeaderParam("user-id") Long userId) {
        LZResult<Object> result = new LZResult<>();
        //远程调用参数
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", airportCode);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("employeeId", userId);

        try {
            JSONObject param = JSON.parseObject(orderInfo);
            JSONObject orderObject = param.getJSONArray("data").getJSONObject(0);
            JSONArray serviceListArray = orderObject.getJSONArray("serviceList");
            JSONArray passengerListArray = orderObject.getJSONArray("passengerList");
            JSONArray flightListArray = orderObject.getJSONArray("flightList");
            orderObject.remove("serviceList");
            orderObject.remove("passengerList");
            orderObject.remove("flightList");

            List<OrderService> orderServiceList = JSON.parseArray(JSON.toJSONString(serviceListArray), OrderService.class);
            List<Passenger> passengerList = JSON.parseArray(JSON.toJSONString(passengerListArray), Passenger.class);

            List<Flight> flightList = JSON.parseArray(JSON.toJSONString(flightListArray), Flight.class);

            Flight targetFlight = null;//全部参数

            OrderInfo order = JSON.toJavaObject(param.getJSONArray("data").getJSONObject(0), OrderInfo.class);
            OrderInfo oldOrder = null;
            if (order.getOrderId() != null) {
                oldOrder = orderInfoService.getById(order.getOrderId(), userId, airportCode);
            }


            if (flightList != null && flightList.get(0) != null && flightList.get(1) != null) {
                Flight source = flightList.get(0);//修改后的部分参数
                targetFlight = flightList.get(1);//全部参数
                CopyProperties.copy(source, targetFlight);
            } else {
                targetFlight = oldOrder.getFlight();
            }


            if (order == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
                return JSON.toJSONString(result);
            } else {
                if (targetFlight.getFlightArrcode() == null || targetFlight.getFlightDepcode() == null || targetFlight.getFlightArrcode().equals("") || targetFlight.getFlightDepcode().equals("")) {
                    result.setMsg("出发地三字码或者目的地三字码为空");
                    result.setStatus(5006);
                    result.setData(null);
                    return JSON.toJSONString(result);
                } else {
                    if (order.getOrderId() != null) {
                        boolean flag = canUpdateOrderStatus(oldOrder.getOrderStatus(), order.getOrderStatus());
                        if (!flag) {
                            result.setMsg(LZStatus.ORDER_STATUS_FLOW_ERROR.display());
                            result.setStatus(LZStatus.ORDER_STATUS_FLOW_ERROR.value());
                            result.setData("错误的状态更新");
                            return JSON.toJSONString(result);
                        }
                    }
                    order.setFlight(targetFlight);

                    //查询当前用户角色
                   // JSONObject userRoleObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/getRoleByUserId", headerMap, paramMap));
                    JSONObject userRoleObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/getRoleByUserId", headerMap, paramMap));
                    if (userRoleObject != null) {
                        JSONArray jsonArray = userRoleObject.getJSONArray("data");
                        List<Map<Object,Object>> list = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<List<Map<Object,Object>>>(){});
                        order.setCreateRole(ListUtil.Map2String(list));
                    }
                    Long orderId = orderInfoService.saveOrUpdate(order, passengerList, orderServiceList, userId, airportCode);
                    result.setMsg(LZStatus.SUCCESS.display());
                    result.setStatus(LZStatus.SUCCESS.value());
                    result.setData(orderId);
                    return JSON.toJSONString(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(e.toString());
            return JSON.toJSONString(result);
        }

    }

    private boolean canUpdateOrderStatus(String currentOrderStatus, String toOrderStatus) {
        boolean flag = false;
        try {
            /**
             * 只有如下的几种订单流转状态
             * 预约草稿 to  {预约取消，已预约}
             已预约 to  {已使用,预约取消}
             已使用 to  {服务草稿，服务取消}
             服务草稿 to  {已使用，服务取消}
             */

            /**
             * 预约草稿-》预约草稿
             *          已预约
             *          预约取消
             */
            if (currentOrderStatus.equals("预约草稿") && (toOrderStatus.equals("预约草稿") || toOrderStatus.equals("已预约") || toOrderStatus.equals("预约取消"))) {
                flag = true;
            }
            /**
             * 已预约-》  已预约
             *          预约草稿
             *          已使用
             *          预约取消
             */
            if (currentOrderStatus.equals("已预约") && (toOrderStatus.equals("已预约") || toOrderStatus.equals("预约草稿") || toOrderStatus.equals("已使用") || toOrderStatus.equals("预约取消"))) {
                flag = true;
            }
            /**
             * 已使用-》  已使用
             *          服务草稿
             *          服务取消
             */
            if (currentOrderStatus.equals("已使用") && (toOrderStatus.equals("已使用") || toOrderStatus.equals("预约取消") || toOrderStatus.equals("服务草稿") || toOrderStatus.equals("服务取消"))) {
                flag = true;
            }
            /**
             * 服务草稿-》 服务草稿
             *          已使用
             *          服务取消
             */
            if (currentOrderStatus.equals("服务草稿") && (toOrderStatus.equals("服务草稿") || toOrderStatus.equals("已使用") || toOrderStatus.equals("服务取消"))) {
                flag = true;
            }
            /**
             * 预约取消-》预约取消
             */
            if (currentOrderStatus.equals("预约取消") && toOrderStatus.equals("预约取消")) {
                flag = true;
            }
            /**
             * 服务取消-》服务取消
             */
            if (currentOrderStatus.equals("服务取消") && toOrderStatus.equals("服务取消")) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



    /**
     * 查看当前登录的机场编码
     * @author zhangpengfei for thrift
     * @param request
     * @return
     */
    public String getAirportCode(JSONObject request) {
        LZResult<Object> result = new LZResult();
        try {
            String airportCode = request.getString("client_id");
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            Map map = new HashMap();
            map.put("airportCode",airportCode);
            result.setData(map);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
            return JSON.toJSONString(result);
        }
    }


    @GET
    @Path("getAirportCode")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "查看当前登录的机场编码", notes = "返回机场编码", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = String.class)
    })
    public String getAirportCode(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<Object> result = new LZResult();
        try {

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            Map map = new HashMap();
            map.put("airportCode",airportCode);
            result.setData(map);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
            return JSON.toJSONString(result);
        }
    }


    /**
     * 订单管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     * @author zhangpengfei for thrift
     * @return
     */
    public String delete(JSONObject request) {
        LZResult<OrderInfo> result = new LZResult();
        try {
            Long userId = Long.valueOf(request.getString("user_id"));
            String airportCode = request.getString("client_id");
            JSONArray jsonarray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonarray.toJSONString(), Long.class);
            orderInfoService.deleteById(ids, userId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
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
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @Context final HttpHeaders headers,
            @RequestBody RequsetParams<Long> params) {
        LZResult<OrderInfo> result = new LZResult();
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            List<Long> ids = params.getData();
            orderInfoService.deleteById(ids, userId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }


    /**
     * 订单管理 - 根据id查询订单详情
     * @author zhangpengfei for thrift
     * @return
     */
    public String view(JSONObject request) {
        LZResult<OrderInfo> result = new LZResult<>();
        try {
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");
            Long orderId = request.getLong("orderId");
            OrderInfo orderInfo = orderInfoService.getById(orderId, userId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderInfo);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 订单管理 - 根据id查询订单详情
     *
     * @param orderId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 根据id查询订单详情 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = OrderInfo.class)
    })
    public String view(
            @QueryParam("orderId") Long orderId,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<OrderInfo> result = new LZResult<>();
        try {
            OrderInfo orderInfo = orderInfoService.getById(orderId, userId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderInfo);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }


    /**
     * 订单管理 - 修改订单服务状态
     * @author zhangpengfei for thrift
     * @return
     */
    public String updateServerComplete(JSONObject request) {
        try {
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");


            if (request.getLong("flightId") == null) {
                logger.error("updateServerComplete error", LZStatus.DATA_EMPTY.display() + ":flightId");
            }
            Map<String,Object> map = new HashMap();
            map.put("flightId", request.getLong("flightId"));
            map.put("serverComplete", request.getShort("serverComplete"));

            orderInfoService.updateServerComplete(map, userId, airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("updateServerComplete error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 订单管理 - 修改订单服务状态
     * @author E.in
     * @return
     */
    @POST
    @Path("updateServerComplete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "修改订单服务状态", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String updateServerComplete(
            @HeaderParam("user-id") Long userId,
            @HeaderParam("client-id") String airportCode,
            String param) {
        try {
            JSONObject paramJson = JSON.parseObject(param);
            if (paramJson.getLong("flightId") == null) {
                logger.error("updateServerComplete error", LZStatus.DATA_EMPTY.display() + ":flightId");
            }
            Map<String,Object> map = new HashMap();
            map.put("flightId", paramJson.getLong("flightId"));
            map.put("serverComplete", paramJson.getShort("serverComplete"));

            orderInfoService.updateServerComplete(map, userId, airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("updateServerComplete error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 根据订单状态/详细服务id 获取服务人次
     * @author zhangpengfei for thrift
     * @return
     */
    public String getServerNumByServlId(JSONObject request) {
        LZResult<Integer> result = new LZResult();
        try {
            String airportCode = request.getString("client_id");
            Long servId = request.getLong("servId");

            Integer orderCount = orderInfoService.getServerNumByServId(OrderConstant.ORDER_STATUS_USED, servId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 根据订单状态/详细服务id 获取服务人次
     * @author E.in
     * @return
     */
    @GET
    @Path("getServerNumByServlId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据订单状态/详细服务id 获取服务人次 ", notes = "返回服务人次", httpMethod = "GET", produces = "application/json")
    public String getServerNumByServlId(
            @QueryParam("servId") Long servId,
            @Context final HttpHeaders headers) {
        LZResult<Integer> result = new LZResult();
        try {
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            Integer orderCount = orderInfoService.getServerNumByServId(OrderConstant.ORDER_STATUS_USED, servId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }


    /**
     * 根据协议id查询订单数量，判断协议能否删除
     * @author zhangpengfei for thrift
     * @return
     */
    public String getOrderCountByProtocolId(JSONObject request) {
        LZResult<Integer> result = new LZResult();
        try {

            String airportCode = request.getString("client_id");
            Long protocolId  = request.getLong("protocolId");
            int orderCount = orderInfoService.getOrderCountByProtocolId(protocolId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据协议id查询订单数量，判断协议能否删除
     * @author E.in
     * @param protocolId
     * @param headers
     * @return
     */
    @GET
    @Path("getOrderCountByProtocolId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "订单管理 - 判断协议是否被订单引用 ", notes = "返回被引用数量", httpMethod = "GET", produces = "application/json")
    public String getOrderCountByProtocolId(
            @QueryParam("protocolId") Long protocolId,
            @Context final HttpHeaders headers) {
        LZResult<Integer> result = new LZResult();
        try {
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            int orderCount = orderInfoService.getOrderCountByProtocolId(protocolId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(orderCount);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }


    /**
     * 查找客户下有订单的协议ID
     *
     * @param
     * //客户ID串
     * @param
     * //账单标志 【0（null）:普通账单,1:特殊客户（南航/国航）;头等舱;常旅客2:,3:】
     * @param
     * //机场码
     * @author zhangpengfei for thrift
     * @return
     */
    public String queryProtocolIdsInOrderInfoByCustomId(JSONObject request) {
        List<ProtocolList> protocolLists = null;
        LZResult<Object> result = new LZResult<>();
        try {
            Integer flag = request.getInteger("flag");
            String customerIds = request.getString("customerIds");
            String airportCode = request.getString("client_id");

            if (flag == null || flag == 0) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomId(customerIds, airportCode);
            } else if (flag == 1) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{9, 10});
            } else if (flag == 2) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{10});
            } else if (flag == 3) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{9});
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(protocolLists);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        } finally {
            return JSON.toJSONString(result);
        }
    }

    /**
     * 查找客户下有订单的协议ID
     *
     * @param customerIds 客户ID串
     * @param flag        账单标志 【0（null）:普通账单,1:特殊客户（南航/国航）;头等舱;常旅客2:,3:】
     * @param airportCode 机场码
     * @return
     */
    @GET
    @Path("queryProtocolIdsInOrderInfoByCustomId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "查询协议 - 判断协议是否被订单引用 ", notes = "返回协议信息", httpMethod = "GET", produces = "application/json")
    public LZResult<List<ProtocolList>> queryProtocolIdsInOrderInfoByCustomId(
            @QueryParam("customerIds") String customerIds,
            @QueryParam("flag") Integer flag,
            @HeaderParam("client-id") String airportCode) {
        List<ProtocolList> protocolLists = null;
        LZResult<List<ProtocolList>> result = new LZResult<>();
        try {
            if (flag == null || flag == 0) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomId(customerIds, airportCode);
            } else if (flag == 1) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{9, 10});
            } else if (flag == 2) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{10});
            } else if (flag == 3) {
                protocolLists = orderInfoService.queryProtocolIdsInOrderInfoByCustomIdAndType(customerIds, airportCode, new Integer[]{9});
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(protocolLists);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        } finally {
            return result;
        }
    }

    /**
     * @author zhangpengfei for thrift
     * 查询卡类别
     */
    public String getCardType(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        try {
            String airportCode = request.getString("client_id");
            List<Map> list = orderInfoService.queryCardType(airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }

    @GET
    @Path("getCardType")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "查询卡类别", notes = "返回卡类别下拉框", httpMethod = "GET", produces = "application/json")
    public String getCardType(@HeaderParam("client-id") String airportCode) {
        LZResult<Object> result = new LZResult<>();
        try {
            List<Map> list = orderInfoService.queryCardType(airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }

    /**
     * 统一处理错误信息
     * @param e
     * @return
     */
    private String errorMsg(Exception e){
        e.printStackTrace();
        LZResult result = new LZResult<>();
        result.setMsg(LZStatus.ERROR.display());
        result.setStatus(LZStatus.ERROR.value());
        result.setData(null);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

}
