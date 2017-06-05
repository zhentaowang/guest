package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.mapper.CustomerPoMapper;
import com.zhiweicloud.guest.mapper.FlightCenterApiPoMapper;
import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.po.FlightCenterApiPo;
import com.zhiweicloud.guest.signature.RSACoder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * FlightCenterApiAspect.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/27 13:43 
 * @author tiecheng
 */
@Component
@Aspect
public class FlightCenterApiAspect {

    private static final Log log = LogFactory.getLog(FlightCenterApiAspect.class);

    @Autowired
    private FlightCenterApiPoMapper flightCenterApiPoMapper;

    @Autowired
    private CustomerPoMapper customerPoMapper;

    @Pointcut("execution(* com.zhiweicloud.guest.service.TrainService.*(..))")
    public void pointcutTrainService(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.service.FlightService.*(..))")
    public void pointcutFlightService(){

    }

    @Around(value = "pointcutTrainService()")
    public Object aroundTrainService(ProceedingJoinPoint joinPoint){
        FlightCenterResult flightCenterResult = new FlightCenterResult();

        String name = joinPoint.getSignature().getName();
        log.debug("【中心接口 切面拦截 方法名:" + name + "】");

        // get params
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];

        String sysCode = object.getString("sysCode");  // 系统码

        if(sysCode == null){
            flightCenterResult.setMessage(FlightCenterStatus.NONE_SYS_CODE.display());
            flightCenterResult.setState(FlightCenterStatus.NONE_SYS_CODE.value());
            flightCenterResult.setData(null);
            return JSON.toJSONString(flightCenterResult);
        }

        // 根据系统码获得 私钥 公钥
        CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);
        Object proceed = null;
        FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
        try {
            String sign = URLDecoder.decode(object.getString("sign"),"UTF-8"); // 签名
            sign = sign.replace(" ", "+");

            if (customerPo == null) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_CUSTOMER.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_CUSTOMER.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }

            if(customerPo.getType() != 0){
                Map<String, Object> params = new HashedMap();

                // 参数需要确认
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    if (!"sign".equals(entry.getKey()) && !"operation".equals(entry.getKey()) && !"access_token".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }

                if (sign == null || "".equals(sign)){
                    flightCenterResult.setMessage(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterResult.setState(FlightCenterStatus.SIGN_INVALID.value());
                    flightCenterResult.setData(null);
                    return JSON.toJSONString(flightCenterResult);
                }else {
                    boolean verify = RSACoder.verify(params, customerPo.getPublicKey(), sign);

                    if (!verify){
                        flightCenterResult.setMessage(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterResult.setState(FlightCenterStatus.NOT_AUTH.value());
                        flightCenterResult.setData(null);
                        return JSON.toJSONString(flightCenterResult);
                    }
                }

                if (customerPo.getTotal() <= 0) {
                    flightCenterResult.setMessage(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterResult.setState(FlightCenterStatus.NONE_TIME_ENOUGH.value());
                    flightCenterResult.setData(null);
                    return JSON.toJSONString(flightCenterResult);
                }
                // reduce total
                customerPo.setTotal(customerPo.getTotal() - 1);
                customerPoMapper.updateTotal(customerPo);
            }

            flightCenterApiPo.setApiName(name);
            flightCenterApiPo.setCustomerId(customerPo.getCustomerId());

            proceed = joinPoint.proceed();
            JSONObject jsonObject = JSON.parseObject(proceed.toString());
            flightCenterApiPo.setInvokeState(jsonObject.getString("state"));
            flightCenterApiPo.setInvokeResult(jsonObject.getString("message"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            flightCenterApiPo.setInvokeState("-1");
            flightCenterApiPo.setInvokeResult(throwable.getCause().getMessage());
        }
        flightCenterApiPoMapper.insert(flightCenterApiPo);
        return proceed;
    }

    @Around(value = "pointcutFlightService()")
    public Object aroundFlightService(ProceedingJoinPoint joinPoint){
        FlightCenterResult flightCenterResult = new FlightCenterResult();

        String name = joinPoint.getSignature().getName();
        log.debug("【中心接口 切面拦截 方法名:" + name + "】");

        // get params
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];

        String sysCode = object.getString("sysCode");  // 系统码

        if(sysCode == null){
            flightCenterResult.setMessage(FlightCenterStatus.NONE_SYS_CODE.display());
            flightCenterResult.setState(FlightCenterStatus.NONE_SYS_CODE.value());
            flightCenterResult.setData(null);
            return JSON.toJSONString(flightCenterResult);
        }

        // 根据系统码获得 私钥 公钥
        CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);
        Object proceed = null;
        FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
        try {
            if (customerPo == null) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_CUSTOMER.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_CUSTOMER.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }

            if(customerPo.getType() != 0){
                Map<String, Object> params = new HashedMap();

                // 参数需要确认
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    if (!"sign".equals(entry.getKey()) && !"operation".equals(entry.getKey()) && !"access_token".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }

                String sign = URLDecoder.decode(object.getString("sign"),"UTF-8"); // 签名
                sign = sign.replace(" ", "+");

                if (sign == null || "".equals(sign)){
                    flightCenterResult.setMessage(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterResult.setState(FlightCenterStatus.SIGN_INVALID.value());
                    flightCenterResult.setData(null);
                    return JSON.toJSONString(flightCenterResult);
                }else {
                    boolean verify = RSACoder.verify(params, customerPo.getPublicKey(), sign);

                    if (!verify){
                        flightCenterResult.setMessage(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterResult.setState(FlightCenterStatus.NOT_AUTH.value());
                        flightCenterResult.setData(null);
                        return JSON.toJSONString(flightCenterResult);
                    }
                }

                if (customerPo.getTotal() <= 0) {
                    flightCenterResult.setMessage(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterResult.setState(FlightCenterStatus.NONE_TIME_ENOUGH.value());
                    flightCenterResult.setData(null);
                    return JSON.toJSONString(flightCenterResult);
                }

                // reduce total
                customerPo.setTotal(customerPo.getTotal() - 1);
                customerPoMapper.updateTotal(customerPo);
            }

            flightCenterApiPo.setApiName(name);
            flightCenterApiPo.setCustomerId(customerPo.getCustomerId());

            proceed = joinPoint.proceed();
            JSONObject jsonObject = JSON.parseObject(proceed.toString());
            flightCenterApiPo.setInvokeState(jsonObject.getString("state"));
            flightCenterApiPo.setInvokeResult(jsonObject.getString("message"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            flightCenterApiPo.setInvokeState("-1");
            flightCenterApiPo.setInvokeResult(throwable.getCause().getMessage());
        }
        flightCenterApiPoMapper.insert(flightCenterApiPo);
        return proceed;
    }

    @Pointcut("execution(* com.zhiweicloud.guest.service.FlightService.queryFlightInfo(..)) || execution(* com.zhiweicloud.guest.service.FlightService.queryDynamicFlightInfo(..)) || " +
        "execution(* com.zhiweicloud.guest.service.FlightService.customFlight(..))")
    public void pointcutVaildDepDate(){

    }

    @Around(value = "pointcutVaildDepDate()")
    public Object aroundFlightInfo(ProceedingJoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
        FlightCenterResult result = new FlightCenterResult();
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        Date depDate = object.getDate("depDate");
        Object proceed = null;
        try {
            if (depDate.before(DateUtils.getDate("yyyy-MM-dd"))) {
                result.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
                result.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
                result.setData(null);
                return JSON.toJSONString(result);
            }else {
                proceed = joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

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

//
//    @Around(value = "pointcutFlightInfo()")
//    public Object aroundFlightInfo(ProceedingJoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
//        System.out.println(joinPoint.getSignature().getName());
//        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
//        FlightCenterApiPo flightCenterApiLog = new FlightCenterApiPo();
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
