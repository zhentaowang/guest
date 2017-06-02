package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.common.util.HttpClientUtils;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.po.CustomFlightPo;
import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.po.PassengerPo;
import com.zhiweicloud.guest.pojo.CustomFlightPojo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            String flightNo = request.getString("flightNo");
            Date depDate = request.getDate("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(depDate);

            if (StringUtils.isBlank(depAirportCode) && StringUtils.isBlank(arrAirportCode)) {
                FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();

                if (log.isInfoEnabled()) {
                    log.info("【参数   flightNo:" + flightNo + " depDate:" + depDate + "】");
                }
                // query local
                List<FlightPo> result = flightPoMapper.selectByDateAndNo(flightPo);
                if (result == null || result.size() == 0) {
                    re = getFlightPosByIbeSource(depDate, flightNo);
                    if (re.getData() != null) {
                        for (FlightPo po : re.getData()) {
                            flightPoMapper.insert(po);
                        }
                    }
                }else {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(result);
                }
                return JSON.toJSONString(re);
            } else {
                FlightCenterResult<FlightPo> re = new FlightCenterResult<>();

                if (log.isInfoEnabled()) {
                    log.info("【参数   flightNo:" + flightNo + "; depDate:" + depDate + "; depAirportCode:" + depAirportCode + "; arrAirportCode" + arrAirportCode + " 】");
                }
                // add args
                flightPo.setDepAirportCode(depAirportCode);
                flightPo.setArrAirportCode(arrAirportCode);
                // query local
                FlightPo result = flightPoMapper.select(flightPo);
                if (result == null) {
                    re = getFlightPoByIbeSource(depDate, flightNo, depAirportCode, arrAirportCode);
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
            Date depDate = request.getDate("depDate");
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【参数   flightNo:" + flightNo + "; depDate:" + depDate + "; depAirportCode:" + depAirportCode + "; arrAirportCode" + arrAirportCode + " 】");
            }

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(depDate);
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            // query local
            FlightPo localFlight = flightPoMapper.select(flightPo);
            // has custom
            if (localFlight != null && localFlight.getIsCustom() == 1) {
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(localFlight);
            } else {
                re = getFlightPoByIbeSource(depDate, flightNo, depAirportCode, arrAirportCode);
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
        Date depDate = request.getDate("depDate");// 出发日期 即航班号
        String depAirportCode = request.getString("depAirportCode");
        String arrAirportCode = request.getString("arrAirportCode");
        // sysCode has valid isn't null in aop
        String sysCode = request.getString("sysCode");
        CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);

        if (log.isInfoEnabled()) {
            log.info("【参数   flightNo:" + flightNo +"; depDate:"+ depDate + "; depAirportCode:" + depAirportCode + "; arrAirportCode" + arrAirportCode  +" 】");
        }

        try {
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(depDate);
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);

            FlightPo localFlight = flightPoMapper.select(flightPo);
            if(localFlight !=null){
                if (localFlight.getIsCustom() != 1) {
                    ibeService.executeCustomFlight(flightNo,depDate);
                    flightPoMapper.updateIsCustom(flightPo);
                }
                CustomFlightPo customFlightPo = new CustomFlightPo();
                customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                customFlightPo.setCustomerId(customerPo.getCustomerId());
                customFlightPo.setFlightId(localFlight.getFlightId());
                customFlightPoMapper.insert(customFlightPo);
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(null);
            }else {
                re = getFlightPoCustomByIbeSource(customerPo,depDate, flightNo, depAirportCode, arrAirportCode);
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
     * 航班推送
     * 用来触发航班信息的更新
     * @param request 参数
     */
    public void flightPush(JSONObject request){
        String notify = request.getString("notify");
        try {

            List<Long> flightIds = parseNotify(notify);

            List<CustomFlightPojo> customFlightPojos = customFlightPoMapper.selectsCustomFlightPojo(flightIds);
            // do push
            FlightPo flightPo;
            for (CustomFlightPojo customFlightPojo : customFlightPojos) {
                flightPo = customFlightPojo.getFlightPo(); // 更新的航班信息
                for (String s : customFlightPojo.getCustomUrls()) {
                    Map<String, String> params = new HashedMap();
                    params.put("data", JSON.toJSONString(flightPo));
                    boolean isSuccess = false;
                    while (!isSuccess){
                        String result = HttpClientUtils.HttpPostForWebService(s, params);
                        if (log.isInfoEnabled()) {
                            log.info("【推送地址：" + s + " 返回的结果：" + result + "+ 】");
                        }
                        JSONObject object = JSON.parseObject(result);
                        if(object.getInteger("state") == 1){
                            isSuccess = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (depAirportCode.equals(flightPo.getDepAirportCode()) && arrAirportCode.equals(flightPo.getArrAirportCode())) {
                isExist = true;
                flightPo.setIsCustom((short)1);
                CustomFlightPo customFlightPo = new CustomFlightPo();
                customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                customFlightPo.setCustomerId(customerPo.getCustomerId());
                customFlightPo.setFlightId(flightPo.getFlightId());
                customFlightPoMapper.insert(customFlightPo);
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

    private FlightCenterResult<FlightPo> getFlightPoByIbeSource(Date depDate, String flightNo,String depAirportCode,String arrAirportCode) throws Exception {
        return getFlightPoByIbeSource(depDate, flightNo, depAirportCode, arrAirportCode, false);
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

    /**
     * 解析参数notify
     * @param notify
     * @return
     * @throws ParseException
     */
    private List<Long> parseNotify(String notify) throws ParseException {
        List<Long> result = new ArrayList<>();
        String[] splitFlights = notify.split("\\^");
        for (String s : splitFlights) {
            if (s.contains("\n")) {
                String[] splitFlight = s.split("\\n");
                for (String s1 : splitFlight) {
                    result.add(parseDetail(s1));
                }
            }
            result.add(parseDetail(s));
        }
        return result;
    }

    /**
     * 解析具体的信息
     * @param flightStr
     * @return
     * @throws ParseException
     */
    private Long parseDetail(String flightStr) throws ParseException {
        String[] split = StringUtils.split(flightStr,"\\t");
        FlightPo flightPo = new FlightPo();
        flightPo.setDepAirportCode(split[3]);
        flightPo.setArrAirportCode(split[4]);
        flightPo.setFlightNo(split[1].toUpperCase());
        flightPo.setDepDate(DateUtils.stringToDate(split[7], "yyyy-MM-dd"));
        flightPo.setArrDate(DateUtils.stringToDate(split[8], "yyyy-MM-dd"));
        flightPo.setDepActualDate(DateUtils.completeToHSM(split[9]));
        flightPo.setArrActualDate(DateUtils.completeToHSM(split[10]));
        flightPo.setFlightState(split[11]);
        flightPo.setIsCustom((short)1);
        flightPoMapper.update(flightPo);
        return flightPo.getFlightId();
    }

    public static void main(String[] args) throws ParseException {
        String notify = "PEKSHA\\tCA1111\\t92\\tPEK\\tSHA\\tT1\\tT2\\t2013-11-20 10:00\\t2013-11-20 12:00\\t2013-11-20 10:01\\t2013-11-20 12:00\\t起飞^" +
            "PEKSHA\\tMU1111\\t92\\tPEK\\tSHA\\tT1\\tT2\\t2013-11-20 10:00\\t2013-11-20 12:00\\t2013-11-20 10:01\\t2013-11-20 12:00\\t起飞\\n" +
            "PEKSHA\\tMU1111\\t92\\tPEK\\tSHA\\tT1\\tT2\\t2013-11-20 10:00\\t2013-11-20 12:00\\t2013-11-20 10:01\\t2013-11-20 12:00\\t起飞";
        System.out.println(notify);
        System.out.println(notify.contains("^"));
        String[] split = notify.split("\\^");
        String s = "2013-11-20 12:00";
        DateUtils.completeToHSM(s);

    }

}
