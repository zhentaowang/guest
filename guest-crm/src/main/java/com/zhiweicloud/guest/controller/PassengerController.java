package com.zhiweicloud.guest.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.service.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

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
    private PassengerService crmInfoService;

    @GET
    @Path(value="getPassengerList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="客户管理 - 列表", notes ="返回集合")
    public String getPassengerList(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
//            @HeaderParam("client-id") String airportCode,
            @BeanParam final PassengerQuery passengerQuery
            ){
        LZResult<PaginationResult<Passenger>> result = new LZResult<>();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            passengerQuery.setAirportCode("LJG");
            passengerQuery.setTypes(ListUtil.List2String(passengerQuery.getProtocolTypes(),passengerQuery.getLabels()));

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result = crmInfoService.getPassengerList(passengerQuery, page, rows);
        } catch (Exception e) {
            logger.error("CrmInfoController.getPassengerList:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }
}
