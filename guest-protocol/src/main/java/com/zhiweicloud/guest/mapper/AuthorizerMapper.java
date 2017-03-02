package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Authorizer;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface AuthorizerMapper {
    Authorizer selectById(Map<String, Object> map);
    List<Authorizer> selectByProtocolId(Map<String, Object> map);
    List<Authorizer> getListByConidition(Map<String, Object> map);
    Integer getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Authorizer authorizer);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
    void insertBySelective(Authorizer authorizer);
}
