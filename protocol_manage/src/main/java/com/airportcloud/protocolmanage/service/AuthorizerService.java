package com.airportcloud.protocolmanage.service;


import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.Constant;
import com.airportcloud.protocolmanage.mapper.AuthorizerMapper;
import com.airportcloud.protocolmanage.mapper.ProtocolMapper;
import com.airportcloud.protocolmanage.model.Authorizer;
import com.airportcloud.protocolmanage.pageUtil.BasePagination;
import com.airportcloud.protocolmanage.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public LZResult<PaginationResult<Authorizer>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = authorizerMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Authorizer> servList = authorizerMapper.getListByConidition(queryCondition);
        PaginationResult<Authorizer> eqr = new PaginationResult<>(count, servList);
        LZResult<PaginationResult<Authorizer>> result = new LZResult<>(eqr);
        return result;
    }

    public Map<String,Object> getById(Map<String,Object> param) {
        return authorizerMapper.selectById(param);
    }

    public void saveOrUpdate(Authorizer authorizer) {
        if (authorizer.getId() != null) {
            authorizerMapper.updateByIdAndAirportCode(authorizer);
        } else {
            Map<String,Object> protocolParam = new HashMap<>();
            protocolParam.put("airportCode",authorizer.getAirportCode());
            protocolParam.put("protocolName",authorizer.getProtocolName());
            authorizer.setProtocolId(protocolMapper.getIdByCondition(protocolParam));
            authorizerMapper.insert(authorizer);
        }
    }

    public void deleteById(List<String> ids) {
        for(int i = 1; i< ids.size();i++){
            Authorizer temp = new Authorizer();
            temp.setAirportCode(ids.get(0));
            temp.setId(Long.parseLong(ids.get(i)));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            authorizerMapper.updateByIdAndAirportCode(temp);
        }
    }
}
