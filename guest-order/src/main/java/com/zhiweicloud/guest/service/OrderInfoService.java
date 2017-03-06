package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.OrderInfoMapper;
import com.zhiweicloud.guest.mapper.OrderServiceMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.OrderInfo;
import com.zhiweicloud.guest.model.OrderService;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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



    public void saveOrUpdate(OrderInfo orderInfo,List<Passenger> passengerList,List<OrderService> orderServiceList,Long userId,String airportCode) throws Exception{
        if (orderInfo.getOrderId() != null) {
            orderInfo.setUpdateTime(new Date());
            orderInfo.setUpdateUser(userId);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            /*
            //涉及到修改状态的时候，怎么处理乘客和车辆的问题,修改状态的时候，是不修改乘客和车辆的相关信息的
            //changeOrderStatus 默认不传这个属性 是 0
            if (orderInfo.getChangeOrderStatus() == null || orderInfo.getChangeOrderStatus() == 0 ) {
                this.addPassengerAndServices(orderInfo);
            }*/
        } else {
            /**
             * 新增修改航班信息
             */
            if(orderInfo.getFlight() != null){
                Flight flight = orderInfo.getFlight();
                if(flight.getFlightId() != null){
                    flight.setUpdateTime(new Date());
                    flight.setUpdateUser(userId);
                    flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
                }else{
                    int count = flightMapper.isFlightExist(flight);
                    if (count > 0){
                        flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
                    }else{
                        flight.setCreateTime(new Date());
                        flight.setCreateUser(userId);
                        flightMapper.insertSelective(flight);
                    }
                }
                orderInfo.setFlightId(flight.getFlightId());
            }
            orderInfo.setCreateTime(new Date());
            orderInfo.setCreateUser(userId);
            orderInfoMapper.insertSelective(orderInfo);
        }
        this.addPassengerAndServiceDetails(orderInfo,passengerList,orderServiceList);
    }

    /**
     * 添加旅客,服务详情
     * @param orderInfo
     * @param passengerList
     * @param orderServiceList
     */
    private void addPassengerAndServiceDetails(OrderInfo orderInfo,List<Passenger> passengerList,List<OrderService> orderServiceList) throws Exception{
        List passengerIds = new ArrayList<>();
        List serverDetailsList = new ArrayList();
        /**
         * 新增修改passenger
         */
        for (int i = 0; passengerList != null && i < passengerList.size(); i++) {
            Passenger p = passengerList.get(i);
            p.setOrderId(orderInfo.getOrderId());
            p.setAirportCode(orderInfo.getAirportCode());
            p.setUpdateTime(new Date());
            p.setUpdateUser(orderInfo.getUpdateUser());
            p.setFlightId(orderInfo.getFlightId());
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
        for (int i = 0; orderServiceList != null && i < orderServiceList.size(); i++) {
            OrderService os = orderServiceList.get(i);
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

    public LZResult<PaginationResult<Map>> getOrderInfoList(Integer page, Integer rows,
                    String customerInfo,String passengerId,String passengerName,String flightDate,String flightNo,String airportCode) throws Exception{
        BasePagination<OrderInfo> queryCondition = new BasePagination<>(new OrderInfo(), new PageModel(page, rows));

        int total = orderInfoMapper.selectOrderInfoTotal(new OrderInfo(),passengerId,passengerName,flightDate,flightNo,airportCode);


        //获取客户id，institution_client 客户名称

        //获取预约人 authorizer 预约人姓名

        //获取协议id protocol 协议名称

        //获取协议id，protocol 预约号

        //HttpClientUtil.httpGetRequest();
        List<Map> orderInfoList = orderInfoMapper.selectOrderInfoList(queryCondition);
        for(int i = 0; i < orderInfoList.size();i++){

        }

        PaginationResult<Map> eqr = new PaginationResult<>(total, orderInfoList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }
}
