package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Permission;
import com.zhiweicloud.guest.model.RolePermission;
import com.zhiweicloud.guest.service.PermissionService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value="权限",description="权限desc", tags={"permission"})
public class PermissionController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private PermissionService permissionService;

    /**
     * 角色权限列表 - 分页查询
     * 如果roleId = null，那么返回所有权限
     * @param airportCode
     * @param page
     * @param rows
     * @param roleId
     * @return
     */
    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色权限列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long", defaultValue = "1", required = false, paramType = "query")})
    public String list( @QueryParam(value = "airportCode") String airportCode,
                        @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @QueryParam(value = "roleId") Long roleId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("roleId",roleId);
        LZResult<PaginationResult<Permission>> result  = permissionService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 权限管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="权限管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "permission", required = true) @RequestBody RequsetParams<Permission> params){
        try{
            Permission permission = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                permission = params.getData().get(0);
            }
            if (permission == null || permission.getName() == null || permission.getUrl() == null || permission.getAirportCode() == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            if(permissionService.selectByName(permission) == true){
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            permissionService.saveOrUpdate(permission);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 角色权限修改:包括添加和修改
     * @param params
     * @return
     */
    @POST
    @Path("update-role-permission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="角色权限修改:包括添加和修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public String updateRolePermission(@ApiParam(value = "permission", required = true) @RequestBody RequsetParams<Permission> params){
        try{
            List<Permission> permissionList = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                permissionList = params.getData();
            }
            permissionService.updateRolePermission(permissionList);
            return  JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 权限详情 - 根据permissionId查询
     * @param airportCode
     * @param permissionId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "权限详情 - 根据permissionId查询", notes = "返回权限详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "permissionId", value = "权限id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "airportCode") String airportCode,
                               @QueryParam(value = "permissionId") Long permissionId
                               ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("permissionId",permissionId);
        Permission permission = permissionService.getById(param);
        return JSON.toJSONString(permission);
    }

    /**
     * 获取用户权限 - 按钮权限验证
     * @param airportCode
     * @param userId
     * @param params urls
     * @return
     */
    @POST
    @Path("get-user-permission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "获取用户权限 - 按钮权限验证", notes = "返回权限验证结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", defaultValue = "40", required = true, paramType = "query")
    })
    public String getUserPermission(
            String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            List<String> urls = Arrays.asList(paramJSON.getString("url"));
            HashMap param = new HashMap();
            param.put("airportCode", paramJSON.getString("client_id"));
            param.put("userId", paramJSON.getLong("user_id"));
            return JSON.toJSONString(permissionService.getUserPermission(urls,param));
        } catch (Exception e) {
            logger.error("get permission by urls error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

}
