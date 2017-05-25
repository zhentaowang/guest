/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.model.FlightCenterApiLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlightCenterApiLogMapper{

    /**
     * insert
     * 
     * @param flightCenterApiLog
     */
    int insert(FlightCenterApiLog flightCenterApiLog);

    /**
     * delete by primaryKey
     *
     * @param flightInfoApiLogId
     */
    int deleteById(Long flightInfoApiLogId);

    /**
     * delete by primaryKey -- set isDeleted = 1
     *
     * @param flightInfoApiLogId
     */
    int deleteByIdBogus(Long flightInfoApiLogId);

    /**
     * updateByPrimaryKey
     *
     * @param flightCenterApiLog
     */
    int updateById(FlightCenterApiLog flightCenterApiLog);

    /**
     * selectByPrimaryKey
     * 
     * @param flightInfoApiLogId
     */
    FlightCenterApiLog selectById(@Param("flightInfoApiLogId") Long flightInfoApiLogId);

    /**
     * selects by condition
     *
     * @param flightCenterApiLog
     */
    List<FlightCenterApiLog> selects(@Param("flightCenterApiLog") FlightCenterApiLog flightCenterApiLog);

    /**
     * selects by condition
     *
     * @param FlightCenterApiLog
     */
    List<FlightCenterApiLog> selectForPage(@Param("FlightCenterApiLog") Flight FlightCenterApiLog);

}