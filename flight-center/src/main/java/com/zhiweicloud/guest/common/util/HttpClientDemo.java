package com.zhiweicloud.guest.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tc on 2017/6/6.
 */
public class HttpClientDemo {

    private static final PoolingHttpClientConnectionManager pool;

    private static final CloseableHttpClient httpClient;

    static {
        pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(20); // 总的并发量
        pool.setDefaultMaxPerRoute(2); // 单个主机的并发量
        httpClient = HttpClients.custom().setConnectionManager(pool).build();
    }

    public static HttpClient createHttpClient(){
        if(httpClient == null){
            return null;
        }else {
            return httpClient;
        }
    }

    public static String HttpPostForWebService(String url,Map<String,String> params) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        return getWebServiceResult(post);
    }

    public static String HttpPostForWebService(String url,Map<String,String> params,String type) throws Exception {
        HttpPost post = new HttpPost(url);
        if ("json".equals(type)) {
            StringEntity entity = new StringEntity(getJsonParams(params).toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
        }else {
            post.setEntity(new UrlEncodedFormEntity(getNameValuePairs(params), Consts.UTF_8));
        }
        return getWebServiceResult(post);
    }

    public static String HttpPostForWebService(String url,String context) throws Exception {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(context, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        return getWebServiceResult(post);
    }

    public static String HttpPostForWebService(URI uri, String context) throws Exception {
        HttpPost post = new HttpPost(uri);
        StringEntity entity = new StringEntity(context, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        return getWebServiceResult(post);
    }

    private static String getWebServiceResult(HttpRequestBase httpRequestBase) {
        String result = "";
        try(CloseableHttpResponse response = httpClient.execute(httpRequestBase); InputStream inputStream = response.getEntity().getContent()) {
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

    private static JSONObject getJsonParams(Map<String,String> params){
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }
        return object;
    }

    private static final List<NameValuePair> getNameValuePairs(Map<String,String> params){
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

}
