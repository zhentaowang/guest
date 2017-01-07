package com.airportcloud.protocolmanage.mapper;


import com.airportcloud.protocolmanage.common.MyMapper;
import com.airportcloud.protocolmanage.model.ProtocolServ;
import com.airportcloud.protocolmanage.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolServMapper extends MyMapper<ProtocolServ> {
    ProtocolServ selectById(Map<String, Object> map);
    List<ProtocolServ> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolServ protocolServ);
}
