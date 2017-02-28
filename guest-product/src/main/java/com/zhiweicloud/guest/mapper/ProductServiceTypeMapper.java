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

package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.ProductServiceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by zhengyiyin on 2017/2/22.
 */
public interface ProductServiceTypeMapper extends MyMapper<ProductServiceType> {

    /**
     * 新增服务
     * @param productParam
     * @return
     */
    int insertProductServiceType(ProductServiceType productParam);

    /**
     * 删除没有选中的服务，非id
     * @param productId
     * @param airportCode
     * @param updateUser
     * @param serviceTypeIds
     * @return
     */
    int deleteProductServiceType(@Param("productId") Long productId, @Param("airportCode") String airportCode,
                                 @Param("updateUser") Long updateUser, @Param("serviceTypeIds") String serviceTypeIds);

    /**
     * 根据产品id&机场编号获取 服务id列表
     * @param productId
     * @param airportCode
     * @return
     */
    List<Long> queryProductServiceTypes(@Param("productId") Long productId, @Param("airportCode") String airportCode);

}
