package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProductService;
import com.zhiweicloud.guest.model.ProtocolServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductServiceMapper extends MyMapper<ProtocolProductService> {
    ProtocolServ selectById(Map<String, Object> map);
    List<ProtocolServ> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    List<ProtocolServ> getProtocolServType(BasePagination<Map<String, Object>> queryCondition);
    int getTypeCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProductService protocolProductService);
    Integer deleteByType(Map<String, Object> map);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
}
