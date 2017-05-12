package com.zhiweicloud.guest.flight.center;

import com.zhiweicloud.guest.common.DateUtils;
import com.zhiweicloud.guest.flight.center.common.IbeUtils;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 从http://www.ibeservice.com/获取数据
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/11 15:02 
 * @author tiecheng
 */
public class IbeService implements FlightCenterService {

    @Autowired
    private FlightMapper flightMapper;

    @Override
    public String flightInfo(String flightNo, String flightDate) throws Exception {
        return IbeUtils.queryFlightNoByDate(flightNo, flightDate);
    }

    @Override
    public String customFlight(Long flightId) throws Exception {
        Flight flight = flightMapper.selectByFlightId(flightId);
        return IbeUtils.customFlight(flight.getFlightNo(), DateUtils.dateToString(flight.getFlightDate(),"yyyy-MM-dd"));
    }

    @Override
    public String flightInfoDynamic(Map<String,String> params) throws Exception {
        return IbeUtils.queryFlightNoByDate(params.get("flightNo"), params.get("flightDate"));
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("flightDate", "2017-05-13");
        map.put("flightNo", "MU2474");
        System.out.println(new IbeService().flightInfoDynamic(map));
    }

}
