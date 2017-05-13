package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.ThriftClientUtils;
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

    /**
     * 插入服务动态
     * @param record
     * @return
     */
    public int insert(OrderServiceRecord record) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("user-id", record.getCreateUser());
        params.put("client-id", record.getAirportCode());
        params.put("employeeId", record.getCreateUser());
        params.put("operation", "view");

        JSONObject updateUserObject = JSON.parseObject(ThriftClientUtils.invokeRemoteMethodCallBack(params, "guest-employee"));

//        JSONObject updateUserObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-employee/guest-employee/view", headerMap, paramMap));
        if (updateUserObject != null) {
            JSONObject obj = updateUserObject.getJSONObject("data");
            record.setCreateUserName(obj.get("name").toString());
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
