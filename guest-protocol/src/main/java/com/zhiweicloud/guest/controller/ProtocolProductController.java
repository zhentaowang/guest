package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.ProtocolProductService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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
@Api(value="协议产品",description="协议产品desc ", tags={"protocolProduct"})
public class ProtocolProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolProductController.class);
    @Autowired
    private ProtocolProductService protocolProductService;

    /**
     * 协议产品管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("protocol-product-save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议产品管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult protocolProductSave(@ApiParam(value = "protocolProduct", required = true) @RequestBody String params,
                         @Context final HttpHeaders headers) {
        try {
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            for(int k = 0; k < param.size(); k++){
                JSONObject param00 = JSON.parseObject(param.get(k).toString());
                ProtocolProduct protocolProduct = JSONObject.toJavaObject(param00,ProtocolProduct.class);
                protocolProduct.setAirportCode(airportCode);
                if(protocolProduct.getProtocolProductId() == null){
                    if (protocolProduct == null || protocolProduct.getProductId() == null || protocolProduct.getProtocolId() == null) {
                        return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                    }
                    if (protocolProductService.selectByProductId(protocolProduct) == true) {
                        return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
                    }
                }
                protocolProductService.saveOrUpdate(protocolProduct);
            }
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 协议产品服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("protocol-product-service-save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议产品服务管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult protocolProductServiceSave(@ApiParam(value = "protocolProductService", required = true) @RequestBody String params,
                                        @Context final HttpHeaders headers) {
        try {
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            for(int i = 0; i < param.size(); i++){
                JSONObject param00 = JSON.parseObject(param.get(i).toString());
                ProtocolProductServ protocolProductServ = JSONObject.toJavaObject(param00,ProtocolProductServ.class);
                protocolProductServ.setAirportCode(airportCode);
                if(protocolProductServ.getProtocolProductServiceId() == null){
                    if (protocolProductServ == null || protocolProductServ.getProtocolProductId() == null || protocolProductServ.getServiceTypeAllocationId() == null) {
                        return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                    }
                }
                protocolProductService.saveOrUpdateProtocolProductServ(protocolProductServ);
            }
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 协议产品管理 - 根据id查询
     *
     * @param protocolProductId
     * @return
     */
    @GET
    @Path("protocol-product-view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议产品管理 - 根据id查询 ", notes = "返回协议详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "4", required = true, paramType = "query")
    })
    public String protocolProductView(@Context final HttpHeaders headers,
                       @QueryParam(value = "protocolProductId") Long protocolProductId
    ) {
        Map<String, Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode", airportCode);
        param.put("protocolProductId", protocolProductId);
        ProtocolProduct protocolProduct = protocolProductService.getById(param);
        return JSON.toJSONString(new LZResult<>(protocolProduct));
    }

        /**
         * 协议产品管理 - 协议产品列表
         * @param page
         * @param rows
         * @return
         */
        @GET
        @Path("protocol-product-list")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议产品管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
        @ApiImplicitParams(
                {
                        @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                        @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                        @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "188", required = true, paramType = "query")})
        public String protocolProductList(
                @Context final HttpHeaders headers,
                @QueryParam(value = "page") Integer page,
                @QueryParam(value = "rows") Integer rows,
                @QueryParam(value = "protocolId") Long protocolId) {
            Map<String,Object> param = new HashMap();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            param.put("airportCode",airportCode);
            param.put("protocolId",protocolId);
            LZResult<PaginationResult<ProtocolProduct>> result  = protocolProductService.getAll(param,page,rows);
            return JSON.toJSONString(result);
        }

    /**
     * 协议产品管理 - 删除
     * @param params ids
     * @return
     */
    @POST
    @Path("protocol-product-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议产品管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String protocolProductDelete(
            @RequestBody RequsetParams<Long> params,
            @Context final HttpHeaders headers) {
        try {
            List<Long> ids = params.getData();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            for(int i = 0; i< ids.size();i++){
                if(protocolProductService.selectOrderByProtocolProductId(ids.get(i),airportCode) == true){
                    return JSON.toJSONString(LXResult.build(5004, "该项已被其他功能引用，无法删除；如需帮助请联系开发者"));
                }
            }
            protocolProductService.deleteById(ids,"LJG");
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete protocolProduct by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }
}