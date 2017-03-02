package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.OrderInfoMapper;
import com.zhiweicloud.guest.mapper.OrderServiceMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.OrderInfo;
import com.zhiweicloud.guest.model.OrderService;
import com.zhiweicloud.guest.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangpengfei on 2017/2/28.
 */
@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private OrderServiceMapper orderServiceMapper;

    @Autowired
    private FlightMapper flightMapper;



    public void saveOrUpdate(OrderInfo orderInfo) throws Exception{
        if (orderInfo.getOrderId() != null) {
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            /*
            //涉及到修改状态的时候，怎么处理乘客和车辆的问题,修改状态的时候，是不修改乘客和车辆的相关信息的
            //changeOrderStatus 默认不传这个属性 是 0
            if (orderInfo.getChangeOrderStatus() == null || orderInfo.getChangeOrderStatus() == 0 ) {
                this.addPassengerAndServices(orderInfo);
            }*/
        } else {
            orderInfoMapper.insertSelective(orderInfo);
        }
        this.addPassengerAndServiceDetails(orderInfo);
    }

    /**
     * 添加旅客,服务详情
     *
     * @param orderInfo
     */
    private void addPassengerAndServiceDetails(OrderInfo orderInfo) {
        List passengerIds = new ArrayList<>();
        List serverDetailsList = new ArrayList();
        /**
         * 新增修改passenger
         */
        for (int i = 0; orderInfo.getPassengerList() != null && i < orderInfo.getPassengerList().size(); i++) {
            Passenger p = orderInfo.getPassengerList().get(i);
            p.setOrderId(orderInfo.getOrderId());
            p.setAirportCode(orderInfo.getAirportCode());
            p.setUpdateTime(new Date());
            p.setUpdateUser(orderInfo.getUpdateUser());

            if(p.getPassengerId() != null){
                passengerMapper.updateByPassengerIdAndAirportCodeKeySelective(p);
                passengerIds.add(p.getPassengerId());
            }else{
                passengerMapper.insertSelective(p);
                passengerIds.add(p.getPassengerId());
            }
        }

        /**
         * 新增修改服务信息
         */
        for (int i = 0; orderInfo.getServiceList() != null && i < orderInfo.getServiceList().size(); i++) {
            OrderService os = orderInfo.getServiceList().get(i);
            os.setOrderId(orderInfo.getOrderId());
            os.setAirportCode(orderInfo.getAirportCode());
            os.setUpdateTime(new Date());
            os.setUpdateUser(orderInfo.getUpdateUser());

            if(os.getOrderServiceId() != null){
                serverDetailsList.add(os.getOrderServiceId());
                orderServiceMapper.updateByOrderServiceIdAndAirportCodeKeySelective(os);
            }else{
                orderServiceMapper.insertSelective(os);
                serverDetailsList.add(os.getOrderServiceId());
            }
        }

        /**
         * 新增修改航班信息
         */
        if(orderInfo.getFlight() != null){
            Flight flight = orderInfo.getFlight();
            flight.setOrderId(orderInfo.getOrderId());
            if(flight.getFlightId() != null){
                flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
            }else{
                flightMapper.insertSelective(orderInfo.getFlight());
            }
        }

        if(passengerIds != null && passengerIds.size() > 0){
            this.deleteData(orderInfo.getOrderId(),ListUtil.List2String(passengerIds),orderInfo.getAirportCode(),"passenger_id","passenger");
        }

        if(serverDetailsList != null && serverDetailsList.size() > 0){
            this.deleteData(orderInfo.getOrderId(),ListUtil.List2String(serverDetailsList),orderInfo.getAirportCode(),"order_service_id","order_service");
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
    private void deleteData(Long orderId,String ids,String airportCode,String idColumn,String tableName){
        orderInfoMapper.markChildRowsAsDeleted(orderId,ids,airportCode,idColumn,tableName);
    }

}
