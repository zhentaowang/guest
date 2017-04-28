package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
@Api(value = "权限", description = "权限desc", tags = {"permission"})
public class PermissionController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GET
    @Path("test")
    @Produces("application/json;charset=utf8")
    public String test() {
        return "Hello world";
    }


    /**
     * 角色权限列表 - 分页查询
     * 如果roleId = null，那么返回所有权限
     * @param page   起始页
     * @param rows   每页显示数目
     * @param roleId 角色id
     * @return 分页结果
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
                    @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long", defaultValue = "1", paramType = "query")})
    public String list(@HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId,
                       @QueryParam(value = "page") Integer page,
                       @QueryParam(value = "rows") Integer rows,
                       @QueryParam(value = "roleId") Long roleId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("roleId", roleId);
        LZResult<PaginationResult<Permission>> result = permissionService.getAll(param, page, rows);
        return JSON.toJSONString(result);
    }

    /**
     * 权限列表 - 分页查询
     * @param page   起始页
     * @param rows   每页显示数目
     * @param name   权限名称
     * @param menuName   菜单名称
     * @return 分页结果
     */
    @GET
    @Path("permission-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "权限列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                        @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                        @ApiImplicitParam(name = "name", value = "权限名称", dataType = "String", paramType = "query"),
                        @ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "String", paramType = "query")})
    public String permissionList(@HeaderParam("client-id") String airportCode,
                                 @HeaderParam("user-id") Long userId,
                                 @QueryParam(value = "page") Integer page,
                                 @QueryParam(value = "rows") Integer rows,
                                 @QueryParam(value = "name") String name,
                                 @QueryParam(value = "menuName") String menuName) {

        HashMap<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("name", name);
        param.put("menuName", menuName);
        LZResult<PaginationResult<Permission>> result = permissionService.getPermissionList(param, page, rows);
        return JSON.toJSONString(result);

    }

    /**
     * 数据权限列表 - 分页查询
     * @param page   起始页
     * @param rows   每页显示数目
     * @return 分页结果
     */
    @GET
    @Path("data-permission-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "数据权限列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer", paramType = "query")})
    public String permissionList(@HeaderParam("client-id") String airportCode,
                                 @HeaderParam("user-id") Long userId,
                                 @QueryParam(value = "page") Integer page,
                                 @QueryParam(value = "rows") Integer rows,
                                 @QueryParam(value = "roleId") Integer roleId) {

        HashMap<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("roleId", roleId);
        LZResult<PaginationResult<Permission>> result = permissionService.getDataPermissionList(param, page, rows);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

    }

    /**
     * 权限管理 - 新增or更新
     * 需要判断name是否重复
     * @param params 权限详情
     * @return 成功还是失败
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "权限管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult save(@ApiParam(value = "permission", required = true) @RequestBody RequsetParams<RolePermission> params,
                         @HeaderParam("client-id") String airportCode,
                         @HeaderParam("user-id") Long userId) {
        try {
            RolePermission rolePermission = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                rolePermission = params.getData().get(0);
                rolePermission.setAirportCode(airportCode);
            }
            if (rolePermission == null || rolePermission.getAirportCode() == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
//            if (permissionService.selectByName(permission)) {
//                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
//            }
            permissionService.saveOrUpdate(rolePermission);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 角色权限修改:包括添加和修改
     * @param params 权限列表
     * @return 成功还是失败
     */
    @POST
    @Path("update-role-permission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "角色权限修改:包括添加和修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String updateRolePermission(@ApiParam(value = "permission", required = true) @RequestBody RequsetParams<Permission> params,
                                       @Context final HttpHeaders headers) {
        try {
            List<Permission> permissionList = null;
            if (!CollectionUtils.isEmpty(params.getData())) {
                permissionList = params.getData();
                for (Permission permission : permissionList) {
                    permission.setAirportCode(headers.getRequestHeaders().getFirst("client-id"));
                }
            }
            permissionService.updateRolePermission(permissionList);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 配置权限给角色
     * @param params 权限和角色id
     * @return 成功还是失败
     */
    @POST
    @Path("allocate-permission-to-role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "配置权限给角色", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String allocatePermissionToRole(@ApiParam(value = "permission", required = true) @RequestBody String params,
                                           @HeaderParam("client-id") String airportCode,
                                           @HeaderParam("user-id") Long userId) {
        try {
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            JSONArray permissionIds = param00.getJSONArray("permissionId");
            JSONArray roleIds = param00.getJSONArray("roleId");
            List<RolePermission> rolePermissionList = new ArrayList<>();
            for (Object roleId : roleIds) {
                for(Object permissionId : permissionIds) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(Long.valueOf(roleId.toString()));
                    rolePermission.setPermissionId(Long.valueOf(permissionId.toString()));
                    rolePermission.setAirportCode(airportCode);
                    rolePermissionList.add(rolePermission);
                }
            }
            permissionService.allocatePermissionToRole(rolePermissionList);
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 权限详情 - 根据permissionId查询
     *
     * @param permissionId 权限id
     * @return 权限详情
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "权限详情 - 根据permissionId查询", notes = "返回权限详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "permissionId", value = "权限id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(ContainerRequestContext requestContext,
                       @QueryParam(value = "permissionId") Long permissionId
    ) {
        Map<String, Object> param = new HashMap<>();
        String airportCode = requestContext.getHeaders().getFirst("client-id");
        param.put("airportCode", airportCode);
        param.put("permissionId", permissionId);
        Permission permission = permissionService.getById(param);
        return JSON.toJSONString(permission);
    }

    /**
     * 获取用户权限 - 按钮权限验证
     *
     * @param params json格式的参数
     * @return 权限验证结果
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
    public String getUserPermission(String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            List<String> urls = Collections.singletonList(paramJSON.getString("url"));
            HashMap<String, Object> param = new HashMap<>();
            param.put("airportCode", paramJSON.getString("client_id"));
            param.put("userId", paramJSON.getLong("user_id"));
            if(paramJSON.containsKey("queryOrderType")){
                param.put("orderType", paramJSON.getLong("queryOrderType"));
            }
            return JSON.toJSONString(permissionService.getUserPermission(urls, param));
        } catch (Exception e) {
            logger.error("get permission by urls error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

}
