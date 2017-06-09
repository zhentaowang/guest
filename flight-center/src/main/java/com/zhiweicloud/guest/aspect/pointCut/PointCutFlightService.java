package com.zhiweicloud.guest.aspect.pointCut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * PointCutFlightService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 16:26
 * @author tiecheng
 */
public class PointCutFlightService {

    @Pointcut(value = "execution(* com.zhiweicloud.guest.service.FlightService.*(..))|| execution(* com.zhiweicloud.guest.service.TrainService.*(..))||execution(* com.zhiweicloud.guest.service.DetrService.*(..))")
    public void pointcutCommon() {

    }

    @Pointcut("execution(* com.zhiweicloud.guest.service.FlightService.*(..))|| execution(* com.zhiweicloud.guest.service.TrainService.*(..))")
    public void pointcutTrafficService() {
    }
    
}
