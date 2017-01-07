package com.airportcloud.protocolmanage.controller;


import com.airportcloud.protocolmanage.APIUtil.LXResult;
import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.LZStatus;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.RequsetParams;
import com.airportcloud.protocolmanage.model.Authorizer;
import com.airportcloud.protocolmanage.service.AuthorizerService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProtocolServ.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@RestController
@RequestMapping("/protocol")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class AuthorizerController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizerController.class);
    @Autowired
    private AuthorizerService authorizerService;

    @RequestMapping(value ="/authorizerList")
    @ApiOperation(value = "授权人管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query")})
    public LZResult<PaginationResult<Authorizer>> list(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "protocolId", required = false) Long protocolId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        LZResult<PaginationResult<Authorizer>> result  = authorizerService.getAll(param,page,rows);
        return result;
    }

    /**
     * 授权人管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/authorizerSaveOrUpdate", method= RequestMethod.POST)
    @ResponseBody
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/authorizerView", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "授权人管理 - 根据id查询 ", notes = "返回授权人详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "授权人id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public LZResult<Map<String,Object>> view(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                             @RequestParam(value = "id", defaultValue = "1", required = true) Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        Map<String,Object> authorizer = authorizerService.getById(param);
        return new LZResult<>(authorizer);
    }

    /**
     * 授权人管理 - 删除
     * {
     "data": [
     "LJG","1"
     ]
     }
     * @return
     */
    @RequestMapping(value = "/authorizerDelete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "授权人管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public LXResult delete(@RequestBody RequsetParams<String> params) {
        try {
            List<String> ids = params.getData();
            authorizerService.deleteById(ids);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return LXResult.error();
        }
    }
}

