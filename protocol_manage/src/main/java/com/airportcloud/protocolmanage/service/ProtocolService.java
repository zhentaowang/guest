package com.airportcloud.protocolmanage.service;


import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.GeneratorSerNo;
import com.airportcloud.protocolmanage.mapper.*;
import com.airportcloud.protocolmanage.model.Authorizer;
import com.airportcloud.protocolmanage.model.Dropdownlist;
import com.airportcloud.protocolmanage.model.Protocol;
import com.airportcloud.protocolmanage.model.ProtocolServ;
import com.airportcloud.protocolmanage.pageUtil.BasePagination;
import com.airportcloud.protocolmanage.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/30.
 */
@Service
public class ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private AuthorizerMapper authorizerMapper;

    @Autowired
    private ProtocolServMapper protocolServMapper;

    public LZResult<PaginationResult<Protocol>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Protocol> protocolList = protocolMapper.getListByConidition(queryCondition);
        PaginationResult<Protocol> eqr = new PaginationResult<>(count, protocolList);
        LZResult<PaginationResult<Protocol>> result = new LZResult<>(eqr);
        return result;
    }

    public void saveOrUpdate(Protocol protocol) {
        if (protocol.getId() != null) {
            protocolMapper.updateByIdAndAirportCode(protocol);
            for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                Authorizer authorizer = protocol.getAuthorizerList().get(i);
                if(authorizer.getId() != null){
                    authorizer.setAirportCode(protocol.getAirportCode());
                    authorizer.setProtocolId(protocol.getId());
                    authorizerMapper.updateByIdAndAirportCode(authorizer);
                }
            }
            for(int i = 0; i < protocol.getProtocolServList().size(); i++){
                ProtocolServ protocolServ = protocol.getProtocolServList().get(i);
                if(protocolServ.getId() != null){
                    protocolServ.setAirportCode(protocol.getAirportCode());
                    protocolServ.setProtocolId(protocol.getId());
                    protocolServMapper.updateByIdAndAirportCode(protocolServ);
                }
            }
        } else {
            Integer currentValue = protocolMapper.getCurrentValue();
            StringBuffer protocolNo = new StringBuffer();
            protocolNo.append(protocol.getAirportCode() + "H");
            protocolNo.append(GeneratorSerNo.generatorCodeFormatFour(currentValue));
            protocol.setNo(protocolNo.toString());
            protocolMapper.insert(protocol);
            if(protocol.getAuthorizerList() != null){
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer p = protocol.getAuthorizerList().get(i);
                    p.setProtocolId(protocol.getId());
                    authorizerMapper.insert(p);
                }
            }

            for(int i = 0; i < protocol.getProtocolServList().size(); i++){
                ProtocolServ p = protocol.getProtocolServList().get(i);
                p.setProtocolId(protocol.getId());
                protocolServMapper.insert(p);
            }
        }
    }

    public List<Dropdownlist> getProtocolDropdownList(String airportCode){
        return protocolMapper.getProtocolDropdownList(airportCode);
    }
}
