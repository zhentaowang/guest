package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Authorizer;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.service.AuthorizerService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
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
@Path("/")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class AuthorizerController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizerController.class);
    @Autowired
    private AuthorizerService authorizerService;

    @GET
    @Path("authorizer-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "授权人管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "18", required = true, paramType = "query")})
    public String list(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "protocolId") Long protocolId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        LZResult<List<Authorizer>> result  = authorizerService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 授权人管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("authorizer-save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="授权人管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "authorizer", required = true) @RequestBody RequsetParams<Authorizer> params){
        try{
            Authorizer authorizer = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                authorizer = params.getData().get(0);
            }

            if (authorizer == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            authorizerService.saveOrUpdate(authorizer);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 授权人管理 - 根据id查询
     * @param airportCode
     * @param authorizerId
     * @return
     */
    @GET
    @Path("authorizer-view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "授权人管理 - 根据id查询 ", notes = "返回授权人详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "authorizerId", value = "授权人id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "airportCode") String airportCode,
                                     @QueryParam(value = "authorizerId") Long authorizerId
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("authorizerId",authorizerId);
        Authorizer authorizer = authorizerService.getById(param);
        return JSON.toJSONString(new LZResult<>(authorizer));
    }

    /**
     * 授权人管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @POST
    @Path("authorizer-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "授权人管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            List<Long> ids = params.getData();
            authorizerService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete authorizer by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 协议预约人模糊匹配下拉框
     * @return
     */
    @GET
    @Path(value = "getAuthorizerDropdownList")
    @ApiOperation(value="订单中用到协议预约人下拉框，只包含id，和value的对象",notes="根据数据字典的分类名称获取详情数据,下拉", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    public LZResult<List<Dropdownlist>> getAuthorizerDropdownList(
            ContainerRequestContext request,
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "name") String name) {
//        String airportCode = request.getHeaders().getFirst("client-id").toString();
        List<Dropdownlist> list = authorizerService.getAuthorizerDropdownList(airportCode,name);
        return new LZResult<>(list);
    }



}

