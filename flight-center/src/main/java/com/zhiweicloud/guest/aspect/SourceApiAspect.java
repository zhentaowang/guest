package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.Dictionary;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.mapper.SourceApiPoMapper;
import com.zhiweicloud.guest.po.SourceApiPo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SourceApiAspect.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/26 16:09 
 * @author tiecheng
 */
@Component
@Aspect
public class SourceApiAspect {

    private static final Log log = LogFactory.getLog(SourceApiAspect.class);

    @Autowired
    private SourceApiPoMapper sourceApiMapper;

    @Pointcut("execution(* com.zhiweicloud.guest.source.juhe.service.JuheService.*(..))")
    public void pointcutJuheService(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.source.ibe.service.IbeService.*(..))")
    public void pointcutIbeService(){

    }

    @Pointcut("execution(* com.zhiweicloud.guest.source.feeyo.service.FeeyoService.*(..))")
    public void pointcutFeeyoService(){

    }

    @Around(value = "pointcutJuheService()")
    public Object aroundJuheService(ProceedingJoinPoint joinPoint){
        // 方法名
        String name = joinPoint.getSignature().getName();
        log.debug("【source export method name: " + name + "】");
//        JSONObject object = (JSONObject) joinPoint.getArgs()[0];

        SourceApiPo sourceApiPo = new SourceApiPo();
        sourceApiPo.setSourceName("Juhe");
        sourceApiPo.setApiName(name);

        Object proceed = null;

        // 源操作表
        try {
            proceed = joinPoint.proceed();
            JSONObject jsonObject = JSON.parseObject(proceed.toString());
            Integer errorCode = jsonObject.getInteger("error_code");
            if (errorCode == 0) {
                sourceApiPo.setInvokeState(jsonObject.getString("resultcode"));
                sourceApiPo.setInvokeResult(jsonObject.getString("reason"));
            }else {
                sourceApiPo.setInvokeState(String.valueOf(errorCode));
                // 根据错误码拿到内容
                sourceApiPo.setInvokeResult(Dictionary.errorCodeEnum.get(errorCode));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            sourceApiPo.setInvokeState("-1");
            sourceApiPo.setInvokeResult("Flight Center Error");
        }
        sourceApiMapper.insert(sourceApiPo);
        return proceed;
    }

    @Around(value = "pointcutIbeService()")
    public Object aroundIbeService(ProceedingJoinPoint joinPoint){
        // get the point method name, not the really name
        String name = joinPoint.getSignature().getName();
        if (log.isDebugEnabled()) {
            log.debug("【source export method name: " + name + "】");
        }
        SourceApiPo sourceApiPo = new SourceApiPo();
        sourceApiPo.setSourceName("Ibe"); // source name for mark
        sourceApiPo.setApiName(name);

        Object proceed = null;

        try {
            proceed = joinPoint.proceed();
            FlightCenterResult result = (FlightCenterResult) proceed;
            sourceApiPo.setInvokeState(String.valueOf(result.getState()));
            sourceApiPo.setInvokeResult(result.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            sourceApiPo.setInvokeState("-1");
            sourceApiPo.setInvokeResult("Flight Center Error");
        }
        sourceApiMapper.insert(sourceApiPo);
        return proceed;
    }

    @Around(value = "pointcutFeeyoService()")
    public Object aroundFeeyoService(ProceedingJoinPoint joinPoint){
        Object proceed = null;
        return proceed;
    }

}
