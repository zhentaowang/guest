package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.OrderConstant;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.OrderInfo;
import com.zhiweicloud.guest.model.OrderServiceRecord;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.service.OrderInfoService;
import com.zhiweicloud.guest.service.OrderServiceRecordService;
import com.zhiweicloud.guest.service.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * 代办服务
 * Created by zhengyiyin on 2017/3/4.
 */
@Component
@Path("/")
@Api(value = "订单", description = "订单desc ", tags = {"订单管理"})
public class OrderServiceRecordController {
    @Autowired
    private OrderServiceRecordService orderServiceRecordService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PassengerService passengerService;


    /**
     * @author zhangpengfei for thrift
     * @param request
     * @return
     */
    public String addOrderServiceRecord(JSONObject request){
        LZResult<String> result = new LZResult<>();
        try {
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");

            OrderServiceRecord orderParam = null;
            if (!CollectionUtils.isEmpty(JSON.parseArray(request.getString("data")))) {
                JSONArray jsonArray = JSON.parseArray(request.getString("data"));
                orderParam = JSON.toJavaObject(jsonArray.getJSONObject(0), OrderServiceRecord.class);
            }
            if (orderParam == null || null == orderParam.getOrderId() || null == orderParam.getOrderType()) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else {
                OrderInfo tempOrder = new OrderInfo();
                tempOrder.setOrderId(orderParam.getOrderId());
                tempOrder.setAirportCode(airportCode);
                tempOrder.setUpdateUser(userId);
                tempOrder.setUpdateTime(new Date());

                OrderServiceRecord record = new OrderServiceRecord();
                record.setOrderId(orderParam.getOrderId());
                record.setAirportCode(airportCode);
                record.setCreateUser(userId);

                //设置代办人
                if(orderParam.getAgentPerson() != null) {
                    tempOrder.setAgentPerson(orderParam.getAgentPerson());
                    tempOrder.setAgentPersonName(orderParam.getAgentPersonName());
                    record.setRecordDesc(OrderConstant.ORDER_SER_AGENT_PERSON + orderParam.getAgentPersonName());
                    orderServiceRecordService.insert(record);
                }
                //设置证件
                if(orderParam.getServerCardNo() != null) {
                    tempOrder.setServerCardNo(orderParam.getServerCardNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_CARDNO + orderParam.getServerCardNo());
                    orderServiceRecordService.insert(record);
                }
                //设置贵宾卡
                if(orderParam.getVipCard() != null) {
                    tempOrder.setVipCard(orderParam.getVipCard());
                    record.setRecordDesc(OrderConstant.ORDER_SER_VIP_CARD + orderParam.getVipCard());
                    orderServiceRecordService.insert(record);
                }
                //设置现金
                if(orderParam.getCash() != null) {
                    tempOrder.setCash(orderParam.getCash());
                    record.setRecordDesc(OrderConstant.ORDER_SER_CASH + orderParam.getCash());
                    orderServiceRecordService.insert(record);
                }
                //设置座位号
                if(orderParam.getSitNo() != null) {
                    tempOrder.setSitNo(orderParam.getSitNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SIT_NO + orderParam.getSitNo());
                    orderServiceRecordService.insert(record);
                }
                //设置代办状态
                if(orderParam.getAgentComplete() != null) {
                    tempOrder.setAgentComplete(orderParam.getAgentComplete());
                    if(Constant.MARK_AS_BUSS_DATA == orderParam.getAgentComplete()) {
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_RESTART);
                    }else {
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_COMPLETE);
                    }
                    orderServiceRecordService.insert(record);
                }

                //修改订单附加服务部分信息
                orderInfoService.updateOrderInfo(tempOrder);
                //乘客信息不为空的话，更新乘客座位
                if(!CollectionUtils.isEmpty(orderParam.getPassengerList())) {
                    for(Passenger p : orderParam.getPassengerList()) {
                        p.setAirportCode(airportCode);
                        passengerService.updateByPassenger(p);
                    }
                }

                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }


    @POST
    @Path(value="addOrderServiceRecord")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "订单管理 - 更新附加服务", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String addOrderServiceRecord(
            @RequestBody RequsetParams<OrderServiceRecord> params,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId
 ){
        LZResult<String> result = new LZResult<>();
        try {
            OrderServiceRecord orderParam = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                orderParam = params.getData().get(0);
            }
            if (orderParam == null || null == orderParam.getOrderId() || null == orderParam.getOrderType()) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else {
                OrderInfo tempOrder = new OrderInfo();
                tempOrder.setOrderId(orderParam.getOrderId());
                tempOrder.setAirportCode(airportCode);
                tempOrder.setUpdateUser(userId);
                tempOrder.setUpdateTime(new Date());

                OrderServiceRecord record = new OrderServiceRecord();
                record.setOrderId(orderParam.getOrderId());
                record.setAirportCode(airportCode);
                record.setCreateUser(userId);

                //设置代办人
                if(orderParam.getAgentPerson() != null) {
                    tempOrder.setAgentPerson(orderParam.getAgentPerson());
                    tempOrder.setAgentPersonName(orderParam.getAgentPersonName());
                    record.setRecordDesc(OrderConstant.ORDER_SER_AGENT_PERSON + orderParam.getAgentPersonName());
                    orderServiceRecordService.insert(record);
                }
                //设置证件
                if(orderParam.getServerCardNo() != null) {
                    tempOrder.setServerCardNo(orderParam.getServerCardNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_CARDNO + orderParam.getServerCardNo());
                    orderServiceRecordService.insert(record);
                }
                //设置贵宾卡
                if(orderParam.getVipCard() != null) {
                    tempOrder.setVipCard(orderParam.getVipCard());
                    record.setRecordDesc(OrderConstant.ORDER_SER_VIP_CARD + orderParam.getVipCard());
                    orderServiceRecordService.insert(record);
                }
                //设置现金
                if(orderParam.getCash() != null) {
                    tempOrder.setCash(orderParam.getCash());
                    record.setRecordDesc(OrderConstant.ORDER_SER_CASH + orderParam.getCash());
                    orderServiceRecordService.insert(record);
                }
                //设置座位号
                if(orderParam.getSitNo() != null) {
                    tempOrder.setSitNo(orderParam.getSitNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SIT_NO + orderParam.getSitNo());
                    orderServiceRecordService.insert(record);
                }
                //设置代办状态
                if(orderParam.getAgentComplete() != null) {
                    tempOrder.setAgentComplete(orderParam.getAgentComplete());
                    if(Constant.MARK_AS_BUSS_DATA == orderParam.getAgentComplete()) {
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_RESTART);
                    }else {
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_COMPLETE);
                    }
                    orderServiceRecordService.insert(record);
                }

                //修改订单附加服务部分信息
                orderInfoService.updateOrderInfo(tempOrder);
                //乘客信息不为空的话，更新乘客座位
                if(!CollectionUtils.isEmpty(orderParam.getPassengerList())) {
                    for(Passenger p : orderParam.getPassengerList()) {
                        p.setAirportCode(airportCode);
                        passengerService.updateByPassenger(p);
                    }
                }

                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 根据订单id 获取附加服务动态
     * @author zhangpengfei for thrift
     * @return
     */
    public String getOrderServiceRecord(JSONObject request) {
        LZResult<List<OrderServiceRecord>> result = new LZResult<>();
        String airportCode = request.getString("client_id");
        Long orderId = request.getLong("orderId");
        if (orderId == null || StringUtils.isEmpty(airportCode)) {
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
        } else {
            List<OrderServiceRecord> list = orderServiceRecordService.getOrderServiceRecord(orderId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss");

    }

    /**
     * 根据订单id 获取附加服务动态
     * @param orderId
     * @return
     */
    @GET
    @Path(value = "getOrderServiceRecord")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "附加服务-获取服务动态", notes = "返回结果集")
    public String getOrderServiceRecord(
                       @QueryParam(value="orderId") Long orderId,
                       @HeaderParam("client-id") String airportCode) {
        LZResult<List<OrderServiceRecord>> result = new LZResult<>();
        if (orderId == null || StringUtils.isEmpty(airportCode)) {
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
        } else {
            List<OrderServiceRecord> list = orderServiceRecordService.getOrderServiceRecord(orderId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss");

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
