/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.StationPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationPoMapper{

    /**
     * insert
     * 
     * @param stationPo
     */
    int insert(StationPo stationPo);

    /**
     * insertSelective
     * 
     * @param stationPos
     */
    int insertBatch(@Param("list") List<StationPo> stationPos, @Param("trainId") Long trainId);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param stationPo
     */
    int updateByCondition(StationPo stationPo);

}