package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.pojo.TrainPojo;

import java.util.Map;

/**
 * Created by tc on 2017/5/25.
 */
public interface TrainPojoMapper {

    TrainPojo queryTrainByCondition(Map<String,String> params);

}
