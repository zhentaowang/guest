package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.common.util.JedisUtils;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.po.CustomFlightPo;
import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.po.PassengerPo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.AttributeList;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FlightService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/31 14:00 
 * @author tiecheng
 */
@Service
public class FlightService {

    private static final Log log = LogFactory.getLog(FlightService.class);

    @Autowired
    private FlightPoMapper flightPoMapper;

    @Autowired
    private CustomFlightPoMapper customFlightPoMapper;

    @Autowired
    private CustomerPoMapper customerPoMapper;

    @Autowired
    private IbeService ibeService;

    /**
     * 查询航班信息 -- 时刻表
     * @param request 参数
     * @return 航班中心结果集
     */
    @Transient
    public String queryFlightInfo2(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        boolean isTodayFlight = false;
        List<FlightPo> result;
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【 ************ queryFlightInfo方法接收到的参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " ************ 】");
            }

            String s = verifyFlightNo(flightNo);

            if (!"OK".equals(s)) {
                return s;
            }

            flightNo = flightNo.trim().toUpperCase();
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate,"yyyy-MM-dd"));

            // 当日航班，直接去查询
            if (DateUtils.stringToDate(depDate,"yyyy-MM-dd").equals(DateUtils.getDate("yyyy-MM-dd"))) {
                isTodayFlight = true;
            }

            if (StringUtils.isBlank(depAirportCode) && StringUtils.isBlank(arrAirportCode)) {
                // 从本地查询
                result = flightPoMapper.selectByDateAndNo(flightPo);

                // 本地不存在
                if (result == null || result.size() == 0) {
                    re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                    // 查询到结果 插入航班中心
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                        }
                    }
                } else {
                    if (isTodayFlight) {
                        re = ibeService.queryFlightNo(flightNo);
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.updateByCondition(po);
                        }
                        result = flightPoMapper.selectByDateAndNo(flightPo);
                    }
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
            } else {
                depAirportCode = depAirportCode.trim().toUpperCase();
                arrAirportCode = arrAirportCode.trim().toUpperCase();
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
                // 从本地查询
                FlightPo flightLocal = flightPoMapper.select(flightPo);
                List<FlightPo> list = new ArrayList<>();
                if (flightLocal == null) {
                    FlightCenterResult<FlightPo> flightPoByIbeSource = getFlightPoByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
                    re.setMessage(flightPoByIbeSource.getMessage());
                    re.setState(flightPoByIbeSource.getState());
                    list.add(flightPoByIbeSource.getData());
                    re.setData(list);
                } else {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    list.add(flightLocal);
                    re.setData(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    @Transient
    public String queryFlightInfo(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        // 是否是本日航班
        boolean isTodayFlight = false;
        // 是否有出发地和目的地的三字码
        boolean isAirportCode = false;
        // 查询的结果数据
        List<FlightPo> result = null;
        // 本地航班
        FlightPo flightLocal = null;
        //
        boolean resultExist = true;
        try {
            // 获取参数
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【 ************ queryFlightInfo方法接收到的参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " ************ 】");
            }

            String s = verifyFlightNo(flightNo);

            if (!"OK".equals(s)) {
                return s;
            }

            flightNo = flightNo.trim().toUpperCase();
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            if (StringUtils.isNoneBlank(depAirportCode) && StringUtils.isNoneBlank(arrAirportCode)) {
                isAirportCode = true;
                depAirportCode = depAirportCode.trim().toUpperCase();
                arrAirportCode = arrAirportCode.trim().toUpperCase();
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
            }

            if (isAirportCode) {
                flightLocal = flightPoMapper.select(flightPo);
            } else {
                result = flightPoMapper.selectByDateAndNo(flightPo);
            }

            if (DateUtils.stringToDate(depDate, "yyyy-MM-dd").equals(DateUtils.getDate("yyyy-MM-dd"))) {
                isTodayFlight = true;
                re = ibeService.queryFlightNo(flightNo);
                if (re.getData() == null) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                    return JSON.toJSONString(re);
                }
            }

            if (isTodayFlight && isAirportCode && flightLocal == null) {
                boolean isExist = false;
                for (FlightPo po : re.getData()) {
                    flightPoMapper.insert(po);
                    if (depAirportCode.equals(po.getDepAirportCode()) && arrAirportCode.equals(po.getArrAirportCode())) {
                        isExist = true;
                        result = new ArrayList<>();
                        result.add(po);
                    }
                }
                if (!isExist) {
                    resultExist = false;
                }
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(result);
            }

            // 当日航班 有三字码 本地存在
            if (isTodayFlight && isAirportCode && flightLocal != null) {
                boolean isExist = false;
                for (FlightPo po : re.getData()) {
                    flightPoMapper.updateByCondition(po);
                    if (depAirportCode.equals(po.getDepAirportCode()) && arrAirportCode.equals(po.getArrAirportCode())) {
                        isExist = true;
                        result = new ArrayList<>();
                    }
                }
                if (!isExist) {
                    resultExist = false;
                }
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                result.add(flightPoMapper.select(flightPo));
                re.setData(result);
            }

            // 当日航班 无三字码 本地不存在
            if (isTodayFlight && !isAirportCode && result == null) {
                for (FlightPo po : re.getData()) {
                    flightPoMapper.insert(po);
                }
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(re.getData());
            }

            // 当日航班 无三字码 本地存在
            if (isTodayFlight && !isAirportCode && result != null) {
                for (FlightPo po : re.getData()) {
                    flightPoMapper.updateByCondition(po);
                }
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                result = flightPoMapper.selectByDateAndNo(flightPo);
                re.setData(result);
            }

            if (!isTodayFlight && isAirportCode && flightLocal == null) {
                re = ibeService.queryFlightNoByDate(flightNo, depDate);
                if (re.getData() == null) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                } else {
                    boolean isExist = true;
                    for (FlightPo po : re.getData()) {
                        flightPoMapper.insert(po);
                        if (depAirportCode.equals(po.getDepAirportCode()) && arrAirportCode.equals(po.getArrAirportCode())) {
                            isExist = false;
                            result = new ArrayList<>();
                            result.add(po);
                        }
                    }
                    if (!isExist) {
                        resultExist = false;
                    }
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
            }

            if (!isTodayFlight && isAirportCode && flightLocal != null) {
                result = new ArrayList<>();
                result.add(flightLocal);
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(result);
            }

            if (!isTodayFlight && !isAirportCode && result == null) {
                re = ibeService.queryFlightNoByDate(flightNo, depDate);
                if (re.getData() == null) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                } else {
                    for (FlightPo po : re.getData()) {
                        flightPoMapper.insert(po);
                    }
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
            }

            if (!isTodayFlight && !isAirportCode && result != null) {
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(result);
            }

            if (!resultExist) {
                re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                re.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 查询航班信息 -- 动态表
     * @param request
     * @return 航班中心结果集
     */
    @Transient
    public String queryDynamicFlightInfo2(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyFlightNo(flightNo);

            if (!"OK".equals(s)) {
                return s;
            }

            flightNo = flightNo.trim().toUpperCase();

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            boolean isCustom =false;

            if (StringUtils.isBlank(depAirportCode) && StringUtils.isBlank(arrAirportCode)) {
                // 从本地查询
                List<FlightPo> result = flightPoMapper.selectByDateAndNo(flightPo);
                // 本地不存在
                if (result == null || result.size() == 0) {
                    re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                    // 查询到结果 插入航班中心
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                        }
                    }
                } else {
                    for (FlightPo flight : result) {
                        if (flight.getIsCustom() == 1) {
                            isCustom = true;
                            break;
                        }
                    }
                    if(isCustom){
                        re.setMessage(FlightCenterStatus.SUCCESS.display());
                        re.setState(FlightCenterStatus.SUCCESS.value());
                        re.setData(result);
                    }else {
                        re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                        // 查询到结果 更新到航班中心
                        if (re.getData() != null) {
                            for (FlightPo po : re.getData()) {
                                flightPoMapper.update(po);
                            }
                        }
                    }
                }
            } else {
                depAirportCode = depAirportCode.trim().toUpperCase();
                arrAirportCode = arrAirportCode.trim().toUpperCase();
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
                // 从本地查询
                FlightPo result = flightPoMapper.select(flightPo);
                List<FlightPo> list = new ArrayList<>();
                if(result !=null && result.getIsCustom() == 1){
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    list.add(result);
                    re.setData(list);
                }else {
                    FlightCenterResult<FlightPo> flightPoByIbeSource = getFlightPoByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
                    re.setMessage(flightPoByIbeSource.getMessage());
                    re.setState(flightPoByIbeSource.getState());
                    list.add(flightPoByIbeSource.getData());
                    re.setData(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    @Transient
    public String queryDynamicFlightInfo(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        boolean isAirportCode = false;
        boolean isCustom = false;
        List<FlightPo> result = null;
        FlightPo flightLocal = null;
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyFlightNo(flightNo);

            if (!"OK".equals(s)) {
                return s;
            }

            flightNo = flightNo.trim().toUpperCase();
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            // 先根据是否带三字码 得到查询的条件
            if (StringUtils.isNoneBlank(depAirportCode) && StringUtils.isNoneBlank(arrAirportCode)) {
                isAirportCode = true;
                depAirportCode = depAirportCode.trim().toUpperCase();
                arrAirportCode = arrAirportCode.trim().toUpperCase();
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
            }

            if (isAirportCode) {
                boolean isExist = false;
                result = new ArrayList<>();
                flightLocal = flightPoMapper.select(flightPo);
                if (flightLocal == null) {
                    re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                            if (depAirportCode.equals(po.getDepAirportCode()) && arrAirportCode.equals(po.getArrAirportCode())) {
                                isExist = true;
                                result.add(po);
                            }
                        }
                    } else {
                        re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                        re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                        re.setData(null);
                        return JSON.toJSONString(re);
                    }
                } else {
                    if (flightLocal.getIsCustom() == 1) {
                        result.add(flightLocal);
                        re.setMessage(FlightCenterStatus.SUCCESS.display());
                        re.setState(FlightCenterStatus.SUCCESS.value());
                        re.setData(result);
                    } else {
                        re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                        if (re.getData() != null) {
                            for (FlightPo po : re.getData()) {
                                flightPoMapper.updateByCondition(po);
                                if (depAirportCode.equals(po.getDepAirportCode()) && arrAirportCode.equals(po.getArrAirportCode())) {
                                    isExist = true;
                                    result.add(po);
                                }
                            }
                            // 更新后 再从数据库拿数据 不直接返回数据
                            flightLocal = flightPoMapper.select(flightPo);
                            result.add(flightLocal);
                        } else {
                            re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                            re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                            re.setData(null);
                            return JSON.toJSONString(re);
                        }
                    }
                }
                if (!isExist) {
                    re.setState(FlightCenterStatus.NONE_RESULT.value());
                    re.setMessage(FlightCenterStatus.NONE_RESULT.display());
                    re.setData(null);
                    return JSON.toJSONString(re);
                }
            } else {
                result = flightPoMapper.selectByDateAndNo(flightPo);
                if (result == null) { // 本地无数据
                    re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                        }
                    } else {
                        re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                        re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                        re.setData(null);
                        return JSON.toJSONString(re);
                    }
                } else {
                    for (FlightPo po : result) {
                        if (po.getIsCustom() == 1) {
                            isCustom = true;
                        } else {
                            isCustom = false;
                        }
                    }
                    if (isCustom) {
                        re.setMessage(FlightCenterStatus.SUCCESS.display());
                        re.setState(FlightCenterStatus.SUCCESS.value());
                        re.setData(result);
                    } else {
                        re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                        if (re.getData() != null) {
                            for (FlightPo po : re.getData()) {
                                flightPoMapper.updateByCondition(po);
                            }
                        } else {
                            re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                            re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                            re.setData(null);
                            return JSON.toJSONString(re);
                        }
                        result = flightPoMapper.selectByDateAndNo(flightPo);
                        re.setMessage(FlightCenterStatus.SUCCESS.display());
                        re.setState(FlightCenterStatus.SUCCESS.value());
                        re.setData(result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 定制航班
     * @param request 参数
     * @return 航班中心结果集
     */
    @Transient
    public String customFlight(JSONObject request){
        FlightCenterResult<FlightPo> re = new FlightCenterResult();
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【 参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyArgs(flightNo, depAirportCode, arrAirportCode);

            if (!"OK".equals(s)) {
                return s;
            }

            String sysCode = request.getString("sysCode");
            CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            List<FlightPo> flightPos = flightPoMapper.selectByDateAndNo(flightPo);

            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);

            FlightPo localFlight = null;

            for (FlightPo flight : flightPos) {
                if (depAirportCode.equals(flight.getDepAirportCode())&& arrAirportCode.equals(flight.getArrAirportCode())) {
                    localFlight = flight;
                }
            }

            // TODO 根据实际数据需要酌情添加条件
            if ("到达".equals(localFlight.getFlightState())) {
                re.setState(FlightCenterStatus.UNABLE_CUSTOM.value());
                re.setMessage(FlightCenterStatus.UNABLE_CUSTOM.display());
                re.setData(null);
                return JSON.toJSONString(re);
            }

            if (localFlight != null) {
                // 本地存在 未定制 -- 定制
                if (localFlight.getIsCustom() != 1) {
                    flightPoMapper.updateIsCustom(flightPo);
                    ibeService.executeCustomFlight(flightNo, DateUtils.stringToDate(depDate,"yyyy-MM-dd"));
                    for (FlightPo flight : flightPos) {
                        flightPoMapper.updateIsCustom(flight);
                    }
                }
                CustomFlightPo localCustomFlight = customFlightPoMapper.selectByCustomerIdAndFlightId(customerPo.getCustomerId(), localFlight.getFlightId());
                if (localCustomFlight == null) {
                    // 插入定制信息
                    CustomFlightPo customFlightPo = new CustomFlightPo();
                    customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                    customFlightPo.setCustomerId(customerPo.getCustomerId());
                    customFlightPo.setFlightId(localFlight.getFlightId());
                    customFlightPoMapper.insert(customFlightPo);
                }
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(null);
            } else {
                re = getFlightPoCustomByIbeSource(customerPo, DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setState(FlightCenterStatus.ERROR.value());
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 从IBE查询航班信息集合
     * @param depDate  起飞时间
     * @param flightNo 航班日期
     * @return FlightCenterResult 航班中心结果集
     * @throws Exception
     */
    private FlightCenterResult<List<FlightPo>> getFlightPosByIbeSource(Date depDate, String flightNo) throws Exception {
        Date today = DateUtils.getDate("yyyy-MM-dd");
        FlightCenterResult<List<FlightPo>> result= new FlightCenterResult<>();
        if(depDate == null || depDate.before(today)){
            result.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
            result.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
            result.setData(null);
        }
        if (depDate.after(today)) {
            result = ibeService.queryFlightNoByDate(flightNo, DateUtils.dateToString(depDate, "yyyy-MM-dd"));
        }
        if (depDate.equals(today)) {
            result = ibeService.queryFlightNo(flightNo);
        }
        return result;
    }

    /**
     * 从IBE查询航班信息集合
     * @param depDate
     * @param flightNo
     * @param depAirportCode
     * @param arrAirportCode
     * @return
     */
      private FlightCenterResult<FlightPo> getFlightPoByIbeSource(Date depDate, String flightNo,String depAirportCode,String arrAirportCode,boolean isCustom) throws Exception {
        FlightCenterResult<List<FlightPo>> re = getFlightPosByIbeSource(depDate, flightNo);
        FlightCenterResult<FlightPo> result = new FlightCenterResult<>();
        boolean isExist = false;
        for (FlightPo flightPo : re.getData()) {
            if (depAirportCode.equals(flightPo.getDepAirportCode()) && arrAirportCode.equals(flightPo.getArrAirportCode())) {
                isExist = true;
                if(isCustom){
                    flightPo.setIsCustom((short)1);
                }
                result.setState(FlightCenterStatus.SUCCESS.value());
                result.setMessage(FlightCenterStatus.SUCCESS.display());
                result.setData(flightPo);
            }
            if (!isExist) {
                result.setState(FlightCenterStatus.NONE_FLIGHT.value());
                result.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                result.setData(null);
            }
            FlightPo lcoalFlight = flightPoMapper.select(flightPo);
            if(lcoalFlight == null){
                flightPoMapper.insert(flightPo);
            }else {
                flightPoMapper.update(flightPo);
            }
        }
        return result;
    }

    private FlightCenterResult<FlightPo> getFlightPoCustomByIbeSource(CustomerPo customerPo,Date depDate, String flightNo,String depAirportCode,String arrAirportCode) throws Exception {
        FlightCenterResult<List<FlightPo>> re = getFlightPosByIbeSource(depDate, flightNo);
        FlightCenterResult<FlightPo> result = new FlightCenterResult<>();
        boolean isExist = false;
        for (FlightPo flightPo : re.getData()) {
            // 存在航班信息
            if (depAirportCode.equals(flightPo.getDepAirportCode()) && arrAirportCode.equals(flightPo.getArrAirportCode())) {
                isExist = true;
                flightPo.setIsCustom((short)1);
                flightPoMapper.insert(flightPo);
                CustomFlightPo customFlightPo = new CustomFlightPo();
                customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                customFlightPo.setCustomerId(customerPo.getCustomerId());
                customFlightPo.setFlightId(flightPo.getFlightId());
                customFlightPoMapper.insert(customFlightPo);
                ibeService.executeCustomFlight(flightNo, depDate);
                result.setState(FlightCenterStatus.SUCCESS.value());
                result.setMessage(FlightCenterStatus.SUCCESS.display());
                result.setData(flightPo);
            }else {
                flightPoMapper.insert(flightPo);
            }
            if (!isExist) {
                result.setState(FlightCenterStatus.NONE_FLIGHT.value());
                result.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                result.setData(null);
            }
        }
        return result;
    }

    private FlightCenterResult<FlightPo> getFlightPoByIbeSource(Date depDate, String flightNo,String depAirportCode,String arrAirportCode) throws Exception {
        return getFlightPoByIbeSource(depDate, flightNo, depAirportCode, arrAirportCode, false);
    }

    private String verifyArgs(String flightNo,String depAirportCode,String arrAirportCode){
        String result;

        result = verifyFlightNo(flightNo);

        result = result.equals("OK") ? "OK" : result;

        if(!"OK".equals(result)){
            return result;
        }

        result = verifyDepAirportCode(depAirportCode);

        result = result.equals("OK") ? "OK" : result;

        if(!"OK".equals(result)){
            return result;
        }

        result = verifyArrAirportCode(arrAirportCode);

        result = result.equals("OK") ? "OK" : result;

        if(!"OK".equals(result)){
            return result;
        }

        return result;
    }

    private String verifyFlightNo(String flightNo) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(flightNo)) {
            re.setMessage(FlightCenterStatus.NONE_FLIGHT_NO.display());
            re.setState(FlightCenterStatus.NONE_FLIGHT_NO.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

    private String verifyDepAirportCode(String depAirportCode) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(depAirportCode)) {
            re.setMessage(FlightCenterStatus.NONE_DEP_AIRPORT_CODE.display());
            re.setState(FlightCenterStatus.NONE_DEP_AIRPORT_CODE.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

    private String verifyArrAirportCode(String arrAirportCode) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(arrAirportCode)) {
            re.setMessage(FlightCenterStatus.NONE_ARR_AIRPORT_CODE.display());
            re.setState(FlightCenterStatus.NONE_ARR_AIRPORT_CODE.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

}
