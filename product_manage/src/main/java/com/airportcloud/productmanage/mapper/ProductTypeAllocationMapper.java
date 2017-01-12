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

package com.airportcloud.productmanage.mapper;


import com.airportcloud.productmanage.common.MyMapper;
import com.airportcloud.productmanage.model.Dropdownlist;
import com.airportcloud.productmanage.model.ProductTypeAllocation;
import com.airportcloud.productmanage.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2016-12-26 22:17
 */
public interface ProductTypeAllocationMapper extends MyMapper<ProductTypeAllocation> {

    public List<Dropdownlist> getProductTypeDropdownList();
    Long getCategoryId(Map<String,Object> params);
    public List<Dropdownlist> getServiceTypeDropdownList(Map<String,Object> param);
    public List<Dropdownlist> getProductCategoryDropdownList(Map<String,Object> param);
    List<ProductTypeAllocation> getListByConidition(BasePagination<Map<String,Object>> queryCondition);
    int getListCount(Map<String,Object> map);

}
