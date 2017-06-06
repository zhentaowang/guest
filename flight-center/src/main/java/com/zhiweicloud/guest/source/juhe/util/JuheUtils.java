package com.zhiweicloud.guest.source.juhe.util;

import com.zhiweicloud.guest.common.util.HttpClientUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * JuheUtils.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/25 9:47
 * @author tiecheng
 */
public class JuheUtils {

    private static final Log log = LogFactory.getLog(JuheUtils.class);

    private static final String JUHE_KEY = "27872a4475ef203c724b8b69ee31bb60";

    private static final String JUHE_HOST = "apis.juhe.cn";

    public static String queryTrainInfoByStation(String start,String end,String date) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("【  ************ JUHE 方法名：根据出发/到达车站和日期查找火车信息（queryTrainInfoByStation） 参数名：start_" + start + " end_" + end + " date_ " + date + " ************ 】");
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("key", JUHE_KEY));
        nameValuePairs.add(new BasicNameValuePair("start", start));
        nameValuePairs.add(new BasicNameValuePair("end", end));
        nameValuePairs.add(new BasicNameValuePair("date", date));
        nameValuePairs.add(new BasicNameValuePair("dtype", ""));
        return HttpClientUtils.HttpGetForWebService("http", JUHE_HOST, "/train/s2swithprice", nameValuePairs);
    }

    public static String queryTrainInfoByName(String name) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("【 ************ JUHE 方法名：根据班次名查找火车信息（queryTrainInfoByName 参数名：name_" + name + " ************ 】");
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("key", JUHE_KEY));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("dtype", ""));
        return HttpClientUtils.HttpGetForWebService("http", JUHE_HOST, "/train/s", nameValuePairs);
    }

}
