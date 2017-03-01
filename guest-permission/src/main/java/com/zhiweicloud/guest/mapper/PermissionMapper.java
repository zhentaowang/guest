package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Permission;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface PermissionMapper extends MyMapper<Permission> {
    Permission selectById(Map<String, Object> map);
    List<Permission> getUserPermission(Map<String, Object> map);
    List<Permission> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    List<Permission> getListByRole(BasePagination<Map<String, Object>> queryCondition);
    int getListCountByRole(Map<String, Object> map);
    Long selectByName(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Permission permission);
}
