/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.FlightCenterApiPo;
import com.zhiweicloud.guest.pojo.ApiQueryPojo;
import com.zhiweicloud.guest.pojo.FlightCenterApiPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * @param apiQueryPojo
     */
//    List<FlightCenterApiPo> selects(FlightCenterApiPojo flightCenterApiPojo);

    List<FlightCenterApiPojo> selects(@Param("apiQueryPojo") ApiQueryPojo apiQueryPojo);

    /**
     * 根据条件分页查询数量
     * @param apiQueryPojo
     * @return
     */
    int countByCondition(@Param("apiQueryPojo") ApiQueryPojo apiQueryPojo);

    /**
     * 根据条件分页查询
     * @param apiQueryPojo
     * @return
     */
    List<FlightCenterApiPojo> selectsByConditionForPage(@Param("apiQueryPojo") ApiQueryPojo apiQueryPojo,@Param("page") int page,@Param("len") int len);

}