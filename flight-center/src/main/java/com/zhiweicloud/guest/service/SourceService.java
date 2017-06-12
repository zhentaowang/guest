package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.SourceApiPoMapper;
import com.zhiweicloud.guest.po.SourceApiPo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SourceService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/9 17:44
 * @author tiecheng
 */
@Service
public class SourceService {

    @Autowired
    private SourceApiPoMapper sourceApiPoMapper;

    public String querySourceDropDownList(JSONObject request) {
        FlightCenterResult<List<String>> result = new FlightCenterResult<>();
        try {
            result.setData(sourceApiPoMapper.selectSourceDropDownList());
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    public String querySourceApiByNameAndDate(JSONObject request) {
        FlightCenterResult<List<SourceApiPo>> result = new FlightCenterResult<>();
        Map<String, Object> params = new HashMap<>();
        try {
            String sourceName = request.getString("sourceName");
            String startDate = request.getString("startDate");
            String endDate = request.getString("endDate");
            String date = request.getString("date");

            params.put("sourceName", sourceName);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("date", date);

            result.setData(sourceApiPoMapper.selectSourceApiByNameAndDate(params));
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

}
