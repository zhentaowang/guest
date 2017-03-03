package com.zhiweicloud.guest.service;


import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.AuthorizerMapper;
import com.zhiweicloud.guest.mapper.ProtocolMapper;
import com.zhiweicloud.guest.model.Authorizer;
import com.zhiweicloud.guest.model.Dropdownlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/5.
 */
@Service
public class AuthorizerService {

    @Autowired
    private AuthorizerMapper authorizerMapper;

    @Autowired
    private ProtocolMapper protocolMapper;

    /**
     * 分页获取授权人列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<List<Authorizer>> getAll(Map<String,Object> param, Integer page, Integer rows) {
        List<Authorizer> authorizerList = authorizerMapper.getListByConidition(param);
        LZResult<List<Authorizer>> result = new LZResult<>(authorizerList);
        return result;
    }

    /**
     * 获取授权人详情
     * @param param
     */
    public Authorizer getById(Map<String,Object> param) {
        return authorizerMapper.selectById(param);
    }

    /**
     * 授权人添加与修改
     * @param authorizer
     */
    public void saveOrUpdate(Authorizer authorizer) {
        if (authorizer.getAuthorizerId() != null) {
            authorizerMapper.updateByIdAndAirportCode(authorizer);
        } else {
            authorizer.setCreateTime(new Date());
            authorizer.setUpdateTime(new Date());
            authorizer.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            authorizerMapper.insertBySelective(authorizer);
        }
    }

    /**
     * 删除授权人
     * @param airportCode
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            Authorizer authorizer = new Authorizer();
            authorizer.setAirportCode(airportCode);
            authorizer.setAuthorizerId(ids.get(i));
            authorizer.setIsDeleted(Constant.MARK_AS_DELETED);
            authorizerMapper.updateByIdAndAirportCode(authorizer);
        }
    }

    /**
     * 根据协议id查询授权人个数
     * @param airportCode
     * @param protocolId
     */
    public Integer selectByProtocolId(Long protocolId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("protocolId",protocolId);
        return authorizerMapper.getListCount(params);
    }

    /**
     * 预约人模糊查询下拉框
     * @param airportCode
     * @param name
     * @return
     */
    public List<Dropdownlist> getAuthorizerDropdownList(String airportCode, String name, Long protocolId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("airportCode",airportCode);
        map.put("name",name);
        map.put("protocolId",protocolId);
        return authorizerMapper.getAuthorizerDropdownList(map);
    }
}
