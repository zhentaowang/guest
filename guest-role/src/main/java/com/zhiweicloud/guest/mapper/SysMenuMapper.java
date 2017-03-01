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


import com.zhiweicloud.guest.model.SysMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2016-12-21 22:17
 */
public interface SysMenuMapper{
    List<SysMenu> getChildMenusByParentIdByUserId(@Param("parentId") Long parentId,@Param("airportCode") String airportCode,@Param("userId") Long userId);

    List<SysMenu> getChildNodesByParentId(@Param("parentId") Long parentId,@Param("airportCode") String airportCode);

    int menuInUse(@Param("menuId") Long menuId,@Param("airportCode") String airportCode);

    String selectMenuNameByIdAndAirportCode(@Param("menuId") Long menuId,@Param("airportCode") String airportCode);

    List<SysMenu> getChildMenusByParentIdByRole(@Param("parentId") Long parentId,@Param("airportCode") String airportCode,@Param("roleId") Long roleId);

    SysMenu selectMenuInstanceByIdAndAirportCode(@Param("menuId") Long menuId,@Param("airportCode") String airportCode);

    void insertSelective(SysMenu sysMenu);

    void updateByPrimaryKeySelective(SysMenu sysMenu);
}
