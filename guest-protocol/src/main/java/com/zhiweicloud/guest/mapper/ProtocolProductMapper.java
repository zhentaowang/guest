package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProduct;
import com.zhiweicloud.guest.model.ProtocolServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductMapper extends MyMapper<ProtocolProduct> {
    ProtocolServ selectById(Map<String, Object> map);
    List<ProtocolServ> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    List<ProtocolServ> getProtocolServType(BasePagination<Map<String, Object>> queryCondition);
    int getTypeCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProduct protocolProduct);
    Integer deleteByType(Map<String, Object> map);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
}
