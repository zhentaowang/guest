package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProduct;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductMapper extends MyMapper<ProtocolProduct> {
    List<ProtocolProduct> selectByProtocolId(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProduct protocolProduct);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
}
