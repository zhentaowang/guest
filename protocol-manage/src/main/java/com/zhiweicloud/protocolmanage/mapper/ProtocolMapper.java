package com.zhiweicloud.protocolmanage.mapper;


import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.protocolmanage.model.Protocol;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/30.
 */
public interface ProtocolMapper extends MyMapper<Protocol> {
    List<Dropdownlist> getProtocolDropdownList(Map<String, Object> map);
    List<Dropdownlist> getProtocolNoDropdownList(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Protocol protocol);
    List<Protocol> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Protocol selectById(Map<String, Object> map);
}
