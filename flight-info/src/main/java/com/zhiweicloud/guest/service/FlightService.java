/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.sign.DragonApiException;
import com.dragon.sign.DragonSignature;
import com.zhiweicloud.guest.common.BeanCompareUtils;
import com.zhiweicloud.guest.common.Dictionary;
import com.zhiweicloud.guest.common.FlightException;
import com.zhiweicloud.guest.mapper.AirportInfoMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.FlightScheduleEventMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightScheduleEvent;
import org.apache.commons.collections.map.HashedMap;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2017-02-04 11:09
 */
@Service
public class FlightService {

    private static final Log log = LogFactory.getLog(FlightService.class);

    @Autowired
    private ExchangeDragonMapper exchangeDragonMapper;

    @Autowired
    private AirportInfoMapper airportInfoMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightScheduleEventMapper flightScheduleEventMapper;

    @Autowired
    private FlightUpdateLogMapper flightUpdateLogMapper;

    public List<Map<String, String>> flightInfoDropdownList(String airportNameOrCode) {
        return airportInfoMapper.queryFlightInfoDropdownList(airportNameOrCode);
    }

    public List<Map<String, String>> flightNoDropdownList(String flightNo, String airportCode) {
        return airportInfoMapper.queryFlightNoDropdownList(flightNo, airportCode);
    }

    /**
     * 更新航班信息 -- 来自龙腾动态数据
     *
     * @param flight      需要更新的航班对象
     * @throws FlightException
     */
    public void updateFlight(Flight flight) throws Exception {
        Flight queryFlight = flightMapper.isFlightExist(flight);    // 从数据库查询出来的航班信息
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
                    flightUpdateLog.setOperatorName("非常准"); // 从非常准推送过来的数据
                    flightUpdateLog.setFlightId(flightId);
                    flightUpdateLog.setAirportCode(flight.getAirportCode());
                    flightUpdateLogMapper.insert(flightUpdateLog);
                    flightMapper.updateFlight(flight);
                }
            } else {
                throw new FlightException("无需更新");
            }
        }
    }

    /**
     * 查询航班日志
     *
     * @param flightId    航班ID
     * @param airportCode 客户端标识
     * @return
     * @throws Exception
     */
    public List<FlightUpdateLog> selectFlightLog(Long flightId,String airportCode) throws Exception{
        return flightUpdateLogMapper.selectByFlightIdForShow(flightId, airportCode);
    }

    public void saveOrUpdateFlightScheduleEvent(FlightScheduleEvent flightScheduleEvent, Long userId) throws Exception {

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

    /**
     * 根据航班ID查询航班信息
     *
     * @param id          航班id
     * @param airportCode 客户端标识
     * @return
     */
    public Flight queryFlightById(Long id, String airportCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("flightId", id);
        params.put("airportCode", airportCode);
        return flightMapper.selectByPrimaryKey(params);
    }

    /**
     * 得到航班信息 -- 时刻表(1)
     *
     * @param fnum        航班号
     * @param date        航班日期
     * @param airportCode 客户端标识
     * @param userId      用户ID
     * @return
     * @throws UnsupportedEncodingException
     * @throws DragonApiException
     * @throws ParseException
     */
    public String getFlightInfo(String fnum, String date, String airportCode, Long userId) throws UnsupportedEncodingException, DragonApiException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("fnum", fnum);
        params.put("lg", Dictionary.LG);
        params.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        params.put("sign", sign);
        Map<String, Object> p = new HashMap<>();
        p.put("date", date);
        p.put("fnum", fnum);
        p.put("lg", Dictionary.LG);
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        String ret = HttpClientUtil.httpPostRequest("http://121.14.200.54:7072/FlightCenter/wcf/FlightWcfService.svc/GetFlightInfo_Lg", p);
        // 生成操作日志
        ExchangeDragon exchangeDragon = new ExchangeDragon();
        exchangeDragon.setFlightDate(simpleDateFormat.parse(date));
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
        return new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
    }

    /**
     * 向龙腾定制航班信息 -- 定制(2)
     *
     * @param flight 航班对象
     * @return
     * @throws DragonApiException
     * @throws UnsupportedEncodingException
     */
    public Integer customDragon(Flight flight) throws DragonApiException, UnsupportedEncodingException {
        log.info("FlightService customDragon 定制龙腾信息");
        // 调用定制航班信息接口
        String customResult = new String(HttpClientUtil.httpPostRequest("http://121.14.200.54:7072/FlightCenter/wcf/FlightWcfService.svc/CustomFlightNo", getParamsForCustomFlight(flight)).getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
        // 解析返回值
        Integer crState = null;
        if (customResult != null) {
            JSONObject crObject = JSON.parseObject(customResult);
            crState = crObject.getInteger("State");
        }
        log.info("FlightService customDragon 生成操作日志");
        // 生成操作日志
        ExchangeDragon exchangeDragon = new ExchangeDragon();
        exchangeDragon.setFlightDate(flight.getFlightDate());
        exchangeDragon.setFlightNo(flight.getFlightNo());
        exchangeDragon.setExchangeType((short) 2); // 2 表示定制
        exchangeDragon.setCreateUser(flight.getUpdateUser());
        exchangeDragon.setInvokeResult(Short.valueOf(String.valueOf(crState)));
        exchangeDragon.setAirportCode(flight.getAirportCode());
        exchangeDragonMapper.insert(exchangeDragon);
        return crState;
    }

    /**
     * 得到航班实时信息 -- 动态表(3)
     * 对于某个航班，定制以后，查询动态表是从龙腾数据库查询出来的，不用再调用非常准
     *
     * @param flight 航班对象
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws DragonApiException
     * @throws UnsupportedEncodingException
     */
    public Flight getFlightDynamic(Flight flight) throws InvocationTargetException, IllegalAccessException, DragonApiException, UnsupportedEncodingException {
        // 调用动态表接口
        String fileMessage = new String(HttpClientUtil.httpPostRequest("http://121.14.200.54:7072/FlightCenter/wcf/FlightWcfService.svc/GetFlightByNo_Lg", getParamsForGetFlight(flight)).getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
        // 解析返回值
        JSONObject jsonObject = JSON.parseObject(fileMessage);
        JSONArray data = jsonObject.getJSONArray("Data");
        JSONObject flightObject = data.getJSONObject(0);
        // 生成操作日志
        ExchangeDragon exchangeDragon = new ExchangeDragon();
        exchangeDragon.setFlightDate(flight.getFlightDate());
        exchangeDragon.setFlightNo(flight.getFlightNo());
        exchangeDragon.setExchangeType((short) 3); // 3 表示动态表
        exchangeDragon.setCreateUser(flight.getUpdateUser());
        exchangeDragon.setInvokeResult(jsonObject.getShort("State"));
        exchangeDragon.setAirportCode(flight.getAirportCode());
        exchangeDragonMapper.insert(exchangeDragon);
        // 得到最新的航班信息
        Flight newFlight = new Flight();
        DynamicFlight flightMatch = JSONObject.toJavaObject(flightObject, DynamicFlight.class);
        BeanUtils.copyProperties(newFlight, flightMatch);
        newFlight.setAirportCode(flight.getAirportCode());
        newFlight.setUpdateUser(flight.getUpdateUser());
        return newFlight;
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
