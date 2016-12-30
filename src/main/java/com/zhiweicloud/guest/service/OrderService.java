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
import com.zhiweicloud.guest.mapper.OrderCarMapper;
import com.zhiweicloud.guest.mapper.GuestOrderMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.GuestOrder;
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
    private OrderCarMapper orderCarMapper;


    @Autowired
    private JdbcTemplate jdbcTemplate;



    public LZResult<PaginationResult<GuestOrder>> getAll(GuestOrder param, Integer page, Integer rows) {
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows, "id");
        }

        // 条件查询，自己拼条件
        Example example = new Example(GuestOrder.class);
        //example.createCriteria().andCondition("order_type=",param.getOrderType());
       // example.createCriteria().andCondition("is_deleted=",0);
        //example.createCriteria().andCondition("airport_code=",param.getAirportCode());

        // example.createCriteria().andCondition("airport_id=",1);

       /* if(param.getName() != null && !param.getName().equals("")){
            example.createCriteria()
                    .andCondition("name like '%" + param.getName() + "%'");
        }*/
        List<GuestOrder> employeeList = guestOrderMapper.selectByExample(example);
        Integer count = guestOrderMapper.selectCountByExample(example);
        PaginationResult<GuestOrder> eqr = new PaginationResult<>(count, employeeList);
        LZResult<PaginationResult<GuestOrder>> result = new LZResult<>(eqr);
        return result;
    }

    public GuestOrder getById(Long id, String airportCode) {
        GuestOrder temp = new GuestOrder();
        temp.setId(id);
        temp.setAirportCode(airportCode);
        /**
         * 只能返回一条记录，如果有多条了就会报错，就跟selectByPrimaryKey一样的效果
         */
        return guestOrderMapper.selectOne(temp);
    }

    /**
     * 新增或者修改订单，包含旅客和车辆
     * @param guestOrder
     */
    public void saveOrUpdate(GuestOrder guestOrder) {
        if (guestOrder.getId() != null) {
            guestOrder.setServerPersonNum(guestOrder.getPassengerList().size());
            guestOrder.setCarNum(guestOrder.getOrderCarList().size());
            Example example = new Example(GuestOrder.class);
            example.createCriteria().andCondition("ariport_code=",guestOrder.getAirportCode());
            example.createCriteria().andCondition("id=",guestOrder.getId());

            guestOrderMapper.updateByExample(guestOrder,example);
            //还涉及到旅客和车辆的更新问题，解决办法：先把之前这个订单下的乘客和车辆都删除，然后重新插入
            this.deletePassengerAndOrderCarByOrderId(guestOrder.getId(),guestOrder.getAirportCode());
            this.addPassengerAndCar(guestOrder);
        } else {
           guestOrder.setServerPersonNum(guestOrder.getPassengerList().size());
           guestOrder.setCarNum(guestOrder.getOrderCarList().size());
           guestOrderMapper.insert(guestOrder);
           this.addPassengerAndCar(guestOrder);
        }
    }

    /**
     * 批量删除订单及其相关的旅客，车辆
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {
        String deleteGuestOrder = "update guest_order set is_deleted = 1 where id = ?　and ariport_id = ?";
        for(int i = 0; i< ids.size();i++){
            this.jdbcTemplate.update(deleteGuestOrder,new java.lang.Object[]{ids,airportCode});
            this.deletePassengerAndOrderCarByOrderId(ids.get(i),airportCode);
        }
    }

    /**
     * 添加旅客和车辆
     * @param guestOrder
     */
    private void addPassengerAndCar(GuestOrder guestOrder){
        for(int i = 0 ; i < guestOrder.getPassengerList().size();i++){
            Passenger p = guestOrder.getPassengerList().get(i);
            p.setOrderId(guestOrder.getId());
            p.setAirportCode(guestOrder.getAirportCode());
            passengerMapper.insert(p);
        }
        for(int i = 0 ; i < guestOrder.getOrderCarList().size();i++){
            OrderCar c = guestOrder.getOrderCarList().get(i);
            c.setOrderId(guestOrder.getId());
            c.setAirportCode(guestOrder.getAirportCode());
            orderCarMapper.insert(c);
        }
    }

    /**
     * 根据订单id逻辑删除旅客和车辆
     * @param orderId
     */
    private void deletePassengerAndOrderCarByOrderId(Long orderId,String airportCode){
        /**
         * 逻辑删除旅客
         */
        String deletePassenger = "update passenger set is_deleted = 1 where order_id = ? and airport_code = ?";
        this.jdbcTemplate.update(deletePassenger, orderId,airportCode) ;
        /**
         * 逻辑删除车辆
         */
        String deleteOrderCar = "update order_car set is_deleted = 1 where order_id = ?";
        this.jdbcTemplate.update(deleteOrderCar, orderId,airportCode) ;
    }





}
