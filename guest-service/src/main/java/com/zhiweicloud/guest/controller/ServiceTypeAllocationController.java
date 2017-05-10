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

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceTypeAllocationController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value = "服务类型配置", description = "服务类型配置desc", tags = {"serviceTypeAllocation"})
public class ServiceTypeAllocationController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTypeAllocationController.class);
    private final ServiceTypeAllocationService serviceTypeAllocationService;

    @Autowired
    public ServiceTypeAllocationController(ServiceTypeAllocationService serviceTypeAllocationService) {
        this.serviceTypeAllocationService = serviceTypeAllocationService;
    }

    @GET
    @Path("service-type-allocation-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")
            })
    public LZResult<PaginationResult<ServiceTypeAllocation>> serviceTypeAllocationList(
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId){

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        return serviceTypeAllocationService.getAll(param,page,rows);

    }

    /**
     * 服务类型配置 - 服务大类下拉框 数据
     * @return 服务菜单列表
     */
    @GET
    @Path("service-menu-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 服务菜单 数据 ", notes = "返回服务菜单列表", httpMethod = "GET", produces = "application/json")
    public LZResult<List<ServiceTypeAllocation>> getServiceMenuList(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        List<ServiceTypeAllocation> serviceMenuList = serviceTypeAllocationService.getServiceMenuList(param);
        return new LZResult<>(serviceMenuList);

    }

    /**
         * 服务类型配置 - 服务大类下拉框 数据
         * @return 服务大类列表
         */
        @GET
        @Path("service-category-dropdown-list")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "服务类型配置 - 服务大类下拉框 数据 ", notes = "返回服务大类列表", httpMethod = "GET", produces = "application/json")
        public LZResult<List<Dropdownlist>> getServiceCategoryDropdownList(
                @HeaderParam("client-id") String airportCode,
                @HeaderParam("user-id") Long userId) {
            Map<String,Object> param = new HashMap<>();
            param.put("airportCode",airportCode);
            List<Dropdownlist> serviceCategoryList = serviceTypeAllocationService.getServiceCategoryDropdownList(param);
            return new LZResult<>(serviceCategoryList);
    }

    /**
     * 服务类型配置 - 根据category查询
     * @param category 服务大类
     * @return 服务类别列表
     */
    @GET
    @Path("service-type-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 根据category查询 ", notes = "返回服务类别列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParam(name = "category", value = "服务大类", dataType = "String", defaultValue = "VIP", required = true, paramType = "query")
    public LZResult<List<Dropdownlist>> getServiceTypeDropdownList(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId,
            @QueryParam(value = "category") String category) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("category",category);
        List<Dropdownlist> serviceTypeList = serviceTypeAllocationService.getServiceTypeDropdownList(param);
        return new LZResult<>(serviceTypeList);

    }

    /**
     * 服务类型配置 - 根据id查询
     * @param id 服务类型配置id
     * @return 服务名称列表
     */
    @GET
    @Path("service-name-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务类型配置 - 根据id查询 ", notes = "返回服务名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParam(name = "id", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    public LZResult<List<Dropdownlist>> getServiceNameDropdownList(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId,
            @QueryParam(value = "id") Long id) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",airportCode);
        param.put("id",id);
        List<Dropdownlist> serviceNameList = serviceTypeAllocationService.getServiceNameDropdownList(param);
        return new LZResult<>(serviceNameList);

    }
}
