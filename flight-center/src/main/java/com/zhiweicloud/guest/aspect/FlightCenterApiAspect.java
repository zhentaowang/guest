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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

/**
 * FlightCenterApiAspect.java
 * 航班中心接口调用切面
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/27 13:43 
 * @author tiecheng
 */
//@Component
//@Aspect
public class FlightCenterApiAspect {

    private static final Log log = LogFactory.getLog(FlightCenterApiAspect.class);

    @Autowired
    private FlightCenterApiPoMapper flightCenterApiPoMapper;

    @Autowired
    private CustomerPoMapper customerPoMapper;

    @Pointcut("execution(* com.zhiweicloud.guest.service.FlightService.*(..))|| execution(* com.zhiweicloud.guest.service.TrainService.*(..))")
    public void pointcutTrafficService(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.service.FlightPushService.flightPush(..))")
    public void pointcutFlightPushService(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.service.DetrService.*(..))")
    public void pointcutDetrService(){

    }

    @Around(value = "pointcutTrafficService()")
    public Object aroundTrafficService(ProceedingJoinPoint joinPoint) {
        FlightCenterResult flightCenterResult = new FlightCenterResult();
        FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
        String name = joinPoint.getSignature().getName();
        Object proceed;
        try {
            if (log.isInfoEnabled()) {
                log.info("【 ************ 航班中心接口: " + name + " 被调用 ************ 】");
            }
            // get params
            JSONObject object = (JSONObject) joinPoint.getArgs()[0];
            if (log.isInfoEnabled()) {
                log.info("【 ************ 请求航班中心接口参数: " + object.toString() + " ************ 】");
            }
            String sysCode = object.getString("sysCode");  // 系统码
            if (log.isInfoEnabled()) {
                log.info("【 ************ 客户码: " + sysCode + " ************ 】");
            }
            if (StringUtils.isBlank(sysCode)) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_SYS_CODE.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_SYS_CODE.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }
            if (log.isInfoEnabled()) {
                log.info("【 ************ 获取客户信息 ************ 】");
            }
            CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);
            if (customerPo == null) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_CUSTOMER.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_CUSTOMER.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }
            if (log.isInfoEnabled()) {
                log.info("【 ************ 查询的客户对象: " + customerPo.toString() + " ************ 】");
            }
            // 客户存在，不管请求参数如何，都进行请求的记录
            flightCenterApiPo.setApiName(name);
            flightCenterApiPo.setCustomerId(customerPo.getCustomerId());
            if (log.isInfoEnabled()) {
                log.info("【 ************ 进行航班日期校验 ************ 】");
            }
            String depDate = object.getString("depDate");
            if (StringUtils.isBlank(depDate)) {
                flightCenterResult.setState(FlightCenterStatus.NONE_FLIGHT_DATE.value());
                flightCenterResult.setMessage(FlightCenterStatus.NONE_FLIGHT_DATE.display());
                flightCenterResult.setData(null);
                flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NONE_FLIGHT_DATE.value()));
                flightCenterApiPo.setInvokeResult(FlightCenterStatus.NONE_FLIGHT_DATE.display());
                flightCenterApiPoMapper.insert(flightCenterApiPo);
                return JSON.toJSONString(flightCenterResult);
            }
            if (!DateUtils.verifyDate(depDate, "yyyy-MM-dd")) {
                flightCenterResult.setState(FlightCenterStatus.ILLEGAL_DATE.value());
                flightCenterResult.setMessage(FlightCenterStatus.ILLEGAL_DATE.display());
                flightCenterResult.setData(null);
                flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.ILLEGAL_DATE.value()));
                flightCenterApiPo.setInvokeResult(FlightCenterStatus.ILLEGAL_DATE.display());
                flightCenterApiPoMapper.insert(flightCenterApiPo);
                return JSON.toJSONString(flightCenterResult);
            }
            if (DateUtils.stringToDate(depDate, "yyyy-MM-dd").before(DateUtils.getDate("yyyy-MM-dd"))) {
                flightCenterResult.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
                flightCenterResult.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
                flightCenterResult.setData(null);
                flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.FLIGHT_DATE_INVALID.value()));
                flightCenterApiPo.setInvokeResult(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
                flightCenterApiPoMapper.insert(flightCenterApiPo);
                return JSON.toJSONString(flightCenterResult);
            }
            if (log.isInfoEnabled()) {
                log.info("【 ************ 日期校验通过 ************ 】");
            }
            if (customerPo.getType() != 0) {
                Map<String, Object> params = new HashedMap();
                // 剔除不需要的参数
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    if (!"sign".equals(entry.getKey()) && !"operation".equals(entry.getKey()) && !"access_token".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }
                String sign = object.getString("sign");
                if (log.isInfoEnabled()) {
                    log.info("【 ************ 客户原签名: " + sign + " ************ 】");
                }
                if (sign == null || "".equals(sign)) {
                    flightCenterResult.setMessage(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterResult.setState(FlightCenterStatus.SIGN_INVALID.value());
                    flightCenterResult.setData(null);
                    flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.SIGN_INVALID.value()));
                    flightCenterApiPo.setInvokeResult(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterApiPoMapper.insert(flightCenterApiPo);
                    return JSON.toJSONString(flightCenterResult);
                } else {
                    sign = URLDecoder.decode(object.getString("sign"), "UTF-8"); // 签名
                    sign = sign.replace(" ", "+"); // 从go过来的代码已经丢失+
                    if (log.isInfoEnabled()) {
                        log.info("【 ************ URL解码后的签名: " + sign + " ************ 】");
                    }
                    boolean verify = RSACoder.verify(params, customerPo.getPublicKey(), sign);
                    if (log.isInfoEnabled()) {
                        log.info("【 ************ 签名校验结果: " + verify + " ************ 】");
                    }
                    if (!verify) {
                        flightCenterResult.setMessage(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterResult.setState(FlightCenterStatus.NOT_AUTH.value());
                        flightCenterResult.setData(null);
                        flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NOT_AUTH.value()));
                        flightCenterApiPo.setInvokeResult(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterApiPoMapper.insert(flightCenterApiPo);
                        return JSON.toJSONString(flightCenterResult);
                    }
                }
                if (!verifyTotal(customerPo.getTotal())) {
                    flightCenterResult.setMessage(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterResult.setState(FlightCenterStatus.NONE_TIME_ENOUGH.value());
                    flightCenterResult.setData(null);
                    flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NONE_TIME_ENOUGH.value()));
                    flightCenterApiPo.setInvokeResult(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterApiPoMapper.insert(flightCenterApiPo);
                    return JSON.toJSONString(flightCenterResult);
                }
                // reduce total
                customerPo.setTotal(customerPo.getTotal() - 1);
                customerPoMapper.updateTotal(customerPo);
            }
            proceed = joinPoint.proceed();
            JSONObject jsonObject = JSON.parseObject(proceed.toString());
            if (log.isInfoEnabled()) {
                log.info("【 ************ 航班中心接口执行的结果: " + jsonObject + " ************ 】");
            }
            flightCenterApiPo.setInvokeState(jsonObject.getString("state"));
            flightCenterApiPo.setInvokeResult(jsonObject.getString("message"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            flightCenterResult.setState((FlightCenterStatus.ERROR.value()));
            flightCenterResult.setMessage(FlightCenterStatus.ERROR.display());
            flightCenterResult.setData(null);
            flightCenterApiPo.setInvokeState("-1");
            flightCenterApiPo.setInvokeResult(throwable.getCause().getMessage());
            flightCenterApiPoMapper.insert(flightCenterApiPo);
            return JSON.toJSONString(flightCenterResult);
        }
        flightCenterApiPoMapper.insert(flightCenterApiPo);
        return proceed;
    }

    @Around(value = "pointcutFlightPushService()")
    public Object aroundFlightPush(ProceedingJoinPoint joinPoint){
        FlightCenterResult flightCenterResult = new FlightCenterResult();
        String name = joinPoint.getSignature().getName();
        Object proceed = null;
        FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
        try{
            if (log.isInfoEnabled()) {
                log.info("【 ************ 航班中心接口: " + name + " 被调用 ************ 】");
            }
            proceed = joinPoint.proceed();
            flightCenterResult.setState((FlightCenterStatus.SUCCESS.value()));
            flightCenterResult.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            flightCenterResult.setState((FlightCenterStatus.ERROR.value()));
            flightCenterResult.setMessage(FlightCenterStatus.ERROR.display());
            flightCenterResult.setData(null);
            flightCenterApiPo.setInvokeState("-1");
            flightCenterApiPo.setInvokeResult(throwable.getCause().getMessage());
            flightCenterApiPoMapper.insert(flightCenterApiPo);
        }
        return proceed;
    }

    @Around(value = "pointcutDetrService()")
    public Object aroundDetrService(ProceedingJoinPoint joinPoint){
        FlightCenterResult flightCenterResult = new FlightCenterResult();
        FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
        String name = joinPoint.getSignature().getName();
        Object proceed;
        try {
            if (log.isInfoEnabled()) {
                log.info("【 ************ 航班中心接口: " + name + " 被调用 ************ 】");
            }
            // get params
            JSONObject object = (JSONObject) joinPoint.getArgs()[0];
            if (log.isInfoEnabled()) {
                log.info("【 ************ 请求航班中心接口参数: " + object.toString() + " ************ 】");
            }
            String sysCode = object.getString("sysCode");  // 系统码
            if (log.isInfoEnabled()) {
                log.info("【 ************ 客户码: " + sysCode + " ************ 】");
            }
            if (StringUtils.isBlank(sysCode)) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_SYS_CODE.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_SYS_CODE.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }
            if (log.isInfoEnabled()) {
                log.info("【 ************ 获取客户信息 ************ 】");
            }
            CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);
            if (customerPo == null) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_CUSTOMER.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_CUSTOMER.value());
                flightCenterResult.setData(null);
                return JSON.toJSONString(flightCenterResult);
            }
            if (log.isInfoEnabled()) {
                log.info("【 ************ 查询的客户对象: " + customerPo.toString() + " ************ 】");
            }
            // 客户存在，不管请求参数如何，都进行请求的记录
            flightCenterApiPo.setApiName(name);
            flightCenterApiPo.setCustomerId(customerPo.getCustomerId());
            if (log.isInfoEnabled()) {
                log.info("【 ************ 进行航班日期校验 ************ 】");
            }
            if (customerPo.getType() != 0) {
                Map<String, Object> params = new HashedMap();
                // 剔除不需要的参数
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    if (!"sign".equals(entry.getKey()) && !"operation".equals(entry.getKey()) && !"access_token".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }
                String sign = object.getString("sign");
                if (log.isInfoEnabled()) {
                    log.info("【 ************ 客户原签名: " + sign + " ************ 】");
                }
                if (sign == null || "".equals(sign)) {
                    flightCenterResult.setMessage(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterResult.setState(FlightCenterStatus.SIGN_INVALID.value());
                    flightCenterResult.setData(null);
                    flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.SIGN_INVALID.value()));
                    flightCenterApiPo.setInvokeResult(FlightCenterStatus.SIGN_INVALID.display());
                    flightCenterApiPoMapper.insert(flightCenterApiPo);
                    return JSON.toJSONString(flightCenterResult);
                } else {
                    sign = URLDecoder.decode(object.getString("sign"), "UTF-8"); // 签名
                    sign = sign.replace(" ", "+"); // 从go过来的代码已经丢失+
                    if (log.isInfoEnabled()) {
                        log.info("【 ************ URL解码后的签名: " + sign + " ************ 】");
                    }
                    boolean verify = RSACoder.verify(params, customerPo.getPublicKey(), sign);
                    if (log.isInfoEnabled()) {
                        log.info("【 ************ 签名校验结果: " + verify + " ************ 】");
                    }
                    if (!verify) {
                        flightCenterResult.setMessage(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterResult.setState(FlightCenterStatus.NOT_AUTH.value());
                        flightCenterResult.setData(null);
                        flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NOT_AUTH.value()));
                        flightCenterApiPo.setInvokeResult(FlightCenterStatus.NOT_AUTH.display());
                        flightCenterApiPoMapper.insert(flightCenterApiPo);
                        return JSON.toJSONString(flightCenterResult);
                    }
                }
                if (!verifyTotal(customerPo.getTotal())) {
                    flightCenterResult.setMessage(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterResult.setState(FlightCenterStatus.NONE_TIME_ENOUGH.value());
                    flightCenterResult.setData(null);
                    flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NONE_TIME_ENOUGH.value()));
                    flightCenterApiPo.setInvokeResult(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                    flightCenterApiPoMapper.insert(flightCenterApiPo);
                    return JSON.toJSONString(flightCenterResult);
                }
                // reduce total
                customerPo.setTotal(customerPo.getTotal() - 1);
                customerPoMapper.updateTotal(customerPo);
            }
            proceed = joinPoint.proceed();
            JSONObject jsonObject = JSON.parseObject(proceed.toString());
            if (log.isInfoEnabled()) {
                log.info("【 ************ 航班中心接口执行的结果: " + jsonObject + " ************ 】");
            }
            flightCenterApiPo.setInvokeState(jsonObject.getString("state"));
            flightCenterApiPo.setInvokeResult(jsonObject.getString("message"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            flightCenterResult.setState((FlightCenterStatus.ERROR.value()));
            flightCenterResult.setMessage(FlightCenterStatus.ERROR.display());
            flightCenterResult.setData(null);
            flightCenterApiPo.setInvokeState("-1");
            flightCenterApiPo.setInvokeResult(throwable.getCause().getMessage());
            flightCenterApiPoMapper.insert(flightCenterApiPo);
            return JSON.toJSONString(flightCenterResult);
        }
        flightCenterApiPoMapper.insert(flightCenterApiPo);
        return proceed;
    }

    private boolean verifyTotal(Long total){
        if (total <= 0) {
            return false;
        }else {
            return true;
        }
    }

}
