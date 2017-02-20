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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    public Employee getById(Long id, String airportCode) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("airportCode", airportCode);
        return employeeMapper.selectByIdAndAirportCode(map);
    }

    public void saveOrUpdate(Employee employee) {
        try {
            if (employee.getId() != null) {
                Example example = new Example(Employee.class);
                String sql = "id = " + employee.getId() + " and airport_code = '" + employee.getAirportCode() + "'";
                example.createCriteria().andCondition(sql);
                employeeMapper.updateByExampleSelective(employee, example);
            } else {
                Map<String, Object> p = new HashMap<>();
                p.put("grant_type", "password");
                p.put("client_id", employee.getAirportCode());
                p.put("client_secret", employee.getPassword());
                p.put("username", employee.getName());
                p.put("password", employee.getPassword());
                p.put("password_confirmation", employee.getPassword());

                String result = HttpClientUtil.httpPostRequest("http://airport.zhiweicloud.com/oauth/auth/register", p);
                JSONObject oauth = JSON.parseObject(result);
                employee.setId(oauth.getLong("user_id"));
                employeeMapper.insertSelective(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(List<Long> ids, String airportCode) {
        for (int i = 0; i < ids.size(); i++) {
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
