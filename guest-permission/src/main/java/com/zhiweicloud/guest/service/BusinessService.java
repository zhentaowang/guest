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

package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.PermissionMapper;
import com.zhiweicloud.guest.mapper.RolePermissionMapper;
import com.zhiweicloud.guest.model.Permission;
import com.zhiweicloud.guest.model.RolePermission;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import jersey.repackaged.com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * PermissionMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * Created by wzt on 05/05/2017.
 */
@Service
public class BusinessService implements IBusinessService {

    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public BusinessService(PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper) {
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;
        /*String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }*/

        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "permission-list":
                success = permissionList(request);
                break;
            case "data-permission-list":
                success = dataPermissionList(request);
                break;
            case "save-or-update":
                success = save(request);
                break;
            case "update-role-permission":
                success = updateRolePermission(request);
                break;
            case "allocate-permission-to-role":
                success = allocatePermissionToRole(request);
                break;
            case "view":
                success = view(request);
                break;
            case "get-user-permission":
                success = getUserPermission(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

    /**
     * 角色权限列表 - 分页查询
     * 如果roleId = null，那么返回所有权限
     * @para page   起始页
     * @para rows   每页显示数目
     * @para roleId 角色id
     * @return 分页结果
     */
    public String list(JSONObject request) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("roleId",  request.getLong("roleId"));
        param.put("airportCode", request.getString("client_id"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int count;
        List<Permission> permissionList;
        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));

        if(param.get("roleId") == null){
            count = permissionMapper.getListCount(param);
            permissionList = permissionMapper.getListByConidition(queryCondition);
        }else {
            count = permissionMapper.getListCountByRole(param);
            permissionList = permissionMapper.getListByRole(queryCondition);
        }

        PaginationResult<Permission> eqr = new PaginationResult<>(count, permissionList);
        LZResult<PaginationResult<Permission>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);
    }

    /**
     * 权限列表 - 分页查询
     * @para page   起始页
     * @para rows   每页显示数目
     * @para name   权限名称
     * @para menuName   菜单名称
     * @return 分页结果
     */
    public String permissionList(JSONObject request) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("name", request.getString("name"));
        param.put("menuName", request.getString("menuName"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int count = permissionMapper.getListCount(param);
        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Permission> permissionList = permissionMapper.getListByConidition(queryCondition);
        PaginationResult<Permission> eqr = new PaginationResult<>(count, permissionList);
        return JSON.toJSONString(new LZResult<>(eqr));
    }

    /**
     * 数据权限列表 - 分页查询
     * @para page   起始页
     * @para rows   每页显示数目
     * @return 分页结果
     */
    public String dataPermissionList(JSONObject request) {
        System.out.println("500 yes or no");
        HashMap<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));

        Integer count = permissionMapper.getDataListCount(param);

        List<Permission> permissionList = permissionMapper.getDataListByConidition(queryCondition);

        PaginationResult<Permission> eqr = new PaginationResult<>(count, permissionList);
        LZResult<PaginationResult<Permission>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);
    }

    /**
     * 权限管理 - 新增or更新
     * 需要判断name是否重复
     * @para params 权限详情
     * @return 成功还是失败
     */
    public String save(JSONObject request) {
        try {
            RolePermission rolePermission = null;
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            rolePermission = JSON.toJavaObject(jsonArray.getJSONObject(0), RolePermission.class);
            rolePermission.setAirportCode(request.getString("client_id"));

            if (rolePermission == null || rolePermission.getAirportCode() == null) {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
            }
            if (rolePermission.getPermissionId() != null) {
                rolePermissionMapper.updateByIdAndAirportCode(rolePermission);

            } else {
                rolePermission.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                rolePermission.setCreateTime(new Date());
                rolePermission.setUpdateTime(new Date());
//            permissionMapper.insertSelective(permission);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 角色权限修改:包括添加和修改
     * @para params 权限列表
     * @return 成功还是失败
     */
    public String updateRolePermission(JSONObject request) {
        try {
            List<Permission> permissionList = new ArrayList<>();
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            for (int i = 0; i < jsonArray.size(); i++) {
                Permission permission = JSON.toJavaObject(jsonArray.getJSONObject(i), Permission.class);
                permission.setAirportCode(request.getString("client_id"));
                permissionList.add(permission);
            }

            Map<String,Object> params = new HashMap<>();
            StringBuffer ids = new StringBuffer();
            if(permissionList != null){
                params.put("airportCode",permissionList.get(0).getAirportCode());
                params.put("roleId",permissionList.get(0).getRoleId());
                for(int i = 0; i < permissionList.size(); i++){
                    Permission permission = permissionList.get(i);
                    if(permission.getRolePermissionId() != null){
                        ids.append(permission.getRolePermissionId()+",");
                    }else{
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setAirportCode(permission.getAirportCode());
                        rolePermission.setRoleId(permission.getRoleId());
                        rolePermission.setPermissionId(permission.getPermissionId());
                        rolePermission.setCreateTime(new Date());
                        rolePermission.setUpdateTime(new Date());
                        rolePermission.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        rolePermissionMapper.insertBySelective(rolePermission);
                        ids.append(rolePermission.getRolePermissionId()+",");
                    }
                }
                if(ids.length() != 0){
                    params.put("ids",ids.substring(0,ids.length() - 1));
                    rolePermissionMapper.deleteByIdAndAirportCode(params);
                }
                else{
                    params.put("ids",ids.append(0));
                    rolePermissionMapper.deleteByIdAndAirportCode(params);
                }
            }
            else{
                params.put("ids",ids.append(0));
                rolePermissionMapper.deleteByIdAndAirportCode(params);
            }

            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 配置权限给角色
     * @para params 权限和角色id
     * @return 成功还是失败
     */
    public String allocatePermissionToRole(JSONObject request) {
        try {
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            JSONObject param00 = jsonArray.getJSONObject(0);
            JSONArray permissionIds = param00.getJSONArray("permissionId");
            JSONArray roleIds = param00.getJSONArray("roleId");
            List<RolePermission> rolePermissionList = new ArrayList<>();
            for (Object roleId : roleIds) {
                for(Object permissionId : permissionIds) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(Long.valueOf(roleId.toString()));
                    rolePermission.setPermissionId(Long.valueOf(permissionId.toString()));
                    rolePermission.setAirportCode(request.getString("client_id"));
                    rolePermissionList.add(rolePermission);
                }
            }

            if(rolePermissionList != null){
                Map<String, Object> param = new HashMap<>();
                for(RolePermission rolePermission: rolePermissionList){
                    param.put("airportCode",rolePermission.getAirportCode());
                    param.put("roleId",rolePermission.getRoleId());
                    param.put("permissionId",rolePermission.getPermissionId());
                    if ( rolePermissionMapper.SelectByRoleIdAndPermissionId(param) == 0) {
                        rolePermission.setCreateTime(new Date());
                        rolePermission.setUpdateTime(new Date());
                        rolePermission.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        rolePermissionMapper.insertBySelective(rolePermission);
                    }
                }
            }

            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 权限详情 - 根据permissionId查询
     * @para permissionId 权限id
     * @return 权限详情
     */
    public String view(JSONObject request) {
        System.out.println("viejw................................");
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("permissionId", request.getLong("permissionId"));
        return JSON.toJSONString(permissionMapper.selectById(param));
    }

    /**
     * 获取用户权限 - 按钮权限验证
     * @para params json格式的参数
     * @return 权限验证结果
     */
    public String getUserPermission(JSONObject request) {
        try {
            System.out.println("request=" + JSON.toJSONString(request));
            List<String> urls = Collections.singletonList(request.getString("url"));

            HashMap<String, Object> param = new HashMap<>();
            param.put("airportCode", request.getString("client_id"));
            param.put("userId", request.getLong("user_id"));
            param.put("urlStr", Joiner.on(",").join(urls));
//            param.put("airportCode", "LJG");
//            param.put("userId", 108);
//            param.put("urlStr", "/guest-order/list");
            if(request.containsKey("queryOrderType")){
                param.put("orderType", request.getLong("queryOrderType"));
            }
            Map<String,Object> params = new HashMap<>();
            List<Permission> permissionList = permissionMapper.getUserPermission(param);
            for(int i = 0; i < urls.size(); i++){
                for(int j = 0; j < permissionList.size(); j++){
                    String url = permissionList.get(j).getUrl();
                    System.out.println("url: " + url);
                    System.out.println("url.get(i)" + urls.get(i));
                    if(urls.get(i).equals(url)){
                        params.put(urls.get(i),"true");
                        if(param.containsKey("orderType") && permissionList.get(j).getDataPermission() != null){
                            JSONObject permissionTypeJSON = JSON.parseObject(permissionList.get(j).getPermissionType());
                            JSONObject dataPermissionJSON = JSON.parseObject(permissionList.get(j).getDataPermission());
//                        String[] dataPermission = permissionList.get(j).getDataPermission().replaceAll("\"|\\{|}", "").split(": |, ");
                            if(param.get("orderType").toString().equals(permissionTypeJSON.getString("orderType"))){
                                params.put("roleId",dataPermissionJSON.getString("roleId"));
                                System.out.printf("角色id：%s\n", params.get("roleId"));
                                break;
                            }
                        }else {
                            break;
                        }
                    }
                }
                if(params.get(urls.get(i)) == null){
                    params.put(urls.get(i),"false");
                }
            }

            return JSON.toJSONString(params);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.error());
        }
    }
}
