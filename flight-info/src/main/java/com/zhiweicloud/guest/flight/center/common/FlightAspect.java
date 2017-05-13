package com.zhiweicloud.guest.flight.center.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.DateUtils;
import com.zhiweicloud.guest.mapper.ExternalInterfaceLogMapper;
import com.zhiweicloud.guest.model.ExternalInterfaceLog;
import com.zhiweicloud.guest.model.Flight;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    private ExternalInterfaceLogMapper exchangeDragonMapper;

    @Around(value = "pointcutFlightInfo()")
    public Object aroundFlightInfo(ProceedingJoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName());
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        ExternalInterfaceLog externalInterfaceLog = new ExternalInterfaceLog();
        String airportCode = object.getString("client_id");
        Long userId = object.getLong("user_id");
        Object proceed = null;
        try {
            externalInterfaceLog.setFlightDate(DateUtils.stringToDate(object.getString("date"),"yyyy-MM-dd"));
            externalInterfaceLog.setFlightNo(object.getString("fnum"));
            externalInterfaceLog.setCreateUser(object.getLong("user_id"));
            externalInterfaceLog.setCreateTime(new Date(System.currentTimeMillis()));
            externalInterfaceLog.setDockingSource("IBE");
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
