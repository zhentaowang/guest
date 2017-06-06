package com.zhiweicloud.guest.common;

import com.zhiweicloud.guest.common.util.HttpClientDemo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Created by tc on 2017/6/6.
 */
public class PushRunnable implements Runnable {

    private static final Log log = LogFactory.getLog(PushRunnable.class);

    private String url;

    private Map<String, String> params;

    public PushRunnable(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    @Override
    public void run() {
        if (log.isInfoEnabled()) {
            log.info("【 ************ 发送http请求: url_" + url + " ************ 】");
        }
        try {
            HttpClientDemo.HttpPostForWebService(url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
