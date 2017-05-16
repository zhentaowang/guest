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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.EmployeeMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class EmployeeService implements IBusinessService{

    private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }

        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "view":
                success = view(request);
                break;
            case "getUserName":
                success = view(request);//跟上一个view 貌似可以合并
                break;
            case "saveOrUpdate":
                success = saveOrUpdate(request);
                break;
            case "delete":
                success = delete(request);
                break;
            case "queryEmployeeDropdownList":
                success = queryEmployeeDropdownList(request);
                break;
            case "getEmployeeDropdownListByRoleId":
                success = getEmployeeDropdownListByRoleId(request);//跟queryEmployeeDropdownList方法可以共用
                break;
            case "getRoleByUserId":
                success = getRoleByUserId(request);
                break;
            default:
                break;
        }

        return success;
    }

    /**
     * 员工列表 modified on 2017/5/8 by zhengyiyin
     * @param request
     * @return
     */
    public String list(JSONObject request) {
        Employee employeeParam = new Employee();
        employeeParam.setName(request.getString("name"));
        employeeParam.setAirportCode(request.getString("client_id"));

        //分页参数
        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }
        LZResult<Object> result = new LZResult<>();
        BasePagination<Employee> queryCondition = new BasePagination<>(employeeParam, new PageModel(page, rows));

        try {
            int total = employeeMapper.selectEmployeeTotal(employeeParam);
            List<Map> flightList = employeeMapper.selectEmployeeList(queryCondition);
            PaginationResult<Map> eqr = new PaginationResult<>(total, flightList);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result = new LZResult<>(eqr);

        } catch (Exception e) {
            logger.error("EmployeeService.list:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 员工管理 - 根据id查询员工 modified on 2017/5/8 by zhengyiyin
     *
     * @return
     */
    public String view(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        try {
            Map employee = employeeMapper.selectByIdAndAirportCode(request.getLong("employeeId"), request.getString("client_id"));
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(employee);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
    }

    /**
     * 员工管理 - 新增or更新
     * 需要判断name是否重复 modified on 2017/5/8 by zhengyiyin
     *
     * @return
     */
    public String saveOrUpdate(JSONObject request) {
        LZResult<String> result = new LZResult<>();
        Employee employee = null;
        try {
            JSONArray jsonArray = request.getJSONArray("data");
            if (!CollectionUtils.isEmpty(jsonArray)) {
                employee = jsonArray.toJavaObject(jsonArray.getJSONObject(0), Employee.class);
            }
            //保存员工信息
            if (employee == null) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
            } else {
                employee.setAirportCode(request.getString("client_id"));
                if (employee.getEmployeeId() != null) {
                    employee.setUpdateUser(request.getLong("user_id"));
                    employee.setUpdateTime(new Date());
                } else {
                    employee.setCreateUser(request.getLong("user_id"));
                    employee.setCreateTime(new Date());
                }

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
                    }
                    //删除
                    employeeMapper.deleteRoles(employee.getEmployeeId(),ListUtil.List2String(employee.getRoleIdList()),employee.getAirportCode());

                } else if(employee.getIsExist() != null && employee.getIsExist() == 0) { //isExist == 1 新增
                    employeeMapper.insertSelectiveCustom(employee);
                    //同时去做用户和role的关联关系 :即：去sys_user_role表中新增一条记录
                    if(employee.getRoleIdList() != null && employee.getRoleIdList().size() > 0){
                        for(int i = 0; i < employee.getRoleIdList().size();i++){
                            employeeMapper.addEmployeeAndRoleRelate(employee.getEmployeeId(),employee.getRoleIdList().get(i),employee.getAirportCode());
                        }
                    }
                }
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
            }

        } catch (Exception e) {
            logger.error("EmployeeService.saveOrUpdate:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
        }
        result.setData(null);
        return JSON.toJSONString(result);
    }

    /**
     * 员工管理 - 删除 modified on 2017/5/8 by zhengyiyin
     * {
     * "data": [
     * 6,7,8
     * ]
     * }
     *
     * @return
     */
    public String delete(JSONObject request) {
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");

        try {
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonArray.toJSONString(),Long.class);
            for (int i = 0; i < ids.size(); i++) {
                Employee temp = new Employee();
                temp.setEmployeeId(ids.get(i));
                temp.setIsDeleted(Constant.MARK_AS_DELETED);
                temp.setAirportCode(airportCode);
                temp.setUpdateUser(userId);
                temp.setUpdateTime(new Date());
                employeeMapper.updateByPrimaryKeySelective(temp);
                //删除
                employeeMapper.deleteRoles(ids.get(i),null,airportCode);
            }
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete employee by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 产品品类下拉框 数据 modified on 2017/5/8 by zhengyiyin
     *
     * @return
     */
    public String queryEmployeeDropdownList(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        try {
            List<Dropdownlist> list = employeeMapper.getEmployeeDropdownList(request.getString("client_id"),request.getString("name"));
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据角色查代办人 下拉框 modified on 2017/5/8 by zhengyiyin
     * （目前用全部员工，前端不传roleId）
     * @return
     */
    public String getEmployeeDropdownListByRoleId(JSONObject request){
        LZResult<Object> result = new LZResult<>();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("airportCode",request.getString("client_id"));
            map.put("roleId",request.getLong("roleId"));
            map.put("name",request.getString("name"));
            List<Dropdownlist> list = employeeMapper.getEmployeeDropdownListByRoleId(map);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);


    }

    /**
     * 员工管理 - 根据用户id获取角色列表 modified on 2017/5/8 by zhengyiyin
     *
     * @return
     */
    public String getRoleByUserId(JSONObject request) {
        LZResult<List<Map>> result = new LZResult<>();
        try {
            String airportCode = request.getString("client_id");
            List<Map> employee = employeeMapper.getRoleListByUserId(request.getLong("employeeId"),airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(employee);
        } catch (Exception e) {
            logger.error("EmployeeService.getRoleListByUserId:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }
}
