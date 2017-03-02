package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Authorizer;
import com.zhiweicloud.guest.model.Dropdownlist;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface AuthorizerMapper extends MyMapper<Authorizer> {
    Authorizer selectById(Map<String, Object> map);
    List<Authorizer> selectByProtocolId(Map<String, Object> map);
    List<Authorizer> getListByConidition(Map<String, Object> map);
    Integer getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Authorizer authorizer);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);

    /**
     * 协议预约人模糊查询下拉框
     * @param map
     * @return
     */
    List<Dropdownlist> getAuthorizerDropdownList(Map<String, Object> map);
}
