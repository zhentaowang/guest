package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.PushRunnable;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.mapper.CustomFlightPoMapper;
import com.zhiweicloud.guest.mapper.FlightPoMapper;
import com.zhiweicloud.guest.mapper.FlightPushPoMapper;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.pojo.CustomFlightPojo2;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * FlightPushService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/5 11:30
 * @author tiecheng
 */
@Service
public class FlightPushService {

    private static final Log log = LogFactory.getLog(FlightPushService.class);

    @Autowired
    private FlightPushPoMapper flightPushPoMapper;

    @Autowired
    private CustomFlightPoMapper customFlightPoMapper;

    @Autowired
    private FlightPoMapper flightPoMapper;

    /**
     * 创建一个线程池
     */
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(20);

    /**
     * 航班推送
     * 用来触发航班信息的更新
     * @param request 参数
     */
    @Transient
    public void flightPush(JSONObject request) {
        // 这个名字配置在go
        String notify = request.getString("notify");
        if (log.isInfoEnabled()) {
            log.info("【 推送过来的参数 notify: " + notify + " 】");
        }
        if (StringUtils.isBlank(notify)) {
            return;
        }
        try {
            // parse
            Set<Long> flightIds = parseNotify(notify);
//            List<CustomFlightPojo> customFlightPojos = customFlightPoMapper.selectsCustomFlightPojo(flightIds);
            List<CustomFlightPojo2> customFlightPojos2 = customFlightPoMapper.selectsCustomFlightPojo2(new ArrayList<>(flightIds));
            for (CustomFlightPojo2 customFlightPojo2 : customFlightPojos2) {
                String customUrl = customFlightPojo2.getCustomUrl();
                Map<String, String> params = new HashedMap();
                params.put("data", JSON.toJSONString(customFlightPojo2.getFlightPos()));
                executor.execute(new PushRunnable(customUrl, params, executor, flightPushPoMapper, customFlightPojo2.getCustomerId()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 解析参数notify
     * @param notify
     * @return
     * @throws ParseException
     */
    private Set<Long> parseNotify(String notify) throws ParseException {
        Set<Long> result = new HashSet<>();
        String[] splitFlights = notify.split("\\^");
        for (String s : splitFlights) {
            if (s.contains("\n")) {
                String[] splitFlight = s.split("\\n");
                for (String s1 : splitFlight) {
                    result.add(parseDetail(s1));
                }
            }else {
                result.add(parseDetail(s));
            }
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
        String[] split = StringUtils.split(flightStr, "\t");
        FlightPo flightPo = new FlightPo();
        flightPo.setDepAirport(split[3]);
        flightPo.setArrAirportCode(split[4]);
        flightPo.setDepAirportCode(split[0].substring(0, 3));
        flightPo.setArrAirportCode(split[0].substring(3));
        flightPo.setFlightNo(split[1].toUpperCase());
        flightPo.setDepDate(DateUtils.stringToDate(split[7], "yyyy-MM-dd"));
        flightPo.setArrDate(DateUtils.stringToDate(split[8], "yyyy-MM-dd"));
        flightPo.setDepActualDate(DateUtils.completeToHSM(split[9]));
        flightPo.setArrActualDate(DateUtils.completeToHSM(split[10]));
        flightPo.setFlightState(split[11]);
        FlightPo localFlight = flightPoMapper.select(flightPo);
        if (localFlight == null) {
            return null;
        } else {
            flightPoMapper.updateByCondition(flightPo);
            return localFlight.getFlightId();
        }
    }

    public String testCustom1(JSONObject request) {
        String data = request.getString("data");
        if (log.isInfoEnabled()) {
            log.info("【 推送过来的参数: data_" + data + " 】");
        }
        return "{\"state\":1;\"message\":\"成功\"}";
    }

    public String testCustom2(JSONObject request) {
        String data = request.getString("data");
        if (log.isInfoEnabled()) {
            log.info("【 推送过来的参数: data_" + data + " 】");
        }
        return "{\"state\":2;\"message\":\"失败\"}";
    }

}
