package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.mapper.OrderServiceRecordMapper;
import com.zhiweicloud.guest.model.OrderServiceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
