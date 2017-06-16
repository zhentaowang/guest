package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * DepDateAspect.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/8 16:07
 * @author tiecheng
 */
@Component
@Aspect
@Order(20)
public class DepDateAspect{

    private static final Log log = LogFactory.getLog(DepDateAspect.class);

    @Around(value = "com.zhiweicloud.guest.aspect.pointCut.PointCutFlightService.pointcutFlightService()")
    public Object aroundDepDate(ProceedingJoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            log.info("【 ************ aspect name："+ this.getClass().getSimpleName() +" ************ 】");
            log.info("【 ************ begin verify - depDate ************ 】");
        }

        FlightCenterResult flightCenterResult = new FlightCenterResult();
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        String depDate = object.getString("depDate");

        if (log.isInfoEnabled()) {
            log.info("【 ************ params - depDate："+ depDate +" ************ 】");
        }

        boolean isSuccess = false;
        Object proceed = null;
        try {
            if (StringUtils.isBlank(depDate)) {
                flightCenterResult.setState(FlightCenterStatus.NONE_FLIGHT_DATE.value());
                flightCenterResult.setMessage(FlightCenterStatus.NONE_FLIGHT_DATE.display());
                flightCenterResult.setData(null);
            }else {
                if (!DateUtils.verifyDate(depDate, "yyyy-MM-dd")) {
                    flightCenterResult.setState(FlightCenterStatus.ILLEGAL_DATE.value());
                    flightCenterResult.setMessage(FlightCenterStatus.ILLEGAL_DATE.display());
                    flightCenterResult.setData(null);
                }else {
                    if (DateUtils.stringToDate(depDate, "yyyy-MM-dd").before(DateUtils.getDate("yyyy-MM-dd"))) {
                        flightCenterResult.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
                        flightCenterResult.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
                        flightCenterResult.setData(null);
                    }else {
                        isSuccess = true;
                    }
                }
            }

            if (log.isInfoEnabled()) {
                log.info("【 ************ end verify - depDate ************ 】");
                log.info("【 ************ verify result：" + isSuccess + " ************ 】");
            }

            if (isSuccess) {
                proceed = joinPoint.proceed();
            }else {
                return JSON.toJSONString(flightCenterResult);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

    @Around(value = "com.zhiweicloud.guest.aspect.pointCut.PointCutFlightService.pointcutTrainService()")
    public Object aroundTrainDate(ProceedingJoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            log.info("【 ************ aspect name："+ this.getClass().getSimpleName() +" ************ 】");
            log.info("【 ************ begin verify - depDate ************ 】");
        }

        FlightCenterResult flightCenterResult = new FlightCenterResult();
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        String trainDate = object.getString("trainDate");

        if (log.isInfoEnabled()) {
            log.info("【 ************ params - trainDate："+ trainDate +" ************ 】");
        }

        boolean isSuccess = false;
        Object proceed = null;
        try {
            if (StringUtils.isBlank(trainDate)) {
                flightCenterResult.setState(FlightCenterStatus.NONE_FLIGHT_DATE.value());
                flightCenterResult.setMessage(FlightCenterStatus.NONE_FLIGHT_DATE.display());
                flightCenterResult.setData(null);
            }else {
                if (!DateUtils.verifyDate(trainDate, "yyyy-MM-dd")) {
                    flightCenterResult.setState(FlightCenterStatus.ILLEGAL_DATE.value());
                    flightCenterResult.setMessage(FlightCenterStatus.ILLEGAL_DATE.display());
                    flightCenterResult.setData(null);
                }else {
                    if (DateUtils.stringToDate(trainDate, "yyyy-MM-dd").before(DateUtils.getDate("yyyy-MM-dd"))) {
                        flightCenterResult.setState(FlightCenterStatus.TRAIN_DATE_INVALID.value());
                        flightCenterResult.setMessage(FlightCenterStatus.TRAIN_DATE_INVALID.display());
                        flightCenterResult.setData(null);
                    }else {
                        isSuccess = true;
                    }
                }
            }

            if (log.isInfoEnabled()) {
                log.info("【 ************ end verify - trainDate ************ 】");
                log.info("【 ************ verify result：" + isSuccess + " ************ 】");
            }

            if (isSuccess) {
                proceed = joinPoint.proceed();
            }else {
                return JSON.toJSONString(flightCenterResult);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

}
