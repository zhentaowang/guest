package com.zhiweicloud.guest.controller;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.ProductTypeAllocation;
import com.zhiweicloud.guest.service.ProductTypeAllocationService;
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
@Path("guest-product")
@Api(value = "产品类型配置", description = "产品类型配置desc", tags = {"productTypeAllocation"})
public class ProductTypeAllocationController {
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeAllocationController.class);

    @Autowired
    private ProductTypeAllocationService productTypeAllocationService;

    @GET
    @Path("product-type-allocation-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品类型配置 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")
            })
    public LZResult<PaginationResult<ProductTypeAllocation>> list(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows){
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        LZResult<PaginationResult<ProductTypeAllocation>> result  = productTypeAllocationService.getAll(param,page,rows);
        return result;
    }

    /**
     * 产品类型配置 - 产品品类下拉框 数据
     * @param airportCode
     * @return
     */
    @GET
    @Path("product-category-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品类型配置 - 产品品类下拉框 数据 ", notes = "返回产品品类列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getProductCategoryDropdownList(@QueryParam(value = "airportCode") String airportCode
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        List<Dropdownlist> productCategoryList = productTypeAllocationService.getProductCategoryDropdownList(param);
        return new LZResult<List<Dropdownlist>>(productCategoryList);
    }

    /**
     * 产品类型配置 - 根据productCategory查询
     * @param airportCode
     * @param productCategory
     * @return
     */
    @GET
    @Path("service-type-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品类型配置 - 根据productCategory查询 ", notes = "返回服务类型列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "productCategory", value = "产品品类", dataType = "String", defaultValue = "VIP", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServiceTypeDropdownList(@QueryParam(value = "airportCode") String airportCode,
                               @QueryParam(value = "productCategory") String productCategory
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("productCategory",productCategory);
        List<Dropdownlist> serviceTypeList = productTypeAllocationService.getServiceTypeDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceTypeList);
    }

    /**
     * 产品类型配置 - 根据id查询
     * @param airportCode
     * @param id
     * @return
     */
    @GET
    @Path("service-name-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品类型配置 - 根据id查询 ", notes = "返回服务名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "产品类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getServiceNameDropdownList(@QueryParam(value = "airportCode") String airportCode,
                                                         @QueryParam(value = "id") Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        List<Dropdownlist> serviceNameList = productTypeAllocationService.getServiceNameDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceNameList);
    }
}
