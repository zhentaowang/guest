/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.ApiCustomer;
import org.apache.ibatis.annotations.Param;

public interface ApiCustomerMapper{

    /**
     * insert
     * 
     * @param apiCustomer
     */
    int insert(ApiCustomer apiCustomer);

    /**
     * delete by primaryKey -- set isDeleted = 1
     *
     * @param customerId
     */
    int deleteByIdBogus(@Param("customerId") Long customerId);

    /**
     * insertSelective
     * 
     * @param record
     */
    int insertSelective(ApiCustomer record);

    /**
     * selectByPrimaryKey
     * 
     * @param customerId
     */
    ApiCustomer selectByPrimaryKey(Long customerId);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record
     */
    int updateByPrimaryKeySelective(ApiCustomer record);

    /**
     * updateByPrimaryKey
     * 
     * @param record
     */
    int updateByPrimaryKey(ApiCustomer record);

    Long selectIdBySysCode(String sysCode);

}