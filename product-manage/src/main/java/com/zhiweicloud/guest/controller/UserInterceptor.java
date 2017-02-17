package com.zhiweicloud.guest.controller;

/**
 * Created by wzt on 2017/1/20.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 对来自后台的请求统一进行日志处理
         */
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        ServletInputStream temp = request.getInputStream();
        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));

        /**
         * 校验权限get:/list
         */

//        Map<String,Object> map = new HashMap<>();
//        map.put("airportCode",request.getParameter("airportCode"));
//        map.put("account","010");//员工账号
//        map.put("url",request.getRequestURI());
//        if(request.getRequestURI().equals("/list")){
//            //request.getSession().setAttribute("productCategory","VIP");
//            List<Permission> permissionList = permissionMapper.getPermission(map);
//            if(permissionList != null){
//                for(int i = 0; i < permissionList.size(); i++){
//                    if(permissionList.get(i).getAuthorizer() != null){
//                        request.getSession().setAttribute(permissionList.get(i).getAuthorizer().split("=")[0],permissionList.get(i).getAuthorizer().split("=")[1]);
//                    }
//                }
//                return true;
//            }
//            else{
//                return false;
//            }
//        }
//        else{
//            return false;
//        }


//        /**
//         * 校验权限post:/delete
//         */
//        if(uri.equals("/delete")){
//            request.getSession().setAttribute("productTypeAllocationId",1);
//        }
//        return true;

        /**
         * 校验用户权限
         * @param authority  权限
         * @param requestContext  requestContext
         * @param data  request数据
         * @param httpDataLog 日志数据
         */
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> param = new HashMap<>();
        if(request.getParameter("access_token") == null){
            return false;
        }
        else{
            if(request.getRequestURI().equals("/guest-product/list")){
                param.put("access_token", request.getParameter("access_token"));
                try {
                    StringBuffer args = new StringBuffer();
                    boolean flag = false;
                    if(param != null){
                        for (String key : param.keySet()) {
                            if (param.get(key) != null) {
                                if (flag) {
                                    args.append("&");
                                }
                                args.append(key + "=" + param.get(key).toString());
                                flag = true;
                            }
                        }
                    }

                    String urlNameString = "http://airport.zhiweicloud.com/oauth/user/role" + "?" + args.toString();
//            String urlNameString = "http://airport.zhiweicloud.com/oauth/user/role?access_token=T5gQYNOa6bAIFER38We8b6Zxll4EFE78orJlgjq6";
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
                JSONObject oauth = JSON.parseObject(result.toString());
                oauth.getString("id");
                if(Integer.parseInt(oauth.getString("permission")) == 2){//2为普通用户
                    request.getSession().setAttribute("productCategory","VIP");
                }
                oauth.getString("permission");
                request.getSession().setAttribute("userId",oauth.getString("id"));
            }
            return true;
        }
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}