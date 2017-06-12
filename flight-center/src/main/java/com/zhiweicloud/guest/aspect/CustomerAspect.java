package com.zhiweicloud.guest.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.CustomerPoMapper;
import com.zhiweicloud.guest.mapper.FlightCenterApiPoMapper;
import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.po.FlightCenterApiPo;
import com.zhiweicloud.guest.signature.RSACoder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.Map;

/**
 * CustomerAspect.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/8 15:49
 * @author tiecheng
 */
@Component
@Aspect
@Order(10)
public class CustomerAspect{

    private static final Log log = LogFactory.getLog(CustomerAspect.class);

    @Autowired
    private CustomerPoMapper customerPoMapper;

    @Autowired
    private FlightCenterApiPoMapper flightCenterApiPoMapper;

    @Around(value = "com.zhiweicloud.guest.aspect.pointCut.PointCutFlightService.pointcutCommon()")
    public Object aroundCustomer(ProceedingJoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            log.info("【 ************ 进入了切面："+ this.getClass().getSimpleName() +" ************ 】");
            log.info("【 ************ 开始校验 - 客户 ************ 】");
        }

        FlightCenterResult flightCenterResult = new FlightCenterResult();
        String name = joinPoint.getSignature().getName();
        JSONObject object = (JSONObject) joinPoint.getArgs()[0];
        CustomerPo customerPo = customerPoMapper.selectBySysCode(object.getString("sysCode"));

        if (log.isInfoEnabled()) {
            log.info("【 ************ 客户："+ customerPo.toString() +" ************ 】");
        }

        boolean isSuccess = false;
        boolean isNull = false;
        Object proceed = null;
        try {
            if (customerPo == null) {
                flightCenterResult.setMessage(FlightCenterStatus.NONE_CUSTOMER.display());
                flightCenterResult.setState(FlightCenterStatus.NONE_CUSTOMER.value());
                flightCenterResult.setData(null);
                isNull = true;
            }else {
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
                        log.info("【 ************ 参数 - 签名："+ sign +" ************ 】");
                        log.info("【 ************ 开始校验 - 签名参数 ************ 】");
                    }

                    if (StringUtils.isBlank(sign)) {
                        flightCenterResult.setMessage(FlightCenterStatus.SIGN_INVALID.display());
                        flightCenterResult.setState(FlightCenterStatus.SIGN_INVALID.value());
                        flightCenterResult.setData(null);
                    } else {
                        sign = URLDecoder.decode(object.getString("sign"), "UTF-8"); // 签名
                        sign = sign.replace(" ", "+"); // 从go过来的代码已经丢失+
                        boolean verify = RSACoder.verify(params, customerPo.getPublicKey(), sign);
                        if (verify) {
                            if (verifyTotal(customerPo.getTotal())) {
                                isSuccess = true;
                            }else {
                                flightCenterResult.setMessage(FlightCenterStatus.NONE_TIME_ENOUGH.display());
                                flightCenterResult.setState(FlightCenterStatus.NONE_TIME_ENOUGH.value());
                                flightCenterResult.setData(null);
                            }
                        }else {
                            flightCenterResult.setMessage(FlightCenterStatus.NOT_AUTH.display());
                            flightCenterResult.setState(FlightCenterStatus.NOT_AUTH.value());
                            flightCenterResult.setData(null);
                        }
                    }

                    if (log.isInfoEnabled()) {
                        log.info("【 ************ 结束校验 - 签名参数 ************ 】");
                    }

                }else {
                    isSuccess = true;
                }

                if (log.isInfoEnabled()) {
                    log.info("【 ************ 结束校验 - 客户 ************ 】");
                    log.info("【 ************ 校验结果：" + isSuccess + " ************ 】");
                }

            }
            FlightCenterApiPo flightCenterApiPo = new FlightCenterApiPo();
            flightCenterApiPo.setApiName(name);
            if(isSuccess){
                // reduce total
                customerPo.setTotal(customerPo.getTotal() - 1);
                customerPoMapper.updateTotal(customerPo);
                proceed = joinPoint.proceed();
                flightCenterApiPo.setCustomerId(customerPo.getCustomerId());
                JSONObject jsonObject = JSON.parseObject(proceed.toString());
                flightCenterApiPo.setInvokeState(jsonObject.getString("state"));
                flightCenterApiPo.setInvokeResult(jsonObject.getString("message"));
                flightCenterApiPoMapper.insert(flightCenterApiPo);
            }else {
                if(!isNull){
                    flightCenterApiPo.setCustomerId(customerPo.getCustomerId());
                }
                flightCenterApiPo.setInvokeState(String.valueOf(flightCenterResult.getState()));
                flightCenterApiPo.setInvokeResult(flightCenterResult.getMessage());
                flightCenterApiPoMapper.insert(flightCenterApiPo);
                return JSON.toJSONString(flightCenterResult);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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
