package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.CustomException;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.OrderInfoMapper;
import com.zhiweicloud.guest.mapper.OrderServiceMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.apache.ibatis.annotations.Param;
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



    public void saveOrUpdate(OrderInfo orderInfo,List<Passenger> passengerList,List<OrderService> orderServiceList,Long userId,String airportCode) throws Exception {
        orderInfo.setAirportCode(airportCode);
        if (orderInfo.getOrderId() != null) {
            orderInfo.setUpdateTime(new Date());
            orderInfo.setUpdateUser(userId);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

            //保存订单日志
            orderInfoMapper.insertIntoOrderStatusRecord(orderInfo);


        } else {
            /**
             * 新增修改航班信息
             */
            if(orderInfo.getFlight() != null){
                Flight flight = orderInfo.getFlight();
                flight.setAirportCode(airportCode);
                if(flight.getFlightArrcode() == null || flight.getFlightDepcode() == null){
                    throw new CustomException("出发地三字码或者目的地三字码为空");
                }
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

    public LZResult<PaginationResult<OrderInfo>> getOrderInfoList(Integer page, Integer rows,
                                                            OrderInfoQuery orderInfoQuery,Long userId) throws Exception{
        Map<String,Object> headerMap = new HashMap();
        headerMap.put("user-id",userId);
        headerMap.put("client-id",orderInfoQuery.getAirportCode());

        //获取预约人 authorizer 预约人姓名

        //获取协议id protocol 协议名称


        /**
         * 协议名称模糊匹配，获取协议ids
         */
        List protocolIdList = new ArrayList();
        JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/getProtocolNameDropdownList?protocolName=" + orderInfoQuery.getQueryCustomerInfo(),headerMap));
        //JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?protocolName=%E5%95%86&access_token=DZ75lFTo7qLmUkcM0zSBz6VA17Aw6cHtWCXb1DYT"));
        if(protocolParam != null){
            JSONArray protocolArray = protocolParam.getJSONArray("data");

            for(int i = 0; i < protocolArray.size();i++){
                JSONObject jsonObject = JSON.parseObject(protocolArray.get(i).toString());
                protocolIdList.add(jsonObject.get("id"));
            }
        }

        /**
         * 预约号，拿到协议id，所以可以用 protocolIds
         */
        JSONObject reservationNumObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/getProtocolNameDropdownList?reservationNum=" + orderInfoQuery.getQueryCustomerInfo(),headerMap));
        //JSONObject reservationNumObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?reservationNum=%E5%95%86&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
        if(reservationNumObject != null){
            JSONArray reservationNumArray = reservationNumObject.getJSONArray("data");
            for(int i = 0; i < reservationNumArray.size();i++){
                JSONObject jsonObject = JSON.parseObject(reservationNumArray.get(i).toString());
                protocolIdList.add(jsonObject.get("id"));
            }
        }


        /**
         * 预约人
         */
        List authorizerNameIdList = new ArrayList();
        JSONObject authorizerNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/getProtocolNameDropdownList?authorizerName=" + orderInfoQuery.getQueryCustomerInfo(),headerMap));
        //JSONObject authorizerNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?authorizerName=%E5%95%86&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
        if(authorizerNameObject != null){
            JSONArray authorizerNameArray = authorizerNameObject.getJSONArray("data");

            for(int i = 0; i < authorizerNameArray.size();i++){
                JSONObject jsonObject = JSON.parseObject(authorizerNameArray.get(i).toString());
                authorizerNameIdList.add(jsonObject.get("id"));
            }
        }
        if(protocolIdList.size() > 0){
            orderInfoQuery.setQueryProtocolIds(ListUtil.List2String(protocolIdList));//协议id
        }

        if(authorizerNameIdList.size() > 0){
            orderInfoQuery.setQueryBookingIds(ListUtil.List2String(authorizerNameIdList));//预约人
        }

        BasePagination<OrderInfoQuery> queryCondition = new BasePagination<>(orderInfoQuery, new PageModel(page, rows));

        int total = orderInfoMapper.selectOrderInfoTotal(queryCondition);



        List<OrderInfo> orderInfoList = orderInfoMapper.selectOrderInfoList(queryCondition);
       for(int i = 0; i < orderInfoList.size();i++){
            OrderInfo temp = orderInfoList.get(i);
            JSONObject protocolJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/view?protocolId=" + temp.getProtocolId(),headerMap));
            //JSONObject protocolJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/view?protocolId="+ temp.getProtocolId() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));

            if(temp.getProtocolId() != null){

                if(protocolJSONObject != null){
                    JSONObject protocolObject = JSON.parseObject(protocolJSONObject.get("data").toString());
                    String protocolName = protocolObject.get("name").toString();//协议名称
                    orderInfoList.get(i).setProtocolName(protocolName);
                }
            }
            if(temp.getProductId() != null) {
                JSONObject productJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/protocol-product-view?protocolProductId=" + temp.getProductId(),headerMap));
                //JSONObject productJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/protocol-product-view?protocolProductId="+ temp.getProductId() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
                if(productJSONObject != null){
                    JSONObject productObject = JSON.parseObject(productJSONObject.get("data").toString());
                    String productName = productObject.get("productName").toString();//产品名称
                    orderInfoList.get(i).setProductName(productName);
                }
            }

            if(temp.getAgentPerson() != null){
                JSONObject agentPersonNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/view?employeeId="+ temp.getAgentPerson() ,headerMap));
                //JSONObject agentPersonNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-employee/view?employeeId="+ temp.getAgentPerson() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
                if(agentPersonNameObject != null){
                    JSONArray jsonArray = agentPersonNameObject.getJSONArray("data");
                    String agentPersonName = jsonArray.getJSONObject(0).get("name").toString();
                    orderInfoList.get(i).setAgentPersonName(agentPersonName);
                }
            }
        }

        PaginationResult<OrderInfo> eqr = new PaginationResult<>(total, orderInfoList);
        LZResult<PaginationResult<OrderInfo>> result = new LZResult<>(eqr);
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
            //保存订单日志
            orderInfo.setOrderStatus("删除订单");
            orderInfoMapper.insertIntoOrderStatusRecord(orderInfo);
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

    public OrderInfo getById(Long orderId, Long userId,String airportCode) throws Exception{
        OrderInfo orderInfo = orderInfoMapper.getDetailById(orderId, airportCode);
        Map<String,Object> headerMap = new HashMap();
        headerMap.put("user-id",userId);
        headerMap.put("client-id",airportCode);
        if(orderInfo.getProtocolId() != null){
            JSONObject protocolJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/view?protocolId="+ orderInfo.getProtocolId() ,headerMap));
            //JSONObject protocolJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/view?protocolId="+ orderInfo.getProtocolId() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
            if(protocolJSONObject != null){
                JSONObject protocolObject = JSON.parseObject(protocolJSONObject.get("data").toString());
                String protocolName = protocolObject.get("name").toString();//协议名称
                orderInfo.setProtocolName(protocolName);
            }
        }

        if(orderInfo.getProductId() != null){
            JSONObject productJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/protocol-product-view?protocolProductId="+ orderInfo.getProductId() ,headerMap));
            //JSONObject productJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/protocol-product-view?protocolProductId="+ orderInfo.getProductId() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
            if(productJSONObject != null){
                JSONObject productObject = JSON.parseObject(productJSONObject.get("data").toString());
                String productName = productObject.get("productName").toString();//产品名称
                orderInfo.setProductName(productName);
            }
        }

        if(orderInfo.getBookingPerson() != null){
            JSONObject bookingPersonJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/getAuthorizerDropdownList?authorizerId="+ orderInfo.getBookingPerson() ,headerMap));
            //JSONObject bookingPersonJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getAuthorizerDropdownList?authorizerId="+ orderInfo.getBookingPerson() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
            if(bookingPersonJSONObject != null){
                JSONArray jsonArray = bookingPersonJSONObject.getJSONArray("data");
                String bookingPersonName = jsonArray.getJSONObject(0).get("value").toString();
                orderInfo.setBookingPersonName(bookingPersonName);
            }
        }

        if(orderInfo.getCreateUser() != null){
            JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/view?employeeId="+ orderInfo.getCreateUser() ,headerMap));
            //JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-employee/view?employeeId="+ orderInfo.getCreateUser() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
            if(createUserObject != null){
                JSONArray jsonArray = createUserObject.getJSONArray("data");
                String createUserName = jsonArray.getJSONObject(0).get("name").toString();
                orderInfo.setCreateUserName(createUserName);
            }
        }

        if(orderInfo.getAgentPerson() != null){
            JSONObject agentPersonNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/view?employeeId="+ orderInfo.getAgentPerson() ,headerMap));
            //JSONObject agentPersonNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-employee/view?employeeId="+ orderInfo.getAgentPerson() +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
            if(agentPersonNameObject != null){
                JSONArray jsonArray = agentPersonNameObject.getJSONArray("data");
                String agentPersonName = jsonArray.getJSONObject(0).get("name").toString();
                orderInfo.setAgentPersonName(agentPersonName);
            }
        }

        return orderInfo;
    }

    /**
     * 根据flight_id 修改订单服务状态
     * @param flightId
     * @param airportCode
     * @return
     */
    public void updateServerComplete(Long flightId,Short serverComplete, Long updateUser, String airportCode) throws Exception{
        orderInfoMapper.updateServerComplete(flightId,serverComplete,updateUser,airportCode);
    }

    /**
     * 根据详细服务id和服务状态获取服务人次
     * @param serviceDetailId
     * @param airportCode
     * @return
     */
    public int getServerNumByServiceDetailId(String orderStatus,Long serviceDetailId, String airportCode){
       return orderInfoMapper.getServerNumByServiceDetailId(orderStatus,serviceDetailId,airportCode);
    }
}
