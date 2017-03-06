package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.OrderServiceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/4.
 */
public interface OrderServiceRecordMapper {

    /**
     * 插入
     * @param recordList
     * @return
     */
    int insert(@Param("recordList") List<OrderServiceRecord> recordList);
}
