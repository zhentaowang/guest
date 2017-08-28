package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.model.ProtocolProductServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface ProtocolProductServiceMapper{
    List<ProtocolProductServ> selectByProtocolProductId(Map<String, Object> map);
    ProtocolProductServ selectByProtocolProductServiceId(Map<String, Object> map);
    List<ProtocolProductServ> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(ProtocolProductServ protocolProductServ);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    List<ProtocolProductServ> getServiceMenuList(Map<String, Object> param);
    List<ProtocolProductServ> getServiceTypeDropdownList(Map<String, Object> param);
    List<ProtocolProductServ> getServiceDropDownBoxByParam(Map<String, Object> param);

    /**
     * 根据协议产品id 删除协议产品服务
     * @param updateUser
     * @param airportCode
     * @param protocolProductId
     * @return
     */
    int deleteByProtocolProductId(@Param("updateUser")Long updateUser, @Param("airportCode") String airportCode, @Param("protocolProductId")Long protocolProductId);
    void insertBySelective(ProtocolProductServ protocolProductServ);
}
