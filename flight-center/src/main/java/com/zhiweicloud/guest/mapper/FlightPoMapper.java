package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.FlightPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tc on 2017/5/31.
 */
public interface FlightPoMapper {

    int insert(FlightPo flightPo);

    // 预留
    int insertBatch(List<FlightPo> flightPos);

    int deleteByIdBogus(@Param("flightId") Long flightId);

    int update(FlightPo flightPo);

    int updateIsCustom(FlightPo flightPo);

    int updateByCondition(FlightPo flightPo);

    FlightPo selectById(@Param("flightId") Long flightId);

    FlightPo select(FlightPo flightPo);

    List<FlightPo> selects(FlightPo flightPo);

    List<FlightPo> selectByDateAndNo(FlightPo flightPo);

}
