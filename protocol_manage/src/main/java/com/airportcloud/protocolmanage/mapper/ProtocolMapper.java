package com.airportcloud.protocolmanage.mapper;


import com.airportcloud.protocolmanage.common.MyMapper;
import com.airportcloud.protocolmanage.model.Dropdownlist;
import com.airportcloud.protocolmanage.model.Protocol;
import com.airportcloud.protocolmanage.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/30.
 */
public interface ProtocolMapper extends MyMapper<Protocol> {
    List<Dropdownlist> getProtocolDropdownList(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Protocol protocol);
    List<Protocol> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Protocol selectById(Map<String, Object> map);
}
