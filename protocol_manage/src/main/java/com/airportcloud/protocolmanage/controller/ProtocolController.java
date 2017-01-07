package com.airportcloud.protocolmanage.controller;


import com.airportcloud.protocolmanage.APIUtil.LXResult;
import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.LZStatus;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.RequsetParams;
import com.airportcloud.protocolmanage.model.Protocol;
import com.airportcloud.protocolmanage.service.ProtocolService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Protocol.java
 * Copyright(C) 2017 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-03 13:17:52 Created By wzt
 */
@RestController
@RequestMapping("/Protocol")
@Api(value="协议",description="协议desc ", tags={"Protocol"})
public class ProtocolController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolController.class);
    @Autowired
    private ProtocolService protocolService;

    @RequestMapping(value ="/list")
    @ApiOperation(value = "协议管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientName", value = "机构客户名称", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientNo", value = "机构客户编号", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientType", value = "机构客户类型", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "name", value = "协议名称", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "no", value = "协议编号", dataType = "String", required = false, paramType = "query")})
    public LZResult<PaginationResult<Protocol>> list(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "institutionClientName", required = false) String institutionClientName,
            @RequestParam(value = "institutionClientNo", required = false) String institutionClientNo,
            @RequestParam(value = "institutionClientType", required = false) String institutionClientType,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "no", required = false) String no) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("institutionClientName",institutionClientName);
        param.put("institutionClientNo",institutionClientNo);
        param.put("institutionClientType",institutionClientType);
        param.put("name",name);
        param.put("no",no);
        LZResult<PaginationResult<Protocol>> result  = protocolService.getAll(param,page,rows);
        return result;
    }

    /**
     * 协议管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/saveOrUpdate", method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="协议管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "protocol", required = true) @RequestBody RequsetParams<Protocol> params){
        try{
            Protocol protocol = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                protocol = params.getData().get(0);
            }

            if (protocol == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            protocolService.saveOrUpdate(protocol);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

}
