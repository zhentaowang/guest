package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.OrderServiceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/4.
 */
public interface OrderServiceRecordMapper {

    /**
     * 插入服务动态
     * @param record
     * @return
     */
    int insert(@Param("record") OrderServiceRecord record);

    /**
     * 根据订单id获取 服务附加单的服务动态
     * @param orderId
     * @param airportCode
     * @return
     */
    List<OrderServiceRecord> getOrderServiceRecord(@Param("orderId") Long orderId,@Param("airportCode")String airportCode);
}
