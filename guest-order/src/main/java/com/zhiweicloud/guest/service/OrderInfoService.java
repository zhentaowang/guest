package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.OrderInfoMapper;
import com.zhiweicloud.guest.mapper.OrderServiceMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
        orderInfo.setAirportCode(airportCode);
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
                flight.setAirportCode(airportCode);

                if(flight.getFlightId() != null){
                    flight.setUpdateTime(new Date());
                    flight.setUpdateUser(userId);
                    flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
                }else{
                    Long flightId = flightMapper.isFlightExist(flight);
                    if(airportCode.equals(flight.getFlightDepcode())){//当前登录三字码 == 航班目的港口
                        flight.setIsInOrOut((short)0);//出港
                    }else if(airportCode.equals(flight.getFlightArrcode())){//当前登录三字码 == 航班出发港口
                        flight.setIsInOrOut((short)1);//进港
                    }
                    if (flightId != null && !flightId.equals("")){
                        flight.setFlightId(flightId);
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
            String detail = os.getServiceDetail();
            JSONObject jsonObject = JSON.parseObject(detail);
            os.setServiceDetail(jsonObject.toJSONString());
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
                    String customerInfo,String passengerId,String passengerName,String flightDate,String flightNo,Long userId,String airportCode) throws Exception{
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setQueryPassengerId(passengerId);
        orderInfoQuery.setQueryPassengerName(passengerName);
        orderInfoQuery.setQueryFlightNo(flightNo);
        orderInfoQuery.setQueryFlightDate(flightDate);
        orderInfoQuery.setAirportCode(airportCode);

        //获取客户id，institution_client 客户名称

        //获取预约人 authorizer 预约人姓名

        //获取协议id protocol 协议名称

        //JSONObject param = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList" + customerInfo,headerMap));
        JSONObject param = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?name=%E5%95%86&access_token=DZ75lFTo7qLmUkcM0zSBz6VA17Aw6cHtWCXb1DYT"));
        JSONArray protocolArray = param.getJSONArray("data");
        String protocolIds = "";
        for(int i = 0; i < protocolArray.size();i++){
            JSONObject jsonObject = JSON.parseObject(protocolArray.get(i).toString());
            protocolIds = protocolIds + jsonObject.get("id") + ",";
        }
        protocolIds = protocolIds.substring(0,protocolIds.length() - 1);

        BasePagination<OrderInfoQuery> queryCondition = new BasePagination<>(orderInfoQuery, new PageModel(page, rows));

        int total = orderInfoMapper.selectOrderInfoTotal(queryCondition);

        Map<String,Object> headerMap = new HashMap();
        headerMap.put("user-id",userId);
        headerMap.put("client-id",airportCode);


        //获取协议id，protocol 预约号

        //HttpClientUtil.httpGetRequest();
        List<Map> orderInfoList = orderInfoMapper.selectOrderInfoList(queryCondition);
        for(int i = 0; i < orderInfoList.size();i++){

        }

        PaginationResult<Map> eqr = new PaginationResult<>(total, orderInfoList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }

    public void deleteById(List<Long> ids, Long userId, String airportCode) throws  Exception{
        //删除订单  删除乘客，订单服务
        for (int i = 0; i < ids.size(); i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setIsDeleted(Constant.MARK_AS_DELETED);
            orderInfo.setOrderId(ids.get(i));
            orderInfo.setAirportCode(airportCode);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            this.deletePassengerAndOrderServiceDeatil(ids.get(i), airportCode);
        }
    }

    /**
     * 根据订单id逻辑删除旅客，详细服务
     *
     * @param orderId
     */
    private void deletePassengerAndOrderServiceDeatil(Long orderId, String airportCode) throws Exception{
        /**
         * 逻辑删除旅客
         */
        Passenger passenger = new Passenger();
        passenger.setAirportCode(airportCode);
        passenger.setOrderId(orderId);
        passengerMapper.markAsDeleted(passenger);

        /**
         * 逻辑删除旅客
         */
        OrderService orderService = new OrderService();
        orderService.setAirportCode(airportCode);
        orderService.setOrderId(orderId);
        orderServiceMapper.markAsDeleted(orderService);

    }

    public OrderInfo getById(Long orderId, String airportCode) throws Exception{
        OrderInfo map = orderInfoMapper.getDetailById(orderId, airportCode);
        return map;
    }
}
