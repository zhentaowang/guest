package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.ProductServiceType;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.model.ServiceDetail;
import com.zhiweicloud.guest.service.ServService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value="服务",description="服务desc", tags={"service"})
public class ServController {
    private static final Logger logger = LoggerFactory.getLogger(ServController.class);
    @Autowired
    private ServService servService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")})
    public String list( @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @QueryParam(value = "typeId") Long typeId,
                        @Context final HttpHeaders headers) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("typeId", typeId);
        LZResult<PaginationResult<JSONObject>> result  = servService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="服务管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query")
    public LXResult save(@ApiParam(value = "service", required = true) @RequestBody String params,
                         @Context final HttpHeaders headers){
        try{
            Serv serv = new Serv();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            JSONArray param= JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            serv.setServId(param00.getLong("servId"));
            serv.setAirportCode(airportCode);
            serv.setName(param00.getString("name"));
            serv.setServiceTypeAllocationId(param00.getLong("serviceTypeAllocationId"));
            param00.remove("servId");
            param00.remove("name");
            param00.remove("serviceTypeAllocationId");
            serv.setServiceDetail(param00.toJSONString());
            Set keys = param00.keySet();
            Map<String,Object> serviceFieldName = ServiceDetail.getServiceFieldName(serv.getServiceTypeAllocationId());
            if(keys.size() != serviceFieldName.size()){
                return LXResult.build(4995, "传输数据字段错误");
            }else{
                if(serviceFieldName != null){
                    for(int i = 0; i < keys.size(); i++){
                        if(!serviceFieldName.containsKey(keys.toArray()[i])){
                            return LXResult.build(4995, "传输数据字段错误");
                        }else{
                            if(param00.getString(keys.toArray()[i].toString()).isEmpty() && !keys.toArray()[i].toString().equals("plateNumber")){
                                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                            }
                        }
                    }
                }
            }
            if (serv.getServId() == null){
                if (serv == null || serv.getAirportCode() == null || serv.getServiceTypeAllocationId() == null
                        || serv.getName() == null) {
                    return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                }
            }else{
                if (serv.getAirportCode() == null || serv.getName() == null) {
                    return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                }
            }

            if(servService.selectByName(serv) == true){
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            servService.saveOrUpdate(serv);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 服务管理 - 根据id查询
     * @param servId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务 - 根据id查询 ", notes = "返回服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "servId", value = "服务id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(@Context final HttpHeaders headers,
                               @QueryParam(value = "servId") Long servId) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("servId",servId);
        Serv serv = servService.getById(param);
        JSONObject result = JSON.parseObject(serv.getServiceDetail());
        result.put("servId",serv.getServId());
        result.put("airportCode",serv.getAirportCode());
        result.put("serviceTypeAllocationId",serv.getServiceTypeAllocationId());
        result.put("name",serv.getName());
        return result.toJSONString();
    }

    /**
     * 服务管理 - 删除
     * @param params ids
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @Context final HttpHeaders headers) {
        try {
            List<Long> ids = params.getData();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
//            for(int i = 0; i < ids.size(); i++){
//                if(servService.selectProductByServiceId(ids.get(i),airportCode) == true){
//                    return JSON.toJSONString(LXResult.build(4996, "该服务被产品引用，不能被删除"));
//                }
//            }
            servService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete serv by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 服务管理 - 根据服务类型配置id和产品id查询服务详情
     * @param page 起始页
     * @param rows 每页显示数目
     * @param typeId 服务类型配置id
     * @param productId 产品id
     * @return
     */
    @GET
    @Path("get-service-list-by-type-and-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 根据服务类型配置id和产品id查询服务详情", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Long", defaultValue = "39", required = true, paramType = "query")})
    public String getServiceList( @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @QueryParam(value = "typeId") Long typeId,
                        @QueryParam(value = "productId") Long productId,
                        @Context final HttpHeaders headers) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("typeId", typeId);
        param.put("productId", productId);
        LZResult<PaginationResult<JSONObject>> result  = servService.getServiceListByTypeId(param,page,rows);
        return JSON.toJSONString(result);
    }

    @GET
    @Path("product-and-service-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "1", required = true, paramType = "query")})
    public String getProductAndServiceList( @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @Context final HttpHeaders headers) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        LZResult<PaginationResult<ProductServiceType>> result  = servService.getProductAndServiceList(param,page,rows);
        return JSON.toJSONString(result);
    }

}
