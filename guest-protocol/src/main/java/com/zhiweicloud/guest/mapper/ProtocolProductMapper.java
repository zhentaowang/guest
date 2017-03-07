package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProduct;
import com.zhiweicloud.guest.model.ProtocolProductServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductMapper {
    List<ProtocolProduct> selectByProtocolId(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProduct protocolProduct);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    void insertBySelective(ProtocolProduct protocolProduct);
    List<ProtocolProduct> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    Integer getListCount(Map<String, Object> map);
    ProtocolProduct selectById(Map<String, Object> map);
    Long selectByProductId(Map<String, Object> map);
    Long selectOrderByProtocolProductId(Map<String, Object> map);
}
