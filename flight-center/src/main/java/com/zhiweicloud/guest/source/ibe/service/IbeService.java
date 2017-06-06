package com.zhiweicloud.guest.source.ibe.service;

import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import com.zhiweicloud.guest.source.ibe.model.FlightStatus;
import com.zhiweicloud.guest.source.ibe.model.IbeDetrTkt;
import com.zhiweicloud.guest.source.ibe.model.IbeDetrTktResult;
import com.zhiweicloud.guest.source.ibe.model.RootResult;
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

    public void executeCustomFlight(String flightNo, Date depDate) throws Exception {
        IbeUtils.customFlight(flightNo, DateUtils.dateToString(depDate,"yyyy-MM-dd"));
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

    private PassengerTicketPojo parse(IbeDetrTkt ibeDetrTkt) throws InvocationTargetException, IllegalAccessException {
        PassengerTicketPojo passengerTicketPojo = new PassengerTicketPojo();
        BeanUtils.copyProperties(passengerTicketPojo,ibeDetrTkt);
        BeanUtils.copyProperties(passengerTicketPojo,ibeDetrTkt.getSegmentGroup().getIbeDetrTktSegment());
        return passengerTicketPojo;
    }

}
