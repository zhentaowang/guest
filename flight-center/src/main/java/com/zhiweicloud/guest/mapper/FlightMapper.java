package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.Flight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlightMapper{

    /**
     * insert
     *
     * @param flight
     */
    int insert(Flight flight);

    /**
     * insert batch
     *
     * @param flights
     */
    int insertBatch(List<Flight> flights);

    /**
     * delete by primaryKey
     *
     * @param flightId
     */
    int deleteById(@Param("flightId") Long flightId);

    /**
     * delete by primaryKey -- set isDeleted = 1
     *
     * @param flightId
     */
    int deleteByIdBogus(@Param("flightId") Long flightId);

    /**
     * update by id
     *
     * @param flight
     */
    int updateById(Flight flight);

    /**
     * select by condition
     *
     * @param flight
     */
    Flight select(Flight flight);

    /**
     * select by id
     *
     * @param flightId
     */
    Flight selectById(@Param("flightId") Long flightId);

    /**
     * selects by condition
     *
     * @param flight
     */
    List<Flight> selects(Flight flight);

    /**
     * selects by condition
     *
     * @param flight
     */
    List<Flight> selectForPage(Flight flight);

}
