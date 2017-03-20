package com.zhiweicloud.guest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dragon.sign.DragonApiException;
import com.dragon.sign.DragonSignature;

import javax.servlet.*;
import java.awt.dnd.DragSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/3/18.
 */
public class UpdateFlightFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        try {
            String dataStr = sb.toString();
            String data = dataStr.substring(dataStr.indexOf("{"), dataStr.indexOf("}") + 1);
            String signIn = dataStr.substring(dataStr.indexOf("&") + 1);
            signIn = signIn.substring(signIn.indexOf("=") + 1);
            System.out.println("参数" + signIn);
            String dataIn = "data=" + data.replace(" ","");
            String signRsa = DragonSignature.rsaSign(dataIn, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
            System.out.println("生成" + signRsa);
            if (signIn.equals(signRsa)){
                request.setAttribute("data",data);
                chain.doFilter(request,response);
            }
        } catch (DragonApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
