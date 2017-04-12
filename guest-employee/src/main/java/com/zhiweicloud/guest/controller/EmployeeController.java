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
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.service.EmployeeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value = "员工管理", description = "", tags = {"Employee"})
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = Employee.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public String list(
            @DefaultValue("1") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @HeaderParam("client-id") String airportCode,
            @QueryParam(value = "name") String name){
        try {
            Employee employeeParam = new Employee();
            employeeParam.setName(name);
            employeeParam.setAirportCode(airportCode);
            LZResult<PaginationResult<Map>> result = employeeService.getAll(employeeParam, page, rows);
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

    /**
     * 员工管理 - 新增or更新
     * 需要判断name是否重复
     *
     * @return
     */
    @POST
    @Path("saveOrUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String save(@ApiParam(value = "employee", required = true) RequsetParams<Employee> param, @HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId) {
        LZResult<String> result = new LZResult<>();
        try {
            Employee employee = null;
            if (!CollectionUtils.isEmpty(param.getData())) {
                employee = param.getData().get(0);
            }
            if (employee == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            } else {
                employee.setAirportCode(airportCode);
                if (employee.getEmployeeId() != null) {
                    employee.setUpdateUser(userId);
                    employee.setUpdateTime(new Date());
                } else {
                    employee.setCreateUser(userId);
                    employee.setCreateTime(new Date());
                }
                employeeService.saveOrUpdate(employee);
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 员工管理 - 根据id查询员工
     *
     * @param employeeId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = Employee.class)
    })
    public String view(
            @QueryParam("employeeId") Long employeeId,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<Object> result = new LZResult<>();
        try {
            Map employee = employeeService.getById(employeeId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(employee);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(new LZResult<>(result));
        }
    }

    /**
     * 员工管理 - 根据用户id获取角色列表
     *
     * @param employeeId
     * @return
     */
    @GET
    @Path("getRoleByUserId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "员工管理 - 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 405, message = "请求方式不对"),
            @ApiResponse(code = 200, message = "请求成功", response = Long.class)
    })
    public String getRoleByUserId(
            @QueryParam("employeeId") Long employeeId,
            ContainerRequestContext request) {
        LZResult<List<Map>> result = new LZResult<>();
        try {
            String airportCode = request.getHeaders().getFirst("client-id").toString();
            List<Map> employee = employeeService.getRoleListByUserId(employeeId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(employee);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
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
            @Context final HttpHeaders headers,
            @RequestBody RequsetParams<Long> params) {
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            List<Long> ids = params.getData();
            employeeService.deleteById(ids, userId, airportCode);
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
    public String queryEmployeeDropdownList(@HeaderParam("client-id") String airportCode,
                                            @QueryParam(value = "name") String name) {
        LZResult<Object> result = new LZResult<>();
        try {
            List<Dropdownlist> list = employeeService.queryEmployeeDropdownList(airportCode,name);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据角色查代办人 下拉框
     * （目前用全部员工，前端不传roleId）
     * @return
     */
    @GET
    @Path("getEmployeeDropdownListByRoleId")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "系统中用到的员工下拉框，只包含id，和value的对象", notes = "根据数据字典的角色id获取用户数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String getEmployeeDropdownListByRoleId(@HeaderParam("client-id") String airportCode,
                                                  @QueryParam(value = "roleId") Long roleId,
                                                  @QueryParam(value = "name") String name) {
        LZResult<List<Dropdownlist>> result = new LZResult<>();
        try {
            List<Dropdownlist> list = employeeService.getEmployeeDropdownListByRoleId(airportCode, roleId, name);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    @GET
    @Path("getUserName")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "根据accesstoken获取用户名", notes = "返回登录人用户名", httpMethod = "GET", produces = "application/json")
    public String getUserName(@HeaderParam("client-id") String airportCode,@HeaderParam("user-id") Long userId){
        LZResult<Object> result = new LZResult<>();
        try {
            Map employee = employeeService.getById(userId,airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(employee);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }


}
