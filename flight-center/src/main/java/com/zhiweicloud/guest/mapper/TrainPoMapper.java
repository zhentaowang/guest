/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.TrainPo;

public interface TrainPoMapper{

    /**
     * insert
     * 
     * @param trainPo
     */
    int insert(TrainPo trainPo);

    /**
     * update
     *
     * @param trainPo
     */
    int update(TrainPo trainPo);

    TrainPo select(TrainPo trainPo);

}