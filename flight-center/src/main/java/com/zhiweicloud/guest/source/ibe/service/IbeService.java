package com.zhiweicloud.guest.source.ibe.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import com.zhiweicloud.guest.source.ibe.model.*;
import com.zhiweicloud.guest.source.ibe.util.IbeUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IbeService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/31 17:02 
 * @author tiecheng
 */
@Service
public class IbeService {

    public FlightCenterResult queryFlightNo(String flightNo) throws Exception {
        RootResult rootResult = IbeUtils.queryFlightNo(flightNo);
        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult();
        result.setMessage(rootResult.getErrorRes().getErrContent());
        result.setState(Integer.valueOf(rootResult.getErrorRes().getErrCode()));
        result.setData(parse(rootResult.getFlightStatuses(), DateUtils.getDate("yyyy-MM-dd")));
        return result;
    }

    public FlightCenterResult queryFlightNoByDate(String flightNo, String flightDate) throws Exception {
        RootResult rootResult = IbeUtils.queryFlightNoByDate(flightNo, flightDate);
        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult();
        result.setMessage(rootResult.getErrorRes().getErrContent());
        result.setState(Integer.valueOf(rootResult.getErrorRes().getErrCode()));
        result.setData(parse(rootResult.getFlightStatuses(), DateUtils.stringToDate(flightDate, "yyyy-MM-dd")));
        return result;
    }

    public FlightCenterResult queryPassengerByTickNo(String tickNo) throws Exception {
        IbeDetrTktResult ibeDetrTktResult = IbeUtils.queryPassengerByTickNo(tickNo);
        FlightCenterResult<PassengerTicketPojo> result = new FlightCenterResult();
        result.setMessage(ibeDetrTktResult.getErrorRes().getErrContent());
        result.setState(Integer.valueOf(ibeDetrTktResult.getErrorRes().getErrCode()));
        result.setData(parse(ibeDetrTktResult.getTktGroup().getIbeDetrTkt()));
        return result;
    }

    public FlightCenterResult executeCustomFlight(String flightNo, Date depDate) throws Exception {
        IbeMessage ibeMessage = IbeUtils.customFlight(flightNo, DateUtils.dateToString(depDate, "yyyy-MM-dd"));
        FlightCenterResult<PassengerTicketPojo> result = new FlightCenterResult();
        result.setState(Integer.valueOf(ibeMessage.getErrorRes().getErrCode()));
        result.setMessage(ibeMessage.getErrorRes().getErrContent());
        result.setData(null);
        return result;
    }

    public FlightCenterResult queryFlightCodeAndDate(String depAirportCode, String arrAirportCode, Date depDate) throws Exception {
//        JSONObject object = IbeUtils.queryFlightByDepCodeAndArrCodeAndDate(depAirportCode, arrAirportCode,DateUtils.dateToString(depDate, "yyyy-MM-dd"));
//        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult();
//        JSONArray errorRes = object.getJSONArray("ErrorRes");
//        JSONArray jsonArray = object.getJSONArray("AVResult").getJSONObject(0).getJSONArray("IBE_FlightGroup");
//        JSONObject jsonObject = errorRes.getJSONObject(0);
//        result.setState(jsonObject.getIntValue("Err_code"));
//        result.setMessage(jsonObject.getString("Err_content"));
//        result.setData(parse(jsonArray));
        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult();
        IbeAvResult ibeAvResult = IbeUtils.queryFlightByDepCodeAndArrCodeAndDate(depAirportCode, arrAirportCode, DateUtils.dateToString(depDate, "yyyy-MM-dd"));
        result.setState(Integer.valueOf(ibeAvResult.getErrorRes().getErrCode()));
        result.setMessage(ibeAvResult.getErrorRes().getErrContent());
        result.setData(parseIbe(ibeAvResult.getIbeFlightGroups(),depDate));
        return result;
    }

    public FlightCenterResult queryFlightCode(String depAirportCode, String arrAirportCode) throws Exception {
        IbeQueryByDepAndArr ibeQueryByDepAndArr = IbeUtils.queryByDepAndArr(depAirportCode, arrAirportCode);
        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult();
        result.setState(Integer.valueOf(ibeQueryByDepAndArr.getErrorRes().getErrCode()));
        result.setMessage(ibeQueryByDepAndArr.getErrorRes().getErrContent());
        result.setData(parse(ibeQueryByDepAndArr.getFlightStatuses(),DateUtils.getDate("yyyy-MM-dd")));
        return result;
    }

    /**
     * 把查询对象解析成航班数据库对象
     * @param flightStatuses
     * @param flightDate
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private List<FlightPo> parse(List<FlightStatus> flightStatuses, Date flightDate) throws InvocationTargetException, IllegalAccessException {
        List<FlightPo> flights = new ArrayList<>();
        if (flightStatuses != null) {
            for (FlightStatus flightStatus : flightStatuses) {
                FlightPo flight = new FlightPo();
                flight.setDepDate(flightDate);
                BeanUtils.copyProperties(flight, flightStatus);
                flights.add(flight);
                Date depScheduledDate = flight.getDepScheduledDate();
                Date arrScheduledDate = flight.getArrScheduledDate();
                if (depScheduledDate.after(arrScheduledDate)) {
                    flight.setArrScheduledDate(DateUtils.addDay(arrScheduledDate, 1));
                    flight.setArrEstimatedDate(DateUtils.addDay(flight.getArrEstimatedDate(),1));
                }
            }
        }
        return flights;
    }

    private List<FlightPo> parseIbe(List<IbeFlightGroup> ibeFlightGroups, Date flightDate) {
        List<FlightPo> flightPos = new ArrayList<>();
        FlightPo flightPo;
        for (IbeFlightGroup ibeFlightGroup : ibeFlightGroups) {
            flightPo = parseIbeFlight(ibeFlightGroup.getIbeFlights().getIbeFlight());
            flightPo.setDepDate(flightDate);
            flightPos.add(flightPo);
        }
        return flightPos;
    }

    private List<FlightPo> parse(JSONArray jsonArray) {
        List<FlightPo> flightPos = new ArrayList<>();
        int size = jsonArray.size();
        JSONObject data;
        FlightPo flightPo;
        for (int i = 0; i < size; i++) {
            data = jsonArray.getJSONObject(i).getJSONArray("IBE_Flights").getJSONObject(0).getJSONArray("IBE_Flight").getJSONObject(0);
            flightPo = new FlightPo();
            flightPo.setDepAirportCode(data.getString("orgCity"));
            flightPo.setArrAirportCode(data.getString("dstcity"));
            flightPo.setFlightNo(data.getString("FlightNO"));
            flightPo.setDepDate(data.getDate("depDate"));
            flightPo.setArrDate(data.getDate("arrDate"));
            flightPo.setDepScheduledDate(addDateAndTime(data.getDate("depDate"),data.getString("depTime")));
            flightPo.setArrScheduledDate(addDateAndTime(data.getDate("arrDate"),data.getString("arrtime")));
            flightPo.setFlightType(data.getString("planeStyle"));
            flightPo.setStopFlag((data.getInteger("stopNumber") == 0 ? (short) 0 : (short) 1));
            flightPo.setDepTerminal(data.getString("OrgAirportTerminal"));
            flightPo.setArrTerminal(data.getString("DstAirportTerminal"));
            flightPos.add(flightPo);
        }
        return flightPos;
    }

    private FlightPo parseIbeFlight(IbeFlight ibeFlight) {
        FlightPo flightPo = new FlightPo();
        flightPo.setFlightNo(ibeFlight.getFlightNo());
        flightPo.setDepDate(ibeFlight.getDepDate());
        flightPo.setArrDate(ibeFlight.getArrDate());
        flightPo.setDepAirportCode(ibeFlight.getDepAirportCode());
        flightPo.setArrAirportCode(ibeFlight.getArrAirportCode());
        flightPo.setDepScheduledDate(addDateAndTime(ibeFlight.getDepDate(), ibeFlight.getDepTime()));
        flightPo.setArrScheduledDate(addDateAndTime(ibeFlight.getArrDate(), ibeFlight.getArrTime()));
        flightPo.setFlightType(ibeFlight.getFlightType());
        flightPo.setStopFlag((ibeFlight.getStopNumber() == 0 ? (short) 0 : (short) 1));
        flightPo.setDepTerminal(ibeFlight.getDepTerminal());
        flightPo.setArrTerminal(ibeFlight.getArrTerminal());
        return flightPo;
    }

    private Date addDateAndTime(Date date, String time) {
        String hourVal = time.substring(0, 2);
        String minuteVal = time.substring(2);
        date = DateUtils.addHour(date, hourVal == null? 0 : Integer.valueOf(hourVal));
        date = DateUtils.addMinute(date, minuteVal == null? 0 : Integer.valueOf(minuteVal));
        return date;
    }

    private PassengerTicketPojo parse(IbeDetrTkt ibeDetrTkt) throws InvocationTargetException, IllegalAccessException {
        PassengerTicketPojo passengerTicketPojo = new PassengerTicketPojo();
        BeanUtils.copyProperties(passengerTicketPojo,ibeDetrTkt);
        BeanUtils.copyProperties(passengerTicketPojo,ibeDetrTkt.getSegmentGroup().getIbeDetrTktSegment());
        return passengerTicketPojo;
    }

}
