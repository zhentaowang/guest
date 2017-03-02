package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProtocolProductService;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductServiceMapper extends MyMapper<ProtocolProductService> {
    List<ProtocolProductService> selectByProtocolProductId(Map<String, Object> map);
    List<ProtocolProductService> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProductService protocolProductService);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    List<ProtocolProductService> getServiceMenuList(Map<String, Object> param);
    List<ProtocolProductService> getServiceTypeDropdownList(Map<String, Object> param);

    /**
     * 根据协议产品id 删除协议产品服务
     * @param updateUser
     * @param airportCode
     * @param protocolProductId
     * @return
     */
    int deleteByProtocolProductId(@Param("updateUser")Long updateUser,@Param("airportCode") String airportCode,@Param("protocolProductId")Long protocolProductId);
}
