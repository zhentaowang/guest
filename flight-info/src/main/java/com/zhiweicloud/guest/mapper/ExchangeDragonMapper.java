package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.ExchangeDragon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ExchangeDragonMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/28 14:03
 * @author tiecheng
 */
public interface ExchangeDragonMapper {

    /**
     * 插入一条数据
     * @param exchangeDragon
     */
    void insert(ExchangeDragon exchangeDragon);

    /**
     * 删除一条数据
     * @param exchangeId
     */
    void deleteByExchangeId(@Param("exchangeId") Long exchangeId);

    /**
     * 改变一条数据为删除状态
     */
    void updateForDeleteByExchangeId(@Param("exchangeId") Long exchangeId);

    /**
     * 根据id查询
     * @param exchangeId
     * @return
     */
    ExchangeDragon selectByExchangeId(@Param("exchangeId") Long exchangeId,@Param("airportCode") String airportCode);

    /**
     * 多条件查询
     * @param exchangeDragon
     * @return
     */
    List<ExchangeDragon> selectByCondition(@Param("exchangeDragon") ExchangeDragon exchangeDragon);

}
