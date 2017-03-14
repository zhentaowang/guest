package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.dragon.sign.DragonSignature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.FlightException;
import com.zhiweicloud.guest.common.Global;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.service.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Created by zhangpengfei on 2017/1/22.
 */
@Component
@Path("/")
@Api(value = "航班信息", description = "航班信息desc ", tags = {"flight-info"})
public class FlightInfoController {
    @Autowired
    private FlightService flightService;

    /**
     * 订单管理 - 根据id查询
     *
     * @param date
     * @param fnum
     * @return
     */
    @GET
    @Path(value = "flightInfo")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班信息 - 航班号+航班日期,航班号+航班日期+航段,航班日期+航段 查询 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "fnum", value = "航班号", dataType = "String", required = true, paramType = "query",defaultValue = "CA1352"),
            @ApiImplicitParam(name = "date", value = "航班日期", dataType = "String", required = true, paramType = "query",defaultValue = "2017-02-03")
    })*/
    public String view(@QueryParam(value = "fnum") String fnum,
                       @QueryParam(value = "date") String date) {
        try {
            String privateKey = Global.getPrivateKey();
            Map<String, String> params = new HashMap<>();
            params.put("date",date);
            params.put("fnum",fnum);

            params.put("lg", "zh-cn");
            params.put("sysCode", "dpctest");


            String sign = DragonSignature.rsaSign(params, privateKey, "UTF-8");
            //System.out.print("sign:" + sign);


            Map<String, Object> p = new HashMap<>();
            p.put("date",date);
            p.put("fnum",fnum);
            p.put("lg", "zh-cn");
            p.put("sysCode", "dpctest");
            p.put("sign", sign);

            String ret = HttpClientUtil.httpPostRequest("http://183.63.121.12:8012/FlightCenter/wcf/FlightWcfService.svc/GetFlightInfo_Lg", p);
            return new String(ret.getBytes("ISO-8859-1"),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return "error...";
        }


    }

    /**
     * 产品品类下拉框 数据
     *
     * @return
     */
    @GET
    @Path("flightInfoDropdownList")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航段下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    public String flightInfoDropdownList(@QueryParam(value = "airportNameOrCode") String airportNameOrCode) {
        LZResult<List<Map<String,String>>> result = new LZResult<>();
        try {
            List<Map<String,String>> list = flightService.flightInfoDropdownList(airportNameOrCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 航班号信息下拉框 数据
     * @param headers 请求头
     * @return
     */
    @GET
    @Path("flightNoDropdownList")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班号下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    public String flightNoDropdownList(@QueryParam(value = "flightNo") String flightNo,@Context final HttpHeaders headers) {
        LZResult<List<Map<String,String>>> result = new LZResult<>();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        try {
            List<Map<String,String>> list = flightService.flightNoDropdownList(flightNo,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新航班信息 根据航班对象的航班ID（flight_id）
     *
     * @return
     */
    @POST
    @Path("updateFlight")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据航班ID更新航班信息", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json", tags = {"flight-info"})
    public String updateFlight(@RequestBody Flight flight,
                               @HeaderParam("client-id") String airportCode,
                               @HeaderParam("user-id") Long userId) {
        try{
            if (flight == null){
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
            }
            flight.setAirportCode(airportCode);
            flight.setUpdateUser(userId);
            flightService.updateFlight(flight);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        }catch (FlightException e){
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.NOT_FOUND.value(), LZStatus.NOT_FOUND.display()));
        }catch (Exception e){
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

}
