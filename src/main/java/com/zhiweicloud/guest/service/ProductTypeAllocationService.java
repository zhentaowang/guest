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

package com.zhiweicloud.guest.service;

import com.github.pagehelper.PageHelper;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.EmployeeMapper;
import com.zhiweicloud.guest.mapper.ProductTypeAllocationMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.model.ProductTypeAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liuzh
 * @since 2015-12-26
 */
@Service
public class ProductTypeAllocationService {

    @Autowired
    private ProductTypeAllocationMapper productTypeAllocationMapper;

    public LZResult<PaginationResult<ProductTypeAllocation>> getAll(Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows, "id");
        }

        // 条件查询，自己拼条件
        Example example = new Example(Employee.class);
        /*if(productTypeAllocationParam.getName() != null && !productTypeAllocationParam.getName().equals("")){
            example.createCriteria()
                    .andCondition("name like '%" + productTypeAllocationParam.getName() + "%'");
        }
        example.createCriteria()
                .andCondition("is_deleted = 0");*/
        List<ProductTypeAllocation> employeeList = productTypeAllocationMapper.selectByExample(example);

        Integer count = productTypeAllocationMapper.selectCountByExample(example);
        PaginationResult<ProductTypeAllocation> eqr = new PaginationResult<ProductTypeAllocation>(count, employeeList);
        LZResult<PaginationResult<ProductTypeAllocation>> result = new LZResult<PaginationResult<ProductTypeAllocation>>(eqr);
        return result;
    }

    public List<Dropdownlist> queryProductTypeAllocationDropdownList(){
       return productTypeAllocationMapper.getProductTypeDropdownList();
    }



}
