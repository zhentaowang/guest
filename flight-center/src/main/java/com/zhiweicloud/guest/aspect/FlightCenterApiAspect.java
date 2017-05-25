package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.mapper.ApiCustomerMapper;
import com.zhiweicloud.guest.mapper.FlightCenterApiLogMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightCenterApiLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by tc on 2017/5/18.
 */
@Component
@Aspect
public class FlightCenterApiAspect {

//    @Pointcut("execution(* com.zhiweicloud.guest.controller.FlightCenterController.flightInfo(..))")
//    public void pointcutFlightInfo(){
//
//    }
//
//    @Pointcut("execution(* com.zhiweicloud.guest.controller.FlightCenterController.customFlight(..))")
//    public void pointcutCustomFlight(){
//
//    }
//
//    @Pointcut("execution(* com.zhiweicloud.guest.controller.FlightCenterController.tickPassengerInfo(..))")
//    public void pointcutTickPassengerInfo(){
//
//    }
//
//    @Autowired
//    private FlightCenterApiLogMapper flightCenterApiLogMapper;
//
//    @Autowired
//    private ApiCustomerMapper apiCustomerMapper;
//
//    @Around(value = "pointcutFlightInfo()")
//    public Object aroundFlightInfo(ProceedingJoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
//        System.out.println(joinPoint.getSignature().getName());
//        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
//        FlightCenterApiLog flightCenterApiLog = new FlightCenterApiLog();
//        flightCenterApiLog.setApiName(name);
//        String airportCode = object.getString("client_id");
//        Long userId = object.getLong("user_id");
//        String sysCode = object.getString("sysCode");// 系统码
//        Object proceed = null;
//        try {
//            flightCenterApiLog.setCustomerId(apiCustomerMapper.selectIdBySysCode(sysCode));
//            proceed = joinPoint.proceed();
//            JSONObject jsonObject = JSON.parseObject(proceed.toString());
//            List<Flight> flightList = JSONArray.parseArray(jsonObject.getString("data"), Flight.class);
//            flightCenterApiLog.setInvokeState(jsonObject.getString("state"));
//            flightCenterApiLog.setInvokeResult(flightList.get(0).getFlightNo());
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            flightCenterApiLog.setInvokeState("-1");
//            flightCenterApiLog.setInvokeResult("error");
//        }
//        flightCenterApiLogMapper.insert(flightCenterApiLog);
//        return proceed;
//    }



}
