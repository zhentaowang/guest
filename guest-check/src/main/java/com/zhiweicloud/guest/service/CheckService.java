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

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.EmployeeMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public LZResult<PaginationResult<Map>> getAll(Employee employeeParam, Integer page, Integer rows) {
        BasePagination<Employee> queryCondition = new BasePagination<>(employeeParam, new PageModel(page, rows));

        int total = employeeMapper.selectEmployeeTotal(employeeParam);
        List<Map> flightList = employeeMapper.selectEmployeeList(queryCondition);
        PaginationResult<Map> eqr = new PaginationResult<>(total, flightList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }

    public List<Map> getById(Long employeeId, String airportCode) {
        return employeeMapper.selectByIdAndAirportCode(employeeId,airportCode);
    }

    public void saveOrUpdate(Employee employee) {
        try {
            if (employee.getIsExist() != null && employee.getIsExist() == 1) { //isExist == 1 修改
                employeeMapper.updateByPrimaryKeySelective(employee);

                /**
                 * 更新角色和菜单的关联关系：
                 * 1：insertByExists。有就更新，没有就插入
                 * 2：删除，用not in 来删除
                 *   update ${tableName} set is_deleted = 1 where id not in (${ids})  and airport_code = #{airportCode} and order_id = #{orderId}
                 */
                if(employee.getRoleIdList() != null && employee.getRoleIdList().size() > 0){
                    for(int i = 0; i < employee.getRoleIdList().size();i++){
                        employeeMapper.insertEmployeeRoleByExists(employee.getEmployeeId(),employee.getRoleIdList().get(i),employee.getAirportCode());
                    }
                    //删除
                    employeeMapper.deleteRoles(employee.getEmployeeId(),ListUtil.List2String(employee.getRoleIdList()),employee.getAirportCode());
                }

            } else if(employee.getIsExist() != null && employee.getIsExist() == 0) { //isExist == 1 新增
                employeeMapper.insertSelectiveCustom(employee);
                //同时去做用户和role的关联关系 :即：去sys_user_role表中新增一条记录
                if(employee.getRoleIdList() != null && employee.getRoleIdList().size() > 0){
                    for(int i = 0; i < employee.getRoleIdList().size();i++){
                        employeeMapper.addEmployeeAndRoleRelate(employee.getEmployeeId(),employee.getRoleIdList().get(i),employee.getAirportCode());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(List<Long> ids,Long deleteUser, String airportCode) {
        for (int i = 0; i < ids.size(); i++) {
            Employee temp = new Employee();
            temp.setEmployeeId(ids.get(i));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            temp.setAirportCode(airportCode);
            temp.setUpdateUser(deleteUser);
            temp.setUpdateTime(new Date());
            employeeMapper.updateByPrimaryKeySelective(temp);
        }
    }


    public List<Dropdownlist> queryEmployeeDropdownList(String airportCode) {
        return employeeMapper.getEmployeeDropdownList(airportCode);
    }

    public List<Dropdownlist> getEmployeeDropdownListByRoleId(String airportCode, Long roleId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("airportCode",airportCode);
        map.put("roleId",roleId);
        return employeeMapper.getEmployeeDropdownListByRoleId(map);
    }

    public List<Map> getRoleListByUserId(Long employeeId, String airportCode) {
        return employeeMapper.getRoleListByUserId(employeeId,airportCode);
    }
}
