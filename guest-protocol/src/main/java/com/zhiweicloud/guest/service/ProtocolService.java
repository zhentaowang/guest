package com.zhiweicloud.guest.service;


import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by wzt on 2016/12/30.
 */
@Service
public class ProtocolService {

    private final ProtocolMapper protocolMapper;

    private final AuthorizerMapper authorizerMapper;

    private final ProtocolProductMapper protocolProductMapper;

    private final ProtocolProductServiceMapper protocolProductServiceMapper;


    @Autowired
    public ProtocolService(ProtocolMapper protocolMapper, AuthorizerMapper authorizerMapper, ProtocolProductMapper protocolProductMapper, ProtocolProductServiceMapper protocolProductServiceMapper) {
        this.protocolMapper = protocolMapper;
        this.authorizerMapper = authorizerMapper;
        this.protocolProductMapper = protocolProductMapper;
        this.protocolProductServiceMapper = protocolProductServiceMapper;
    }

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
        return new LZResult<>(eqr);
    }

    /**
     * 协议添加与修改
     * @param protocol
     */
    public void saveOrUpdate(Protocol protocol) {
        if (protocol.getProtocolId() != null) {
            Map<String,Object> params = new HashMap<>();
            params.put("airportCode",protocol.getAirportCode());
            params.put("protocolId",protocol.getProtocolId());

            //协议修改
            protocolMapper.updateByIdAndAirportCode(protocol);

            //授权人修改
            StringBuffer ids = new StringBuffer();
            if(protocol.getAuthorizerList() != null){
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer authorizer = protocol.getAuthorizerList().get(i);
                    if(authorizer.getAuthorizerId() != null){
                        authorizer.setAirportCode(protocol.getAirportCode());
                        authorizer.setProtocolId(protocol.getProtocolId());
                        ids.append(authorizer.getAuthorizerId()).append(",");
                        authorizerMapper.updateByIdAndAirportCode(authorizer);
                    }else{
                        authorizer.setCreateTime(new Date());
                        authorizer.setProtocolId(protocol.getProtocolId());
                        authorizer.setUpdateTime(new Date());
                        authorizer.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        authorizer.setAirportCode(protocol.getAirportCode());
                        authorizerMapper.insertBySelective(authorizer);
                        ids.append(authorizer.getAuthorizerId()+",");
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

            //协议产品修改
            StringBuffer ids00 = new StringBuffer();
            if(protocol.getProtocolProductList() != null){
                for(int i = 0; i < protocol.getProtocolProductList().size(); i++){
                    ProtocolProduct protocolProduct = protocol.getProtocolProductList().get(i);
                    if (protocolProduct.getProtocolProductId() != null){
                        ids00.append(protocolProduct.getProtocolProductId()+",");
                        protocolProductMapper.updateByIdAndAirportCode(protocolProduct);
                    }else{
                        protocolProduct.setProtocolId(protocol.getProtocolId());
                        protocolProduct.setCreateTime(new Date());
                        protocolProduct.setUpdateTime(new Date());
                        protocolProduct.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        protocolProductMapper.insertBySelective(protocolProduct);
                        ids00.append(protocolProduct.getProtocolProductId()+",");
                    }
                    if(protocolProduct.getProtocolProductServList() != null){
                        for(int j = 0; j < protocolProduct.getProtocolProductServList().size(); j++ ){
                            ProtocolProductServ protocolProductService = protocolProduct.getProtocolProductServList().get(j);
                            if (protocolProductService.getProtocolProductServiceId() != null){
                                protocolProductServiceMapper.updateByIdAndAirportCode(protocolProductService);
                            }else{
                                protocolProductService.setProtocolProductId(protocolProduct.getProtocolProductId());
                                protocolProductService.setCreateTime(new Date());
                                protocolProductService.setUpdateTime(new Date());
                                protocolProductService.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                                protocolProductServiceMapper.insertBySelective(protocolProductService);
                            }
                        }
                    }
                }
                if(ids00.length() != 0){
                    params.put("ids00",ids00.substring(0,ids00.length() - 1));
                    protocolProductMapper.deleteByIdAndAirportCode(params);
                    protocolProductServiceMapper.deleteByIdAndAirportCode(params);
                }
                else{
                    params.put("ids00",ids00.append(0));
                    protocolProductMapper.deleteByIdAndAirportCode(params);
                    protocolProductServiceMapper.deleteByIdAndAirportCode(params);
                }
            }
            else{
                params.put("ids00",ids00.append(0));
                protocolProductMapper.deleteByIdAndAirportCode(params);
                protocolProductServiceMapper.deleteByIdAndAirportCode(params);
            }
        } else {

            //协议添加
            protocol.setCreateTime(new Date());
            protocol.setUpdateTime(new Date());
            protocol.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            protocolMapper.insertBySelective(protocol);

            //授权人添加
            if(protocol.getAuthorizerList() != null){
                for(int i = 0; i < protocol.getAuthorizerList().size(); i++){
                    Authorizer p = protocol.getAuthorizerList().get(i);
                    p.setCreateTime(new Date());
                    p.setProtocolId(protocol.getProtocolId());
                    p.setUpdateTime(new Date());
                    p.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    p.setAirportCode(protocol.getAirportCode());
                    authorizerMapper.insertBySelective(p);
                }
            }

            //协议产品添加
            if(protocol.getProtocolProductList() != null){
                for(int j = 0; j < protocol.getProtocolProductList().size(); j++){
                    ProtocolProduct pp = protocol.getProtocolProductList().get(j);
                    pp.setProtocolId(protocol.getProtocolId());
                    pp.setCreateTime(new Date());
                    pp.setUpdateTime(new Date());
                    pp.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    protocolProductMapper.insertBySelective(pp);
                    for(int i = 0; i < protocol.getProtocolProductList().get(j).getProtocolProductServList().size(); i++){
                        ProtocolProductServ p = protocol.getProtocolProductList().get(j).getProtocolProductServList().get(i);
                        p.setProtocolProductId(pp.getProtocolProductId());
                        p.setCreateTime(new Date());
                        p.setUpdateTime(new Date());
                        p.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        protocolProductServiceMapper.insertBySelective(p);
                    }
                }
            }
        }
    }

    /**
     * 获取协议详情
     * @param param
     */
    public Protocol getById(Map<String,Object> param) {
        Protocol protocol = protocolMapper.selectById(param);
        List<Authorizer> authorizerList = authorizerMapper.selectByProtocolId(param);
        if(authorizerList.size() != 0){
            protocol.setAuthorizerList(authorizerList);
        }
        List<ProtocolProduct> protocolProductList = protocolProductMapper.selectByProtocolId(param);
        if(protocolProductList != null){
            for(int i = 0; i < protocolProductList.size(); i++){
                ProtocolProduct protocolProduct = protocolProductList.get(i);
                param.put("protocolProductId",protocolProduct.getProtocolProductId());
                List<ProtocolProductServ> protocolProductServiceList = protocolProductServiceMapper.selectByProtocolProductId(param);
                protocolProduct.setProtocolProductServList(protocolProductServiceList);
            }
        }

        if(protocolProductList.size() != 0){
            protocol.setProtocolProductList(protocolProductList);
        }
        return protocol;
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
    public void deleteById(List<Long> ids,Long userId, String airportCode) {

        for(int i = 0; i< ids.size();i++){

            //根据协议id获取 协议产品列表
            Map<String, Object> map = new HashMap<>();
            map.put("protocolId",ids.get(i));
            map.put("airportCode",airportCode);
            List<ProtocolProduct> tempProtocolProductList = protocolProductMapper.selectByProtocolId(map);

            if(!CollectionUtils.isEmpty(tempProtocolProductList)){
                ProtocolProduct tempPro = new ProtocolProduct();
                for(ProtocolProduct pro : tempProtocolProductList){
                    //根据协议产品id删除关联的 协议产品服务
                    protocolProductServiceMapper.deleteByProtocolProductId(userId,airportCode,pro.getProtocolProductId());

                    //删除该协议产品
                    tempPro.setProtocolProductId(pro.getProtocolProductId());
                    tempPro.setIsDeleted(Constant.MARK_AS_DELETED);
                    tempPro.setUpdateUser(userId);
                    tempPro.setAirportCode(airportCode);
                    protocolProductMapper.updateByIdAndAirportCode(tempPro);
                }
            }

            //删除该协议对应的所有授权人
            Authorizer authorizer = new Authorizer();
            authorizer.setProtocolId(ids.get(i));
            authorizer.setIsDeleted(Constant.MARK_AS_DELETED);
            authorizer.setAirportCode(airportCode);
            authorizer.setUpdateUser(userId);
            authorizerMapper.updateByIdAndAirportCode(authorizer);

            //删除该协议
            Protocol protocol = new Protocol();
            protocol.setProtocolId(ids.get(i));
            protocol.setIsDeleted(Constant.MARK_AS_DELETED);
            protocol.setAirportCode(airportCode);
            protocol.setUpdateUser(userId);
            protocolMapper.updateByIdAndAirportCode(protocol);

        }
    }

    /**
     * 协议名称查重
     * @param protocol
     * @return boolean
     */
    public boolean selectByName(Protocol protocol) {
        Map<String,Object> params = new HashMap<>();
        params.put("protocolName",protocol.getName());
        params.put("airportCode",protocol.getAirportCode());
        params.put("id",protocol.getProtocolId());
        Long count = protocolMapper.selectByName(params);
        if(count > 0){//count大于0，说明该名称已存在
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 删除协议时判断是否有订单已经引用
     * @param protocolId
     * @param airportCode
     * @return boolean
     */
    public boolean selectOrderByProtocolId(Long protocolId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("protocolId",protocolId);
        params.put("airportCode",airportCode);
        Long count = protocolMapper.selectOrderByProtocolId(params);
        if(count > 0){//count大于0，说明有订单已经引用该协议
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 获取服务类型树
     * @param param
     * @return List<ProtocolProductServ>
     */
    public List<ProtocolProductServ> getServiceMenuList(Map<String,Object> param){
        List<ProtocolProductServ> result = protocolProductServiceMapper.getServiceMenuList(param);
        for(int i = 0; i < result.size(); i++){
            param.put("category",result.get(i).getCategory());
            List<ProtocolProductServ> out = protocolProductServiceMapper.getServiceTypeDropdownList(param);
            result.get(i).setServiceTypeList(out);
        }
        return result;
    }

    /**
     * 获取服务类别
     * @param param
     * @return List<ProtocolProductServ>
     */
    public List<ProtocolProductServ> getServiceTypeList(Map<String,Object> param){
        List<ProtocolProductServ> out = protocolProductServiceMapper.getServiceTypeDropdownList(param);
        return out;
    }

    /**
     * 根据服务类型配置id和协议产品id查询服务详情
     * @param param
     * @param page
     * @param rows
     * @return PaginationResult<JSONObject>
     */
    public LZResult<PaginationResult<JSONObject>> getServiceListByTypeId(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolProductServiceMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProtocolProductServ> protocolProductServiceList = protocolProductServiceMapper.getListByConidition(queryCondition);
        List<JSONObject> protocolProductServiceJson = new ArrayList<>();
        for(int i = 0; i < protocolProductServiceList.size(); i++){
            JSONObject result = new JSONObject();
            result.put("protocolProductServiceId",protocolProductServiceList.get(i).getProtocolProductServiceId());
            result.put("airportCode",protocolProductServiceList.get(i).getAirportCode());
            result.put("serviceTypeAllocationId",protocolProductServiceList.get(i).getServiceTypeAllocationId());
            result.put("no",protocolProductServiceList.get(i).getNo());
            result.put("name",protocolProductServiceList.get(i).getName());
            if(param.get("typeId") != null){
                Map<String,Object> protocolProductFieldName = ProtocolProductDetail.getProtocolProductFieldName(Long.parseLong(param.get("typeId").toString()));
                if(protocolProductFieldName != null){
                    result.putAll(protocolProductFieldName);
                }
                result.put("isPricing",protocolProductServiceList.get(i).getIsPricing());
                result.put("isPrioritized",protocolProductServiceList.get(i).getIsPrioritized());
                result.put("isAvailabled",protocolProductServiceList.get(i).getIsAvailabled());
            }
            protocolProductServiceJson.add(result);
        }
        PaginationResult<JSONObject> eqr = new PaginationResult<>(count, protocolProductServiceJson);
        LZResult<PaginationResult<JSONObject>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 根据服务类型配置id和协议产品id查询服务下拉框
     * @param param
     * @return PaginationResult<JSONObject>
     */
    public LZResult<List<ProtocolProductServ>> getServiceDropDownBox(Map<String,Object> param) {
        List<ProtocolProductServ> protocolProductServiceList = protocolProductServiceMapper.getServiceDropDownBoxByParam(param);
        LZResult<List<ProtocolProductServ>> result = new LZResult<>(protocolProductServiceList);
        return result;
    }

    /**
     * 重写分页获取协议列表 2017.2.23
     * @param protocolParam
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<Protocol>> getProtocolList(Protocol protocolParam, Integer page, Integer rows) {
        int count = protocolMapper.selectProtocolTotal(protocolParam);
        List<Protocol> protocolList = protocolMapper.queryProtocolList(protocolParam,(page-1)*rows, rows);
        PaginationResult<Protocol> eqr = new PaginationResult<>(count, protocolList);
        LZResult<PaginationResult<Protocol>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 协议名称模糊查询下拉框
     * @param map
     * @return
     */
    public List<Dropdownlist> getProtocolNameDropdownList(Map<String,Object> map){
        return protocolMapper.getProtocolNameDropdownList(map);
    }

}
