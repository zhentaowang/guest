package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.mapper.OrderInfoMapper;
import com.zhiweicloud.guest.mapper.OrderServiceMapper;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by zhangpengfei on 2017/2/28.
 */
@Service
public class OrderInfoService {

    private final OrderInfoMapper orderInfoMapper;

    private final PassengerMapper passengerMapper;

    private final OrderServiceMapper orderServiceMapper;

    private final FlightMapper flightMapper;

    @Autowired
    public OrderInfoService(OrderInfoMapper orderInfoMapper, PassengerMapper passengerMapper, OrderServiceMapper orderServiceMapper, FlightMapper flightMapper) {
        this.orderInfoMapper = orderInfoMapper;
        this.passengerMapper = passengerMapper;
        this.orderServiceMapper = orderServiceMapper;
        this.flightMapper = flightMapper;
    }

    public void saveOrUpdate(OrderInfo orderInfo, List<Passenger> passengerList, List<OrderService> orderServiceList, Long userId, String airportCode) throws Exception {
        orderInfo.setAirportCode(airportCode);
        if (orderInfo.getOrderId() != null) {
            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);
            paramMap.put("employeeId", userId);

            /**
             * 预约订单和服务订单保存的创建人和创建时间不是同一个字段
             */
            if (orderInfo.getOrderType() == 0) {//预约订单
                orderInfo.setUpdateTime(new Date());
                orderInfo.setUpdateUser(userId);
                if (orderInfo.getUpdateUser() != null) {
                    JSONObject updateUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
                    if (updateUserObject != null) {
                        JSONArray jsonArray = updateUserObject.getJSONArray("data");
                        String updateUserName = jsonArray.getJSONObject(0).get("name").toString();
                        orderInfo.setCreateUserName(updateUserName);
                    }
                }
            } else {//服务订单
                orderInfo.setServerUpdateTime(new Date());
                orderInfo.setServerUpdateUserId(userId);
                if (orderInfo.getServerUpdateUserId() != null) {
                    JSONObject updateUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
                    if (updateUserObject != null) {
                        JSONArray jsonArray = updateUserObject.getJSONArray("data");
                        String updateUserName = jsonArray.getJSONObject(0).get("name").toString();
                        orderInfo.setServerUpdateUserName(updateUserName);
                    }
                }
            }


            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (orderInfo.getFlight() != null) {
                Flight flight = orderInfo.getFlight();
                flight.setAirportCode(airportCode);

                if (airportCode.equals(flight.getFlightDepcode())) {//当前登录三字码 == 航班目的港口
                    flight.setIsInOrOut((short) 0);//出港
                } else if (airportCode.equals(flight.getFlightArrcode())) {//当前登录三字码 == 航班出发港口
                    flight.setIsInOrOut((short) 1);//进港
                }

                if (flight.getFlightId() != null) {
                    flight.setUpdateTime(new Date());
                    flight.setUpdateUser(userId);
                    flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
                }
            }
            //保存订单日志
            if (!StringUtils.isEmpty(orderInfo.getOrderStatus())) {
                orderInfoMapper.insertIntoOrderStatusRecord(orderInfo);
            }

        } else {
            /**
             * 新增修改航班信息
             */
            if (orderInfo.getFlight() != null) {
                Flight flight = orderInfo.getFlight();
                flight.setAirportCode(airportCode);
                Long flightId = flightMapper.isFlightExist(flight);
                if (airportCode.equals(flight.getFlightDepcode())) {//当前登录三字码 == 航班目的港口
                    flight.setIsInOrOut((short) 0);//出港
                } else if (airportCode.equals(flight.getFlightArrcode())) {//当前登录三字码 == 航班出发港口
                    flight.setIsInOrOut((short) 1);//进港
                } else {
                    flight.setIsInOrOut((short) 0);//出港
                }
                if (flightId != null && !flightId.equals("")) {
                    flight.setFlightId(flightId);
                    flightMapper.updateByFlithIdAndAirportCodeSelective(flight);
                } else {
                    flight.setCreateTime(new Date());
                    flight.setCreateUser(userId);
                    flightMapper.insertSelective(flight);
                }
                orderInfo.setFlightId(flight.getFlightId());
            }

            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);
            paramMap.put("employeeId", userId);

            /**
             * 预约订单和服务订单保存的创建人和创建时间不是同一个字段
             */
            if (orderInfo.getOrderType() == 0) {
                orderInfo.setCreateTime(new Date());
                orderInfo.setCreateUser(userId);
                if (orderInfo.getCreateUser() != null) {
                    JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
                    if (createUserObject != null) {
                        JSONArray jsonArray = createUserObject.getJSONArray("data");
                        String createUserName = jsonArray.getJSONObject(0).get("name").toString();
                        orderInfo.setCreateUserName(createUserName);
                    }
                }
            } else {
                orderInfo.setServerCreateTime(new Date());
                orderInfo.setServerCreateUserId(userId);
                if (orderInfo.getServerCreateUserId() != null) {
                    JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
                    if (createUserObject != null) {
                        JSONArray jsonArray = createUserObject.getJSONArray("data");
                        String createUserName = jsonArray.getJSONObject(0).get("name").toString();
                        orderInfo.setServerCreateUserName(createUserName);
                    }
                }
            }


            orderInfoMapper.insertSelective(orderInfo);
        }
        this.addPassengerAndServiceDetails(orderInfo, passengerList, orderServiceList, userId, airportCode);
    }

    /**
     * 添加旅客,服务详情
     *
     * @param orderInfo
     * @param passengerList
     * @param orderServiceList
     */
    private void addPassengerAndServiceDetails(OrderInfo orderInfo, List<Passenger> passengerList, List<OrderService> orderServiceList, Long userId, String airportCode) throws Exception {
        try {
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
                if (p.getPassengerId() != null) {
                    passengerMapper.updateByPassengerIdAndAirportCodeKeySelective(p);
                    passengerIds.add(p.getPassengerId());
                } else {
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

                Map<String, Object> headerMap = new HashMap<>();
                Map<String, Object> paramMap = new HashMap<>();
                headerMap.put("user-id", userId);
                headerMap.put("client-id", airportCode);
                paramMap.put("protocolProductId", orderInfo.getProductId());
                paramMap.put("typeId", jsonObject.get("serviceId"));

                if (jsonObject.get("serviceDetailId") != null && jsonObject.get("serviceId") != null) {
                    JSONObject jsonObject1 = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/get-service-box-by-type-and-protocol-product-id", headerMap, paramMap));
                    if (jsonObject1 != null) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int k = 0; k < jsonArray.size(); k++) {
                            if (jsonArray.getJSONObject(k).get("protocolProductServiceId").equals(jsonObject.get("serviceDetailId"))) {
                                if (jsonArray.getJSONObject(k).get("pricingRule") != null) {
                                    os.setPriceRule(jsonArray.getJSONObject(k).get("pricingRule").toString());
                                }
                            }
                        }
                    }
                }
                os.setServiceDetail(jsonObject.toJSONString());
                if (os.getOrderServiceId() != null) {
                    serverDetailsList.add(os.getOrderServiceId());
                    orderServiceMapper.updateByOrderServiceIdAndAirportCodeKeySelective(os);
                } else {
                    orderServiceMapper.insertSelective(os);
                    serverDetailsList.add(os.getOrderServiceId());
                }
            }


            if (passengerIds != null && passengerIds.size() > 0) {
                this.deleteData(orderInfo.getOrderId(), ListUtil.List2String(passengerIds), orderInfo.getAirportCode(), "passenger_id", "passenger");
            }

            if (serverDetailsList != null && serverDetailsList.size() > 0) {
                this.deleteData(orderInfo.getOrderId(), ListUtil.List2String(serverDetailsList), orderInfo.getAirportCode(), "order_service_id", "order_service");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除车辆，乘客，收费服务
     * 数据库有1,2,3,4,5 这5条数据
     * 现在前台只传递 1,2,3 三条数据，那么我需要把4,5 删除
     * update ${tableName} set is_deleted = 1 where id not in ( #{ids} )  and airport_code = #{airportCode} and order_id = #{orderId}
     *
     * @param ids
     * @param airportCode
     * @param tableName
     */
    private void deleteData(Long orderId, String ids, String airportCode, String idColumn, String tableName) {
        orderInfoMapper.markChildRowsAsDeleted(orderId, ids, airportCode, idColumn, tableName);
    }

    public LZResult<PaginationResult<OrderInfo>> getOrderInfoList(Integer page, Integer rows,
                                                                  OrderInfoQuery orderInfoQuery, Long userId) throws Exception {
        Map<String, Object> headerMap = new HashMap();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", orderInfoQuery.getAirportCode());

        /**
         * 协议名称模糊匹配，获取协议ids
         */
        List protocolIdList = new ArrayList();
        if (orderInfoQuery.getQueryCustomerInfo() != null && !orderInfoQuery.getQueryCustomerInfo().equals("")) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("protocolName", orderInfoQuery.getQueryCustomerInfo());
            JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolNameDropdownList", headerMap, paramMap));
            //JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?access_token=KoePhbd1pseoMyjxCnO4gCBf60dUJk1HomZyrGsZ&protocolName=" + orderInfoQuery.getQueryCustomerInfo()));
            if (protocolParam != null) {
                JSONArray protocolArray = protocolParam.getJSONArray("data");
                for (int i = 0; i < protocolArray.size(); i++) {
                    JSONObject jsonObject = JSON.parseObject(protocolArray.get(i).toString());
                    protocolIdList.add(jsonObject.get("id"));
                }
            }
        }


        /**
         * 预约号，拿到协议id，所以可以用 protocolIds
         */
        if (orderInfoQuery.getQueryCustomerInfo() != null && !orderInfoQuery.getQueryCustomerInfo().equals("")) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("reservationNum", orderInfoQuery.getQueryCustomerInfo());
            JSONObject reservationNumObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolNameDropdownList", paramMap, headerMap));
            //JSONObject reservationNumObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?access_token=KoePhbd1pseoMyjxCnO4gCBf60dUJk1HomZyrGsZ&reservationNum=" + orderInfoQuery.getQueryCustomerInfo()));
            if (reservationNumObject != null) {
                JSONArray reservationNumArray = reservationNumObject.getJSONArray("data");
                for (int i = 0; i < reservationNumArray.size(); i++) {
                    JSONObject jsonObject = JSON.parseObject(reservationNumArray.get(i).toString());
                    protocolIdList.add(jsonObject.get("id"));
                }
            }
        }

        /**
         * 预约人
         */
        List authorizerNameIdList = new ArrayList();
        if (orderInfoQuery.getQueryCustomerInfo() != null && !orderInfoQuery.getQueryCustomerInfo().equals("")) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("authorizerName", orderInfoQuery.getQueryCustomerInfo());
            JSONObject authorizerNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolNameDropdownList", paramMap, headerMap));
            //JSONObject authorizerNameObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/getProtocolNameDropdownList?access_token=KoePhbd1pseoMyjxCnO4gCBf60dUJk1HomZyrGsZ&authorizerName=" + orderInfoQuery.getQueryCustomerInfo()));
            if (authorizerNameObject != null) {
                JSONArray authorizerNameArray = authorizerNameObject.getJSONArray("data");

                for (int i = 0; i < authorizerNameArray.size(); i++) {
                    JSONObject jsonObject = JSON.parseObject(authorizerNameArray.get(i).toString());
                    authorizerNameIdList.add(jsonObject.get("id"));
                }
            }
        }
        if (protocolIdList.size() > 0) {
            orderInfoQuery.setQueryProtocolIds(ListUtil.List2String(protocolIdList));//协议id
        }

        if (authorizerNameIdList.size() > 0) {
            orderInfoQuery.setQueryBookingIds(ListUtil.List2String(authorizerNameIdList));//预约人
        }

        //根据旅客姓名或者旅客身份证号 获取 订单id
        /*if(orderInfoQuery.getQueryPassengerName() != null || orderInfoQuery.getQueryIdentityCard() != null){
            String orderIds = passengerMapper.getOrderIdsByPassengerNameOrCard(orderInfoQuery);
            orderInfoQuery.setOrderIds(orderIds);
        }*/

        BasePagination<OrderInfoQuery> queryCondition = new BasePagination<>(orderInfoQuery, new PageModel(page, rows));

        int total = orderInfoMapper.selectOrderInfoTotal(queryCondition);


        List<OrderInfo> orderInfoList = orderInfoMapper.selectOrderInfoList(queryCondition);

        PaginationResult<OrderInfo> eqr = new PaginationResult<>(total, orderInfoList);
        LZResult<PaginationResult<OrderInfo>> result = new LZResult<>(eqr);
        return result;
    }

    public void deleteById(List<Long> ids, Long userId, String airportCode) throws Exception {
        //删除订单  删除乘客，订单服务
        for (int i = 0; i < ids.size(); i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setIsDeleted(Constant.MARK_AS_DELETED);
            orderInfo.setOrderId(ids.get(i));
            orderInfo.setAirportCode(airportCode);
            orderInfo.setUpdateTime(new Date());
            orderInfo.setUpdateUser(userId);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            //保存订单日志
            orderInfo.setOrderStatus("删除订单");
            orderInfoMapper.insertIntoOrderStatusRecord(orderInfo);
            this.deletePassengerAndOrderServiceDeatil(ids.get(i), userId, airportCode);
        }
    }

    /**
     * 根据订单id逻辑删除旅客，详细服务
     *
     * @param orderId
     */
    private void deletePassengerAndOrderServiceDeatil(Long orderId, Long userId, String airportCode) throws Exception {
        try {
            /**
             * 逻辑删除旅客
             */
            Passenger passenger = new Passenger();
            passenger.setAirportCode(airportCode);
            passenger.setUpdateTime(new Date());
            passenger.setUpdateUser(userId);
            passenger.setOrderId(orderId);
            passengerMapper.markAsDeleted(passenger);

            /**
             * 逻辑删除旅客
             */
            OrderService orderService = new OrderService();
            orderService.setAirportCode(airportCode);
            orderService.setOrderId(orderId);
            orderService.setUpdateTime(new Date());
            orderService.setUpdateUser(userId);
            orderServiceMapper.markAsDeleted(orderService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderInfo getById(Long orderId, Long userId, String airportCode) throws Exception {
        OrderInfo orderInfo = orderInfoMapper.getDetailById(orderId, airportCode);
        return orderInfo;
    }

    /**
     * 根据flight_id 修改订单服务状态
     *
     * @param flightId
     * @param airportCode
     * @return
     */
    public void updateServerComplete(Long flightId, Short serverComplete, Long updateUser, String airportCode) throws Exception {
        orderInfoMapper.updateServerComplete(flightId, serverComplete, updateUser, airportCode);
    }

    /**
     * 根据详细服务id和服务状态获取服务人次
     *
     * @param serviceDetailId
     * @param airportCode
     * @return
     */
    public int getServerNumByServiceDetailId(String orderStatus, Long serviceDetailId, String airportCode) throws Exception {
        return orderInfoMapper.getServerNumByServiceDetailId(orderStatus, serviceDetailId, airportCode);
    }

    /**
     * 根据协议id查询订单是否存在
     *
     * @return
     */
    public int getOrderCountByProtocolId(Long protocolId, String airportCode) throws Exception {
        return orderInfoMapper.getOrderCountByProtocolId(protocolId, airportCode);
    }

    /**
     * 根据客户ID和机场码获取客户下被订单引用的协议
     *
     * @param customIds
     * @param airportCode
     */
    public List<ProtocolList> queryProtocolIdsInOrderInfoByCustomId(String customIds, String airportCode) throws Exception {
        String[] customIdArray = customIds.split(",");
        List<ProtocolList> protocolLists = new ArrayList<>();
        for (String s : customIdArray) {
            List<ProtocolVo> protocolVos = orderInfoMapper.queryProtocolIdsInOrderInfoByCustomId(Long.valueOf(s), airportCode);
            ProtocolList protocolList = new ProtocolList();
            protocolList.setCustomerId(Long.valueOf(s));
            protocolList.setProtocolVos(protocolVos);
            protocolLists.add(protocolList);
        }
        return protocolLists;
    }
}
