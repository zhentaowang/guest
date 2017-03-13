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
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2016-12-21 22:17
 */
public interface EmployeeMapper{
    List<Dropdownlist> getEmployeeDropdownList(String airportCode);

    /**
     * 跟上一个方法有重叠，
     * getEmployeeDropdownList 不知道别的项目有没有用到，，日后可以改用当前接口
     * @param map
     * @return
     */
    List<Dropdownlist> getEmployeeDropdownListByRoleId(Map<String, Object> map);

    int selectEmployeeTotal(Employee employeeParam);

    List<Map> selectEmployeeList(BasePagination<Employee> queryCondition);

    List<Map> selectByIdAndAirportCode(@Param("employeeId") Long employeeId, @Param("airportCode") String airportCode );

    void addEmployeeAndRoleRelate(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId,@Param("airportCode") String airportCode );

    void insertEmployeeRoleByExists(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId,@Param("airportCode") String airportCode );

    void deleteRoles(@Param("employeeId") Long employeeId, @Param("roleIds") String roleIds,@Param("airportCode") String airportCode);

    List<Map> getRoleListByUserId(@Param("employeeId") Long employeeId, @Param("airportCode") String airportCode);

    void insertSelectiveCustom(Employee employee);

    void updateByPrimaryKeySelective(Employee employee);
}
