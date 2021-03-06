package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.RolePermission;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface RolePermissionMapper {
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    Integer SelectByRoleIdAndPermissionId(Map<String, Object> map);
    void insertBySelective(RolePermission rolePermission);
    Integer updateByIdAndAirportCode(RolePermission rolePermission);
}
