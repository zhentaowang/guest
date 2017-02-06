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
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    public LZResult<PaginationResult<Employee>> getAll(Employee employeeParam, Integer page, Integer rows) {
        /*if (page != null && rows != null) {
            PageHelper.startPage(page, rows, "id");
        }

        // 条件查询，自己拼条件
        Example example = new Example(Employee.class);
        if(employeeParam.getName() != null && !employeeParam.getName().equals("")){
            example.createCriteria()
                    .andCondition("name like '%" + employeeParam.getName() + "%'");
        }
        example.createCriteria()
                .andCondition("is_deleted = 0");
        List<Employee> employeeList = employeeMapper.selectByExample(example);

        Integer count = employeeMapper.selectCountByExample(example);
        // 测试复杂sql，自定义方法
        Employee testComplexSql = employeeMapper.complexSqlQuery(12L);
        System.out.println(" 测试复杂sql，自定义方法 : " + testComplexSql.getName());
        PaginationResult<Employee> eqr = new PaginationResult<Employee>(count, employeeList);
        LZResult<PaginationResult<Employee>> result = new LZResult<PaginationResult<Employee>>(eqr);
        return result;*/

        BasePagination<Employee> queryCondition = new BasePagination<>(employeeParam, new PageModel(page, rows));

        int total = employeeMapper.selectEmployeeTotal(employeeParam);
        List<Employee> flightList = employeeMapper.selectEmployeeList(queryCondition);
        PaginationResult<Employee> eqr = new PaginationResult<>(total, flightList);
        LZResult<PaginationResult<Employee>> result = new LZResult<>(eqr);
        return result;
    }

    public Employee getById(Long id,String airportCode) {
        Map map = new HashMap();
        map.put("id",id);
        map.put("airportCode",airportCode);
        return employeeMapper.selectByIdAndAirportCode(map);
    }

    public void saveOrUpdate(Employee employee) {
        if (employee.getId() != null) {
            Example example = new Example(Employee.class);
            String sql = "id = " + employee.getId() + " and airport_code = '" + employee.getAirportCode() + "'";
            example.createCriteria().andCondition(sql);
            employeeMapper.updateByExampleSelective(employee, example);
        } else {
            employeeMapper.insert(employee);
        }
    }

    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            Employee temp = new Employee();
            temp.setId(ids.get(i));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            temp.setAirportCode(airportCode);
            employeeMapper.updateByPrimaryKeySelective(temp);
        }
    }


    public List<Dropdownlist> queryEmployeeDropdownList(String airportCode) {
        return employeeMapper.getEmployeeDropdownList(airportCode);
    }
}
