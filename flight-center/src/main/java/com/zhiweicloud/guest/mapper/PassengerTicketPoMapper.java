package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.PassengerTicketPo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import org.apache.ibatis.annotations.Param;

/**
 * PassengerTicketPoMapper.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/31 18:09 
 * @author tiecheng
 */
public interface PassengerTicketPoMapper {

    int insert(PassengerTicketPo passengerTicketPo);

    int insertPassengerTicketPojo(PassengerTicketPojo PassengerTicketPojo);

    PassengerTicketPojo selectPassengerTicketPojo(@Param("ticketNo") String ticketNo);

}
