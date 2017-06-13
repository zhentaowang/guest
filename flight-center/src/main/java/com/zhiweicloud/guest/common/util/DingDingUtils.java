package com.zhiweicloud.guest.common.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URL;

/**
 * Created by tc on 2017/6/13.
 */
public class DingDingUtils {

    public static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=cd7a8daa10868b623ffb871402cc43392a485660276e89adb33ae252d0d0003b";

    public static void send(WebHook webHook){
        try {
            URL url = new URL(webHook.getToken());
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

//            httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

            String textMsg = getMsg(webHook);
            System.out.println(textMsg);

            String result = HttpClientDemo.HttpPostForWebService(uri, textMsg);
            System.out.println(result);

        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getMsg(WebHook webHook) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
            .append("\"msgtype\":\"")
            .append(webHook.getMsgType())
            .append("\",\"text\":{\"content\":")
            .append(webHook.getContent())
            .append("},")
            .append("\"at\":{\"atMobiles\":[");
        String[] atMobiles = webHook.getAtMobiles();
        for (int i = 0; i < atMobiles.length; i++) {
            sb.append("\"").append(atMobiles[i]).append("\"");
        }
        sb.append("],\"isAtAll\":").
            append(webHook.isAtAll()).
            append("}").append("}");
        return sb.toString();
    }

    public static WebHook createWebHook(String token,String content,String[] atMobiles,boolean isAtAll,String msgType){
        WebHook webHook = new WebHook();
        if(StringUtils.isNoneBlank(token)){
            webHook.setToken(token);
        }else {
            return null;
        }
        if(StringUtils.isNoneBlank(content)){
            webHook.setContent(content);
        }else {
            webHook.setContent("内容为空");
        }
        if(atMobiles !=null && atMobiles.length >0){
            webHook.setAtMobiles(atMobiles);
        }
        webHook.setAtAll(isAtAll);
        webHook.setMsgType(msgType);
        return webHook;
    }

    public static WebHook createWebHook(String token,String content){
        return createWebHook(token, content, null, false, "text");
    }

    public static WebHook createWebHook(String token){
        return createWebHook(token, null, null, false, "text");
    }

    public static void main(String[] args) {
        String[] strings = {"18768171164"};
        System.out.println(strings.toString());


        WebHook webHook = createWebHook("https://oapi.dingtalk.com/robot/send?access_token=0a3d9186cd2e2eed6c17deaa2d87abc0b99f3b686d059d9ad12fae9df77563c0", "测试海贼王", new String[]{"18768171164"}, false, "text");
        send(webHook);
    }

}
