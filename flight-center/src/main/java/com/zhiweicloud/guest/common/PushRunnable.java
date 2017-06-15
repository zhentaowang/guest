package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.util.DingDingUtils;
import com.zhiweicloud.guest.common.util.HttpClientDemo;
import com.zhiweicloud.guest.common.util.WebHook;
import com.zhiweicloud.guest.conf.BaseAttributeConfig;
import com.zhiweicloud.guest.mapper.FlightPushPoMapper;
import com.zhiweicloud.guest.po.FlightPushPo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * PushRunnable.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 10:09 
 * @author tiecheng
 */
public class PushRunnable implements Runnable {

    private static final Log log = LogFactory.getLog(PushRunnable.class);

    private WebHook webHook = DingDingUtils.createWebHook(BaseAttributeConfig.webhookTokenMonteDFengshu);

    private String url;

    private Long customerId;

    private Map<String, String> params;

    private ScheduledExecutorService executor;

    private FlightPushPoMapper flightPushPoMapper;

    private int count = 1;

    public PushRunnable(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public PushRunnable(String url, Map<String, String> params, ScheduledExecutorService executor, FlightPushPoMapper flightPushPoMapper, Long customerId) {
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
        boolean isSuccess = false;
        String result = null;
        try {
            result = HttpClientDemo.HttpPostForWebService(url, params,"json");
            this.count ++;
            if (log.isInfoEnabled()) {
                log.info("【 ************ http请求的结果："+ result +" ************ 】");
            }
            // 都需要记录
            FlightPushPo flightPushPo = new FlightPushPo();
            flightPushPo.setCustomerId(this.customerId);
            if (StringUtils.isNoneBlank(result)) {
                flightPushPo.setInvokeResult(result);
            }
            Integer state = JSON.parseObject(result).getInteger("state");
            flightPushPoMapper.insert(flightPushPo);
            if (StringUtils.isNoneBlank(result) &&
                state == 200 &&
                state == 302) {
                isSuccess = true;
                return;
            }
            if(this.count >= 96){
                isSuccess = true;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (!isSuccess) {
                JSONObject object = new JSONObject();
                object.put("请求的URL", url);
                object.put("返回的结果", result);
                webHook.setContent(object.toJSONString());
                webHook.setAtMobiles(new String[]{"18768171164"});
                DingDingUtils.send(webHook);
                executor.schedule(this, 5, TimeUnit.MINUTES);
            }
        }
    }

}
