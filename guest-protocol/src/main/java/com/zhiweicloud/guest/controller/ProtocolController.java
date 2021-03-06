package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.ProtocolTypeEnum;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.ProtocolService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Protocol.java
 * Copyright(C) 2017 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-03 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class ProtocolController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolController.class);
    @Autowired
    private ProtocolService protocolService;

    /**
     * 协议管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return LXResult 成功还是失败
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String save(@ApiParam(value = "protocol", required = true) @RequestBody String params,
                       @HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId) {
        try {
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            JSONArray authorizerList = param00.getJSONArray("authorizer");
            param00.remove("authorizer");
            param00.remove("protocolProduct");
            Protocol protocol = JSONObject.toJavaObject(param00,Protocol.class);
            protocol.setAirportCode(airportCode);
            protocol.setCreateUser(userId);
            protocol.setUpdateUser(userId);
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
            if (protocolService.selectByName(protocol) == true) {
                return JSON.toJSONString(LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display()));
            }
            Long data = protocolService.saveOrUpdate(protocol);
            LZResult<Long> result = new LZResult<>();
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(data);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 协议管理 - 根据id查询
     * @param protocolId 协议id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return LZResult 协议详情
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议 - 根据id查询 ", notes = "返回协议详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "188", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "protocolId") Long protocolId,
                       @HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("protocolId", protocolId);
        Protocol protocol = protocolService.getDetialById(param);
        return JSON.toJSONString(new LZResult<>(protocol));
    }

    /**
     * 协议管理 - 根据id查询
     * @param airportCode 机场代码
     * @param protocolId 协议id
     * @return LZResult 协议详情
     */
    @GET
    @Path("getById")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议 - 根据id仅查询协议信息 ", notes = "返回协议详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "188", required = true, paramType = "query")
    })
    public String getById(@HeaderParam("client-id") String airportCode,
                       @QueryParam(value = "protocolId") Long protocolId
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("protocolId", protocolId);
        Protocol protocol = protocolService.getById(param);
        return JSON.toJSONString(new LZResult<>(protocol));
    }

    /**
     * 协议管理 - 根据协议产品id查询服务类别树
     * @param protocolProductId 协议产品id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return LZResult 服务类别树
     */
    @GET
    @Path("get-service-type-tree-by-protocol-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 根据协议产品id查询服务类别树 ", notes = "返回服务类别树", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "4", required = true, paramType = "query")
    })
    public String getServiceMenuList(@HeaderParam("client-id") String airportCode,
                                     @HeaderParam("user-id") Long userId,
                                     @QueryParam(value = "protocolProductId") Long protocolProductId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("protocolProductId", protocolProductId);
        List<ProtocolProductServ> serviceMenuList = protocolService.getServiceMenuList(param);
        return JSON.toJSONString(new LZResult<>(serviceMenuList));
    }

    /**
     * 协议管理 - 根据协议产品id查询服务类别
     * @param airportCode 机场代码
     * @param userId 用户id
     * @param protocolProductId  协议产品id
     * @return LZResult 服务类别树
     */
    @GET
    @Path("get-service-type-by-protocol-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 根据协议产品id查询服务类别 ", notes = "返回服务类别", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "4", required = true, paramType = "query")
    })
    public String getServiceTypeList(@HeaderParam("client-id") String airportCode,
                                     @HeaderParam("user-id") Long userId,
                                     @QueryParam(value = "protocolProductId") Long protocolProductId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("protocolProductId", protocolProductId);
        List<ProtocolProductServ> serviceMenuList = protocolService.getServiceTypeList(param);
        return JSON.toJSONString(new LZResult<>(serviceMenuList));
    }

    /**
     * 协议管理 - 根据服务类型配置id和协议产品id查询服务详情
     * @param page 起始页
     * @param rows 每页显示数目
     * @param typeId 服务类型配置id
     * @param protocolProductId 协议产品id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return LZResult 服务详情列表
     */
    @GET
    @Path("get-service-list-by-type-and-protocol-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 根据服务类型配置id和协议产品id查询服务详情", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "4", required = true, paramType = "query")})
    public String list( @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @QueryParam(value = "typeId") Long typeId,
                        @QueryParam(value = "protocolProductId") Long protocolProductId,
                        @HeaderParam("client-id") String airportCode,
                        @HeaderParam("user-id") Long userId) {
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("typeId", typeId);
        param.put("protocolProductId", protocolProductId);
        LZResult<PaginationResult<JSONObject>> result  = protocolService.getServiceListByTypeId(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 协议管理 - 根据服务类型配置id和协议产品id查询服务下拉框
     * @param typeId 服务类型配置id
     * @param protocolProductId 协议产品id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return LZResult 服务下拉框
     */
    @GET
    @Path("get-service-box-by-type-and-protocol-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 根据服务类型配置id和协议产品id查询服务下拉框", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {       @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "4", required = true, paramType = "query")})
    public String getServiceDropDownBox(@QueryParam(value = "typeId") Long typeId,
                        @QueryParam(value = "protocolProductId") Long protocolProductId,
                                        @HeaderParam("client-id") String airportCode,
                                        @HeaderParam("user-id") Long userId) {
        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("typeId", typeId);
        param.put("protocolProductId", protocolProductId);
        LZResult<List<ProtocolProductServ>> result  = protocolService.getServiceDropDownBox(param);
        return JSON.toJSONString(result);
    }

        /**
         * 重写协议列表，2017.2.23
         * @param page 起始页
         * @param rows 每页显示数目
         * @param protocolType 协议类型
         * @param institutionClientName 机构客户名称
         * @param protocolName 协议名称
         * @param airportCode 机场代码
         * @return LZResult 协议详情列表
         */
        @GET
        @Path("protocolList")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 分页查询", notes = "返回分页结果")
        public String protocolList (
                @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
                @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
                @QueryParam(value = "protocolType") Integer protocolType,
                @QueryParam(value = "institutionClientName") String institutionClientName,
                @QueryParam(value = "institutionClientId") Long institutionClientId,
                @QueryParam(value = "protocolName") String protocolName,
                @HeaderParam("client-id") String airportCode
        ){
            Protocol protocolParam = new Protocol();
            protocolParam.setAirportCode(airportCode);

            protocolParam.setInstitutionClientName(institutionClientName);
            protocolParam.setInstitutionClientId(institutionClientId);
            protocolParam.setName(protocolName);
            if (protocolType != null) {
                protocolParam.setType(protocolType);
            }

            LZResult<PaginationResult<Protocol>> result = protocolService.getProtocolList(protocolParam, page, rows);
            return JSON.toJSONString(result);
        }

        /**
         * 协议类型下拉框
         * @return 协议类型下拉框
         */
        @GET
        @Path("protocolTypeSelect")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 协议类型下拉框", notes = "返回协议类型")
        public String getProtocolTypeSelect () {
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
         * @param params ids
         * @return
         */
        @POST
        @Path("deleteProtocol")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
        public String deleteProtocol (
                @Context final HttpHeaders headers,
                @RequestBody RequsetParams< Long > params){
            try {
                List<Long> ids = params.getData();
                Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id").toString());
                String airportCode = headers.getRequestHeaders().getFirst("client-id").toString();

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
                protocolService.deleteById(ids, userId, airportCode);
                return JSON.toJSONString(LXResult.success());
            } catch (Exception e) {
                logger.error("delete protocol by ids error", e);
                return JSON.toJSONString(LXResult.error());
            }
        }


    }