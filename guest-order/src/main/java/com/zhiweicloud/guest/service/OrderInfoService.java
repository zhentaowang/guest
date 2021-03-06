package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
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

    private final CardTypeMapper cardTypeMapper;

    private static MyService.Iface employeeClient = SpringBeanUtil.getBean("employeeClient");

    private static MyService.Iface protocolClient = SpringBeanUtil.getBean("protocolClient");
    @Autowired
    public OrderInfoService(OrderInfoMapper orderInfoMapper, PassengerMapper passengerMapper, OrderServiceMapper orderServiceMapper, FlightMapper flightMapper,CardTypeMapper cardTypeMapper) {
        this.orderInfoMapper = orderInfoMapper;
        this.passengerMapper = passengerMapper;
        this.orderServiceMapper = orderServiceMapper;
        this.flightMapper = flightMapper;
        this.cardTypeMapper = cardTypeMapper;
    }

    public Long saveOrUpdate(OrderInfo orderInfo, List<Passenger> passengerList, List<OrderService> orderServiceList, Long userId, String airportCode) throws Exception {
        orderInfo.setAirportCode(airportCode);


        JSONObject jsonObjectParam = new JSONObject();
        jsonObjectParam.put("user_id", userId);
        jsonObjectParam.put("client_id", airportCode);
        jsonObjectParam.put("employeeId", userId);


        if (orderInfo.getOrderId() != null) {

             //预约订单和服务订单保存的创建人和创建时间不是同一个字段
            if (orderInfo.getOrderType() == 0) {//预约订单
                if(orderInfo.getOrderStatus() != null && orderInfo.getOrderStatus().equals("已使用")){//预约订单 转为 服务订单，需要保持 服务订单的更新时间，更新人
                    orderInfo.setServerUpdateTime(new Date());
                    orderInfo.setServerUpdateUserId(userId);
                    //jsonObjectParam.put("operation", "view");

                    JSONObject updateUserObject = new JSONObject();
                    Response response = ClientUtil.clientSendData(employeeClient, "businessService", "view",jsonObjectParam);
                    if (response != null && response.getResponeCode().getValue() == 200) {
                        updateUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                    }

                    if (updateUserObject != null) {
                        JSONObject obj = updateUserObject.getJSONObject("data");
                        orderInfo.setServerUpdateUserName(obj.get("name").toString());
                    }
                }else{
                    orderInfo.setUpdateTime(new Date());
                    orderInfo.setUpdateUser(userId);

                    JSONObject updateUserObject = new JSONObject();
                    Response response = ClientUtil.clientSendData(employeeClient, "businessService","view", jsonObjectParam);
                    if (response != null && response.getResponeCode().getValue() == 200) {
                        updateUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                    }

                    if (updateUserObject != null) {
                        JSONObject obj = updateUserObject.getJSONObject("data");
                        orderInfo.setCreateUserName(obj.get("name").toString());
                    }
                }
            } else {//服务订单
                orderInfo.setServerUpdateTime(new Date());
                orderInfo.setServerUpdateUserId(userId);
                //jsonObjectParam.put("operation", "view");
                JSONObject updateUserObject = new JSONObject();
                Response response = ClientUtil.clientSendData(employeeClient, "businessService", "view",jsonObjectParam);
                if (response != null && response.getResponeCode().getValue() == 200) {
                    updateUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                }
                if (updateUserObject != null) {
                    JSONObject obj = updateUserObject.getJSONObject("data");
                    orderInfo.setServerUpdateUserName(obj.get("name").toString());
                }
            }
            Flight flight = orderInfo.getFlight();
            if (flight != null) {
                flight.setAirportCode(airportCode);
                Long flightId = flightMapper.isFlightExist(flight);
                flight.setAirportCode(airportCode);

                jsonObjectParam.remove("employeeId");
                if (flightId != null) {
                    executeFlightOperate(flightId,orderInfo,flight,jsonObjectParam);
                } else {
                    flight.setCreateTime(new Date());
                    flight.setCreateUser(userId);
                    flight.setFlightId(null);
                    flightMapper.insertSelective(flight);
                    customFlight(orderInfo,flight,jsonObjectParam);
                }
                orderInfo.setFlightId(flight.getFlightId());
            }
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

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

                //this.setFlightInOrOut(flight);

                if (flightId != null && !flightId.equals("")) {
                    executeFlightOperate(flightId,orderInfo,flight,jsonObjectParam);
                } else {
                    flight.setCreateTime(new Date());
                    flight.setCreateUser(userId);
                    flightMapper.insertSelective(flight);
                    customFlight(orderInfo,flight,jsonObjectParam);
                }
                orderInfo.setFlightId(flight.getFlightId());
            }


            /**
             * 预约订单和服务订单保存的创建人和创建时间不是同一个字段
             */
            if (orderInfo.getOrderType() == 0) {
                orderInfo.setCreateTime(new Date());
                orderInfo.setCreateUser(userId);
//                JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
                //jsonObjectParam.put("operation", "view");
                JSONObject createUserObject = new JSONObject();
                Response response = ClientUtil.clientSendData(employeeClient, "businessService", "view",jsonObjectParam);
                if (response != null && response.getResponeCode().getValue() == 200) {
                    createUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                }
                if (createUserObject != null) {
                    JSONObject obj = createUserObject.getJSONObject("data");
                    orderInfo.setCreateUserName(obj.get("name").toString());
                }
            } else {
                orderInfo.setServerCreateTime(new Date());
                orderInfo.setServerCreateUserId(userId);
                orderInfo.setServerUpdateUserId(userId);
                //jsonObjectParam.put("operation", "view");
                JSONObject createUserObject = new JSONObject();
                Response response = ClientUtil.clientSendData(employeeClient, "businessService", "view",jsonObjectParam);
                if (response != null && response.getResponeCode().getValue() == 200) {
                    createUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                }
                if (createUserObject != null) {
                    JSONObject obj = createUserObject.getJSONObject("data");
                    orderInfo.setServerCreateUserName(obj.get("name").toString());
                    orderInfo.setServerUpdateUserName(obj.get("name").toString());
                }
            }
            orderInfoMapper.insertSelective(orderInfo);
        }
        this.addPassengerAndServiceDetails(orderInfo, passengerList, orderServiceList, userId, airportCode);
        return orderInfo.getOrderId();
    }

    private void executeFlightOperate(Long flightId, OrderInfo orderInfo, Flight flight, JSONObject jsonObjectParam) throws UnsupportedEncodingException, URISyntaxException {
        flight.setFlightId(flightId);
        jsonObjectParam.put("flight", JSON.toJSONString(flight));
        //jsonObjectParam.put("operation", "updateFlightInfo");
        ClientUtil.clientSendData(employeeClient, "businessService", "updateFlightInfo",jsonObjectParam);
        jsonObjectParam.remove("flight");
        Boolean isCustom = flightMapper.selectIsCustomById(flightId);
        if (!isCustom){
            customFlight(orderInfo,flight,jsonObjectParam);
        }
    }

    //龙腾定制航班 如果产品为异地贵宾服务，不走定制航班
    private void customFlight(OrderInfo orderInfo,Flight flight,Map<String, Object> params) throws URISyntaxException {
        if(orderInfo.getProductName()!=null && !orderInfo.getProductName().equals("异地贵宾服务")){
            params.put("flightId", flight.getFlightId());
            params.put("operation", "customFlight");
//            暂时先不去定制，因为定制以后的推送会去线上
//            ThriftClientUtils.invokeRemoteMethod(params,"flight-info");

//            Map<String, Object> flightMap = new HashMap<>();
//            flightMap.put("flightId", flight.getFlightId());
//            HttpClientUtil.httpGetRequest("http://flight-info/flight-info/customFlight", headerMap,flightMap);
        }
    }

    /*private void setFlightInOrOut(Flight flight){
        if (flight.getAirportCode().equals(flight.getFlightDepcode())) {//当前登录三字码 == 航班目的港口
            flight.setIsInOrOut((short) 0);//出港
        } else if (flight.getAirportCode().equals(flight.getFlightArrcode())) {//当前登录三字码 == 航班出发港口
            flight.setIsInOrOut((short) 1);//进港
        } else {
            flight.setIsInOrOut((short) 0);//出港
        }
    }*/

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
                p.setFlightId(orderInfo.getFlight().getFlightId());
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

//                Map<String, Object> headerMap = new HashMap<>();
//                Map<String, Object> paramMap = new HashMap<>();
//                headerMap.put("user-id", userId);
//                headerMap.put("client-id", airportCode);
//                paramMap.put("protocolProductId", orderInfo.getProductId());
//                paramMap.put("typeId", jsonObject.get("serviceId"));

                JSONObject params = new JSONObject();
                params.put("user_id", userId);
                params.put("client_id", airportCode);
                params.put("protocolProductId", orderInfo.getProductId());
                params.put("typeId", jsonObject.get("serviceId"));

                if (jsonObject.get("serviceDetailId") != null && jsonObject.get("serviceId") != null) {
//                    JSONObject jsonObject1 = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/get-service-box-by-type-and-protocol-product-id", headerMap, paramMap));
                    //params.put("operation", "get-service-box-by-type-and-protocol-product-id");
                    JSONObject jsonObject1 = new JSONObject();
                    Response response = ClientUtil.clientSendData(protocolClient, "businessService", "get-service-box-by-type-and-protocol-product-id",params);
                    if (response != null && response.getResponeCode().getValue() == 200) {
                        jsonObject1 = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                    }

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
//        Map<String, Object> headerMap = new HashMap();
//        headerMap.put("user-id", userId);
//        headerMap.put("client-id", orderInfoQuery.getAirportCode());

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
     * @param map
     * @return
     */
    public void updateServerComplete(Map<String,Object> map, Long userId, String airportCode) throws Exception {
//        Map<String, Object> headerMap = new HashMap<>();
//        headerMap.put("user-id", userId);
//        headerMap.put("client-id", airportCode);
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("employeeId", userId);
//        //远程调用查询该用户id，的用户名字，存到订单表（多余的字段）
//        JSONObject createUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        params.put("client_id", airportCode);
        params.put("employeeId", userId);
        //params.put("operation", "view");
        JSONObject createUserObject = new JSONObject();
        Response response = ClientUtil.clientSendData(employeeClient, "businessService","view", params);
        if (response != null && response.getResponeCode().getValue() == 200) {
            createUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
        }


        if (createUserObject != null) {
            JSONObject obj = createUserObject.getJSONObject("data");
            map.put("serverCompleteName", obj.get("name").toString());
        }
        map.put("userId", userId);
        map.put("airportCode", airportCode);
        orderInfoMapper.updateServerComplete(map);
    }

    /**
     * 根据详细服务id和服务状态获取服务人次
     *
     * @param servId
     * @param airportCode
     * @return
     */
    public int getServerNumByServId(String orderStatus, Long servId, String airportCode) throws Exception {
        return orderInfoMapper.getServerNumByServId(orderStatus, servId, airportCode);
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
     * @param customerIds 客户ID"集合"  eg. 1,2,3
     * @param airportCode 机场码
     */
    public List<ProtocolList> queryProtocolIdsInOrderInfoByCustomId(String customerIds, String airportCode) throws Exception {
        String[] customIdArray = customerIds.split(",");
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

    /**
     * 根据客户ID、机场码和账单标志获取客户下被订单引用的协议
     *
     * @param customerIds 客户ID"集合"  eg. 1,2,3
     * @param airportCode 机场码
     * @param flag        定制账单标志
     * @return
     */
    public List<ProtocolList> queryProtocolIdsInOrderInfoByCustomIdAndType(String customerIds, String airportCode, Integer[] flag) {
        String[] customIdArray = customerIds.split(",");
        List<ProtocolList> protocolLists = new ArrayList<>();
        for (String s : customIdArray) {
            List<ProtocolVo> protocolVos = orderInfoMapper.queryProtocolIdsInOrderInfoByCustomIdAndType(Long.valueOf(s), airportCode, flag);
            ProtocolList protocolList = new ProtocolList();
            protocolList.setCustomerId(Long.valueOf(s));
            protocolList.setProtocolVos(protocolVos);
            protocolLists.add(protocolList);
        }
        return protocolLists;
    }

    public List<Map> queryCardType(String airportCode) {
        List<Map> list = cardTypeMapper.queryCardTypeByAirportCode(airportCode);
        return list;
    }

    /**
     * 更新订单信息
     * @param orderInfo
     * @throws Exception
     */
    public void updateOrderInfo(OrderInfo orderInfo) throws Exception{
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

}
