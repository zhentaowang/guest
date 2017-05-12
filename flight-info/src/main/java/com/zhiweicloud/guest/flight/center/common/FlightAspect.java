package com.zhiweicloud.guest.flight.center.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.mapper.ExchangeDragonMapper;
import com.zhiweicloud.guest.model.Flight;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tc on 2017/5/12.
 */
@Component
@Aspect
public class FlightAspect{

//    @Pointcut("execution(* com.zhiweicloud.guest.flight.center.common.IbeUtils.queryFlightNoByDate(String,String)) and args(flightNo,flightDate)")
    @Pointcut("execution(* com.zhiweicloud.guest.thrift.util.FlightUtils.flightInfo(..))")
    public void pointcutFlightInfo(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.thrift.util.FlightUtils.customFlight(..))")
    public void pointcutCustomFlight(){

    }

    /**
     * 龙腾接口
     */
    private ExchangeDragonMapper exchangeDragonMapper;

    @Around(value = "pointcutFlightInfo()")
    public void around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature().getName());
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        String fnum = object.getString("fnum");
        String date = object.getString("date");
        String airportCode = object.getString("client_id");
        Long userId = object.getLong("user_id");
        Object proceed = joinPoint.proceed();
        List<Flight> flightList = JSONArray.parseArray(JSON.parseObject(proceed.toString()).getString("data"), Flight.class);
    }

}
