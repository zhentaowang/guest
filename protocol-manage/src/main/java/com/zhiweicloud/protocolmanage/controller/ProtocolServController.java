package com.zhiweicloud.protocolmanage.controller;


import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.protocolmanage.model.ProtocolServ;
import com.zhiweicloud.protocolmanage.service.ProtocolServService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/guest-protocol")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class ProtocolServController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolServController.class);
    @Autowired
    private ProtocolServService protocolServService;

    @RequestMapping(value ="/protocol-serv-list")
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
    public LZResult<PaginationResult<ProtocolServ>> list(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "protocolId", required = false) Long protocolId,
            @RequestParam(value = "productTypeAllocationId", required = false) Long productTypeAllocationId,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "freeRetinueNum", required = false) Integer freeRetinueNum,
            @RequestParam(value = "overStaffUnitPrice", required = false) BigDecimal overStaffUnitPrice,
            @RequestParam(value = "description", required = false) String description) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        param.put("productTypeAllocationId",productTypeAllocationId);
        param.put("price",price);
        param.put("freeRetinueNum",freeRetinueNum);
        param.put("overStaffUnitPrice",overStaffUnitPrice);
        param.put("description",description);
        LZResult<PaginationResult<ProtocolServ>> result  = protocolServService.getAll(param,page,rows);
        return result;
    }

    @RequestMapping(value ="/protocol-serv-type")
    @ApiOperation(value = "协议服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "serviceId", value = "服务id", dataType = "Long", required = false, paramType = "query")})
    public LZResult<PaginationResult<ProtocolServ>> protocolServType(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "protocolId", required = false) Long protocolId,
            @RequestParam(value = "serviceId", required = false) Long serviceId) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("protocolId",protocolId);
        param.put("serviceId",serviceId);
        LZResult<PaginationResult<ProtocolServ>> result  = protocolServService.getProtocolServType(param,page,rows);
        return result;
    }

    /**
     * 协议服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/protocol-serv-save-or-update", method= RequestMethod.POST)
    @ResponseBody
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
    @RequestMapping(value = "/protocol-serv-view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "协议服务管理 - 根据id查询 ", notes = "返回协议服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "协议服务id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public LZResult<ProtocolServ> view(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                             @RequestParam(value = "id", defaultValue = "1", required = true) Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        ProtocolServ protocolServ = protocolServService.getById(param);
        return new LZResult<>(protocolServ);
    }

    /**
     * 协议服务管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @RequestMapping(value = "/protocol-serv-delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "协议服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public LXResult delete(
            @RequestBody RequsetParams<Long> params,
            @RequestParam(value = "airportCode", required = true) String airportCode) {
        try {
            List<Long> ids = params.getData();
            protocolServService.deleteById(ids,airportCode);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete  protocolServ by ids error", e);
            return LXResult.error();
        }
    }

    /**
     * 协议服务管理 - 按类别删除
     * @param airportCode
     * @param productTypeAllocationId
     * @param description
     * @return
     */
    @RequestMapping(value = "/protocol-serv-delete-by-type", method = RequestMethod.POST)
    @ResponseBody
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
    public LXResult deleteByType(
            @RequestParam(value = "airportCode", required = true) String airportCode,
            @RequestParam(value = "productTypeAllocationId", required = true) String productTypeAllocationId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "freeRetinueNum", required = false) String freeRetinueNum,
            @RequestParam(value = "overStaffUnitPrice", required = false) String overStaffUnitPrice,
            @RequestParam(value = "protocolId", required = true) String protocolId) {
        try {
            Map<String,Object> param = new HashMap();
            param.put("airportCode",airportCode);
            param.put("productTypeAllocationId",productTypeAllocationId);
            param.put("description",description);
            param.put("protocolId",protocolId);
            param.put("price",price);
            param.put("freeRetinueNum",freeRetinueNum);
            param.put("overStaffUnitPrice",overStaffUnitPrice);
            protocolServService.deleteByType(param);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete  protocolServ by type error", e);
            return LXResult.error();
        }
    }

}
