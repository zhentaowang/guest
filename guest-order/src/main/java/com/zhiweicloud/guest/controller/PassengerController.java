package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.service.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/2.
 */
@Component
@Path("/")
@Api(value = "订单客户", description = "passengerdesc ", tags = {"passenger"})
public class PassengerController {

    @Autowired
    private PassengerService passengerService;


    /**
     * 身份证号模糊匹配下拉框 数据
     * @author zhangpengfei for thrift
     * @return
     */
    public String getIdentityCardDropdownList(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        String identityCard = request.getString("identityCard");
        String airportCode = request.getString("client_id");
        if(airportCode == null){
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
        List<Dropdownlist> list = passengerService.getIdentityCardDropdownList(identityCard, airportCode);
        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(list);
        return JSON.toJSONString(result);
    }

    /**
     * 身份证号模糊匹配下拉框 数据
     * @return
     */
    @GET
    @Path(value="getIdentityCardDropdownList")
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="乘客身份证模糊匹配下拉框，不重复的字符串",notes="乘客身份证模糊匹配下拉框", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    public String getIdentityCardDropdownList(
            ContainerRequestContext request,
            @QueryParam(value = "identityCard") String identityCard) {
        LZResult<List<Dropdownlist>> result = new LZResult<>();
        String airportCode = request.getHeaders().getFirst("client-id").toString();
        if(airportCode == null){
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
        List<Dropdownlist> list = passengerService.getIdentityCardDropdownList(identityCard, airportCode);
        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(list);
        return JSON.toJSONString(result);
    }

    /**
     * 根据航班id获取乘客信息
     * @author zhangpengfei for thirft
     */
    public String getPassengerByFlightId(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        Long servId = request.getLong("servId");
        Long flightId = request.getLong("flightId");
        String airportCode = request.getString("client_id");

        Long typeId = null;


        if(servId == null){
            typeId = 1L; //1:贵宾厅，5：休息室。不传servId，就默认查全部贵宾厅(调度列表中用到)
        }
        List<Passenger> passengerList = passengerService.getPassengerlistByFlightId(flightId,typeId,servId,airportCode);

        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(passengerList);
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);

    }

    /**
     * 根据航班id获取乘客信息
     */
    @GET
    @Path(value="getPassengerByFlightId")
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="根据航班id查询乘客信息",notes="根据航班id获取乘客信息", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    public String getPassengerByFlightId(@QueryParam(value = "flightId") Long flightId,
                                         @QueryParam(value = "servId") Long servId,
                                         @HeaderParam("client-id") String airportCode) {
        LZResult<List<Passenger>> result = new LZResult<>();
        Long typeId = null;
        if(servId == null){
            typeId = 1L; //1:贵宾厅，5：休息室。不传servId，就默认查全部贵宾厅(调度列表中用到)
        }
        List<Passenger> passengerList = passengerService.getPassengerlistByFlightId(flightId,typeId,servId,airportCode);

        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(passengerList);
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);

    }
}
