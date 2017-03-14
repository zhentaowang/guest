package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.OrderConstant;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.OrderInfo;
import com.zhiweicloud.guest.model.OrderServiceRecord;
import com.zhiweicloud.guest.service.OrderInfoService;
import com.zhiweicloud.guest.service.OrderServiceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/4.
 */
@Component
@Path("/")
@Api(value = "订单", description = "订单desc ", tags = {"订单管理"})
public class OrderServiceRecordController {
    @Autowired
    private OrderServiceRecordService orderServiceRecordService;

    @Autowired
    private OrderInfoService orderInfoService;

    @POST
    @Path(value="addOrderServiceRecord")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "订单管理 - 更新附加服务", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String addOrderServiceRecord(
            @RequestBody RequsetParams<OrderServiceRecord> params,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId){
        LZResult<String> result = new LZResult<>();
        try {
            OrderServiceRecord orderParam = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                orderParam = params.getData().get(0);
            }
            if (orderParam == null || null == orderParam.getOrderId()) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{
                OrderInfo tempOrder = new OrderInfo();
                tempOrder.setOrderId(orderParam.getOrderId());
                tempOrder.setAirportCode(airportCode);
                tempOrder.setUpdateUser(userId);

                OrderServiceRecord record = new OrderServiceRecord();
                record.setOrderId(orderParam.getOrderId());
                record.setAirportCode(airportCode);
                record.setCreateUser(userId);

                //设置代办人
                if(orderParam.getAgentPerson() != null){
                    tempOrder.setAgentPerson(orderParam.getAgentPerson());
                    tempOrder.setAgentPersonName(orderParam.getAgentPersonName());
                    record.setRecordDesc(OrderConstant.ORDER_SER_AGENT_PERSON + orderParam.getAgentPersonName());
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }
                //设置证件
                if(orderParam.getServerCardNo() != null){
                    tempOrder.setServerCardNo(orderParam.getServerCardNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_CARDNO + orderParam.getServerCardNo());
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }
                //设置贵宾卡
                if(orderParam.getVipCard() != null){
                    tempOrder.setVipCard(orderParam.getVipCard());
                    record.setRecordDesc(OrderConstant.ORDER_SER_VIP_CARD + orderParam.getVipCard());
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }
                //设置现金
                if(orderParam.getCash() != null){
                    tempOrder.setCash(orderParam.getCash());
                    record.setRecordDesc(OrderConstant.ORDER_SER_CASH + orderParam.getCash());
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }
                //设置座位号
                if(orderParam.getSitNo() != null){
                    tempOrder.setSitNo(orderParam.getSitNo());
                    record.setRecordDesc(OrderConstant.ORDER_SER_SIT_NO + orderParam.getSitNo());
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }
                //设置代办状态
                if(orderParam.getAgentComplete() != null){
                    tempOrder.setAgentComplete(orderParam.getAgentComplete());
                    if(Constant.MARK_AS_BUSS_DATA == orderParam.getAgentComplete()){
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_COMPLETE);
                    }else{
                        record.setRecordDesc(OrderConstant.ORDER_SER_SERVER_RESTART);
                    }
                    record.setCreateTime(new Date());
                    orderServiceRecordService.insert(record);
                }

                //修改订单附加服务部分信息
                orderInfoService.saveOrUpdate(tempOrder,null,null,userId,airportCode);

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
     * @param request
     * @param orderId
     * @return
     */
    @GET
    @Path(value = "getOrderServiceRecord")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "附加服务-获取服务动态", notes = "返回结果集")
    public String getOrderServiceRecord(ContainerRequestContext request,
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
        return JSON.toJSONString(result);

    }
}
