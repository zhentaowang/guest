/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.FlightCenterApiPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlightCenterApiPoMapper {

    /**
     * insert
     * 
     * @param flightCenterApiPo
     */
    int insert(FlightCenterApiPo flightCenterApiPo);

    /**
     * delete by primaryKey
     *
     * @param flightCenterApiId
     */
    int delete(Long flightCenterApiId);

    /**
     * delete by primaryKey -- set isDeleted = 1
     *
     * @param flightCenterApiId
     */
    int deleteByIdBogus(Long flightCenterApiId);

    /**
     * updateByPrimaryKey
     *
     * @param flightCenterApiPo
     */
    int update(FlightCenterApiPo flightCenterApiPo);

    /**
     * selectByPrimaryKey
     * 
     * @param flightCenterApiId
     */
    FlightCenterApiPo select(@Param("flightCenterApiId") Long flightCenterApiId);

    /**
     * selects by condition
     *
     * @param flightCenterApiPo
     */
    List<FlightCenterApiPo> selects(@Param("flightCenterApiPo") FlightCenterApiPo flightCenterApiPo);

    /**
     * selects by condition
     *
     * @param flightCenterApiPo
     */
    List<FlightCenterApiPo> selectForPage(@Param("flightCenterApiPo") FlightCenterApiPo flightCenterApiPo,@Param("start")int start, @Param("len")int len);

}