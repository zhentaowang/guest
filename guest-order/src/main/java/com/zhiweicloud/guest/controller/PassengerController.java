package com.zhiweicloud.guest.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
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
     */
    @GET
    @Path(value="getPassengerByFlightId")
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="根据航班id查询乘客信息",notes="根据航班id获取乘客信息", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    public String getPassengerByFlightId(ContainerRequestContext request,
                                         @QueryParam(value = "flightId") Long flightId,
                                         @HeaderParam("client-id") String airportCode) {
        LZResult<List<Passenger>> result = new LZResult<>();
        List<Passenger> passengerList = passengerService.getPassengerlistByFlightId(flightId,airportCode);

        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(passengerList);
        return JSON.toJSONString(result);

    }
}
