package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
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

import javax.persistence.Transient;
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
    private PassengerPoMapper passengerPoMapper;

    @Autowired
    private PassengerTicketPoMapper passengerTicketPoMapper;

    @Autowired
    private IbeService ibeService;

    /**
     * 查询航班信息 -- 时刻表
     * @param request 参数
     * @return 航班中心结果集
     */
    @Transient
    public String queryFlightInfo(JSONObject request) {
        try {
            // get args
            String flightNo = request.getString("flightNo").toUpperCase();
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

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            if (StringUtils.isBlank(depAirportCode) && StringUtils.isBlank(arrAirportCode)) {
                FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
                // 从本地查询
                List<FlightPo> result = flightPoMapper.selectByDateAndNo(flightPo);
                // 本地不存在
                if (result == null || result.size() == 0) {
                    re = getFlightPosByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo);
                    // 查询到结果 插入
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                        }
                    }
                } else {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
                return JSON.toJSONString(re);
            } else {
                FlightCenterResult<FlightPo> re = new FlightCenterResult<>();
                // 添加目的地/出发地 三字码
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
                // 从本地查询
                FlightPo result = flightPoMapper.select(flightPo);
                if (result == null) {
                    re = getFlightPoByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
                } else {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
                return JSON.toJSONString(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
            return JSON.toJSONString(re);
        }
    }

    /**
     * 查询航班信息 -- 动态表
     * @param request
     * @return 航班中心结果集
     */
    @Transient
    public String queryDynamicFlightInfo(JSONObject request) {
        FlightCenterResult<FlightPo> re = new FlightCenterResult<>();

        try {
            // get args
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyArgs(flightNo, depAirportCode, arrAirportCode);

            if (!"OK".equals(s)) {
                return s;
            }

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            // 先从本地查询
            FlightPo localFlight = flightPoMapper.select(flightPo);
            // 已定制 直接返回
            if (localFlight != null && localFlight.getIsCustom() == 1) {
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(localFlight);
            } else {
                re = getFlightPoByIbeSource(DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
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
        // get args
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

        try {
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate,"yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);

            FlightPo localFlight = flightPoMapper.select(flightPo);

            if (localFlight != null) {
                // 本地存在 未定制 -- 定制
                if (localFlight.getIsCustom() != 1) {
//                    ibeService.executeCustomFlight(flightNo, DateUtils.stringToDate(depDate,"yyyy-MM-dd"));
                    flightPoMapper.updateIsCustom(flightPo);
                }
                // 插入定制信息
                CustomFlightPo customFlightPo = new CustomFlightPo();
                customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                customFlightPo.setCustomerId(customerPo.getCustomerId());
                customFlightPo.setFlightId(localFlight.getFlightId());
                customFlightPoMapper.insert(customFlightPo);

                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(null);
            } else {
                re = getFlightPoCustomByIbeSource(customerPo, DateUtils.stringToDate(depDate,"yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
            }
        }catch (Exception e){
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
            flightPoMapper.insert(flightPo);
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
//                ibeService.executeCustomFlight(flightNo, depDate);
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

    /**
     * 根据客票号查询旅客/航班信息
     * @param request
     * @return
     */
    @Transient
    public String queryPassengerByTickNo(JSONObject request){
        FlightCenterResult<PassengerTicketPojo> re = new FlightCenterResult<>();
        // get args
        String tickNo = request.getString("tickNo");
        if (log.isInfoEnabled()) {
            log.info("【参数   tickNo:" + tickNo +" 】");
        }
        try {
            // look for local
            PassengerTicketPojo passengerTicketPojo = passengerTicketPoMapper.selectPassengerTicketPojo(tickNo);
            // get from remote
            if (passengerTicketPojo == null) {
                re = ibeService.queryPassengerByTickNo(tickNo);
                PassengerTicketPojo data = re.getData();
                PassengerPo lPassengerPo = passengerPoMapper.selectByName(data.getPassengerName());
                if (lPassengerPo == null) {
                    PassengerPo passengerPo = new PassengerPo();
                    passengerPo.setPassengerName(data.getPassengerName());
                    passengerPo.setInfantBirthday(data.getInfantBirthday());
                    passengerPoMapper.insert(passengerPo);
                }
                data.setPassengerId(lPassengerPo.getPassengerId());
                passengerTicketPoMapper.insertPassengerTicketPojo(data);
            }else {
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(passengerTicketPojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

}
