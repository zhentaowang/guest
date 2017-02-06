package com.zhiweicloud.productmanage.mapper;

import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.productmanage.model.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionMapper extends MyMapper<Permission> {
    List<Permission> selectByAccount(Map<String,Object> map);
}