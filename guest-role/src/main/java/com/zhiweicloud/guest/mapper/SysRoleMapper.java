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
import com.zhiweicloud.guest.model.SysRole;
import com.zhiweicloud.guest.model.SysRoleParam;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2016-12-21 22:17
 */
public interface SysRoleMapper extends tk.mybatis.mapper.common.Mapper<SysRole>, tk.mybatis.mapper.common.MySqlMapper<SysRole> {
    SysRole complexSqlQuery(Long id);

    List<Dropdownlist> getSysRoleDropdownList(String airportCode);

    int selectSysRoleTotal(SysRole sysRoleParam);

    List<SysRole> selectSysRoleList(BasePagination<SysRole> queryCondition);

    /**
     * 查询角色详情
     * @return
     */
    SysRole selectByIdAndAirportCode(@Param("roleId") Long roleId,@Param("airportCode") String airportCode);

    List<Long> getMenuIdByUserId(Map map);

    /**
     * 查询哪些角色不能删除
     * @param map
     * @return
     */
    int roleInUse(Map<String, Object> map);

    /**
     * 查询角色名称
     * @param map
     * @return
     */
    String selectRoleNameByIdAndAirportCode(Map<String, Object> map);
}
