package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.FlightPoMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    private IbeService ibeService;

    public String queryFlightInfo(JSONObject request){
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();

        try {
            String flightNo = request.getString("flightNo");
            Date depDate = request.getDate("depDate");// 出发日期 即航班号

            log.info("【参数   flightNo:" + flightNo + " depDate:" + depDate + "】");

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(depDate);

            List<FlightPo> result = flightPoMapper.selectByDateAndNo(flightPo);

            if (result == null|| result.size()==0) {
                Date today = DateUtils.getDate("yyyy-MM-dd");
                if(depDate.before(today)){
                    re.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
                    re.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
                    re.setData(null);
                }
                if (depDate.after(today)) {
                    re = ibeService.queryFlightNoByDate(flightNo, DateUtils.dateToString(depDate, "yyyy-MM-dd"));
                }
                if (depDate.equals(today)) {
                    re = ibeService.queryFlightNo(flightNo);
                }
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
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

}
