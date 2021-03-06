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
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.SysRole;
import com.zhiweicloud.guest.service.SysRoleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value="角色管理",description="", tags={"角色管理"})
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * @author zhangpengfei for thrift
     * @param request
     * @return
     */
    @GET
    @Path(value ="list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功",response = SysRole.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名称", dataType = "String", required = true, paramType = "query")
    })
    public String list(JSONObject request) {
        try {
            SysRole SysRoleParam = new SysRole();
            String name = request.getString("name");
            SysRoleParam.setName(name);
            String airportCode = request.getString("client_id");
            SysRoleParam.setAirportCode(airportCode);
            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");

            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
            }
            LZResult<PaginationResult<SysRole>> result  = sysRoleService.getAll(SysRoleParam,page,rows);
            return JSON.toJSONString(result);
        }catch(Exception e){
           return errorMsg(e);
        }
    }

    @GET
    @Path(value ="list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功",response = SysRole.class)
    })
    public String list(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "name") String name,
            ContainerRequestContext request) {
        try {
            SysRole SysRoleParam = new SysRole();
            SysRoleParam.setName(name);
            String airportCode = request.getHeaders().getFirst("client-id").toString();
            logger.info("airportCode=" + airportCode);
            SysRoleParam.setAirportCode(airportCode);

            LZResult<PaginationResult<SysRole>> result  = sysRoleService.getAll(SysRoleParam,page,rows);
            return JSON.toJSONString(result);
        }catch(Exception e){
            e.printStackTrace();
            LZResult result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return  JSON.toJSONString(result);
        }
    }

    /**
     * @author zhangpengfei for thrift
     * 角色管理 - 新增or更新
     * 需要判断name是否重复
     * @return
     */
    @POST
    @Path(value="saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="角色管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysRole", value = "角色Object", dataType = "SysRole", required = true, paramType = "query")
    })
    public String save(JSONObject request){
        LZResult<String> result = new LZResult<>();
        try{
            String airportCode = request.getString("client_id");
            SysRole sysRole = null;
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            sysRole = JSON.toJavaObject(jsonArray.getJSONObject(0), SysRole.class);
            if (sysRole == null || sysRole.getName() == null || sysRole.getName().equals("")) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }else{
                sysRole.setAirportCode(airportCode);
                //判断是否重名
                int exists = sysRoleService.judgeRepeat(sysRole);
//                时区问题 -- 暂时用数据库自动生成的时间
//                if(sysRole.getRoleId() != null){
//                    sysRole.setUpdateUser(userId);
//                    sysRole.setUpdateTime(new Date());
//                }else{
//                    sysRole.setCreateUser(userId);
//                    sysRole.setCreateTime(new Date());
//                }
                if (exists > 0) {
                    result.setMsg(LZStatus.REPNAM.display());
                    result.setStatus(LZStatus.REPNAM.value());
                    result.setData(sysRole.getName());
                    return JSON.toJSONString(result);
                }
                sysRoleService.saveOrUpdate(sysRole);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
        } catch (Exception e) {
           return this.errorMsg(e);
        }

    }

    /**
     * 角色管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path(value="saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="角色管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public String save(@ApiParam(value = "sysRole", required = true) RequsetParams<SysRole> params,
                       @HeaderParam("client-id")String airportCode,
                       @HeaderParam("user-id") Long userId ){
        LZResult<String> result = new LZResult<>();
        try{
            SysRole sysRole = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                sysRole = params.getData().get(0);
            }
            if (sysRole == null || sysRole.getName() == null || sysRole.getName().equals("")) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }else{
                sysRole.setAirportCode(airportCode);
                //判断是否重名
                int exists = sysRoleService.judgeRepeat(sysRole);
//                时区问题 -- 暂时用数据库自动生成的时间
//                if(sysRole.getRoleId() != null){
//                    sysRole.setUpdateUser(userId);
//                    sysRole.setUpdateTime(new Date());
//                }else{
//                    sysRole.setCreateUser(userId);
//                    sysRole.setCreateTime(new Date());
//                }
                if (exists > 0) {
                    result.setMsg(LZStatus.REPNAM.display());
                    result.setStatus(LZStatus.REPNAM.value());
                    result.setData(sysRole.getName());
                    return JSON.toJSONString(result);
                }
                sysRoleService.saveOrUpdate(sysRole);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }

    }


    /**
     * 角色管理 - 根据id查询员工
     * @author zhangpengfei for thrift
     * @param
     * @return
     */
    @GET
    @Path(value = "view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色管理- 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long", required = true, paramType = "query")
    })
    public String view(JSONObject request) {
        LZResult<SysRole> result = new LZResult<>();
        try {
            Long roleId =  request.getLong("roleId");
            String airportCode = request.getString("client_id");
            SysRole sysRole = sysRoleService.getById(roleId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(sysRole);
            return JSON.toJSONString(result);
        }catch (Exception e){
           return this.errorMsg(e);
        }
    }

    /**
     * 角色管理 - 根据id查询员工
     * @param roleId airportCode
     * @return
     */
    @GET
    @Path(value = "view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色管理- 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")

    public String view(
            @QueryParam(value = "roleId") Long roleId,
            ContainerRequestContext request) {
        LZResult<SysRole> result = new LZResult<>();
        try {
            String airportCode = request.getHeaders().getFirst("client-id").toString();
            SysRole sysRole = sysRoleService.getById(roleId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(sysRole);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * author zhangpengfei for thrift
     * 角色管理 - 删除
     * {
     "data": [
     6,7,8
     ]
     }
     * @return
     */
    @POST
    @Path(value = "deleteRoles")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String deleteRoles(JSONObject request) {
        LZResult<String> result = new LZResult<>();
        try {
            JSONArray jsonarray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonarray.toJSONString(), Long.class);
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");
            String roleName = sysRoleService.deleteById(ids, userId,airportCode);
            if(roleName != null && roleName.length() > 0){
                roleName = roleName + " 角色已经被用户引用了，不能删除！";
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(roleName);
            }else{
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(roleName);
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }

    /**
     * 角色管理 - 删除
     * {
     "data": [
     6,7,8
     ]
     }
     * @return
     */
    @POST
    @Path(value = "deleteRoles")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String deleteRoles(
            @RequestBody RequsetParams<Long> params,@Context final HttpHeaders headers) {
        LZResult<String> result = new LZResult<>();
        try {
            List<Long> ids = params.getData();
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            String roleName = sysRoleService.deleteById(ids, userId,airportCode);
            if(roleName != null && roleName.length() > 0){
                roleName = roleName + " 角色已经被用户引用了，不能删除！";
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(roleName);
            }else{
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(roleName);
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
