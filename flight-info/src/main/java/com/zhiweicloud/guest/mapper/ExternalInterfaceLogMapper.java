package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.ExternalInterfaceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ExternalInterfaceLogMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/28 14:03
 * @author tiecheng
 */
public interface ExternalInterfaceLogMapper {

    /**
     * 插入一条数据
     * @param externalInterfaceLog
     */
    void insert(ExternalInterfaceLog externalInterfaceLog);

    /**
     * 删除一条数据
     * @param logId
     */
    void deleteByLogId(@Param("logId") Long logId);

    /**
     * 改变一条数据为删除状态
     */
    void updateForDeleteByLogId(@Param("logId") Long logId);

    /**
     * 根据id查询
     * @param logId
     * @param airportCode
     * @return
     */
    ExternalInterfaceLog selectByLogId(@Param("logId") Long logId,@Param("airportCode") String airportCode);

    /**
     * 多条件查询
     * @param externalInterfaceLog
     * @return
     */
    List<ExternalInterfaceLog> selectByCondition(@Param("externalInterfaceLog") ExternalInterfaceLog externalInterfaceLog);

}
