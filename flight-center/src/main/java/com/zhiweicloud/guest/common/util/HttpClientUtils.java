package com.zhiweicloud.guest.common.util;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpclient 辅助类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/8 9:36
 * @author tiecheng
 */
public class HttpClientUtils {

    public static final CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }

    public static String HttpGetForWebService(String scheme, String host, String path, Map<String,String> params) throws Exception {
        HttpGet get = new HttpGet(getGetURI(scheme, host, path, getNameValuePairs(params)));
        return getWebServiceResult(get);
    }

    public static String HttpGetForWebService(String scheme,String host,String path,List<NameValuePair> nameValuePairs) throws URISyntaxException, IOException {
        HttpGet get = new HttpGet(getGetURI(scheme,host,path,nameValuePairs));
        return getWebServiceResult(get);
    }

    public static String HttpPostForWebService(String url,Map<String,String> params) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        return getWebServiceResult(post);
    }

    public static String HttpPostForWebService(String scheme,String host,String path,Map<String,String> params) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme,host,path));
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        return getWebServiceResult(post);
    }

    public static String HttpPostForWebService(String scheme,String host,String path,List<NameValuePair> nameValuePairs) throws Exception {
        HttpPost post = new HttpPost(getPostURI(scheme,host,path));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
        return getWebServiceResult(post);
    }

    private static String getWebServiceResult(HttpRequestBase httpRequestBase) {
        String result = "";
        try(CloseableHttpResponse response = httpClient.execute(httpRequestBase);InputStream inputStream = response.getEntity().getContent()) {
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int len;
            while ((len = inputStream.read(bytes))!=-1){
                outSteam.write(bytes, 0, len);
            }
            result = outSteam.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static final URI getGetURI(String scheme, String host, String path, List<NameValuePair> nameValuePairs) throws URISyntaxException {
        return new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .setParameters(nameValuePairs)
                .build();
    }

    private static final URI getPostURI(String scheme, String host, String path) throws URISyntaxException {
        return new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .build();
    }

    private static final List<NameValuePair> getNameValuePairs(Map<String,String> params){
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

}
