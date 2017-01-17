package com.zhiweicloud.protocolmanage.mapper;



import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.protocolmanage.model.Authorizer;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/4.
 */
public interface AuthorizerMapper extends MyMapper<Authorizer> {
    Authorizer selectById(Map<String, Object> map);
    List<Authorizer> getListByConidition(Map<String, Object> map);
    int getListCount(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Authorizer authorizer);
    Integer deleteByIdAndAirportCode(Map<String, Object> map);
}
