package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.FlightException;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightMatch;
import com.zhiweicloud.guest.model.FlightScheduleEvent;
import com.zhiweicloud.guest.model.FlightUpdateLog;
import com.zhiweicloud.guest.service.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FlightInfoController.java.
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/17 17:44
 *
 * @author tiecheng
 */
@Component
@Path("/")
@Api(value = "航班信息", description = "航班信息desc ", tags = {"flight-info"})
public class FlightInfoController {

    private static final Log log = LogFactory.getLog(FlightInfoController.class);

    /**
     * FlightService.
     */
    @Autowired
    private FlightService flightService;

    /**
     * 订单管理 - 根据id查询.
     *
     * @param fnum        航班号
     * @param date        航班日期
     * @param airportCode 客户端标识
     * @param userId      用户ID
     * @return
     */
    @GET
    @Path(value = "flightInfo")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班信息 - 航班号+航班日期,航班号+航班日期+航段,航班日期+航段 查询 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    public String view(
            @QueryParam(value = "fnum") String fnum,
            @QueryParam(value = "date") String date,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            return flightService.getFlightInfo(fnum, date, airportCode, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"Data\": [],\"Info\": \"" + e.getMessage() + "\",\"DPtime\": ,\"Vtime\": ,\"State\": -1}";
        }
    }

    /**
     * 产品品类下拉框 数据
     *
     * @param airportNameOrCode 机场名或机场码
     * @return
     */
    @GET
    @Path("flightInfoDropdownList")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航段下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    public String flightInfoDropdownList(
            @QueryParam(value = "airportNameOrCode") String airportNameOrCode) {
        LZResult<List<Map<String, String>>> result = new LZResult<>();
        try {
            List<Map<String, String>> list = flightService.flightInfoDropdownList(airportNameOrCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 航班号信息下拉框 数据
     *
     * @param flightNo    航班号
     * @param airportCode 机场码
     * @param userId      用户ID
     * @return
     */
    @GET
    @Path("flightNoDropdownList")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班号下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    public String flightNoDropdownList(
            @QueryParam(value = "flightNo") String flightNo,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<List<Map<String, String>>> result = new LZResult<>();
        try {
            List<Map<String, String>> list = flightService.flightNoDropdownList(flightNo, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新航班信息 -- 提供给龙腾推送
     *
     * @param request     请求对象
     * @param airportCode 机场码
     * @param userId      用户ID
     * @return
     */
    @POST
    @Path("updateFlight")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据航班ID更新航班信息", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json", tags = {"flight-info"})
    public String updateFlight(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId,
            @Context HttpServletRequest request) {
        try {
            String data = request.getAttribute("data").toString();
            FlightMatch flightMatch = JSONObject.toJavaObject(JSON.parseObject(data), FlightMatch.class);
            Flight flight = new Flight();
            BeanUtils.copyProperties(flight, flightMatch);
            Map<String, Object> result = new HashMap<>();
            // 龙腾推送过来的航班信息为空，需要一直推送直到不为空
            if (flight == null) {
                result.put("state", -1);
                result.put("info", "推送航班信息为空");
                return JSON.toJSONString(result);
            }
            flight.setAirportCode(airportCode);
            flight.setUpdateUser(userId);
            flightService.updateFlight(flight);
            result.put("state", 1);
            result.put("info", "接收并处理成功");
            return JSON.toJSONString(result);
        } catch (FlightException e) {
            log.error(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("state", 2);
            result.put("info", e.getMessage());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("state", -1);
            result.put("info", "操作失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 航班调度事件管理 - 新增or更新
     *
     * @param params
     * @param airportCode
     * @param userId
     * @return
     */
    @POST
    @Path("saveOrUpdateFlightScheduleEvent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "保存 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json", tags = {"flight-schedule"})
    public String saveOrUpdateFlightScheduleEvent(
            @ApiParam(value = "flightScheduleEvent", required = true) @RequestBody String params,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            for (int i = 0; i < param.size(); i++) {
                FlightScheduleEvent flightScheduleEvent = JSONObject.toJavaObject(JSON.parseObject(param.get(i).toString()), FlightScheduleEvent.class);
                flightScheduleEvent.setAirportCode(airportCode);
                flightService.saveOrUpdateFlightScheduleEvent(flightScheduleEvent, userId);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 定制航班信息
     * 调用了龙腾的接口
     *
     * @param flightId    航班ID
     * @param airportCode 机场码
     * @param userId      用户ID
     * @return
     */
    @GET
    @Path("customFlight")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "定制航班 - 推送给龙腾", notes = "返回成功还是失败", httpMethod = "GET", produces = "application/json")
    public String customFlight(
            @QueryParam(value = "flightId") Long flightId,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            Map<String, Object> result = new HashMap<>();
            // 根据ID查询航班信息
            Flight flight = flightService.queryFlightById(flightId, airportCode);
            if (flight == null) {
                result.put("state", -3);
                result.put("info", "无法定制不存在航班");
                return JSON.toJSONString(result);
            }
            flight.setAirportCode(airportCode);
            flight.setUpdateUser(userId);
            // 向龙腾定制航班信息
            Integer state = flightService.customDrangon(flight);
            // 定制成功 -- 拿到航班动态信息 并且 更新航班表数据
            if (state != null && state == 1) {
                flightService.updateFlight(flightService.getFlightDynamic(flight));
                result.put("state", 1);
                result.put("info", "定制航班成功");
                return JSON.toJSONString(result);
            }
//            String ret = HttpClientUtil.httpPostRequest("http://183.63.121.12:8012/FlightCenter/wcf/FlightWcfService.svc/CustomFlightNo", p);
            result.put("state", -2);
            result.put("info", "定制航班失败");
            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.debug(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("state", -1);
            result.put("info", "定制航班异常：" + e.getMessage());
            return JSON.toJSONString(result);
        }
    }

    /**
     * 航班信息修改日志
     *
     * @param flightId    航班ID
     * @param airportCode 客户端标识
     * @param userId      用户ID
     * @return
     */
    @GET
    @Path("flightLog")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班修改日志", notes = "返回航班修改日志", httpMethod = "GET", produces = "application/json")
    public String flightLog(
            @QueryParam(value = "flightId") Long flightId,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<List<FlightUpdateLog>> result = new LZResult<>();
        try {
            List<FlightUpdateLog> list = flightService.selectFlightLog(flightId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
            return JSON.toJSONStringWithDateFormat(result, "MM-dd HH:mm", SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.debug(e.getMessage());
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }

}
