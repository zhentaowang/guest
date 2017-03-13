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


import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * InstitutionClientMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
 */
public interface InstitutionClientMapper{

    List<InstitutionClient> getListByConidition(BasePagination<InstitutionClient> queryCondition);

    List<Dropdownlist> getInstitutionClientDropdownList(Map<String,Object> map);

    InstitutionClient viewByIdAndAirCode(@Param("institutionClientId") Long institutionClientId,@Param("airportCode") String airportCode);

    Integer getListByConiditionCount(InstitutionClient queryCondition);

    void markAsDeleted(@Param("institutionClientId")Long institutionClientId,@Param("deleteUser")Long deleteUser, @Param("airportCode")String airportCode);

    void updateByPrimaryKeySelective(InstitutionClient institutionClient);

    void insertSelective(InstitutionClient institutionClient);

    // 测试批量删除
    void deleteBatchByIdsAndUserId(Map params);

}
