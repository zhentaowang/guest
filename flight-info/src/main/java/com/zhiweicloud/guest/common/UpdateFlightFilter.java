package com.zhiweicloud.guest.common;

import javax.servlet.*;
import java.io.IOException;
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
        String sign = new String(request.getParameter("sign").getBytes("iso-8859-1"), "utf-8");
        sign = sign.replaceAll(" ", "+");
        Map<String, String> params = new HashMap<>();
        params.put("sysCode", "dragon");
        if (sign != null) {
            boolean isCheck = FengShuSignature.doCheck(params, sign, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2H3v0l99gO9WoIfn//6erEiVg1v1Sy/07tZMD9oJtzXblBFHga+bsLl4zFHycpDc3MhqZuGUtwfyqIpXroYsrhJF1nCHaRoZJkMEAI89+hfbefVgt4t9Y2LiqrqMJtYZ9q0OjtVVgRh7kfpDnzW803cto/FbQmo3CgI7bHhXikwIDAQAB","UTF-8");
            if (isCheck) {
                chain.doFilter(request,response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
