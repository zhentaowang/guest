package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.common.util.HttpClientUtils;
import com.zhiweicloud.guest.mapper.FlightPushPoMapper;
import com.zhiweicloud.guest.po.FlightPushPo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * SimplePushRunnable.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 9:12 
 * @author tiecheng
 */
public class SimplePushRunnable implements Runnable {

    private static final Log log = LogFactory.getLog(SimplePushRunnable.class);

    private Long customerId;

    private String url;

    private Map<String, String> params;

    private ScheduledExecutorService executor;

    private FlightPushPoMapper flightPushPoMapper;

    public SimplePushRunnable(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public SimplePushRunnable(String url, Map<String, String> params, ScheduledExecutorService executor,FlightPushPoMapper flightPushPoMapper,Long customerId) {
        this.url = url;
        this.params = params;
        this.executor = executor;
        this.flightPushPoMapper = flightPushPoMapper;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        if (log.isInfoEnabled()) {
            log.info("【 ************ 发送http请求: url_" + url + " ************ 】");
            log.info("【 ************ " + Thread.currentThread().getName() + " 线程被调用 ************ 】");
        }
        try {
            String result = HttpClientUtils.httpPostForWebService(url, params, "json");
            if (log.isInfoEnabled()) {
                log.info("【 ************ " + result + " ************ 】");
            }
            // 都需要记录
            FlightPushPo flightPushPo = new FlightPushPo();
            flightPushPo.setCustomerId(this.customerId);
            if (StringUtils.isBlank(result)) {
                flightPushPo.setInvokeResult(result);
            }
            flightPushPoMapper.insert(flightPushPo);
            if (result == null || JSON.parseObject(result).getInteger("state") != 1) {
                ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(this, 60, 60, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            executor.scheduleAtFixedRate(this, 60, 60, TimeUnit.SECONDS);
        }
    }

}
