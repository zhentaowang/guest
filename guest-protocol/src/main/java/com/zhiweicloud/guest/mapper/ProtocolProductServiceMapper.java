package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProductService;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductServiceMapper {
    List<ProtocolProductService> selectByProtocolProductId(Map<String, Object> map);
    List<ProtocolProductService> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProductService protocolProductService);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    List<ProtocolProductService> getServiceMenuList(Map<String, Object> param);
    List<ProtocolProductService> getServiceTypeDropdownList(Map<String, Object> param);
    void insertBySelective(ProtocolProductService protocolProductService);
}
