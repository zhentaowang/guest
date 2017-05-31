package com.zhiweicloud.guest.source.ibe.service;

import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.TickPassenger;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.source.ibe.model.FlightStatus;
import com.zhiweicloud.guest.source.ibe.model.IbeDetrTkt;
import com.zhiweicloud.guest.source.ibe.model.IbeDetrTktResult;
import com.zhiweicloud.guest.source.ibe.model.RootResult;
import com.zhiweicloud.guest.source.ibe.util.IbeUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tc on 2017/5/18.
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
        FlightCenterResult<TickPassenger> result = new FlightCenterResult();
        result.setMessage(ibeDetrTktResult.getErrorRes().getErrContent());
        result.setState(Integer.valueOf(ibeDetrTktResult.getErrorRes().getErrCode()));
        result.setData(parse(ibeDetrTktResult.getTktGroup().getIbeDetrTkt()));
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
            }
        }
        return flights;
    }

    private TickPassenger parse(IbeDetrTkt ibeDetrTkt) throws InvocationTargetException, IllegalAccessException {
        TickPassenger tickPassenger = new TickPassenger();
        BeanUtils.copyProperties(tickPassenger,ibeDetrTkt);
        BeanUtils.copyProperties(tickPassenger,ibeDetrTkt.getSegmentGroup());
        return tickPassenger;
    }

}
