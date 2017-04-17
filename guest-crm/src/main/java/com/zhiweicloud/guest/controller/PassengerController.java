package com.zhiweicloud.guest.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.model.ServiceInfo;
import com.zhiweicloud.guest.service.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/2/23.
 */
@Component
@Path("/")
@Api(value="客户信息", description = "",tags = "crmInfo")
public class PassengerController {
    private static Logger logger = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerService;

    /**
     * crm列表
     * @param page
     * @param rows
     * @param airportCode
     * @param passengerQuery
     * @author E.in
     * @return
     */
    @GET
    @Path(value="getPassengerList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="客户管理 - 列表", notes ="返回集合")
    public String getPassengerList(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @HeaderParam("client-id") String airportCode,
            @BeanParam final PassengerQuery passengerQuery
            ){
        LZResult<PaginationResult<Passenger>> result = new LZResult<>();
        try{
            passengerQuery.setAirportCode(airportCode);
            passengerQuery.setTypes(ListUtil.StringFormat(passengerQuery.getProtocolTypes(),passengerQuery.getLabels()));

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result = passengerService.getPassengerList(passengerQuery, page, rows);
        } catch (Exception e) {
            logger.error("PassengerController.getPassengerList:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
       
        return  JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 客户详情信息，以及 服务信息
     * @param crmPassengerId
     * @author E.in
     * @return
     */
    @GET
    @Path(value = "getPassengerById")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="客户管理 - 详情", notes ="返回结果")
    public String getPassengerById(
            @HeaderParam("client-id") String airportCode,
            @QueryParam("passengerId") Long crmPassengerId){
        LZResult<Passenger> result = new LZResult<>();
        try{
            Passenger passenger = passengerService.getPassengerById(crmPassengerId,airportCode);
            if(passenger != null){
                Map<String, Object> param = new HashMap();
                param.put("airportCode", airportCode);
                param.put("crmPassengerId", crmPassengerId);
                param.put("phone", passenger.getPhone());
                param.put("identityCard", passenger.getIdentityCard());
                List<ServiceInfo>  serviceInfoList = passengerService.getServiceInfoList(param);
                passenger.setServiceInfoList(serviceInfoList);
            }

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(passenger);
        }catch (Exception e){
            logger.error("PassengerController.getPassengerById:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }


    /**
     * crm详情页 获取标签的信息
     * @param airportCode
     * @param crmPassengerId
     * @param phone
     * @param identityCard
     * @param protocolTypes
     * @author E.in
     * @return
     */
    @GET
    @Path(value = "getLabelInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="客户详情 - 标签信息", notes ="返回结果")
    public String getLabelInfo(
            @HeaderParam("client-id") String airportCode,
            @QueryParam("passengerId") Long crmPassengerId,
            @QueryParam("phone") Long phone,
            @QueryParam("identityCard") String identityCard,
            @QueryParam("protocolTypes") String protocolTypes
            ){
        LZResult<List<ServiceInfo>> result = new LZResult<>();
        try{

            Map<String, Object> param = new HashMap();
            param.put("airportCode", airportCode);
            param.put("crmPassengerId", crmPassengerId);
            param.put("phone", phone);
            param.put("identityCard", identityCard);
            param.put("protocolTypes", protocolTypes);

            List<ServiceInfo> serviceInfoList = passengerService.getServiceInfoList(param);

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(serviceInfoList);
        }catch (Exception e){
            logger.error("PassengerController.getLabelInfo:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm", SerializerFeature.WriteMapNullValue);
    }
}
