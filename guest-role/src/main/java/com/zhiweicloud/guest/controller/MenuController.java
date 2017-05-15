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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
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
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

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
     * @author zhngpengfei for thrift
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
    public String menuTree(JSONObject request){
        LZResult<List<SysMenu>> result = new LZResult<>();
        try{
            String airportCode = request.getString("client_id");
            List<SysMenu> list =  sysMenuService.queryAllMenus(airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 菜单管理 - 菜单树
     * 需要判断name是否重复
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
    public String menuTree(ContainerRequestContext request){
        LZResult<List<SysMenu>> result = new LZResult<>();
        try{
            String airportCode = request.getHeaders().getFirst("client-id").toString();
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
     * @param
     * @author zhangpengfei for thrift
     * @return
     */
    @POST
    @Path(value="addMenu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public String addMenu(JSONObject request){
        LZResult<String> result = new LZResult<>();
        try{
            Long userId = Long.valueOf(request.getString("user_id"));
            String airportCode = request.getString("client_id");
            SysMenu sysMenu = null;

            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            sysMenu = JSON.toJavaObject(jsonArray.getJSONObject(0), SysMenu.class);


            if (sysMenu == null || sysMenu.getName() == null || sysMenu.getName().equals("") || sysMenu.getPosition() == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{
                sysMenu.setAirportCode(airportCode);
                if(sysMenu.getMenuId() != null){
                    sysMenu.setUpdateUser(userId);
                    sysMenu.setUpdateTime(new Date());
                }else{
                    sysMenu.setCreateUser(userId);
                    sysMenu.setCreateTime(new Date());
                }
                sysMenuService.saveOrUpdate(sysMenu);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            return this.errorMsg(e);
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
    public String addMenu(@ApiParam(value = "sysMenu", required = true) RequsetParams<SysMenu> params,
                          @Context final HttpHeaders headers){
        LZResult<String> result = new LZResult<>();
        try{
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));

            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            SysMenu sysMenu = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                sysMenu = params.getData().get(0);
            }

            if (sysMenu == null || sysMenu.getName() == null || sysMenu.getName().equals("") || sysMenu.getPosition() == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{
                sysMenu.setAirportCode(airportCode);
                if(sysMenu.getMenuId() != null){
                    sysMenu.setUpdateUser(userId);
                    sysMenu.setUpdateTime(new Date());
                }else{
                    sysMenu.setCreateUser(userId);
                    sysMenu.setCreateTime(new Date());
                }
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
     * author zhangpengfei for thrift
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
    public String deleteMenus(JSONObject request) {
        LZResult<String> result = new LZResult<>();
        try {
            Long userId = Long.valueOf(request.getString("user_id"));
            String airportCode = request.getString("client_id");
            JSONArray jsonarray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonarray.toJSONString(), Long.class);
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
            return this.errorMsg(e);
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
            @RequestBody RequsetParams<Long> params,
            @Context final HttpHeaders headers
           ) {
        LZResult<String> result = new LZResult<>();
        try {
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
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
           return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 菜单管理 - 查询菜单详情
     * @author zhangpengfei for thrift
     * @param
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
    public String viewMenu(JSONObject request) {
        LZResult<SysMenu> result = new LZResult<>();
        try {
            Long menuId = request.getLong("menuId");
            String airportCode = request.getString("client_id");

            SysMenu sysMenu = sysMenuService.getMenuInstanceByUserId(menuId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(sysMenu);
            return JSON.toJSONString(result);
        }catch (Exception e){
           return this.errorMsg(e);
        }
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
            @RequestHeader ContainerRequestContext request) {
        LZResult<SysMenu> result = new LZResult<>();
        try {
            String airportCode = request.getHeaders().getFirst("client-id").toString();
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
     * @author zhangpengfei for thrift
     * @return
     */
    @GET
    @Path(value="getMenuByRoleId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 查询当前角色有哪些菜单", notes ="返回成功还是失败",httpMethod ="GET", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long", required = true, paramType = "query")
    })
    public String getMenuByRoleId(JSONObject request){
        LZResult<List<SysMenu>> result = new LZResult<>();
        try{
            String airportCode = request.getString("client_id");
            Long roleId = request.getLong("roleId");
            List<SysMenu> list =  sysMenuService.queryMenuListByRoleId(airportCode,roleId);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
           return this.errorMsg(e);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 菜单管理 - 查询当前角色有哪些菜单
     * 需要判断name是否重复
     * @return
     */
    @GET
    @Path(value="getMenuByRoleId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="菜单管理 - 查询当前角色有哪些菜单", notes ="返回成功还是失败",httpMethod ="GET", produces="application/json")
    public String getMenuByRoleId(
            ContainerRequestContext request,
            @QueryParam(value = "roleId") Long roleId){
        LZResult<Object> result = new LZResult<>();
        try{
            String airportCode = request.getHeaders().getFirst("client-id").toString();
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
     * @author zhangpengfei for thrift
     * @param
     * @return
     */
    @GET
    @Path(value="getMenuByUserId")
    @Produces("application/json;charset=utf8")
    public String getMenuByUserId(JSONObject request){
        try{
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");
            List<SysMenu> res = sysMenuService.getMenuByUserId(userId,airportCode);
            return JSON.toJSONString(res);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }

    /**
     * 菜单管理 - 查询登录人的菜单
     * @param
     * @return
     */
    @GET
    @Path(value="getMenuByUserId")
    @Produces("application/json;charset=utf8")
    public LZResult<List<SysMenu>> getMenuByUserId(ContainerRequestContext request){
        try{
            Long user_id = Long.valueOf(request.getHeaders().getFirst("user-id").toString());
            String airportCode = request.getHeaders().getFirst("client-id").toString();
            List<SysMenu> result = sysMenuService.getMenuByUserId(Long.valueOf(user_id),airportCode);
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


    /**
     * 统一处理错误信息
     * @param e
     * @return
     */
    private String errorMsg(Exception e){
        e.printStackTrace();
        LZResult result = new LZResult<>();
        result.setMsg(LZStatus.ERROR.display());
        result.setStatus(LZStatus.ERROR.value());
        result.setData(null);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }




}
