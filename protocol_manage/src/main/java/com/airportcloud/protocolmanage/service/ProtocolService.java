package com.airportcloud.protocolmanage.service;


import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.Constant;
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

import java.util.Date;
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

    /**
     * 分页获取协议列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<Protocol>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Protocol> protocolList = protocolMapper.getListByConidition(queryCondition);
        PaginationResult<Protocol> eqr = new PaginationResult<>(count, protocolList);
        LZResult<PaginationResult<Protocol>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 协议添加与修改
     * @param protocol
     */
    public void saveOrUpdate(Protocol protocol) {
        if (protocol.getId() != null) {

            //协议修改
            protocolMapper.updateByIdAndAirportCode(protocol);

            //授权人修改
            if(protocol.getAuthorizerList() != null){
                Map<String,Object> params = new HashMap<>();
                StringBuffer ids = new StringBuffer();
                params.put("airportCode",protocol.getAirportCode());
                params.put("protocolId",protocol.getId());
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer authorizer = protocol.getAuthorizerList().get(i);
                    if(authorizer.getId() != null){
                        authorizer.setAirportCode(protocol.getAirportCode());
                        authorizer.setProtocolId(protocol.getId());
                        ids.append(authorizer.getId()+",");
                        authorizerMapper.updateByIdAndAirportCode(authorizer);
                    }else{
                        authorizer.setCreateTime(new Date());
                        authorizer.setProtocolId(protocol.getId());
                        authorizer.setUpdateTime(new Date());
                        authorizer.setIsDeleted(Constant.MARK_AS_NOT_DELETED);
                        authorizer.setAirportCode(protocol.getAirportCode());
                        authorizerMapper.insertSelective(authorizer);
                    }
                }
                params.put("ids",ids.substring(0,ids.length() - 1));
                authorizerMapper.deleteByIdAndAirportCode(params);
            }

            //协议服务修改
            if(protocol.getProtocolServList() != null){
                for(int i = 0; i < protocol.getProtocolServList().size(); i++){
                    ProtocolServ protocolServ = protocol.getProtocolServList().get(i);
                    if (protocolServ.getId() != null){
                        protocolServ.setAirportCode(protocol.getAirportCode());
                        protocolServ.setProtocolId(protocol.getId());
                        protocolServMapper.updateByIdAndAirportCode(protocolServ);
                    }
                }
            }
        } else {

            //协议添加
            protocol.setCreateTime(new Date());
            protocol.setStartTime(new Date());
            protocol.setUpdateTime(new Date());
            protocol.setEndTime(new Date());
            protocol.setIsDeleted(Constant.MARK_AS_NOT_DELETED);
            protocolMapper.insert(protocol);

            //授权人添加
            if(protocol.getAuthorizerList() != null){
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer p = protocol.getAuthorizerList().get(i);
                    p.setCreateTime(new Date());
                    p.setProtocolId(protocol.getId());
                    p.setUpdateTime(new Date());
                    p.setIsDeleted(Constant.MARK_AS_NOT_DELETED);
                    p.setAirportCode(protocol.getAirportCode());
                    authorizerMapper.insert(p);
                }
            }

            //协议服务添加
            if(protocol.getProtocolServList() != null){
                for(int i = 0; i < protocol.getProtocolServList().size(); i++){
                    ProtocolServ p = protocol.getProtocolServList().get(i);
                    p.setCreateTime(new Date());
                    p.setProtocolId(protocol.getId());
                    p.setUpdateTime(new Date());
                    p.setIsDeleted(Constant.MARK_AS_NOT_DELETED);
                    p.setAirportCode(protocol.getAirportCode());
                    protocolServMapper.insert(p);
                }
            }
        }
    }

    /**
     * 获取协议详情
     * @param param
     */
    public Protocol getById(Map<String,Object> param) {
        return protocolMapper.selectById(param);
    }

    /**
     * 获取协议列表，包括id,name两个字段
     * @param airportCode
     * @param type
     */
    public List<Dropdownlist> getProtocolDropdownList(String airportCode,String type){
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("type",type);
        return protocolMapper.getProtocolDropdownList(params);
    }

    /**
     * 删除协议及其关联的授权人和协议服务
     * @param airportCode
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {

        for(int i = 0; i< ids.size();i++){

            //删除一条协议
            Protocol protocol = new Protocol();
            protocol.setId(ids.get(i));
            protocol.setIsDeleted(Constant.MARK_AS_DELETED);
            protocol.setAirportCode(airportCode);
            protocolMapper.updateByIdAndAirportCode(protocol);

            //删除该协议对应的所有授权人
            Authorizer authorizer = new Authorizer();
            authorizer.setAirportCode(protocol.getAirportCode());
            authorizer.setProtocolId(protocol.getId());
            authorizer.setIsDeleted(Constant.MARK_AS_DELETED);
            authorizerMapper.updateByIdAndAirportCode(authorizer);

            //删除该协议对应的所有协议服务
            ProtocolServ protocolServ = new ProtocolServ();
            protocolServ.setAirportCode(protocol.getAirportCode());
            protocolServ.setProtocolId(protocol.getId());
            protocolServ.setIsDeleted(Constant.MARK_AS_DELETED);
            protocolServMapper.updateByIdAndAirportCode(protocolServ);

        }
    }
}
