package com.zhiweicloud.guest.flight.center.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.mapper.ExchangeDragonMapper;
import com.zhiweicloud.guest.model.Flight;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tc on 2017/5/12.
 */
@Component
@Aspect
public class FlightAspect{

    @Pointcut("execution(* com.zhiweicloud.guest.thrift.util.FlightUtils.flightInfo(..))")
    public void pointcutFlightInfo(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.thrift.util.FlightUtils.customFlight(..))")
    public void pointcutCustomFlight(){

    }

    /**
     * 龙腾接口
     */
    @Autowired
    private ExchangeDragonMapper exchangeDragonMapper;

    @Around(value = "pointcutFlightInfo()")
    public Object aroundFlightInfo(ProceedingJoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName());
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        String fnum = object.getString("fnum");
        String date = object.getString("date");
        String airportCode = object.getString("client_id");
        Long userId = object.getLong("user_id");
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        List<Flight> flightList = JSONArray.parseArray(JSON.parseObject(proceed.toString()).getString("data"), Flight.class);
        return proceed;
    }

    @Around(value = "pointcutCustomFlight()")
    public Object aroundCustomFlight(ProceedingJoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName());
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        Long flightId = object.getLong("flightId");
        String airportCode = object.getString("client_id");
        Long userId = object.getLong("user_id");
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        List<Flight> flightList = JSONArray.parseArray(JSON.parseObject(proceed.toString()).getString("data"), Flight.class);
        return proceed;
    }

}
