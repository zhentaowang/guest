package com.zhiweicloud.guest.thrift.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dragon.sign.DragonApiException;
import com.dragon.sign.DragonSignature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.*;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航班请求方法工具类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/9 14:05
 * @author tiecheng
 */
@Service
public class FlightUtils {

    private static final Log log = LogFactory.getLog(FlightUtils.class);

    @Autowired
    private ExchangeDragonMapper exchangeDragonMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private AirportInfoMapper airportInfoMapper;

    @Autowired
    private FlightUpdateLogMapper flightUpdateLogMapper;

    @Autowired
    private FlightScheduleEventMapper flightScheduleEventMapper;

    /**
     * 从龙腾得到航班信息
     * @param request
     * @return
     * @test
     */
    public String flightInfo(JSONObject request) {
        try {
            /*
            get request params
             */
            String fnum = request.getString("fnum");
            String date = request.getString("date");
            String airportCode = request.getString("client_id");
            Long userId = request.getLong("user_id");
            /*
            get sign from dragon
             */
            Map<String, String> params = new HashMap<>();
            params.put("date", date);
            params.put("fnum", fnum);
            params.put("lg", Dictionary.LG);
            params.put("sysCode", Dictionary.SYSCODE);
            String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
            params.put("sign", sign);
            /*
            get flight info from dragon interface by http request
             */
            Map<String, Object> p = new HashMap<>();
            p.put("date", date);
            p.put("fnum", fnum);
            p.put("lg", Dictionary.LG);
            p.put("sysCode", Dictionary.SYSCODE);
            p.put("sign", sign);
            String ret = HttpClientUtil.httpPostRequest(Dictionary.DRAGON_URL_GETFLIGHTINFO, p);
            /*
            create operator log
             */
            ExchangeDragon exchangeDragon = new ExchangeDragon();
            exchangeDragon.setFlightDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            exchangeDragon.setFlightNo(fnum);
            exchangeDragon.setExchangeType((short) 1); // 1 表示查询
            exchangeDragon.setCreateUser(userId);
            Integer crState = null;
            if (ret != null) {
                JSONObject crObject = JSON.parseObject(ret);
                crState = crObject.getInteger("State");
            }
            exchangeDragon.setInvokeResult(Short.valueOf(String.valueOf(crState)));
            exchangeDragon.setAirportCode(airportCode);
            exchangeDragonMapper.insert(exchangeDragon);
            if (log.isDebugEnabled()) {
                log.debug(new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8));
            }
            return new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "{ \"Data\": [],\"Info\": \"" + e.getMessage() + "\",\"DPtime\": ,\"Vtime\": ,\"State\": -1}";
        }
    }

    /**
     * 获取航班信息 配合flightInfo接口，确定每次选择航班的信息是否需要回调
     * @param request
     * @return
     * @test
     */
    public String getFlightForOrderDetail(JSONObject request){
        String result = null;
        try {
            Flight flight = new Flight();
            flight.setFlightNo(request.getString("fnum"));
            flight.setFlightDate(DateUtils.stringToDate(request.getString("date"),"yyyy-MM-dd"));
            flight.setFlightDepcode(request.getString("flightDepcode"));
            flight.setFlightArrcode(request.getString("flightArrcode"));
            flight.setFlightDeptimePlanDate(DateUtils.stringToDate(request.getString("flightDeptimePlanDate"),"yyyy-MM-dd HH:mm:ss"));
            flight.setFlightArrtimePlanDate(DateUtils.stringToDate(request.getString("flightArrtimePlanDate"),"yyyy-MM-dd HH:mm:ss"));
            flight.setIsInOrOut(request.getShort("isInOrOut"));
            flight.setAirportCode(request.getString("client_id"));
            Flight localFlight = flightMapper.isFlightExist(flight);
            if (localFlight == null) {
                Flight newFlight = new Flight();
                newFlight.setFlightDeptimePlanDate(flight.getFlightDeptimePlanDate());
                newFlight.setFlightArrtimePlanDate(flight.getFlightArrtimePlanDate());
                newFlight.setIsInOrOut(flight.getIsInOrOut());
                result = JSONObject.toJSONString(newFlight, SerializerFeature.WriteMapNullValue);
            }else {
                localFlight.setFlightDeptimePlanDate(flight.getFlightDeptimePlanDate());
                localFlight.setFlightArrtimePlanDate(flight.getFlightArrtimePlanDate());
                localFlight.setIsInOrOut(flight.getIsInOrOut());
                result = JSONObject.toJSONString(localFlight,SerializerFeature.WriteMapNullValue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 航班模糊匹配
     * @param request
     * @return
     */
    public String flightInfoDropdownList(JSONObject request){
        LZResult<List<Map<String, String>>> result = new LZResult<>();
        try {
            List<Map<String, String>> list = airportInfoMapper.queryFlightInfoDropdownList(request.getString("airportNameOrCode"));
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
     * 航班信息下拉框
     * @param request
     * @return
     * @test
     */
    public String flightNoDropdownList(JSONObject request){
        LZResult<List<Map<String, String>>> result = new LZResult<>();
        try {
            List<Map<String, String>> list = airportInfoMapper.queryFlightNoDropdownList(request.getString("fnum"), request.getString("client_id"));
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
     * 更新航班信息 -- 提供给龙腾推送.
     * @param request
     * @return
     */
    public String updateFlight(JSONObject request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String airportCode = request.getString("client_id");
            Long userId = request.getLong("user_id");
            String data = request.getString("data");
            String sign = request.getString("sign");
            sign = URLDecoder.decode(sign, "UTF-8");
            FlightMatch flightMatch = JSONObject.toJavaObject(JSON.parseObject(data), FlightMatch.class);
            log.info("sign: " + sign + "data: " + data);
            String signPrivate = DragonSignature.rsaSign("data=" + data, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
            if (sign.equals(signPrivate)) {
                String flightState = "";
                switch (flightMatch.getFlightState()) {
                    case "计划":
                        flightState = "Plan";
                        break;
                    case "起飞":
                        flightState = "Take off";
                        break;
                    case "到达":
                        flightState = "Arrivals";
                        break;
                    case "延误":
                        flightState = "Delay";
                        break;
                    case "取消":
                        flightState = "Cancel";
                        break;
                    case "备降":
                        flightState = "Alternate";
                        break;
                    case "返航":
                        flightState = "Return";
                        break;
                    case "提前取消":
                        flightState = "Advance cancel";
                        break;
                }
                flightMatch.setFlightState(flightState);

                // 传入的航班对象
                Flight flight = new Flight();
                BeanUtils.copyProperties(flight, flightMatch);
                // 龙腾推送过来的航班信息为空，需要一直推送直到不为空
                if (flight == null) {
                    result.put("state", PushStatus.EMPTY.state());
                    result.put("info", PushStatus.EMPTY.info());
                    return JSON.toJSONString(result);
                }
                flight.setAirportCode(airportCode);
                flight.setUpdateUser(userId);

                // 从数据库查询出来的航班信息
                Flight queryFlight = flightMapper.isFlightExist(flight);
                if (queryFlight == null || queryFlight.getFlightId() == 0) {
                    throw new FlightException("没有找到对应的航班信息");
                } else {
                    Long flightId = queryFlight.getFlightId();
                    String fdId = queryFlight.getFdId(); // 爱飞云数据库的fdId
                    fdId = (fdId == null ? "-1" : fdId);
                    String fdId2 = flight.getFdId();    //传入参数的fdId2   主要是自己 第一次更新的时候 动态航班信息里面 fdId也可能是null
                    fdId2 = (fdId2 == null ? "-1" : fdId2);
                    if (Long.valueOf(fdId) <= Long.valueOf(fdId2)) {
                        flight.setFlightId(flightId);
                        FlightUpdateLog flightUpdateLog = new FlightUpdateLog();
                        String updateMessage = BeanCompareUtils.compareTwoBean(queryFlight, flight);
                        if (updateMessage != null) {
                            flightUpdateLog.setCreateUser(flight.getUpdateUser());
                            flightUpdateLog.setUpdateMessage(updateMessage);
                            flightUpdateLog.setOperatorName("非常准"); // 从非常准推送过来的数据,暂时写死
                            flightUpdateLog.setFlightId(flightId);
                            flightUpdateLog.setAirportCode(flight.getAirportCode());
                            flightUpdateLogMapper.insert(flightUpdateLog);
                        }
                        flightMapper.updateFlight(flight);
                    } else {
                        throw new FlightException("无需更新");
                    }
                    result.put("state", PushStatus.SUCCESS.state());
                    result.put("info", PushStatus.SUCCESS.info());
                    return JSON.toJSONString(result);
                }
            }else {
                result.put("state", PushStatus.INVALID_FAIL.state());
                result.put("info", PushStatus.INVALID_FAIL.info());
                return JSON.toJSONString(result);
            }
        } catch (FlightException e) {
            log.error(e.getMessage());
            result.put("state", PushStatus.REPEAT.state());
            result.put("info", PushStatus.REPEAT.info());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.put("state", PushStatus.ERROR.state());
            result.put("info", PushStatus.ERROR.info());
            return JSON.toJSONString(result);
        }
    }

    /**
     * 航班调度事件管理 - 新增or更新.
     * @param request
     * @return
     */
    public String saveOrUpdateFlightScheduleEvent(JSONObject request){
        try {
            String airportCode = request.getString("client_id");
            Long userId = request.getLong("user_id");
            JSONArray data = request.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                FlightScheduleEvent flightScheduleEvent = data.getJSONObject(i).toJavaObject(FlightScheduleEvent.class);
                flightScheduleEvent.setAirportCode(airportCode);
                if (flightScheduleEvent.getFlightScheduleEventId() == null) {
                    flightScheduleEvent.setCreateUser(userId);
                    flightScheduleEventMapper.insertSelective(flightScheduleEvent);
                } else {
                    flightScheduleEvent.setUpdateUser(userId);
                    flightScheduleEventMapper.updateByPrimaryKeySelective(flightScheduleEvent);
                }

                Map<String, Object> param = new HashedMap();
                param.put("airportCode", flightScheduleEvent.getAirportCode());
                param.put("userId", userId);
                param.put("flightScheduleEventId", flightScheduleEvent.getFlightScheduleEventId());
                FlightScheduleEvent flightScheduleEvent0 = flightScheduleEventMapper.selectByPrimaryKey(param);
                flightScheduleEvent0.setAirportCode(flightScheduleEvent.getAirportCode());
                flightScheduleEventMapper.updateByPrimaryKeySelective(flightScheduleEvent0);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 定制航班
     * @param request
     * @return
     */
    public String customFlight(JSONObject request) {
        try {
            String airportCode = request.getString("client_id");
            Long userId = request.getLong("user_id");
            Long flightId = request.getLong("flightId");
            Map<String, Object> result = new HashMap<>();

            // 根据ID查询航班信息
            Map<String, Object> params = new HashMap<>();
            params.put("flightId", flightId);
            params.put("airportCode", airportCode);
            Flight flight = flightMapper.selectByPrimaryKey(params);
            if (flight == null) {
                result.put("state", CustomStatus.EMPTY.state());
                result.put("info", CustomStatus.EMPTY.info());
                return JSON.toJSONString(result);
            }
            flight.setAirportCode(airportCode);
            flight.setUpdateUser(userId);

            ExchangeDragon exchangeDragon = new ExchangeDragon();
            exchangeDragon.setFlightDate(flight.getFlightDate());
            exchangeDragon.setFlightNo(flight.getFlightNo());
            exchangeDragon.setCreateUser(flight.getUpdateUser());
            exchangeDragon.setAirportCode(flight.getAirportCode());

            // 向龙腾定制航班信息
            String customResult = new String(HttpClientUtil.httpPostRequest("http://121.14.200.54:7072/FlightCenter/wcf/FlightWcfService.svc/CustomFlightNo", getParamsForCustomFlight(flight)).getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);

            // 解析返回值
            Integer crState;
            if (customResult != null) {
                JSONObject crObject = JSON.parseObject(customResult);
                crState = crObject.getInteger("State");

                exchangeDragon.setExchangeType((short) 2); // 2 表示定制
                exchangeDragon.setInvokeResult(Short.valueOf(String.valueOf(crState)));
                exchangeDragonMapper.insert(exchangeDragon);

                if (crState != null && crState == 1) {
                    // 定制成功
                    flight.setIsCustom(true);
                    flightMapper.updateFlightDirect(flight);

                    // 调用动态表接口
                    String fileMessage = new String(HttpClientUtil.httpPostRequest("http://121.14.200.54:7072/FlightCenter/wcf/FlightWcfService.svc/GetFlightByNo_Lg", getParamsForGetFlight(flight)).getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);

                    // 解析返回值
                    JSONObject jsonObject = JSON.parseObject(fileMessage);
                    JSONArray data = jsonObject.getJSONArray("Data");
                    JSONObject flightObject = data.getJSONObject(0);

                    // 生成操作日志
                    exchangeDragon.setExchangeType((short) 3); // 3 表示动态表
                    exchangeDragon.setInvokeResult(jsonObject.getShort("State"));
                    exchangeDragonMapper.insert(exchangeDragon);

                    // 得到最新的航班信息
                    Flight newFlight = new Flight();
                    DynamicFlight flightMatch = JSONObject.toJavaObject(flightObject, DynamicFlight.class);
                    BeanUtils.copyProperties(newFlight, flightMatch);
                    newFlight.setAirportCode(flight.getAirportCode());
                    newFlight.setUpdateUser(flight.getUpdateUser());

                    String fdId = flight.getFdId(); // 本地库的fdId
                    String fdId2 = flight.getFdId();    //传入参数的fdId2   主要是自己 第一次更新的时候 动态航班信息里面 fdId也可能是null

                    if (Long.valueOf(fdId) <= Long.valueOf(fdId2)) {
                        flight.setFlightId(flightId);
                        FlightUpdateLog flightUpdateLog = new FlightUpdateLog();
                        String updateMessage = BeanCompareUtils.compareTwoBean(flight, newFlight);
                        if (updateMessage != null) {
                            flightUpdateLog.setCreateUser(flight.getUpdateUser());
                            flightUpdateLog.setUpdateMessage(updateMessage);
                            flightUpdateLog.setOperatorName("非常准"); // 从非常准推送过来的数据,暂时写死
                            flightUpdateLog.setFlightId(flightId);
                            flightUpdateLog.setAirportCode(flight.getAirportCode());
                            flightUpdateLogMapper.insert(flightUpdateLog);
                        }
                        flightMapper.updateFlight(flight);
                    } else {
                        throw new FlightException("无需更新");
                    }

                    result.put("state", CustomStatus.SUCCESS.state());
                    result.put("info", CustomStatus.SUCCESS.info());
                    return JSON.toJSONString(result);
                }
            }
            // 定制失败
            result.put("state", CustomStatus.FAIL.state());
            result.put("info", CustomStatus.FAIL.info());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.debug(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("state", CustomStatus.ERROR.state());
            result.put("info", "定制航班异常：" + e.getMessage());
            return JSON.toJSONString(result);
        }
    }

    /**
     * 获取航班日志
     * @param request
     * @return
     * @test
     */
    public String flightLog(JSONObject request){
        LZResult<List<FlightUpdateLog>> result = new LZResult<>();
        try {
            String airportCode = request.getString("client_id");
            Long flightId = request.getLong("flightId");
            List<FlightUpdateLog> list = flightUpdateLogMapper.selectByFlightIdForShow(flightId, airportCode);
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

    /**
     * 更新航班信息
     * @param request
     * @return
     */
    public String updateFlightInfo(JSONObject request){
        try {
            Flight flight = request.getObject("flight", Flight.class);
            String airportCode = request.getString("client_id");
            Long userId = request.getLong("user_id");
            flight.setUpdateUser(userId);
            flight.setAirportCode(airportCode);
            Long flightId = flight.getFlightId();
            if (flightId == null || "".equals(flightId)) {
                return JSON.toJSONString(LXResult.build(LZStatus.BAD_REQUEST.value(), LZStatus.BAD_REQUEST.display()));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("flightId", flightId);
            params.put("airportCode", airportCode);
            updateFlight(queryFlightById(flightId,airportCode),flight);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 具体的航班更新方法
     * @param flightOld
     * @param flightNew
     */
    public void updateFlight(Flight flightOld,Flight flightNew){
        // a json string about flightOld and flightNew object comparison
        String updateMessage;
        try {
            updateMessage = BeanCompareUtils.compareTwoBean(flightOld, flightNew);
            // 考虑到需要记录的更新字段不包括全部航班字段，虽然没有记录航班更新信息，但任然需要更新航班信息
            if (updateMessage != null) {
                FlightUpdateLog flightUpdateLog = new FlightUpdateLog();
                flightUpdateLog.setUpdateMessage(updateMessage);
                flightUpdateLog.setCreateUser(flightNew.getUpdateUser());
                flightUpdateLog.setFlightId(flightNew.getFlightId());
                flightUpdateLog.setAirportCode(flightNew.getAirportCode());
                flightUpdateLogMapper.insert(flightUpdateLog);
            }
            flightMapper.updateFlight(flightNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * query flight by flightId
     * @param id
     * @param airportCode
     * @return
     */
    public Flight queryFlightById(Long id, String airportCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("flightId", id);
        params.put("airportCode", airportCode);
        return flightMapper.selectByPrimaryKey(params);
    }

    /**
     * 定制航班 所需参数
     *
     * @param flight
     * @return
     * @throws DragonApiException
     */
    private Map<String, Object> getParamsForCustomFlight(Flight flight) throws DragonApiException {
        Map<String, String> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("date", sdf.format(flight.getFlightDate()));
        params.put("fnum", flight.getFlightNo());
        params.put("dep", flight.getFlightDepcode());
        params.put("arr", flight.getFlightArrcode());
        params.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        Map<String, Object> p = new HashMap<>();
        p.put("date", sdf.format(flight.getFlightDate()));
        p.put("fnum", flight.getFlightNo());
        p.put("dep", flight.getFlightDepcode());
        p.put("arr", flight.getFlightArrcode());
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        return p;
    }

    /**
     * 航班动态信息接口 所需参数
     *
     * @param flight
     * @return
     * @throws DragonApiException
     */
    private Map<String, Object> getParamsForGetFlight(Flight flight) throws DragonApiException {
        Map<String, String> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("date", sdf.format(flight.getFlightDate()));
        params.put("fnum", flight.getFlightNo());
        params.put("dep", flight.getFlightDepcode());
        params.put("arr", flight.getFlightArrcode());
        params.put("lg", Dictionary.LG);
        params.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        Map<String, Object> p = new HashMap<>();
        p.put("date", sdf.format(flight.getFlightDate()));
        p.put("fnum", flight.getFlightNo());
        p.put("dep", flight.getFlightDepcode());
        p.put("arr", flight.getFlightArrcode());
        p.put("lg", Dictionary.LG);
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        return p;
    }

}
