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

package com.airportcloud.productmanage.controller;

import com.airportcloud.productmanage.APIUtil.LZResult;
import com.airportcloud.productmanage.APIUtil.PaginationResult;
import com.airportcloud.productmanage.model.Dropdownlist;
import com.airportcloud.productmanage.model.ProductTypeAllocation;
import com.airportcloud.productmanage.service.ProductTypeAllocationService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@RestController
@Api(value="产品类型配置",description="产品类型配置desc ", tags={"productTypeAllocation"})
public class ProductTypeAllocationController {
    private static final Logger logger = LoggerFactory.getLogger(ProductTypeAllocationController.class);

    @Autowired
    private ProductTypeAllocationService productTypeAllocationService;

    @RequestMapping(value ="/product-type-allocation-list")
    @ApiOperation(value = "产品类型配置 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")
            })
    public LZResult<PaginationResult<ProductTypeAllocation>> list(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows){
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
    @RequestMapping(value = "/product-category-dropdown-list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "产品类型配置 - 产品品类下拉框 数据 ", notes = "返回产品品类列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query")
    })
    public LZResult<List<Dropdownlist>> getProductCategoryDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        List<Dropdownlist> serviceTypeList = productTypeAllocationService.getProductCategoryDropdownList(param);
        return new LZResult<List<Dropdownlist>>(serviceTypeList);
    }

    /**
     * 产品类型配置 - 根据productCategory查询
     * @param airportCode
     * @param productCategory
     * @return
     */
    @RequestMapping(value = "/service-type-dropdown-list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "产品类型配置 - 根据productCategory查询 ", notes = "返回服务类型列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "productCategory", value = "产品品类", dataType = "String", defaultValue = "VIP", required = true, paramType = "query")
    })
    public List<Dropdownlist> getServiceTypeDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                               @RequestParam(value = "productCategory", defaultValue = "VIP", required = true) String productCategory
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("productCategory",productCategory);
        List<Dropdownlist> serviceTypeList = productTypeAllocationService.getServiceTypeDropdownList(param);
        return serviceTypeList;
    }

    /**
     * 产品类型配置 - 根据id查询
     * @param airportCode
     * @param id
     * @return
     */
    @RequestMapping(value = "/service-name-dropdown-list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "产品类型配置 - 根据id查询 ", notes = "返回服务名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "产品类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public List<Dropdownlist> getServiceNameDropdownList(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                                                         @RequestParam(value = "id", defaultValue = "1", required = true) Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        List<Dropdownlist> serviceTypeList = productTypeAllocationService.getServiceNameDropdownList(param);
        return serviceTypeList;
    }
}
