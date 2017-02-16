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
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.service.InstitutionClientService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path(value ="institution-client")
@Api(value="机构客户管理",description="", tags={"InstitutionClientModel"})
public class InstitutionClientController {
    private static final Logger logger = LoggerFactory.getLogger(InstitutionClientController.class);

    @Autowired
    private InstitutionClientService institutionClientService;

    @GET
    @Path(value ="list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "机构客户管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    public String list(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "no") String no,
            @QueryParam(value = "name") String name,
            @QueryParam(value = "type") String type,
            @QueryParam(value="airportCode") String airportCode) {
        InstitutionClient param = new InstitutionClient();
        param.setNo(no);
        param.setName(name);
        param.setType(type);
        param.setAirportCode(airportCode);
        LZResult<PaginationResult<InstitutionClient>> result  = institutionClientService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 机构客户管理 - 新增or更新
     * @return
     */
    @POST
    @Path(value="saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="机构客户管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "institutionClient", required = true) @RequestBody RequsetParams<InstitutionClient> params){
        try{
            InstitutionClient institutionClient = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                institutionClient = params.getData().get(0);
            }

            if (institutionClient == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            if (institutionClient.getName() == null || "".equals(institutionClient.getName())
                    || institutionClient.getEmployeeId() == null || "".equals(institutionClient.getEmployeeId())
                    || institutionClient.getType() == null || "".equals(institutionClient.getType())) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            institutionClientService.saveOrUpdate(institutionClient);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 机构客户管理 - 根据id查询员工
     * @param id
     * @return
     */
    @GET
    @Path(value = "view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "机构客户管理 - 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })*/
    public String view(
            @QueryParam(value = "id") Long id,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            InstitutionClient institutionClient = institutionClientService.getById(id,airportCode);
            return JSON.toJSONString(new LZResult<>(institutionClient));
        }catch (Exception e){
            e.printStackTrace();
            LZResult<InstitutionClient> result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(new LZResult<>(result));
        }
    }

    /**
     * 机构客户管理 - 删除
     * {
        "data": [
        6,7,8
        ]
    }
     * @return
     */
    @POST
    @Path(value = "delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "机构客户管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public LXResult delete(
            @RequestBody RequsetParams<Long> params,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            List<Long> ids = params.getData();
            institutionClientService.deleteById(ids,airportCode);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return LXResult.error();
        }
    }

    /**
     * 产品品类下拉框 数据
     * @return
     */
    @GET
    @Path(value="queryInstitutionClientDropdownList")
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="系统中用到机构客户信息下来框，只包含id，和value的对象",notes="根据数据字典的分类名称获取详情数据,下拉", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "模糊查询name", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "no", value = "模糊查询编号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> queryInstitutionClientDropdownList(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "name") String name,
            @QueryParam(value = "no") String no) {
        List<Dropdownlist> list = institutionClientService.queryInstitutionClientDropdownList(airportCode,name,no);
        return new LZResult<>(list);
    }




}
