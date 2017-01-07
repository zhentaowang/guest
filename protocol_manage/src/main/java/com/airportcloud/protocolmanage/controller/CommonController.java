/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.airportcloud.protocolmanage.controller;


import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.model.Dropdownlist;
import com.airportcloud.protocolmanage.service.ProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiImplicitParam(name = "type", value = "协议类型:冠名、头等舱、金银卡", dataType = "String", required = false, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getProtocolDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                                      @RequestParam(value = "type", required = false) String type
    ) {
        List<Dropdownlist> protocolNameList = protocolService.getProtocolDropdownList(airportCode,type);
        return new LZResult<List<Dropdownlist>>(protocolNameList);
    }
}
