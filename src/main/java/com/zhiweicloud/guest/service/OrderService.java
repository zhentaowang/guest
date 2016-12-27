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
import com.zhiweicloud.guest.mapper.OrderCarMapper;
import com.zhiweicloud.guest.mapper.GuestOrderMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.GuestOrder;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.model.OrderCar;
import com.zhiweicloud.guest.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author zhangpengfei
 * @since 2015-12-19 11:09
 */
@Service
public class OrderService {

    @Autowired
    private GuestOrderMapper guestOrderMapper;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private OrderCarMapper orderCarrMapper;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LZResult<PaginationResult<GuestOrder>> getAll(GuestOrder param, Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows, "id");
        }

        // 条件查询，自己拼条件
        Example example = new Example(Employee.class);
       /* if(param.getName() != null && !param.getName().equals("")){
            example.createCriteria()
                    .andCondition("name like '%" + param.getName() + "%'");
        }*/
        example.createCriteria()
                .andCondition("is_deleted = 0");
        List<GuestOrder> employeeList = guestOrderMapper.selectByExample(example);
        Integer count = guestOrderMapper.selectCountByExample(example);
        PaginationResult<GuestOrder> eqr = new PaginationResult<>(count, employeeList);
        LZResult<PaginationResult<GuestOrder>> result = new LZResult<>(eqr);
        return result;
    }

    public GuestOrder getById(Long id) {
        return guestOrderMapper.selectByPrimaryKey(id);
    }

    public void saveOrUpdate(GuestOrder guestOrder) {
        if (guestOrder.getId() != null) {
            guestOrderMapper.updateByPrimaryKey(guestOrder);
        } else {
           guestOrderMapper.insert(guestOrder);
           for(int i = 0 ; i < guestOrder.getPassengerList().size();i++){
               Passenger p = guestOrder.getPassengerList().get(i);
               p.setOrderId(guestOrder.getId());
               passengerMapper.insert(p);
           }
            for(int i = 0 ; i < guestOrder.getOrderCarList().size();i++){
                OrderCar p = guestOrder.getOrderCarList().get(i);
                p.setOrderId(guestOrder.getId());
                orderCarrMapper.insert(p);
            }
        }
    }

    public void deleteById(List<Long> ids) {
        for(int i = 0; i< ids.size();i++){
            GuestOrder temp = new GuestOrder();
            temp.setId(ids.get(i));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            guestOrderMapper.updateByPrimaryKeySelective(temp);
        }
    }

}
