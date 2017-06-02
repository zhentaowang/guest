package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.CustomFlightPo;
import com.zhiweicloud.guest.pojo.CustomFlightPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CustomFlightPoMapper.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/1 14:17
 * @author tiecheng
 */
public interface CustomFlightPoMapper {

    int insert(CustomFlightPo customFlightPo);

    List<CustomFlightPo> selectsByFlightId(@Param("flightId") Long flightId);

    List<CustomFlightPojo> selectsCustomFlightPojo(List<Long> flightIds);

}