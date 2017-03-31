package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.FlightUpdateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FlightUpdateLogMapper.java
 * 航班更新日志Mapper
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/29 11:22
 * @author tiecheng
 */
public interface FlightUpdateLogMapper {

    void insert(FlightUpdateLog flightUpdateLog);

    FlightUpdateLog selectByLogId(@Param("logId") Long logId, @Param("airportCode") String airportCode);

    List<FlightUpdateLog> selectByFlightId(@Param("flightId") Long flightId,@Param("airportCode") String airportCode);

    List<FlightUpdateLog> selectByFlightIdForShow(@Param("flightId") Long flightId,@Param("airportCode") String airportCode);

}
