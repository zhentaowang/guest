/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.ProtocolTypeEnum;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * ServMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * Created by wzt on 05/05/2017.
 */
@Service
public class BusinessService implements IBusinessService {

    private final ProtocolMapper protocolMapper;

    private final AuthorizerMapper authorizerMapper;

    private final ProtocolProductMapper protocolProductMapper;

    private final ProtocolProductServiceMapper protocolProductServiceMapper;

    private final ProtocolTypeMapper protocolTypeMapper;


    @Autowired
    public BusinessService(ProtocolMapper protocolMapper, AuthorizerMapper authorizerMapper, ProtocolProductMapper protocolProductMapper,
                           ProtocolProductServiceMapper protocolProductServiceMapper, ProtocolTypeMapper protocolTypeMapper) {
        this.protocolMapper = protocolMapper;
        this.authorizerMapper = authorizerMapper;
        this.protocolProductMapper = protocolProductMapper;
        this.protocolProductServiceMapper = protocolProductServiceMapper;
        this.protocolTypeMapper = protocolTypeMapper;
    }

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }

        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "save-or-update":
                success = save(request);
                break;
            case "view":
                success = view(request);
                break;
            case "delete":
                success = delete(request);
                break;
            case "getById":
                success = getById(request);
                break;
            case "get-service-type-tree-by-protocol-product-id":
                success = getServiceMenuList(request);
                break;
            case "get-service-type-by-protocol-product-id":
                success = getServiceTypeList(request);
                break;
            case "get-service-list-by-type-and-protocol-product-id":
                success = list(request);
                break;
            case "get-service-box-by-type-and-protocol-product-id":
                success = getServiceDropDownBox(request);
                break;
            case "protocolList":
                success = protocolList(request);
                break;
            case "protocolTypeSelect":
                success = getProtocolTypeSelect(request);
                break;
            case "deleteProtocol":
                success = deleteProtocol(request);
                break;
            case "protocol-product-save-or-update":
                success = protocolProductSave(request);
                break;
            case "protocol-product-service-save-or-update":
                success = protocolProductServiceSave(request);
                break;
            case "protocol-product-view":
                success = protocolProductView(request);
                break;
            case "protocol-product-service-view":
                success = protocolProductServiceView(request);
                break;
            case "protocol-product-list":
                success = protocolProductList(request);
                break;
            case "protocol-product-delete":
                success = protocolProductDelete(request);
                break;
            case "getProtocolProductServByServId":
                success = getProtocolProductServByServId(request);
                break;
            case "authorizer-list":
                success = authorizerList(request);
                break;
            case "authorizer-save-or-update":
                success = authorizerSaveOrUpdate(request);
                break;
            case "authorizer-view":
                success = authorizerView(request);
                break;
            case "authorizer-delete":
                success = delete(request);
                break;
            case "getAuthorizerDropdownList":
                success = getAuthorizerDropdownList(request);
                break;
            case "protocol-dropdown-list":
                success = getProtocolDropdownList(request);
                break;
            case "protocol-no-dropdown-list":
                success = getProtocolNoDropdownList(request);
                break;
            case "getProtocolNameDropdownList":
                success = getProtocolNameDropdownList(request);
                break;
            case "getProtocolTypeDropdownList":
                success = getProtocolTypeDropdownList(request);
                break;
            default:
                break;
        }
        return success;
    }

    /**
     * 协议管理 - 新增or更新
     * 需要判断name是否重复
     * @para params
     * @return LXResult 成功还是失败
     */
    public String save(JSONObject request) {

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            JSONArray authorizerList = param00.getJSONArray("authorizer");
            param00.remove("authorizer");
            param00.remove("protocolProduct");
            Protocol protocol = JSONObject.toJavaObject(param00,Protocol.class);
            protocol.setAirportCode(request.getString("client_id"));
            protocol.setCreateUser(request.getLong("user_id"));
            protocol.setUpdateUser(request.getLong("user_id"));
            List<Authorizer> authorizers = new ArrayList<>();
            if(authorizerList != null){
                for (int i = 0; i < authorizerList.size(); i++) {
                    JSONObject authorizer00 = JSON.parseObject(authorizerList.get(i).toString());
                    Authorizer authorizer = JSONObject.toJavaObject(authorizer00,Authorizer.class);
                    authorizer.setAirportCode(protocol.getAirportCode());
                    authorizer.setCreateUser(protocol.getCreateUser());
                    authorizer.setUpdateUser(protocol.getUpdateUser());
                    authorizers.add(authorizer);
                }
            }
            protocol.setAuthorizerList(authorizers);

            if (protocol == null) {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
            }
            if (protocol.getName() == null || protocol.getInstitutionClientId() == null || protocol.getType() == null
                    || protocol.getReservationNum() == null) {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
            }

            Map<String,Object> params = new HashMap<>();
            params.put("protocolName",protocol.getName());
            params.put("airportCode",protocol.getAirportCode());
            params.put("id",protocol.getProtocolId());
            if (protocolMapper.selectByName(params) > 0) {
                return JSON.toJSONString(LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display()));
            }

            if (protocol.getProtocolId() != null) {
                Map<String,Object> paramss = new HashMap<>();
                paramss.put("airportCode",protocol.getAirportCode());
                paramss.put("protocolId",protocol.getProtocolId());

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
                        paramss.put("ids",ids.substring(0,ids.length() - 1));
                        authorizerMapper.deleteByIdAndAirportCode(paramss);
                    }
                    else{
                        paramss.put("ids",ids.append(0));
                        authorizerMapper.deleteByIdAndAirportCode(paramss);
                    }
                }
                else{
                    paramss.put("ids",ids.append(0));
                    authorizerMapper.deleteByIdAndAirportCode(paramss);
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
            }

            LZResult<Long> result = new LZResult<>();
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(protocol.getProtocolId());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 协议管理 - 根据id查询
     * @para protocolId 协议id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return LZResult 协议详情
     */
    public String view(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolId", request.getLong("protocolId"));

        Protocol protocol = protocolMapper.selectById(param);
        if(protocol != null){
            List<Authorizer> authorizerList = authorizerMapper.selectByProtocolId(param);
            if(authorizerList.size() != 0){
                protocol.setAuthorizerList(authorizerList);
            }
            List<ProtocolProduct> protocolProductList = protocolProductMapper.selectByProtocolId(param);

            if(protocolProductList.size() != 0){
                protocol.setProtocolProductList(protocolProductList);
            }
        }
        return JSON.toJSONString(new LZResult<>(protocol));

    }

    /**
     * 协议管理 - 根据id查询
     * @para airportCode 机场代码
     * @para protocolId 协议id
     * @return LZResult 协议详情
     */
    public String getById(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolId", request.getLong("protocolId"));
        return JSON.toJSONString(new LZResult<>(protocolMapper.selectById(param)));

    }

    /**
     * 协议管理 - 根据协议产品id查询服务类别树
     * @para protocolProductId 协议产品id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return LZResult 服务类别树
     */
    public String getServiceMenuList(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolProductId", request.getLong("protocolProductId"));

        List<ProtocolProductServ> result = protocolProductServiceMapper.getServiceMenuList(param);
        for(int i = 0; i < result.size(); i++){
            param.put("category",result.get(i).getCategory());
            List<ProtocolProductServ> out = protocolProductServiceMapper.getServiceTypeDropdownList(param);
            result.get(i).setServiceTypeList(out);
        }
        return JSON.toJSONString(new LZResult<>(result));

    }

    /**
     * 协议管理 - 根据协议产品id查询服务类别
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para protocolProductId  协议产品id
     * @return LZResult 服务类别树
     */
    public String getServiceTypeList(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolProductId", request.getLong("protocolProductId"));
        List<ProtocolProductServ> out = protocolProductServiceMapper.getServiceTypeDropdownList(param);
        return JSON.toJSONString(new LZResult<>(out));

    }

    /**
     * 协议管理 - 根据服务类型配置id和协议产品id查询服务详情
     * @para page 起始页
     * @para rows 每页显示数目
     * @para typeId 服务类型配置id
     * @para protocolProductId 协议产品id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return LZResult 服务详情列表
     */
    public String list(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("typeId", request.getLong("typeId"));
        param.put("protocolProductId", request.getLong("protocolProductId"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

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
                if(protocolProductFieldName.size() != 0 & protocolProductServiceList.get(i).getPricingRule() != null){
                    result.putAll(JSON.parseObject(protocolProductServiceList.get(i).getPricingRule()));
                }
                protocolProductFieldName.clear();
            }
            result.put("isPricing",protocolProductServiceList.get(i).getIsPricing());
            result.put("isPrioritized",protocolProductServiceList.get(i).getIsPrioritized());
            result.put("isAvailabled",protocolProductServiceList.get(i).getIsAvailabled());
            protocolProductServiceJson.add(result);
        }
        PaginationResult<JSONObject> eqr = new PaginationResult<>(count, protocolProductServiceJson);
        LZResult<PaginationResult<JSONObject>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);

    }

    /**
     * 协议管理 - 根据服务类型配置id和协议产品id查询服务下拉框
     * @para typeId 服务类型配置id
     * @para protocolProductId 协议产品id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return LZResult 服务下拉框
     */
    public String getServiceDropDownBox(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("typeId", request.getLong("typeId"));
        param.put("protocolProductId", request.getLong("protocolProductId"));

        List<ProtocolProductServ> protocolProductServiceList = protocolProductServiceMapper.getServiceDropDownBoxByParam(param);
        LZResult<List<ProtocolProductServ>> result = new LZResult<>(protocolProductServiceList);
        return JSON.toJSONString(result);

    }

    /**
     * 重写协议列表，2017.2.23
     * @para page 起始页
     * @para rows 每页显示数目
     * @para protocolType 协议类型
     * @para institutionClientName 机构客户名称
     * @para protocolName 协议名称
     * @para airportCode 机场代码
     * @return LZResult 协议详情列表
     */
    public String protocolList (JSONObject request){

        Protocol protocolParam = new Protocol();
        protocolParam.setAirportCode(request.getString("client_id"));

        protocolParam.setInstitutionClientName(request.getString("institutionClientName"));
        protocolParam.setInstitutionClientId(request.getLong("institutionClientId"));
        protocolParam.setName(request.getString("protocolName"));
        if (request.getInteger("protocolType") != null) {
            protocolParam.setType(request.getInteger("protocolType"));
        }

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int count = protocolMapper.selectProtocolTotal(protocolParam);
        List<Protocol> protocolList = protocolMapper.queryProtocolList(protocolParam,(page-1)*rows, rows);
        PaginationResult<Protocol> eqr = new PaginationResult<>(count, protocolList);
        LZResult<PaginationResult<Protocol>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);

    }

    /**
     * 协议类型下拉框
     * @return 协议类型下拉框
     */
    public String getProtocolTypeSelect (JSONObject request) {

        LZResult<List<Dropdownlist>> result = new LZResult<>();
        List<Dropdownlist> list = new ArrayList<>();

        for (ProtocolTypeEnum e : ProtocolTypeEnum.values()) {
            Dropdownlist dropdown = new Dropdownlist();
            dropdown.setId(Long.valueOf(e.getTypeValue()));
            dropdown.setValue(e.getTypeName());
            list.add(dropdown);
        }
        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 重写协议管理 - 删除
     * @para params ids
     * @return
     */
    public String deleteProtocol (JSONObject request){

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(param.toJSONString(), Long.class);
            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");

            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);
            for (int i = 0; i < ids.size(); i++) {
                //调用order应用，根据协议id 判断有无被引用
                paramMap.put("protocolId", ids.get(i));
                JSONObject orderJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-order/guest-order/getOrderCountByProtocolId", headerMap, paramMap));
                //解析协议产品服务对象
                int orderCount = Integer.valueOf(orderJSONObject.get("data").toString());
                if(orderCount > 0){
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_REF_ERROR));
                }
            }

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
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            return JSON.toJSONString(LXResult.error());
        }

    }

    /**
     * 协议产品管理 - 新增or更新
     * 需要判断name是否重复
     * @para params 协议产品信息
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 成功还是失败
     */
    public String protocolProductSave(JSONObject request) {

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            for(int k = 0; k < param.size(); k++){
                JSONObject param00 = JSON.parseObject(param.get(k).toString());
                JSONArray serviceList = param00.getJSONArray("serviceList");
                param00.remove("serviceList");
                ProtocolProduct protocolProduct = JSONObject.toJavaObject(param00,ProtocolProduct.class);
                protocolProduct.setAirportCode(request.getString("client_id"));
                protocolProduct.setCreateUser(request.getLong("user_id"));
                protocolProduct.setUpdateUser(request.getLong("user_id"));
                List<ProtocolProductServ> protocolProductServs = new ArrayList<>();
                if(serviceList != null){
                    for (int i = 0; i < serviceList.size(); i++) {
                        JSONObject protocolProductServ00 = JSON.parseObject(serviceList.get(i).toString());
                        protocolProductServ00.remove("servId");
                        protocolProductServ00.remove("no");
                        protocolProductServ00.remove("name");
                        ProtocolProductServ protocolProductServ = JSONObject.toJavaObject(protocolProductServ00,ProtocolProductServ.class);
                        protocolProductServ.setAirportCode(protocolProduct.getAirportCode());
                        protocolProductServ.setCreateUser(protocolProduct.getCreateUser());
                        protocolProductServ.setUpdateUser(protocolProduct.getUpdateUser());
                        protocolProductServs.add(protocolProductServ);
                    }
                }
                protocolProduct.setProtocolProductServList(protocolProductServs);
                if(protocolProduct.getProtocolProductId() == null){
                    if (protocolProduct == null || protocolProduct.getProductId() == null || protocolProduct.getProtocolId() == null) {
                        return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                    }

                    Map<String,Object> params = new HashMap<>();
                    params.put("productId",protocolProduct.getProductId());
                    params.put("airportCode",protocolProduct.getAirportCode());
                    params.put("protocolId",protocolProduct.getProtocolId());
                    if (protocolProductMapper.selectByProductId(params) > 0) {
                        return JSON.toJSONString(LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display()));
                    }
                }

                if (protocolProduct.getProtocolProductId() != null) {
                    protocolProductMapper.updateByIdAndAirportCode(protocolProduct);
                } else {
                    protocolProduct.setCreateTime(new Date());
                    protocolProduct.setUpdateTime(new Date());
                    protocolProduct.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    protocolProductMapper.insertBySelective(protocolProduct);
                    List<ProtocolProductServ> protocolProductServList = protocolProduct.getProtocolProductServList();
                    for(int i = 0; i < protocolProductServList.size(); i++){
                        ProtocolProductServ protocolProductServ = protocolProductServList.get(i);
                        protocolProductServ.setAirportCode(protocolProduct.getAirportCode());
                        protocolProductServ.setProtocolProductId(protocolProduct.getProtocolProductId());
                        protocolProductServ.setCreateTime(new Date());
                        protocolProductServ.setUpdateTime(new Date());
                        protocolProductServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                        protocolProductServiceMapper.insertBySelective(protocolProductServ);
                    }
                }
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 协议产品服务管理 - 新增or更新
     * 需要判断name是否重复
     * @para params 协议产品信息
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 成功还是失败
     */
    public String protocolProductServiceSave(JSONObject request) {

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            for (int i = 0; i < param.size(); i++) {
                JSONObject protocolProductService00 = JSON.parseObject(param.get(i).toString());
                ProtocolProductServ protocolProductServ = JSONObject.toJavaObject(protocolProductService00,ProtocolProductServ.class);
                protocolProductServ.setAirportCode(request.getString("client_id"));
                protocolProductServ.setCreateUser(request.getLong("user_id"));
                protocolProductServ.setUpdateUser(request.getLong("user_id"));
                protocolProductService00.remove("protocolProductServiceId");
                protocolProductService00.remove("protocolProductId");
                protocolProductService00.remove("serviceTypeAllocationId");
                protocolProductService00.remove("isPricing");
                protocolProductService00.remove("isPrioritized");
                protocolProductService00.remove("isAvailabled");
                protocolProductServ.setPricingRule(protocolProductService00.toJSONString());
                Map<String, Object> protocolProductFieldName = ProtocolProductDetail.getProtocolProductFieldName(protocolProductServ.getServiceTypeAllocationId());
                Set keys = protocolProductService00.keySet();
                if (keys.size() != protocolProductFieldName.size()) {
                    return JSON.toJSONString(LXResult.build(4995, "传输数据字段错误"));
                } else {
                    if (protocolProductFieldName != null) {
                        for (int k = 0; k < keys.size(); k++) {
                            if (!protocolProductFieldName.containsKey(keys.toArray()[k])) {
                                return JSON.toJSONString(LXResult.build(4995, "传输数据字段错误"));
                            } else {
                                if (protocolProductService00.getString(keys.toArray()[k].toString()).isEmpty()) {
                                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                                }
                            }
                        }
                    }
                }
                protocolProductFieldName.clear();

                if (protocolProductServ.getProtocolProductServiceId() != null) {
                    protocolProductServiceMapper.updateByIdAndAirportCode(protocolProductServ);
                } else {
                    protocolProductServ.setCreateTime(new Date());
                    protocolProductServ.setUpdateTime(new Date());
                    protocolProductServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    protocolProductServiceMapper.insertBySelective(protocolProductServ);
                }
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 协议产品管理 - 根据id查询
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para protocolProductId 协议产品id
     * @return LZResult 协议产品详情
     */
    public String protocolProductView(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolProductId", request.getLong("protocolProductId"));
        return JSON.toJSONString(new LZResult<>(protocolProductMapper.selectById(param)));

    }

    /**
     * 协议产品服务管理 - 根据id查询
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para protocolProductServiceId 协议产品服务id
     * @return LZResult 协议产品服务详情
     */
    public String protocolProductServiceView(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("protocolProductServiceId", request.getLong("protocolProductServiceId"));
        return JSON.toJSONString(new LZResult<>(protocolProductServiceMapper.selectByProtocolProductServiceId(param)));

    }

    /**
     * 协议产品管理 - 协议产品列表
     * @para page 起始页
     * @para rows 每页显示数目
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para protocolId 协议id
     * @return LZResult 协议产品信息
     */
    public String protocolProductList(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));
        param.put("protocolId",request.getLong("protocolId"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int count = protocolProductMapper.getListCount(param);
        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProtocolProduct> protocolList = protocolProductMapper.getListByConidition(queryCondition);
        PaginationResult<ProtocolProduct> eqr = new PaginationResult<>(count, protocolList);
        return JSON.toJSONString(new LZResult<>(eqr));

    }

    /**
     * 协议产品管理 - 删除
     * @para params ids
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 响应结果
     */
    public String protocolProductDelete(JSONObject request) {

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(param.toJSONString(), Long.class);
            for(int i = 0; i< ids.size();i++){

                Map<String,Object> params = new HashMap<>();
                params.put("protocolProductId",ids.get(i));
                params.put("airportCode",request.getString("client_id"));
                if(protocolProductMapper.selectOrderByProtocolProductId(params) > 0){
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_REF_ERROR));
                }
            }

            for(int i = 0; i< ids.size();i++){
                ProtocolProduct protocolProduct = new ProtocolProduct();
                protocolProduct.setAirportCode(request.getString("client_id"));
                protocolProduct.setProtocolProductId(ids.get(i));
                protocolProduct.setIsDeleted(Constant.MARK_AS_DELETED);
                protocolProduct.setUpdateUser(request.getLong("user_id"));
                protocolProductMapper.updateByIdAndAirportCode(protocolProduct);
                ProtocolProductServ protocolProductServ = new ProtocolProductServ();
                protocolProductServ.setAirportCode(request.getString("client_id"));
                protocolProductServ.setProtocolProductId(protocolProduct.getProtocolProductId());
                protocolProductServ.setUpdateUser(request.getLong("user_id"));
                protocolProductServ.setIsDeleted(Constant.MARK_AS_DELETED);
                protocolProductServiceMapper.updateByIdAndAirportCode(protocolProductServ);
            }
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            return JSON.toJSONString(LXResult.error());
        }

    }

    /**
     * 根据服务servId 查询 协议产品服务列表
     * @para servId
     * @para headers
     * @return
     */
    public String getProtocolProductServByServId (JSONObject request){

        String airportCode = request.getString("client-id");
        LZResult<List<ProtocolProductServ>> result = new LZResult<>();
        try {

            Map<String,Object> params = new HashMap<>();
            params.put("serviceId",request.getLong("serviceId"));
            params.put("airportCode",airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(protocolProductServiceMapper.selectByProtocolProductId(params));
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);

    }

    public String authorizerList(JSONObject request) {

        Map<String,Object> param = new HashMap();
        param.put("airportCode",request.getString("client_id"));
        param.put("protocolId",request.getLong("protocolId"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        List<Authorizer> authorizerList = authorizerMapper.getListByConidition(param);
        LZResult<List<Authorizer>> result = new LZResult<>(authorizerList);
        return JSON.toJSONString(result);

    }

    /**
     * 授权人管理 - 新增or更新
     * 需要判断name是否重复
     * @para params
     * @return
     */
    public String authorizerSaveOrUpdate(JSONObject request){

        LZResult<String> result = new LZResult<>();
        try{
            JSONArray param = JSON.parseArray(request.getString("data"));
            Authorizer authorizer = JSON.toJavaObject(param.getJSONObject(0), Authorizer.class);
            if (authorizer == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            }else{

                if (authorizer.getAuthorizerId() != null) {
                    authorizerMapper.updateByIdAndAirportCode(authorizer);
                } else {
                    authorizer.setCreateTime(new Date());
                    authorizer.setUpdateTime(new Date());
                    authorizer.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    authorizerMapper.insertBySelective(authorizer);
                }
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);

            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }

    /**
     * 授权人管理 - 根据id查询
     * @para authorizerId
     * @return
     */
    public String authorizerView(JSONObject request) {

        Map<String,Object> param = new HashMap();
        param.put("airportCode",request.getString("client_id"));
        param.put("authorizerId",request.getLong("authorizerId"));
        return JSON.toJSONString(new LZResult<>(authorizerMapper.selectById(param)));

    }

    /**
     * 授权人管理 - 删除
     * @para params ids
     * @return
     */
    public String delete(JSONObject request) {

        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(param.toJSONString(), Long.class);

            for(int i = 0; i< ids.size();i++){
                Authorizer authorizer = new Authorizer();
                authorizer.setAirportCode(request.getString("client_id"));
                authorizer.setAuthorizerId(ids.get(i));
                authorizer.setIsDeleted(Constant.MARK_AS_DELETED);
                authorizerMapper.updateByIdAndAirportCode(authorizer);
            }
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            return JSON.toJSONString(LXResult.error());
        }

    }

    /**
     * 协议预约人模糊匹配下拉框
     * @return
     */
    public String getAuthorizerDropdownList(JSONObject request) {

        LZResult<List<Dropdownlist>> result = new LZResult<>();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("airportCode",request.getString("client_id"));
            map.put("name",request.getString("name"));
            map.put("protocolId",request.getLong("protocolId"));
            map.put("authorizerId",request.getLong("authorizerId"));
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(authorizerMapper.getAuthorizerDropdownList(map));
        }catch(Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }

    /**
     * 协议表 - 查询协议名称
     *
     * @return
     */
    public String getProtocolDropdownList(JSONObject request) {

        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",request.getString("client_id"));
        params.put("type",request.getString("type"));
        params.put("name",request.getString("name"));
        return JSON.toJSONString(new LZResult<>(protocolMapper.getProtocolDropdownList(params)));

    }

    /**
     * 协议表 - 查询协议编号
     *
     * @return
     */
    public String getProtocolNoDropdownList(JSONObject request) {

        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",request.getString("client_id"));
        params.put("type",request.getString("type"));
        params.put("no",request.getString("no"));
        return JSON.toJSONString(new LZResult<>(protocolMapper.getProtocolNoDropdownList(params)));

    }

    /**
     * 协议名称name模糊匹配下拉框
     * 或者 根据authorizerId 获取协议下拉框
     * 或者 预约号
     * 或者 预约人
     *
     * @return
     */
    public String getProtocolNameDropdownList(JSONObject request) {

        LZResult<List<Map>> result = new LZResult<>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("airportCode", request.getString("client_id"));
            map.put("protocolId", request.getLong("protocolId"));
            map.put("protocolName", request.getString("protocolName"));
            map.put("protocolType", request.getLong("protocolType"));//协议类型
            map.put("authorizerId", request.getLong("authorizerId"));
            map.put("authorizerName", request.getString("authorizerName"));//预约人
            map.put("reservationNum", request.getString("reservationNum"));//预约号
            map.put("customerId", request.getLong("customerId"));//客户id
            List<Map> list = protocolMapper.getProtocolNameDropdownList(map);

            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            headerMap.put("user-id", request.getLong("user_id"));
            headerMap.put("client-id", request.getString("client_id"));


            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("clientId") != null) {
                    paramMap.put("institutionClientId", list.get(i).get("clientId"));
                    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://institution-client/institution-client/view", headerMap, paramMap));
                    if (jsonObject != null) {
                        JSONObject institutionClientObject = jsonObject.getJSONObject("data");
                        String clientValue = institutionClientObject.get("name").toString();
                        list.get(i).put("clientValue",clientValue);
                    }
                }
            }

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }

    /**
     * 重写协议类型下拉框 2017.3.27
     * 从数据库获取
     * @return
     */
    public String getProtocolTypeDropdownList (JSONObject request) {

        LZResult<List<Dropdownlist>> result = new LZResult<>();
        try{
            List<Dropdownlist> list = protocolTypeMapper.getProtocolTypeDropdownList(request.getLong("protocolTypeId"),request.getString("client_id"));
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }
}
