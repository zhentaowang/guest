package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.PermissionMapper;
import com.zhiweicloud.guest.mapper.RolePermissionMapper;
import com.zhiweicloud.guest.model.Permission;
import com.zhiweicloud.guest.model.RolePermission;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wzt on 2016/12/26.
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 分页获取权限列表
     * @param param
     * @param page
     * @param rows
     * @return PaginationResult<Permission>
     */
    public LZResult<PaginationResult<Permission>> getAll(Map<String,Object> param, Integer page, Integer rows) {
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
        return result;
    }

    /**
     * 权限添加与修改
     * @param permission
     */
    public void saveOrUpdate(Permission permission) {
        if (permission.getPermissionId() != null) {
            permissionMapper.updateByIdAndAirportCode(permission);

        } else {
            permission.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            permission.setCreateTime(new Date());
            permission.setUpdateTime(new Date());
            permissionMapper.insertSelective(permission);
        }
    }

    /**
     * 角色权限修改:包括添加和修改
     * @param permissionList
     */
    public void updateRolePermission(List<Permission> permissionList) {
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
    }

    /**
     * 权限名称查重
     * @param permission
     * @return boolean
     */
    public boolean selectByName(Permission permission) {
        Map<String,Object> params = new HashMap<>();
        params.put("permissionName",permission.getName());
        params.put("airportCode",permission.getAirportCode());
        params.put("permissionId",permission.getPermissionId());
        Long count = permissionMapper.selectByName(params);
        if(count > 0){//count大于0，说明该名称已存在
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 获取权限详情
     * @param param
     * @return Permission
     */
    public Permission getById(Map<String,Object> param) {
        return permissionMapper.selectById(param);
    }

    /**
     * 获取用户权限
     * @param param
     * @return List<Map<String,Object>>
     */
    public Map<String,Object> getUserPermission(List<String> urls, Map<String,Object> param) {
        List<Permission> permissionList = permissionMapper.getUserPermission(param);
        Map<String,Object> params = new HashMap<>();
        for(int i = 0; i < urls.size(); i++){
            for(int j = 0; j < permissionList.size(); j++){
                String url = permissionList.get(j).getUrl();
                if(urls.get(i).equals(url)){
                    params.put(urls.get(i),true);
                    break;
                }
            }
            if(params.get(urls.get(i)) == null){
                params.put(urls.get(i),false);
            }
        }
        return params;
    }

}