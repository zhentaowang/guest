package com.zhiweicloud.guest.common;

import com.dragon.sign.DragonApiException;
import com.dragon.sign.DragonSignature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tc on 2017/3/18.
 */
public class UpdateFlightFilter implements Filter{

    private static final Log log = LogFactory.getLog(UpdateFlightFilter.class);

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
            log.info("完整的参数" + dataStr);
            String data = dataStr.substring(dataStr.indexOf("{"), dataStr.indexOf("}") + 1);
            System.out.println("data数据" + dataStr);
            String signIn = dataStr.substring(dataStr.indexOf("&") + 1);
            signIn = signIn.substring(signIn.indexOf("=") + 1);
            signIn = signIn.replace("%2B", "+");
            log.info("参数的sign" + signIn);
            String signRsa = DragonSignature.rsaSign("data=" + data, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8);
            log.info("计算的sign" + signRsa);
            log.info("是否相等" + signIn.equals(signRsa));
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
