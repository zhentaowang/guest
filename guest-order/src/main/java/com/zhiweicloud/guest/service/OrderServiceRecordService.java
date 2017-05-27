package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.mapper.OrderServiceRecordMapper;
import com.zhiweicloud.guest.model.OrderServiceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/3/6.
 */
@Service
public class OrderServiceRecordService {

    @Autowired
    private OrderServiceRecordMapper orderServiceRecordMapper;

    private static MyService.Iface employeeClient = SpringBeanUtil.getBean("employeeClient");

    /**
     * 插入服务动态
     * @param record
     * @return
     */
    public int insert(OrderServiceRecord record) throws Exception {
        JSONObject params = new JSONObject();
        params.put("user-id", record.getCreateUser());
        params.put("client-id", record.getAirportCode());
        params.put("employeeId", record.getCreateUser());
        params.put("operation", "view");



        Response response = ClientUtil.clientSendData(employeeClient, "businessService", params);
        if (response != null && response.getResponeCode().getValue() == 200) {
            JSONObject updateUserObject = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
            if (updateUserObject != null) {
                JSONObject obj = updateUserObject.getJSONObject("data");
                record.setCreateUserName(obj.get("name").toString());
            }
        }
        return orderServiceRecordMapper.insert(record);
    }

    /**
     * 根据订单id获取 服务附加单的服务动态
     * @param orderId
     * @param airportCode
     * @return
     */
    public List<OrderServiceRecord> getOrderServiceRecord(Long orderId, String airportCode){
        return orderServiceRecordMapper.getOrderServiceRecord(orderId, airportCode);
    }

}
