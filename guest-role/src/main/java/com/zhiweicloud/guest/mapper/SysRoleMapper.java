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
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangpengfei
 * @since 2016-12-21 22:17
 */
public interface SysRoleMapper{
    List<Dropdownlist> getSysRoleDropdownList(String airportCode);

    int selectSysRoleTotal(SysRole sysRoleParam);

    List<SysRole> selectSysRoleList(BasePagination<SysRole> queryCondition);

    /**
     * 查询角色详情
     * @return
     */
    SysRole selectByIdAndAirportCode(@Param("roleId") Long roleId,@Param("airportCode") String airportCode);

    /**
     * 查询哪些角色不能删除
     * @return
     */
    int roleInUse(@Param("roleId") Long roleId,@Param("airportCode") String airportCode);

    /**
     * 查询角色名称
     * @return
     */
    String selectRoleNameByIdAndAirportCode(@Param("roleId") Long roleId,@Param("airportCode") String airportCode);

    void updateCustomColumn(SysRole sysRole);

    void insertSelective(SysRole sysRole);

    void updateByPrimaryKeySelective(SysRole temp);

    int judgeRepeat(SysRole sysRole);
}
