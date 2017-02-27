package com.zhiweicloud.guest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhiweicloud.guest.model.CommonUseEntity;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtils {
    private static Logger log = Logger.getLogger(HttpUtils.class);

    public static String doPost(String url, Map<String, Object> param){
        return doPost(url,param,"application/json;charset=utf-8");
    }

    public static String doPost(String url, Map<String, Object> param,String contentType) {
        long startTime = System.currentTimeMillis();
        StringBuffer result = new StringBuffer();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(CommonUseEntity.POST);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(CommonUseEntity.CONTENT_TYPE, contentType);

            connection.connect();

            out = new PrintWriter(connection.getOutputStream());
            JSONObject args = new JSONObject();
            for (String key : param.keySet()) {
                if (param.get(key) != null) {
                    args.put(key, param.get(key));
                }
            }
            out.print(args.toString());
            out.flush();
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                //return null;
            }
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        } catch (Exception e) {
            // log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        
        try{
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(result.toString());
            if(json != null){
                json.put("time",endTime-startTime);
                return json.toJSONString();
            }
        }catch (JSONException e){
            
        }
        return result.toString();
    }

    public static String doPost(String url, Map<String, Object> param,Map<String, String> requestProperty) {
        long startTime = System.currentTimeMillis();
        StringBuffer result = new StringBuffer();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(CommonUseEntity.POST);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(CommonUseEntity.CONTENT_TYPE, CommonUseEntity.CONTENT_TYPE_VALUE);
            for (String key : requestProperty.keySet()) {
                if (requestProperty.get(key) != null) {
                    connection.setRequestProperty(key, requestProperty.get(key));
                }
            }
            connection.connect();
            out = new PrintWriter(connection.getOutputStream());
            JSONObject args = new JSONObject();
            for (String key : param.keySet()) {
                if (param.get(key) != null) {
                    args.put(key, param.get(key));
                }
            }
            out.print(args.toString());
            out.flush();
            if (connection.getResponseCode() == 200||connection.getResponseCode() == 201) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                //return null;
            }
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        } catch (Exception e) {
            // log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();

        try{
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(result.toString());
            if(json != null){
                json.put("time",endTime-startTime);
                return json.toJSONString();
            }
        }catch (JSONException e){

        }
        return result.toString();
    }

    public static String doGet(String url, Map<String, Object> param) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            StringBuffer args = new StringBuffer();
            boolean flag = false;
            if(param != null){
                for (String key : param.keySet()) {
                    if (param.get(key) != null) {
                        if (flag) {
                            args.append(CommonUseEntity.CONNECTION_SIGN);
                        }
                        args.append(key + CommonUseEntity.EQUAL_SIGN + param.get(key).toString());
                        flag = true;
                    }
                }
            }

//            String urlNameString = url + CommonUseEntity.QUESTION_SIGN + args.toString();
            String urlNameString = "http://airport.zhiweicloud.com/oauth/user/role" + "?" + args.toString();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String doPost(String url, JSONObject jsonObject) {
        long startTime = System.currentTimeMillis();
        StringBuffer result = new StringBuffer();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(CommonUseEntity.POST);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(CommonUseEntity.CONTENT_TYPE, CommonUseEntity.CONTENT_TYPE_VALUE);
            connection.connect();
            out = new PrintWriter(connection.getOutputStream());
            
            out.print(jsonObject.toString());
            out.flush();
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
               // return null;
            }
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            JSONObject resultJson = new JSONObject(result.toString());
            if(resultJson.optString("error")!=""){
                log.error(resultJson.optString("error"));
                return null;
            }
            connection.disconnect();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                log.error(ex.getMessage());
            }
        }
       
        long endTime = System.currentTimeMillis();
        try{
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(result.toString());
            json.put("time",endTime-startTime);
            return json.toJSONString();
        }catch (JSONException e){
            
        }
        return result.toString();
    }

    public static String httpPost(String url,Map<String,Object> params){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> nvps = params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(),  String.valueOf(entry.getValue()))).collect(Collectors.toList());

            request.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));

            CloseableHttpResponse response = httpclient.execute(request);
            byte[] content = EntityUtils.toByteArray(response.getEntity());
            String responseBody = new String(content);

            return responseBody;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpGet(String url,Map<String,Object> params){
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            if(params!=null){
                StringBuffer stringBuffer = new StringBuffer();
                params.keySet().stream().filter(key -> params.get(key) != null).forEach(key -> stringBuffer.append(key + CommonUseEntity.EQUAL_SIGN + params.get(key).toString()+"&"));
                if(url.indexOf("?")!=-1){
                    url = url+stringBuffer.toString();
                }else{
                    url = url+"?"+stringBuffer.toString();
                }
            }

            HttpGet httpget = new HttpGet(url);

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
