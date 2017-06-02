package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.PassengerPo;

/**
 * PassengerPoMapper.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/31 18:09 
 * @author tiecheng
 */
public interface PassengerPoMapper {

    int insert(PassengerPo passengerPo);

    PassengerPo selectByName(String passengerName);

}
