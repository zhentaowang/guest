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
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.service.EmployeeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Path("guest-employee")
@Api(value = "员工管理", description = "", tags = {"Employee"})
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")

    public String list(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "name") String name,
            @QueryParam(value = "airportCode") String airportCode) {

        try {
            Employee employeeParam = new Employee();
            employeeParam.setName(name);
            employeeParam.setAirportCode(airportCode);

            LZResult<PaginationResult<Employee>> result = employeeService.getAll(employeeParam, page, rows);
            return JSON.toJSONString(result);
        }catch (Exception e){
            e.printStackTrace();
            LZResult result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return  JSON.toJSONString(result);
        }
    }

    /**
     * 员工管理 - 新增or更新
     * 需要判断name是否重复
     * @return
     */
    @POST
    @Path("saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult save(@ApiParam(value = "employee", required = true) @RequestBody RequsetParams<Employee> param) {
        try {
            Employee employee = null;
            if (!CollectionUtils.isEmpty(param.getData())) {
                employee = param.getData().get(0);
            }
            if (employee == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            employeeService.saveOrUpdate(employee);

            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 员工管理 - 根据id查询员工
     *
     * @param id
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public String view(
            @QueryParam("id") Long id,
            @QueryParam("airportCode") String airportCode) {
        try {
            Employee employee = employeeService.getById(id,airportCode);
            return JSON.toJSONString(new LZResult<>(employee));
        }catch (Exception e){
            e.printStackTrace();
            LZResult<Employee> result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(new LZResult<>(result));
        }
    }

    /**
     * 员工管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     *
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @QueryParam("airportCode") String airportCode,
            @RequestBody RequsetParams<Long> params) {
        try {
            List<Long> ids = params.getData();
            employeeService.deleteById(ids, airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 产品品类下拉框 数据
     *
     * @return
     */
    @GET
    @Path("queryEmployeeDropdownList")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "系统中用到员工信息下来框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public LZResult<List<Dropdownlist>> queryEmployeeDropdownList(@QueryParam("airportCode") String airportCode) {
        List<Dropdownlist> list = employeeService.queryEmployeeDropdownList(airportCode);
        return new LZResult<List<Dropdownlist>>(list);
    }


}
