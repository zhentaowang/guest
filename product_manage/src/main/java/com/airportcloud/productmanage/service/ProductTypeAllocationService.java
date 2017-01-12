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

package com.airportcloud.productmanage.service;

import com.airportcloud.productmanage.mapper.ServMapper;
import com.airportcloud.productmanage.pageUtil.BasePagination;
import com.airportcloud.productmanage.pageUtil.PageModel;
import com.airportcloud.productmanage.APIUtil.LZResult;
import com.airportcloud.productmanage.APIUtil.PaginationResult;
import com.airportcloud.productmanage.mapper.ProductTypeAllocationMapper;
import com.airportcloud.productmanage.model.Dropdownlist;
import com.airportcloud.productmanage.model.ProductTypeAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 * @since 2015-12-26
 */
@Service
public class ProductTypeAllocationService {

    @Autowired
    private ProductTypeAllocationMapper productTypeAllocationMapper;

    @Autowired
    private ServMapper servMapper;

    public LZResult<PaginationResult<ProductTypeAllocation>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = productTypeAllocationMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProductTypeAllocation> productTypeAllocationList = productTypeAllocationMapper.getListByConidition(queryCondition);
        PaginationResult<ProductTypeAllocation> eqr = new PaginationResult<>(count, productTypeAllocationList);
        LZResult<PaginationResult<ProductTypeAllocation>> result = new LZResult<>(eqr);
        return result;
    }

    public List<Dropdownlist> queryProductTypeAllocationDropdownList(){
       return productTypeAllocationMapper.getProductTypeDropdownList();
    }

    public List<Dropdownlist> getServiceNameDropdownList(Map<String,Object> param){
        return servMapper.getServiceNameDropdownList(param);
    }

    public List<Dropdownlist> getServiceTypeDropdownList(Map<String,Object> param){
        return productTypeAllocationMapper.getServiceTypeDropdownList(param);
    }

    public List<Dropdownlist> getProductCategoryDropdownList(Map<String,Object> param){
        return productTypeAllocationMapper.getProductCategoryDropdownList(param);
    }



}
