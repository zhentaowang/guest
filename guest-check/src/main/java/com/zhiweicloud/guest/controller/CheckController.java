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

package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ExcelUtils;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.service.CheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value = "对账单管理", description = "", tags = {"对账单管理"})
public class CheckController {

    private static final Logger logger = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    private CheckService checkService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务账单列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    public String list(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @BeanParam final CheckQueryParam checkQueryParam,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            LZResult<PaginationResult<Map>> result = checkService.getAll(userId,airportCode,checkQueryParam, page, rows);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            LZResult result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }

    @GET
    @Path("customer-checklist")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务账单详情列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    public String customerChecklist(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @BeanParam final OrderCheckDetail orderCheckDetail,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            Map result = checkService.customerChecklist(airportCode,orderCheckDetail, page, rows);
            Map map = new HashMap();
            map.put("data",result);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            e.printStackTrace();
            LZResult result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }

    /**
     * 导出文件
     * Excel
     * @param orderCheckDetail 参数
     * @param airportCode 机场码
     * @param userId 用户ID
     * @return
     */
    @GET
    @Path("exportFile")
    @Produces("application/x-msdownload;charset=utf8")
    @ApiOperation(value = "导出文件 - 默认Excel", notes = "返回分页结果", httpMethod = "GET", produces = "application/x-msdownload")
    public void exportFile(
            @BeanParam final OrderCheckDetail orderCheckDetail,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId,
            @Context HttpServletResponse response) {
        try {

            Map result = checkService.customerChecklist(airportCode, orderCheckDetail, 1, 10);
            if (result != null) {
                checkService.exportExcel(orderCheckDetail, result, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
