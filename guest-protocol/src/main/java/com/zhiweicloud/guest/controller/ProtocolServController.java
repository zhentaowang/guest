package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.ProtocolServ;
import com.zhiweicloud.guest.service.ProtocolServService;
import com.zhiweicloud.guest.service.ProtocolService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProtocolServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("guest-protocol")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class ProtocolServController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolServController.class);
    @Autowired
    private ProtocolServService protocolServService;

    @Autowired
    private ProtocolService protocolService;

    @GET
    @Path("protocol-serv-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "productTypeAllocationId", value = "产品类型配置id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "price", value = "服务单价", dataType = "BigDecimal", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "freeRetinueNum", value = "免费随员人数", dataType = "Integer", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "overStaffUnitPrice", value = "超员单价", dataType = "BigDecimal", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "description", value = "价格说明", dataType = "String", required = false, paramType = "query")})
    public String list(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "protocolId") Long protocolId,
            @QueryParam(value = "productTypeAllocationId") Long productTypeAllocationId,
            @QueryParam(value = "price") BigDecimal price,
            @QueryParam(value = "freeRetinueNum") Integer freeRetinueNum,
            @QueryParam(value = "overStaffUnitPrice") BigDecimal overStaffUnitPrice,
            @QueryParam(value = "description") String description) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        param.put("productTypeAllocationId",productTypeAllocationId);
        param.put("price",price);
        param.put("freeRetinueNum",freeRetinueNum);
        param.put("overStaffUnitPrice",overStaffUnitPrice);
        param.put("description",description);
        LZResult<PaginationResult<ProtocolServ>> result  = protocolServService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    @GET
    @Path("protocol-serv-type")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "serviceId", value = "服务id", dataType = "Long", required = false, paramType = "query")})
    public String protocolServType(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "protocolId") Long protocolId,
            @QueryParam(value = "serviceId") Long serviceId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        param.put("serviceId",serviceId);
        LZResult<PaginationResult<ProtocolServ>> result  = protocolServService.getProtocolServType(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 协议服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("protocol-serv-save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="协议服务管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "protocolServ", required = true) @RequestBody RequsetParams<ProtocolServ> params){
        try{
            ProtocolServ protocolServ = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                protocolServ = params.getData().get(0);
            }

            if (protocolServ.getProtocolServList().size() == 0) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            protocolServService.saveOrUpdate(protocolServ);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 协议服务管理 - 根据id查询
     * @param airportCode
     * @param id
     * @return
     */
    @GET
    @Path("protocol-serv-view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议服务管理 - 根据id查询 ", notes = "返回协议服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "协议服务id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "airportCode") String airportCode,
                                       @QueryParam(value = "id") Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        ProtocolServ protocolServ = protocolServService.getById(param);
        return JSON.toJSONString(new LZResult<>(protocolServ));
    }

    /**
     * 协议服务管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @POST
    @Path("protocol-serv-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议服务id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "protocolId") Long protocolId) {
        try {
            List<Long> ids = params.getData();
            boolean flame = protocolService.selectOrderByProtocolId(protocolId,airportCode);
            if(flame == true){
                return JSON.toJSONString(LXResult.build(4998, "该协议被订单引用，协议下服务不能被删除"));
            }
            protocolServService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete  protocolServ by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 协议服务管理 - 按类别删除
     * @param airportCode
     * @param productTypeAllocationId
     * @param description
     * @return
     */
    @POST
    @Path("protocol-serv-delete-by-type")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "productTypeAllocationId", value = "产品类型配置id", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "description", value = "价格说明", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "price", value = "服务单价", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "freeRetinueNum", value = "免费随员人数", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "overStaffUnitPrice", value = "超员单价", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "String", required = true, paramType = "query")
    })
    public String deleteByType(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "productTypeAllocationId") String productTypeAllocationId,
            @QueryParam(value = "description") String description,
            @QueryParam(value = "price") String price,
            @QueryParam(value = "freeRetinueNum") String freeRetinueNum,
            @QueryParam(value = "overStaffUnitPrice") String overStaffUnitPrice,
            @QueryParam(value = "protocolId") String protocolId) {
        try {
            Map<String,Object> param = new HashMap();
            param.put("airportCode",airportCode);
            param.put("productTypeAllocationId",productTypeAllocationId);
            param.put("description",description);
            param.put("protocolId",protocolId);
            param.put("price",price);
            param.put("freeRetinueNum",freeRetinueNum);
            param.put("overStaffUnitPrice",overStaffUnitPrice);
            if(protocolService.selectOrderByProtocolId(Long.parseLong(protocolId),airportCode) == true){
                return JSON.toJSONString(LXResult.build(4998, "该协议被订单引用，协议下服务不能被删除"));
            }
            protocolServService.deleteByType(param);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete  protocolServ by type error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

}
