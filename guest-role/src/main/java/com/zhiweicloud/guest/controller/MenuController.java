/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.SysMenu;
import com.zhiweicloud.guest.service.SysMenuService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value="菜单管理",description="", tags={"菜单管理"})
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单管理 - 菜单树
     * 需要判断name是否重复
     * @param airportCode
     * @return
     */
    @GET
    @Path(value="menuTree")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 菜单树", notes ="返回成功还是失败",httpMethod ="GET", produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功",response = SysMenu.class)
    })
    public String menuTree(@QueryParam(value = "airportCode") String airportCode){
        LZResult<List<SysMenu>> result = new LZResult<>();
        try{
            Map map = new HashMap();

            map.put("airportCode","LJG");
            map.put("userId",40);

            String objectStr = "{\"data\":[\"menuTree\"]}";

            map.put("body",objectStr);

            HttpClientUtil.httpPostRequest("http://192.168.1.130:8081/get-user-permission?userId=40&airportCode=LJG",map);

            List<SysMenu> list =  sysMenuService.queryAllMenus(airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 菜单管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path(value="addMenu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public String addMenu(@ApiParam(value = "sysMenu", required = true) RequsetParams<SysMenu> params){
        LZResult<String> result = new LZResult<>();
        try{
            SysMenu sysMenu = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                sysMenu = params.getData().get(0);
            }

            if (sysMenu == null || sysMenu.getName() == null || sysMenu.getName().equals("") || sysMenu.getPosition() == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{
                sysMenuService.saveOrUpdate(sysMenu);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 菜单管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     *
     * @return
     */
    @POST
    @Path("deleteMenus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "菜单管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String deleteMenus(
            @QueryParam("airportCode") String airportCode,
            @RequestBody RequsetParams<Long> params) {
        LZResult<String> result = new LZResult<>();
        try {
            List<Long> ids = params.getData();
            String menuName = sysMenuService.deleteById(ids, airportCode);
            if(menuName != null && menuName.length() > 0){
                menuName = menuName + " 已经有角色引用了，不能删除！";
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(menuName);
            }else{
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 菜单管理 - 查询菜单详情
     * @param menuId
     * @return
     */
    @GET
    @Path(value = "viewMenu")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "菜单管理 - 查询菜单详情 ", notes = "返回查询菜单详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "员工id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public String viewMenu(
            @QueryParam(value = "menuId") Long menuId,
            @QueryParam(value = "airportCode") String airportCode) {
        LZResult<SysMenu> result = new LZResult<>();
        try {
            SysMenu sysMenu = sysMenuService.getMenuInstanceByUserId(menuId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(sysMenu);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }


    /**
     * @TODO,两个菜单交换顺序还需要做
     * 1,默认会同时添加对应的权限
     * 2,需要 menu_permission
     * 3,从header里面获取user_id,airportCode 你可以试一下@HeaderParam 注解 或者 request.getHeader("user_id")
     * 4,判断当前人是否能添加，能删除，能展示列表，需要从permission表中去获取一个状态
     */

    /**
     * 菜单管理 - 查询当前角色有哪些菜单
     * 需要判断name是否重复
     * @param airportCode
     * @return
     */
    @GET
    @Path(value="getMenuByRoleId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 查询当前角色有哪些菜单", notes ="返回成功还是失败",httpMethod ="GET", produces="application/json")
    public String getMenuByRoleId(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "roleId") Long roleId){
        LZResult<List<SysMenu>> result = new LZResult<>();
        try{
            List<SysMenu> list =  sysMenuService.queryMenuListByRoleId(airportCode,roleId);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 菜单管理 - 查询登录人的菜单
     * @param
     * @return
     */
    @GET
    @Path(value="getMenuByUserId")
    @Produces("application/json;charset=utf8")
    public LZResult<List<SysMenu>> getMenuByUserId( HttpServletRequest request,
                                                    @QueryParam(value = "access_token") String access_token){
        try{
            //@TODO:权限过滤
//            String userIdStr = (String)request.getSession().getAttribute("userId");
            //String userIdAndAirportCode = HttpClientUtil.httpGetRequest("http://airport.zhiweicloud.com/oauth/user/getUser?access_token=" + access_token);
            //JSONObject oauth = JSON.parseObject(userIdAndAirportCode);
            String user_id = request.getHeader("user_id");
            String airporeCode = request.getHeader("client_id");
            System.out.println("===user_id:" + user_id +" ==airporeCode" + airporeCode) ;
            List<SysMenu> result = sysMenuService.getMenuByUserId(Long.valueOf(user_id),airporeCode);
            return new LZResult<>(result);
        } catch (Exception e) {
            e.printStackTrace();
            LZResult<List<SysMenu>> result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return result;
        }
    }







}
