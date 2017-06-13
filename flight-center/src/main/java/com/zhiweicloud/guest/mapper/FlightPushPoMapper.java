package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.FlightPushPo;
import com.zhiweicloud.guest.pojo.ApiQueryPojo;
import com.zhiweicloud.guest.pojo.FlightPushPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FlightPushPoMapper.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/7 19:29 
 * @author tiecheng
 */
public interface FlightPushPoMapper {

    int insert(FlightPushPo flightPushPo);

    List<FlightPushPojo> selectsByConditionForPage(@Param("apiQueryPojo") ApiQueryPojo apiQueryPojo, @Param("page") int page, @Param("len") int len);

    int countByCondition(@Param("apiQueryPojo") ApiQueryPojo apiQueryPojo);
    
}
