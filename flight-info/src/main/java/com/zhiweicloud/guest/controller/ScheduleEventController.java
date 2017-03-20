package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.ScheduleEvent;
import com.zhiweicloud.guest.service.ScheduleEventService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 2017-03-03 20:47:22 Created By wzt
 */
@Component
@Path("/")
@Api(value = "调度管理", description = "调度管理desc ", tags = {"schedule-event"})
public class ScheduleEventController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleEventController.class);
    @Autowired
    private ScheduleEventService scheduleEventService;

    /**
     * 航班管理 - 修改航班
     * @param params
     * @return
     */
    @POST
    @Path("flight-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="航班管理 - 修改航班", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public String flightUpdate(@ApiParam(value = "flight", required = true) @RequestBody String params,
                         @Context final HttpHeaders headers){
        try{
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            for (int i = 0; i < param.size(); i++) {
                Flight flight = JSONObject.toJavaObject(JSON.parseObject(param.get(i).toString()),Flight.class);
                flight.setAirportCode(airportCode);
                scheduleEventService.flightUpdate(flight);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    @GET
    @Path("get-flight-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据航班号，日期，进出港，航班状态，调度事件查询航班列表  - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "flightNo", value = "航班号", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "flightDate", value = "航班日期", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "flightState", value = "航班状态", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "isInOrOut", value = "进出港", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "scheduleEventId", value = "调度事件id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "servIds", value = "服务id", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "serverComplete", value = "订单是否为服务完成状态", dataType = "Long", required = false, paramType = "query")
            })
    public String getFlightList(
            @Context final HttpHeaders headers,
            @QueryParam(value = "flightNo") String flightNo,
            @QueryParam(value = "flightDate") String flightDate,
            @QueryParam(value = "flightState") Long flightState,
            @QueryParam(value = "isInOrOut") Long isInOrOut,
            @QueryParam(value = "scheduleEventId") Long scheduleEventId,
            @QueryParam(value = "servIds") String servIds,
            @QueryParam(value = "typeId") Long typeId,
            @QueryParam(value = "serverComplete") Long serverComplete,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("flightNo",flightNo);
        param.put("flightDate",flightDate);
        param.put("flightState",flightState);
        param.put("isInOrOut",isInOrOut);

        param.put("scheduleEventId",scheduleEventId);
        param.put("servIds",servIds);
        param.put("serverComplete",serverComplete);
        LZResult<PaginationResult<Flight>> result  = scheduleEventService.getFlightList(param,page,rows);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 航班管理 - 根据flightId查询航班详情
     * @param flightId
     * @return
     */
    @GET
    @Path("get-flight-view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "航班管理 - 根据flightId查询航班详情 ", notes = "返回航班详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "flightId", value = "航班id", dataType = "Long", defaultValue = "28", required = true, paramType = "query")
    })
    public String getFlightView(@Context final HttpHeaders headers,
                       @QueryParam(value = "flightId") Long flightId
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        Flight flight = scheduleEventService.getByFlightId(param);
        return JSON.toJSONString(new LZResult<>(flight), SerializerFeature.WriteMapNullValue);
    }

    @GET
    @Path("schedule-event-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "调度事件管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")})
    public String list(
            @Context final HttpHeaders headers,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        LZResult<PaginationResult<ScheduleEvent>> result  = scheduleEventService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 调度事件管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("schedule-event-save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="调度事件管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "scheduleEvent", required = true) @RequestBody RequsetParams<ScheduleEvent> params,
                         @Context final HttpHeaders headers){
        try{
            ScheduleEvent scheduleEvent = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                scheduleEvent = params.getData().get(0);
            }
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            scheduleEvent.setAirportCode(airportCode);
            if(scheduleEvent.getScheduleEventId() != null){
                scheduleEventService.saveOrUpdate(scheduleEvent);
                return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
            }else{
                if (scheduleEvent == null  || scheduleEvent.getName() == null || scheduleEvent.getType() == null
                        || scheduleEvent.getIsApproach() == null || scheduleEvent.getAirportCode() == null) {
                    return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                }else{
                    scheduleEventService.saveOrUpdate(scheduleEvent);
                    return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 调度事件管理 - 根据scheduleEventId查询事件详情
     * @param scheduleEventId
     * @return
     */
    @GET
    @Path("schedule-event-view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "调度事件管理 - 根据scheduleEventId查询事件详情 ", notes = "返回调度事件详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "scheduleEventId", value = "调度事件id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String view(@Context final HttpHeaders headers,
                       @QueryParam(value = "scheduleEventId") Long scheduleEventId
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("scheduleEventId",scheduleEventId);
        ScheduleEvent scheduleEvent = scheduleEventService.getByScheduleEventId(param);
        return JSON.toJSONString(new LZResult<>(scheduleEvent));
    }

    /**
     * 调度事件管理 - 根据flightId和scheduleEventId查询
     * @param flightId
     * @param scheduleEventId
     * @return
     */
    @GET
    @Path("get-schedule-event-by-flight-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "调度事件管理 - 根据flightId和scheduleEventId查询 ", notes = "返回调度时间和类型", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "flightId", value = "航班id", dataType = "Long", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "scheduleEventId", value = "调度事件id", dataType = "Long", required = false, paramType = "query")
    })
    public String getScheduleEventByFlightId(@Context final HttpHeaders headers,
                       @QueryParam(value = "flightId") Long flightId,
                                             @QueryParam(value = "scheduleEventId") Long scheduleEventId
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        param.put("scheduleEventId",scheduleEventId);
        List<ScheduleEvent> scheduleEvent = scheduleEventService.getScheduleEventByFlightId(param);
        return JSON.toJSONString(new LZResult<>(scheduleEvent));
    }

    /**
     * 调度事件管理 - 获取调度事件下拉框
     * @return
     */
    @GET
    @Path("get-schedule-event-drop-down-box")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "调度事件管理 - 获取调度事件下拉框 ", notes = "返回调度时间和类型", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flightId", value = "航班id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String getScheduleEventBox(@Context final HttpHeaders headers,
                                      @QueryParam(value = "flightId") Long flightId
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("flightId",flightId);
        List<ScheduleEvent> scheduleEvent = scheduleEventService.getScheduleEventBox(param);
        return JSON.toJSONString(new LZResult<>(scheduleEvent));
    }

    /**
     * 调度事件管理 - 删除
     * @param params ids
     * @return
     */
    @POST
    @Path("schedule-event-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "调度事件管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @Context final HttpHeaders headers) {
        try {
            List<Long> ids = params.getData();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            for (int i = 0; i < ids.size(); i++) {
                if (scheduleEventService.selectFlightByScheduleEventId(ids.get(i), airportCode) == true) {
                    return JSON.toJSONString(LXResult.build(5004, "该项已被其他功能引用，无法删除；如需帮助请联系开发者"));
                }
            }
            scheduleEventService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete scheduleEvent by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }
}
