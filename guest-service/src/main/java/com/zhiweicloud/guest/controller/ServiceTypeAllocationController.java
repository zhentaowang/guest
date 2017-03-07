package com.zhiweicloud.guest.controller;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.ServiceTypeAllocation;
import com.zhiweicloud.guest.service.ServiceTypeAllocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.HashMap;
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
@Api(value = "服务类型配置", description = "服务类型配置desc", tags = {"serviceTypeAllocation"})
public class ServiceTypeAllocationController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceTypeAllocationController.class);

    @Autowired
    private ServiceTypeAllocationService serviceTypeAllocationService;

    @GET
    @Path("service-type-allocation-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")
            })
    public LZResult<PaginationResult<ServiceTypeAllocation>> list(
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @Context final HttpHeaders headers){
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        LZResult<PaginationResult<ServiceTypeAllocation>> result  = serviceTypeAllocationService.getAll(param,page,rows);
        return result;
    }

    /**
     * 服务类型配置 - 服务大类下拉框 数据
     * @return
     */
    @GET
    @Path("service-menu-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 服务菜单 数据 ", notes = "返回服务菜单列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query")
    })
    public LZResult<List<ServiceTypeAllocation>> getServiceMenuList(@Context final HttpHeaders headers
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        List<ServiceTypeAllocation> serviceMenuList = serviceTypeAllocationService.getServiceMenuList(param);
        return new LZResult<>(serviceMenuList);
    }

    /**
     * 服务类型配置 - 服务大类下拉框 数据
     * @return
     */
    @GET
    @Path("service-category-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 服务大类下拉框 数据 ", notes = "返回服务大类列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServiceCategoryDropdownList(@Context final HttpHeaders headers
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        List<Dropdownlist> serviceCategoryList = serviceTypeAllocationService.getServiceCategoryDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceCategoryList);
    }

    /**
     * 服务类型配置 - 根据category查询
     * @param category
     * @return
     */
    @GET
    @Path("service-type-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 根据category查询 ", notes = "返回服务类别列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "category", value = "服务大类", dataType = "String", defaultValue = "VIP", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServiceTypeDropdownList(@Context final HttpHeaders headers,
                               @QueryParam(value = "category") String category
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("category",category);
        List<Dropdownlist> serviceTypeList = serviceTypeAllocationService.getServiceTypeDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceTypeList);
    }

    /**
     * 服务类型配置 - 根据id查询
     * @param id
     * @return
     */
    @GET
    @Path("service-name-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 根据id查询 ", notes = "返回服务名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServiceNameDropdownList(@Context final HttpHeaders headers,
                                                         @QueryParam(value = "id") Long id
    ) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("id",id);
        List<Dropdownlist> serviceNameList = serviceTypeAllocationService.getServiceNameDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceNameList);
    }
}
