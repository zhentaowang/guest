package com.zhiweicloud.protocolmanage.service;


import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.protocolmanage.mapper.*;
import com.zhiweicloud.protocolmanage.model.Authorizer;
import com.zhiweicloud.protocolmanage.model.Protocol;
import com.zhiweicloud.protocolmanage.model.ProtocolServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
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
            Map<String,Object> params = new HashMap<>();
            params.put("airportCode",protocol.getAirportCode());
            params.put("protocolId",protocol.getId());

            //协议修改
            protocolMapper.updateByIdAndAirportCode(protocol);

            //授权人修改
            StringBuffer ids = new StringBuffer();
            if(protocol.getAuthorizerList() != null){
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
                        authorizer.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        authorizer.setAirportCode(protocol.getAirportCode());
                        authorizerMapper.insertSelective(authorizer);
                        ids.append(authorizer.getId()+",");
                    }
                }
                if(ids.length() != 0){
                    params.put("ids",ids.substring(0,ids.length() - 1));
                    authorizerMapper.deleteByIdAndAirportCode(params);
                }
                else{
                    params.put("ids",ids.append(0));
                    authorizerMapper.deleteByIdAndAirportCode(params);
                }
            }
            else{
                params.put("ids",ids.append(0));
                authorizerMapper.deleteByIdAndAirportCode(params);
            }

//            //协议服务修改
//            StringBuffer ids00 = new StringBuffer();
//            if(protocol.getProtocolServList() != null){
//                for(int i = 0; i < protocol.getProtocolServList().size(); i++){
//                    ProtocolServ protocolServ = protocol.getProtocolServList().get(i);
//                    if (protocolServ.getId() != null){
//                        protocolServ.setAirportCode(protocol.getAirportCode());
//                        protocolServ.setProtocolId(protocol.getId());
//                        ids00.append(protocolServ.getId()+",");
//                        protocolServMapper.updateByIdAndAirportCode(protocolServ);
//                    }else{
//                        protocolServ.setCreateTime(new Date());
//                        protocolServ.setProtocolId(protocol.getId());
//                        protocolServ.setUpdateTime(new Date());
//                        protocolServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
//                        protocolServ.setAirportCode(protocol.getAirportCode());
//                        protocolServMapper.insertSelective(protocolServ);
//                        ids00.append(protocolServ.getId()+",");
//                    }
//                }
//                if(ids00.length() != 0){
//                    params.put("ids00",ids00.substring(0,ids00.length() - 1));
//                    protocolServMapper.deleteByIdAndAirportCode(params);
//                }
//                else{
//                    params.put("ids00",ids00.append(0));
//                    protocolServMapper.deleteByIdAndAirportCode(params);
//                }
//            }
//            else{
//                params.put("ids00",ids00.append(0));
//                protocolServMapper.deleteByIdAndAirportCode(params);
//            }
        } else {

            //协议添加
            protocol.setCreateTime(new Date());
            protocol.setUpdateTime(new Date());
            protocol.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            protocolMapper.insert(protocol);

            //授权人添加
            if(protocol.getAuthorizerList() != null){
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer p = protocol.getAuthorizerList().get(i);
                    p.setCreateTime(new Date());
                    p.setProtocolId(protocol.getId());
                    p.setUpdateTime(new Date());
                    p.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
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
                    p.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
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
     * @param name
     */
    public List<Dropdownlist> getProtocolDropdownList(String airportCode,String type,String name){
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("type",type);
        params.put("name",name);
        return protocolMapper.getProtocolDropdownList(params);
    }

    /**
     * 获取协议列表，包括id,no两个字段
     * @param airportCode
     * @param no
     */
    public List<Dropdownlist> getProtocolNoDropdownList(String airportCode,String type,String no){
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("type",type);
        params.put("no",no);
        return protocolMapper.getProtocolNoDropdownList(params);
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
