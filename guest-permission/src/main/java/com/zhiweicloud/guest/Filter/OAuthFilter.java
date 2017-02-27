package com.zhiweicloud.guest.Filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.model.CommonUseEntity;
import com.zhiweicloud.guest.model.HttpDataLog;
import com.zhiweicloud.guest.model.HttpStatus;
import com.zhiweicloud.guest.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider
@PreMatching
@Priority(CommonUseEntity.AUTH_FILTER_PRIORITY)
public class OAuthFilter implements ContainerRequestFilter, ContainerResponseFilter{
    private final Integer dataListSizeLimit = 30;
    private String permissionUrl;
    private String oauthGetUser;
    protected String remoteAddressHeader = null;
    @Autowired
    private Environment environment;

    @PostConstruct
    public void initParams(){
        if(CommonUseEntity.DEV_ENV.equals(environment.getDefaultProfiles()[0])){
            permissionUrl = CommonUseEntity.DEV_OAUTH_PERMISSION_URL;
            oauthGetUser = CommonUseEntity.DEV_OAUTH_GET_USER;
        }else{
            permissionUrl = CommonUseEntity.OAUTH_PERMISSION_URL;
            oauthGetUser = CommonUseEntity.OAUTH_GET_USER;
        }
    }
    @Context
    private HttpServletRequest servletRequest;

    private Logger log = Logger.getLogger(OAuthFilter.class);

    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (CommonUseEntity.POST.equals(requestContext.getMethod())) {
            String url = requestContext.getUriInfo().getPath();
            if (url.equals("api/upload/image") || url.indexOf("druid") !=-1) {
                return;
            }
//            StringBuilder result = new StringBuilder();
//            BufferedReader in = new BufferedReader(new InputStreamReader(requestContext.getEntityStream(), "utf-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//
//            JSONObject data = JSON.parseObject(result.toString());
//            Map<String,Object> data = new HashMap<>();
            MultivaluedMap<String,String> param = requestContext.getUriInfo().getQueryParameters();
//            data.put(CommonUseEntity.AIRPORTCODE, param.get(CommonUseEntity.AIRPORTCODE));
//            data.put(CommonUseEntity.ACCESSTOKEN,param.get(CommonUseEntity.ACCESSTOKEN));
            JSONObject data = JSON.parseObject(param.toString());
            if (data != null && data.get(CommonUseEntity.ACCESSTOKEN) != null) {
                getUserId(data);
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (CommonUseEntity.POST.equals(requestContext.getMethod())) {
            try {
//                HttpDataLog httpDataLog = (HttpDataLog) requestContext.getProperty("request_info");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * 获取用户id
     * @param data request 数据
     */
    private void getUserId(JSONObject data) {
        try {
            String accessToken = data.getString(CommonUseEntity.ACCESSTOKEN);
            if("".equals(accessToken)){
                return;
            }

            Map<String, Object> param = new HashMap<>();
            param.put(CommonUseEntity.ACCESSTOKEN, accessToken);
            String out = HttpUtils.doGet(oauthGetUser, param);  //根据token获取用户id
            JSONObject output = JSON.parseObject(out);

            if (output != null&&output.get("error")!=null) {
                data.put(CommonUseEntity.USERID, output.get(CommonUseEntity.USERID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查权限
     * @param requestContext request context
     * @param data request数据
     */
    private void checkDataListSize(ContainerRequestContext requestContext, JSONObject data) {
        if (data.getInteger(CommonUseEntity.VIP_LEVEL) == null || data.getInteger(CommonUseEntity.VIP_LEVEL) == 2) {
            Integer size = (data.getInteger(CommonUseEntity.OFFSET) == null ? 0 : data.getInteger(CommonUseEntity.OFFSET)) + (data.getInteger(CommonUseEntity.SIZE) == null ? 0 : data.getInteger(CommonUseEntity.SIZE));
            if (size > dataListSizeLimit) {
                requestContext.abortWith(Response.ok().entity(HttpStatus.NOT_VIP_FAIL.toJSONString()).build());
                return;
            }
        }
    }

    /**
     * 校验用户权限
     * @param authority  权限
     * @param requestContext  requestContext
     * @param data  request数据
     * @param httpDataLog 日志数据
     */
    private void getPermission(String authority, ContainerRequestContext requestContext, JSONObject data, HttpDataLog httpDataLog) {
        String accessToken = data.getString(CommonUseEntity.ACCESSTOKEN);

        if (accessToken == null) {
            requestContext.abortWith(Response.ok().entity(HttpStatus.NO_AUTHORITY.toJSONString()).build());   //没有权限
            return;
        }

        Map<String, Object> param = new HashMap<>();
        param.put(CommonUseEntity.ACCESSTOKEN, accessToken);
        param.put(CommonUseEntity.PERMISSION, authority);
        String out = HttpUtils.doGet(permissionUrl, param);   //校验token和权限

        JSONObject oauth = JSON.parseObject(out);
        if (oauth != null && oauth.getInteger(CommonUseEntity.STATUS) != null && oauth.getInteger(CommonUseEntity.STATUS) != 0) {
            requestContext.abortWith(Response.ok().entity(HttpStatus.NO_AUTHORITY.toJSONString()).build());   //没有权限
            return;
        }

        if (oauth.getString(CommonUseEntity.ERROR) != null) {
            oauth.put(CommonUseEntity.STATUS, CommonUseEntity.DELAY_STATUS);   //网络错误
            requestContext.abortWith(Response.ok().entity(oauth.toJSONString()).build());
            return;
        }

        data.put(CommonUseEntity.VIP_LEVEL, oauth.get(CommonUseEntity.VIP_LEVEL));
        data.put(CommonUseEntity.USERID, oauth.get(CommonUseEntity.USERID));

        httpDataLog.setVipLevel(oauth.getInteger(CommonUseEntity.VIP_LEVEL));
        httpDataLog.setUserId(oauth.getInteger(CommonUseEntity.USERID));
    }

    /**
     * 获取用户浏览器的ip
     * @return
     */
    private String getIP() {
        String remoteAddress = null;
        if(this.remoteAddressHeader != null) {
            remoteAddress = servletRequest.getHeader(this.remoteAddressHeader);
        }

        if(remoteAddress == null) {
            remoteAddress = servletRequest.getRemoteAddr();
        }

        return remoteAddress;
    }
}
