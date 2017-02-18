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
import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private OrderChargeMapper orderChargeMapper;


    @Autowired
    private FlightMapper flightMapper;

    public LZResult<PaginationResult<GuestOrder>> getAll(GuestOrderQueryParam param) throws  Exception{

        GuestOrder guestOrder = new GuestOrder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        guestOrder.setQueryBookingStartDate(param.getQueryBookingStartDate() != null ? sdf.parse(param.getQueryBookingStartDate()) : null);
        guestOrder.setQueryBookingEndDate(param.getQueryBookingEndDate() != null ? sdf.parse(param.getQueryBookingEndDate()) : null);
        guestOrder.setQueryFlightStartDate(param.getQueryFlightStartDate() != null ? sdf.parse(param.getQueryFlightStartDate()) : null);
        guestOrder.setQueryFlightEndDate(param.getQueryFlightEndDate() != null ? sdf.parse(param.getQueryFlightEndDate()) : null);
        guestOrder.setQueryFlightNo(param.getQueryFlightNo() != null ? param.getQueryFlightNo() : null);
        guestOrder.setQueryIsConsign(param.getQueryIsConsign() != null ? param.getQueryIsConsign() : null);
        guestOrder.setQueryIsInOrOut(param.getQueryIsInOrOut() != null ? param.getQueryIsInOrOut() : null);
        guestOrder.setQueryIsPrintBoardingCheck(param.getQueryIsPrintBoardingCheck() != null ? param.getQueryIsPrintBoardingCheck() : null);
        guestOrder.setQueryCustomerName(param.getQueryCustomerName() != null ? param.getQueryCustomerName() : null);
        guestOrder.setQueryOrderType(param.getQueryOrderType() != null ? param.getQueryOrderType() : null);
        guestOrder.setQueryPassengerName(param.getQueryPassengerName() != null ? param.getQueryPassengerName() : null);
        guestOrder.setQueryServerName(param.getQueryServerName() != null ? param.getQueryServerName() : null);
        guestOrder.setAirportCode(param.getAirportCode());

        BasePagination<GuestOrder> queryCondition = new BasePagination<>(guestOrder, new PageModel(param.getPage(), param.getRows()));

        List<GuestOrder> employeeList = guestOrderMapper.selectByComplexQuery(queryCondition);
        Integer count = guestOrderMapper.selectByComplexQueryCount(queryCondition);
        PaginationResult<GuestOrder> eqr = new PaginationResult<>(count, employeeList);
        LZResult<PaginationResult<GuestOrder>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 根据id和机场编码获取单条记录详情
     *
     * @param id
     * @param airportCode
     * @return
     */
    public GuestOrder getById(Long id, String airportCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("airportCode", airportCode);
        /**
         * 只能返回一条记录，如果有多条了就会报错，就跟selectByPrimaryKey一样的效果
         */
        return guestOrderMapper.selectByIdAndAirCode(map);
    }

    /**
     * 新增或者修改订单，包含旅客和车辆
     *
     * @param guestOrder
     */
    public void saveOrUpdate(GuestOrder guestOrder) throws Exception{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(guestOrder.getFlight() != null){
                if(guestOrder.getFlight().getStrFlightDate() != null && !guestOrder.getFlight().getStrFlightDate().equals("string")){
                    guestOrder.getFlight().setFlightDate(sdf.parse(guestOrder.getFlight().getStrFlightDate()));
                }
                if(guestOrder.getFlight().getStrPlanLandingTime() != null && !guestOrder.getFlight().getStrPlanLandingTime().equals("string")){
                    guestOrder.getFlight().setPlanLandingTime(sdf.parse(guestOrder.getFlight().getStrPlanLandingTime()));
                }
                if(guestOrder.getFlight().getStrPlanTakeOffTime() != null && !guestOrder.getFlight().getStrPlanTakeOffTime().equals("string")){
                    guestOrder.getFlight().setPlanTakeOffTime(sdf.parse(guestOrder.getFlight().getStrPlanTakeOffTime()));
                }
                if(guestOrder.getStrServerTime() != null && !guestOrder.getStrServerTime().equals("string")){
                    guestOrder.setServerTime(sdf.parse(guestOrder.getStrServerTime()));
                }
            }

            if (guestOrder.getId() != null) {
                //车辆和乘客个数由前端传递过来
                /*if (guestOrder.getPassengerList() != null) {
                    guestOrder.setServerPersonNum(guestOrder.getPassengerList().size());
                }

                if (guestOrder.getOrderCarList() != null) {
                    guestOrder.setCarNum(guestOrder.getOrderCarList().size());
                }*/

                Example example = new Example(GuestOrder.class);
                String sql = "id = " + guestOrder.getId() + " and airport_code = '" + guestOrder.getAirportCode() + "'";
                example.createCriteria().andCondition(sql);

                guestOrderMapper.updateByExampleSelective(guestOrder, example);

                //涉及到修改状态的时候，怎么处理乘客和车辆的问题,修改状态的时候，是不修改乘客和车辆的相关信息的
                //changeOrderStatus 默认不传这个属性 是 0
                if (guestOrder.getChangeOrderStatus() == null || guestOrder.getChangeOrderStatus() == 0 ) {
                    this.addPassengerAndCarAndOrderCharge(guestOrder);
                }
            } else {
                if (guestOrder.getPassengerList() != null) {
                    guestOrder.setServerPersonNum(guestOrder.getPassengerList().size());
                }

                if (guestOrder.getOrderCarList() != null) {
                    guestOrder.setCarNum(guestOrder.getOrderCarList().size());
                }
                guestOrderMapper.insertSelective(guestOrder);
                this.addPassengerAndCarAndOrderCharge(guestOrder);
            }

    }

    /**
     * 批量删除订单及其相关的旅客，车辆
     *
     * @param ids
     */
    public void deleteById(List<Long> ids, String airportCode) {
        for (int i = 0; i < ids.size(); i++) {
            String sql = "id = " + ids.get(i) + " and airport_code = '" + airportCode + "'";
            Example examplePassenger = new Example(GuestOrder.class);
            examplePassenger.createCriteria().andCondition(sql);

            GuestOrder guestOrder = new GuestOrder();
            guestOrder.setIsDeleted(Constant.MARK_AS_DELETED);
            guestOrderMapper.updateByExampleSelective(guestOrder, examplePassenger);
            this.deletePassengerAndOrderCarByOrderId(ids.get(i), airportCode);
        }
    }

    /**
     * 添加旅客,车辆和收费服务
     *
     * @param guestOrder
     */
    private void addPassengerAndCarAndOrderCharge(GuestOrder guestOrder) {
        List passengerIds = new ArrayList<>();
        List orderChargeIds = new ArrayList();
        for (int i = 0; guestOrder.getPassengerList() != null && i < guestOrder.getPassengerList().size(); i++) {
            Passenger p = guestOrder.getPassengerList().get(i);
            p.setOrderId(guestOrder.getId());
            p.setAirportCode(guestOrder.getAirportCode());
            p.setUpdateTime(new Date());
            if(p.getId() != null){
                passengerIds.add(p.getId());
                this.updateByExample(p.getId(),guestOrder.getAirportCode(),passengerMapper,p);
            }else{
                passengerMapper.insertSelective(p);
                passengerIds.add(p.getId());
            }
        }

        if(guestOrder.getCarNo() != null && !guestOrder.getCarNo().equals("") && !guestOrder.getCarNo().equals("string")){
            List list = new ArrayList();
            //不包含&，说明 只有一个车牌
            if(!guestOrder.getCarNo().contains("&")){
                String carNo = guestOrder.getCarNo();
                Map<String,Object> map = new HashMap();
                map.put("orderId",guestOrder.getId());
                map.put("carNo",carNo);
                map.put("airportCode",guestOrder.getAirportCode());
                orderCarMapper.insertByExists(map);

                Map<String,Object> deleteCarMap = new HashMap();
                deleteCarMap.put("airportCode",guestOrder.getAirportCode());
                deleteCarMap.put("carNoStr",carNo);
                deleteCarMap.put("orderId",guestOrder.getId());
                orderCarMapper.deleteCarNo(deleteCarMap);
            }else{
                String carNos[] = guestOrder.getCarNo().split("&");
                for(int i = 0; i < carNos.length;i++){
                    list.add(carNos[i]);
                    Map<String,Object> map = new HashMap();
                    map.put("orderId",guestOrder.getId());
                    map.put("carNo",carNos[i]);
                    map.put("airportCode",guestOrder.getAirportCode());
                    orderCarMapper.insertByExists(map);
                }

                Map<String,Object> deleteCarMap = new HashMap();
                deleteCarMap.put("airportCode",guestOrder.getAirportCode());
                deleteCarMap.put("carNoStr",ListUtil.List2String(list));
                deleteCarMap.put("orderId",guestOrder.getId());
                orderCarMapper.deleteCarNo(deleteCarMap);
            }
        }

        for (int i = 0; guestOrder.getOrderChargeList() != null && i < guestOrder.getOrderChargeList().size(); i++) {
            OrderCharge oc = guestOrder.getOrderChargeList().get(i);
            oc.setOrderId(guestOrder.getId());
            oc.setAirportCode(guestOrder.getAirportCode());
            oc.setUpdateTime(new Date());
            if(oc.getId() != null){
                orderChargeIds.add(oc.getId());
                this.updateByExample(oc.getId(),guestOrder.getAirportCode(),orderChargeMapper,oc);
            }else{
                orderChargeMapper.insertSelective(oc);
                orderChargeIds.add(oc.getId());
            }
        }

        if(guestOrder.getFlight() != null){
            Flight flight = guestOrder.getFlight();
            flight.setOrderId(guestOrder.getId());
            if(flight.getId() != null){
                this.updateByExample(guestOrder.getFlight().getId(),guestOrder.getAirportCode(),flightMapper,guestOrder.getFlight());
            }else{
                flightMapper.insertSelective(guestOrder.getFlight());
            }
        }

        if(passengerIds.size() > 0){
            this.deleteData(guestOrder.getId(),ListUtil.List2String(passengerIds),guestOrder.getAirportCode(),"passenger");
        }

        if(orderChargeIds.size() > 0){
            this.deleteData(guestOrder.getId(),ListUtil.List2String(orderChargeIds),guestOrder.getAirportCode(),"order_charge");
        }
    }

    /**
     * 删除车辆，乘客，收费服务
     * 数据库有1,2,3,4,5 这5条数据
     * 现在前台只传递 1,2,3 三条数据，那么我需要把4,5 删除
     * update ${tableName} set is_deleted = 1 where id not in ( #{ids} )  and airport_code = #{airportCode} and order_id = #{orderId}
     * @param ids
     * @param airportCode
     * @param tableName
     */
    private void deleteData(Long orderId,String ids,String airportCode,String tableName){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("ids",ids);
        map.put("airportCode",airportCode);
        map.put("tableName",tableName);

        guestOrderMapper.markChildRowsAsDeleted(map);
    }


    /**
     * 根据主键id，机场code，mapper，对象 来选择性的更新
     * @param id
     * @param airportCode
     * @param myMapper
     * @param o
     */
    private void updateByExample(Long id,String airportCode,MyMapper myMapper,Object o){
        Example example = new Example(o.getClass());
        String sql = "id = " + id + " and airport_code = '" + airportCode + "'";
        example.createCriteria().andCondition(sql);
        myMapper.updateByExampleSelective(o, example);
    }

    /**
     * 根据订单id逻辑删除旅客，车辆和收费服务
     *
     * @param orderId
     */
    private void deletePassengerAndOrderCarByOrderId(Long orderId, String airportCode) {
        /**
         * 逻辑删除旅客
         */
        Passenger passenger = new Passenger();
        passenger.setIsDeleted(Constant.MARK_AS_DELETED);
        Example examplePassenger = new Example(Passenger.class);
        String sql = "order_id = " + orderId + " and airport_code = '" + airportCode + "'";
        examplePassenger.createCriteria().andCondition(sql);
        passengerMapper.updateByExampleSelective(passenger, examplePassenger);
        /**
         * 逻辑删除车辆
         */
        OrderCar orderCar = new OrderCar();
        orderCar.setIsDeleted(Constant.MARK_AS_DELETED);
        Example exampleOrderCar = new Example(Passenger.class);
        String sqlOrderCar = "order_id = " + orderId + " and airport_code = '" + airportCode + "'";
        exampleOrderCar.createCriteria().andCondition(sqlOrderCar);
        orderCarMapper.updateByExampleSelective(orderCar, exampleOrderCar);
        /**
         * 逻辑删除收费服务
         */
        OrderCharge orderCharge = new OrderCharge();
        orderCharge.setIsDeleted(Constant.MARK_AS_DELETED);
        Example exampleOrderCharge = new Example(OrderCharge.class);
        String sqlOrderCharge = "order_id = " + orderId + " and airport_code = '" + airportCode + "'";
        exampleOrderCar.createCriteria().andCondition(sqlOrderCharge);
        orderChargeMapper.updateByExampleSelective(orderCharge, exampleOrderCharge);

        /**
         * 逻辑删除航班信息
         */
        Flight flight = new Flight();
        flight.setIsDeleted(Constant.MARK_AS_DELETED);
        Example exampleFlight = new Example(Flight.class);
        String sqlFlight = "order_id = " + orderId + " and airport_code = '" + airportCode + "'";
        exampleFlight.createCriteria().andCondition(sqlFlight);
        flightMapper.updateByExampleSelective(flight, exampleFlight);
    }


    /**
     * 根据协议id或者协议编号  获取协议客户名称，协议备注，预约人
     *
     * @param protocolInfoParam
     * @param protocolInfoParam
     * @return
     */
    public ProtocolInfo getProtocolInfoBy(ProtocolInfo protocolInfoParam) {
        //获取协议名称
        ProtocolInfo protocolInfo = guestOrderMapper.getProtocolInfo(protocolInfoParam);
        if(protocolInfo != null){
            //协议预约人
            List<Dropdownlist> personList = guestOrderMapper.getProtocolPersonInfo(protocolInfoParam);
            if(personList != null && personList.size() > 0){
                protocolInfo.setPersonDropDownList(personList);
            }
        }
        return protocolInfo;
    }


    public List<Dropdownlist> getServerList(String productCategory, String serviceType, String protocolId,String airportCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productCategory", productCategory);
        map.put("serviceType", serviceType);
        map.put("protocolId", protocolId);
        map.put("airportCode",airportCode);
        List<Dropdownlist> serverList = guestOrderMapper.getServerListInfo(map);
        return serverList;
    }

    /**
     * 查询航班列表
     * @param flight
     * @param page
     * @param rows
     * @return
     */
    public LZResult<PaginationResult<Flight>> getFlightList(Flight flight, Integer page, Integer rows) {
        BasePagination<Flight> queryCondition = new BasePagination<>(flight, new PageModel(page, rows));

        int total = flightMapper.selectFlightTotal(flight);
        List<Flight> flightList = flightMapper.selectFlightList(queryCondition);
        PaginationResult<Flight> eqr = new PaginationResult<>(total, flightList);
        LZResult<PaginationResult<Flight>> result = new LZResult<>(eqr);
        return result;
    }
}
