package com.zhiweicloud.protocolmanage.controller;

import com.zhiweicloud.protocolmanage.service.ProtocolService;
import com.zhiweicloud.guest.APIUtil.*;
import com.zhiweicloud.guest.model.Dropdownlist;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@RestController
@RequestMapping("/commonController")
@Api(value="系统公共接口",description="产品品类desc ", tags={"commonController"})
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ProtocolService protocolService;

    /**
     * 协议表 - 查询协议名称
     * @return
     */
    @RequestMapping(value = "/getProtocolDropdownList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "协议表 - 查询协议名称", notes = "返回协议名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "协议类型:VIP、CIP", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "协议名称", dataType = "String", required = false, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getProtocolDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                                      @RequestParam(value = "type", required = false) String type,
                                                                @RequestParam(value = "name", required = false) String name
    ) {
        List<Dropdownlist> protocolNameList = protocolService.getProtocolDropdownList(airportCode,type,name);
        return new LZResult<List<Dropdownlist>>(protocolNameList);
    }

    /**
     * 协议表 - 查询协议编号
     * @return
     */
    @RequestMapping(value = "/getProtocolNoDropdownList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "协议表 - 查询协议编号", notes = "返回协议编号列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "no", value = "协议编号", dataType = "String", required = false, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getProtocolNoDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                                                @RequestParam(value = "no", required = false) String no
    ) {
        List<Dropdownlist> protocolNameList = protocolService.getProtocolNoDropdownList(airportCode, no);
        return new LZResult<List<Dropdownlist>>(protocolNameList);
    }
}
