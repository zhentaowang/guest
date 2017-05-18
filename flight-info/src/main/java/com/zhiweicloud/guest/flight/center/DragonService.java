package com.zhiweicloud.guest.flight.center;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dragon.sign.DragonSignature;
import com.zhiweicloud.guest.common.DateUtils;
import com.zhiweicloud.guest.common.Dictionary;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.model.Flight;
import javafx.collections.transformation.FilteredList;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DragonService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/11 15:14
 * @author tiecheng
 */
public class DragonService implements FlightCenterService {

    @Autowired
    private FlightMapper flightMapper;

    @Override
    public String flightInfo(String flightNo, String flightDate) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("date", flightDate);
        params.put("fnum", flightNo);
        params.put("lg", Dictionary.LG);
        params.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        params.put("sign", sign);
        Map<String, Object> p = new HashMap<>();
        p.put("date", flightDate);
        p.put("fnum", flightNo);
        p.put("lg", Dictionary.LG);
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        String ret = HttpClientUtil.httpPostRequest(Dictionary.DRAGON_URL_GETFLIGHTINFO, p);
//        JSONObject object = JSON.parseObject(new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8));
//        Map<String, Object> map = new HashMap<>();
//        List<Flight> flightList = JSON.parseArray(object.getString("Data"), Flight.class);
//        map.put("data", flightList);
//        map.put("state", object.getInteger("State"));
//        map.put("info", object.get("Info"));
//        return JSON.toJSONString(map);
        return new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
    }

    @Override
    public String customFlight(Long flightId) throws Exception{
        Flight flight = flightMapper.selectByFlightId(flightId);
        Map<String, String> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("date", sdf.format(flight.getFlightDate()));
        params.put("fnum", flight.getFlightNo());
        params.put("dep", flight.getFlightDepcode());
        params.put("arr", flight.getFlightArrcode());
        params.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(params, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        Map<String, Object> p = new HashMap<>();
        p.put("date", sdf.format(flight.getFlightDate()));
        p.put("fnum", flight.getFlightNo());
        p.put("dep", flight.getFlightDepcode());
        p.put("arr", flight.getFlightArrcode());
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        String ret = HttpClientUtil.httpPostRequest(Dictionary.DRAGON_URL_CUSTOMFLIGHT, p);
        return new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
    }

    @Override
    public String flightInfoDynamic(Map<String,String> params) throws Exception {
        Map<String, String> pSign = new HashMap<>();
        pSign.put("date", params.get("flightDate"));
        pSign.put("fnum", params.get("flightNo"));
        pSign.put("dep", params.get("flightDepcode"));
        pSign.put("arr", params.get("flightArrcode"));
        pSign.put("lg", Dictionary.LG);
        pSign.put("sysCode", Dictionary.SYSCODE);
        String sign = DragonSignature.rsaSign(pSign, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
        Map<String, Object> p = new HashMap<>();
        p.put("date", params.get("flightDate"));
        p.put("fnum", params.get("flightNo"));
        p.put("dep", params.get("flightDepcode"));
        p.put("arr", params.get("flightArrcode"));
        p.put("lg", Dictionary.LG);
        p.put("sysCode", Dictionary.SYSCODE);
        p.put("sign", sign);
        String ret = HttpClientUtil.httpPostRequest(Dictionary.DRAGON_URL_GETFLIGHTBYNO_LG, p);
        return new String(ret.getBytes(Dictionary.ENCODING_ISO8859_1), Dictionary.ENCODING_UTF_8);
    }

}
