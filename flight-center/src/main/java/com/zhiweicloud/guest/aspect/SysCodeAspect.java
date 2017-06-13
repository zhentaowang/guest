package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.FlightCenterApiPoMapper;
import com.zhiweicloud.guest.po.FlightCenterApiPo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * SysCodeAspect.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 15:38 
 * @author tiecheng
 */
@Component
@Aspect
@Order(1)
public class SysCodeAspect{

    private static final Log log = LogFactory.getLog(SysCodeAspect.class);

    @Autowired
    private FlightCenterApiPoMapper flightCenterApiPoMapper;

    @Around(value = "com.zhiweicloud.guest.aspect.pointCut.PointCutFlightService.pointcutCommon()")
    public Object aroundSysCode(ProceedingJoinPoint joinPoint) {
        FlightCenterResult flightCenterResult = new FlightCenterResult();
        String name = joinPoint.getSignature().getName();

        if (log.isInfoEnabled()) {
            log.info("【 ************ aspect name： "+ this.getClass().getSimpleName() +" ************ 】");
            log.info("【 ************ begin verify - sysCode ************ 】");
        }

        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        String sysCode = object.getString("sysCode");  // 系统码

        if (log.isInfoEnabled()) {
            log.info("【 ************ params - sysCode："+ sysCode +" ************ 】");
        }

        boolean isSuccess = false;
        Object proceed = null;
        try {
            if (StringUtils.isBlank(sysCode)) {
                flightCenterResult.setState(FlightCenterStatus.NONE_SYS_CODE.value());
                flightCenterResult.setMessage(FlightCenterStatus.NONE_SYS_CODE.display());
                flightCenterResult.setData(null);
            }else {
                isSuccess = true;
            }

            if (log.isInfoEnabled()) {
                log.info("【 ************ end verify - sysCode ************ 】");
                log.info("【 ************ verify result：" + isSuccess + " ************ 】");
            }

            if(isSuccess){
                proceed = joinPoint.proceed();
            }else {
                FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
                flightCenterApiPo.setApiName(name);
                flightCenterApiPo.setInvokeState(String.valueOf(FlightCenterStatus.NONE_SYS_CODE.value()));
                flightCenterApiPo.setInvokeResult(FlightCenterStatus.NONE_SYS_CODE.display());
                flightCenterApiPoMapper.insert(flightCenterApiPo);
                return JSON.toJSONString(flightCenterResult);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

}
