package com.zhiweicloud.productmanage.controller;

/**
 * Created by wzt on 2017/1/20.
 */
import com.zhiweicloud.productmanage.mapper.PermissionMapper;
import com.zhiweicloud.productmanage.model.Permission;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);
    @Autowired
    private PermissionMapper permissionMapper;

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

//        //请求的路径
//        String contextPath=request.getContextPath();
//        String	url=request.getServletPath().toString();
//        HttpSession session = request.getSession();
//        String user = (String) session.getAttribute("seesion_member");
//        //这里可以根据session的用户来判断角色的权限，根据权限来重定向不同的页面，简单起见，这里只是做了一个重定向
//        if (StringUtils.isEmpty(user)) {
//            //被拦截，重定向到login界面
//            response.sendRedirect(contextPath+"/login.htm?redirectURL="
//                    + URLEncoder.encode(url));
//            return false;
//        }

        /**
         * 对来自后台的请求统一进行日志处理
         */
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
        /**
         * 校验权限get:/list
         */
        if(request.getRequestURI().equals("/list")){
            request.getSession().setAttribute("productCategory","VIP");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("airportCode","LJG");
        map.put("account","010");//员工账号
        map.put("uri","/list");
//        List<Permission> permissionList = permissionMapper.selectByAccount(map);
//        if(permissionList != null){
//            for(int i = 0; i < permissionList.size(); i++){
//                request.getSession().setAttribute(permissionList.get(i).getAuthorizer().split("=")[0],permissionList.get(i).getAuthorizer().split("=")[1]);
//            }
//            return true;
//        }
//        else{
//            return false;
//        }

        /**
         * 校验权限post:/delete
         */
        if(uri.equals("/delete")){
            request.getSession().setAttribute("productTypeAllocationId",1);
        }
        return true;
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