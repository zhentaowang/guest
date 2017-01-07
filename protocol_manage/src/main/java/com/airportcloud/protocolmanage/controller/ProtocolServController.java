package com.airportcloud.protocolmanage.controller;


import com.airportcloud.protocolmanage.APIUtil.LXResult;
import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.LZStatus;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.RequsetParams;
import com.airportcloud.protocolmanage.model.ProtocolServ;
import com.airportcloud.protocolmanage.service.ProtocolServService;
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
public class ProtocolServController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolServController.class);
    @Autowired
    private ProtocolServService protocolServService;

    @RequestMapping(value ="/protocolServList")
    @ApiOperation(value = "协议服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query")})
    public LZResult<PaginationResult<ProtocolServ>> list(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "protocolId", required = false) Long protocolId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        LZResult<PaginationResult<ProtocolServ>> result  = protocolServService.getAll(param,page,rows);
        return result;
    }

    /**
     * 协议服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/protocolServSaveOrUpdate", method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="协议服务管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "protocolServ", required = true) @RequestBody RequsetParams<ProtocolServ> params){
        try{
            ProtocolServ protocolServ = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                protocolServ = params.getData().get(0);
            }

            if (protocolServ == null) {
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
    @RequestMapping(value = "/protocolServView", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "协议服务 - 根据id查询 ", notes = "返回协议服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "服务id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public LZResult<Map<String,Object>> view(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                             @RequestParam(value = "id", defaultValue = "1", required = true) Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        Map<String,Object> protocolServ = protocolServService.getById(param);
        return new LZResult<>(protocolServ);
    }

    /**
     * 协议服务管理 - 删除
     * {
     "data": [
     "LJG","1"
     ]
     }
     * @return
     */
    @RequestMapping(value = "/protocolServDelete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "协议服务 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public LXResult delete(@RequestBody RequsetParams<String> params) {
        try {
            List<String> ids = params.getData();
            protocolServService.deleteById(ids);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return LXResult.error();
        }
    }

}
