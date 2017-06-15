package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.PassengerPoMapper;
import com.zhiweicloud.guest.mapper.PassengerTicketPoMapper;
import com.zhiweicloud.guest.po.PassengerPo;
import com.zhiweicloud.guest.pojo.PassengerTicketPojo;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

/**
 * DetrService.java
 * detr指令用来提取客人购买机票后的信息记录
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/8 13:19 
 * @author tiecheng
 */
@Service
public class DetrService {

    private static final Log log = LogFactory.getLog(DetrService.class);

    @Autowired
    private PassengerPoMapper passengerPoMapper;

    @Autowired
    private PassengerTicketPoMapper passengerTicketPoMapper;

    @Autowired
    private IbeService ibeService;

    /**
     * 根据客票号查询旅客/航班信息
     * @param request
     * @return
     */
    @Transient
    public String queryPassengerByTickNo(JSONObject request){
        FlightCenterResult<PassengerTicketPojo> re = new FlightCenterResult<>();
        // get args
        String tickNo = request.getString("tickNo");
        if (log.isInfoEnabled()) {
            log.info("【 ************ 请求航班中心方法 -- queryPassengerByTickNo 参数：tickNo:" + tickNo +" ************ 】");
        }
        try {
            // look for local
            PassengerTicketPojo passengerTicketPojo = passengerTicketPoMapper.selectPassengerTicketPojo(tickNo);
            // get from remote
            if (passengerTicketPojo == null) {
                re = ibeService.queryPassengerByTickNo(tickNo);
                PassengerTicketPojo data = re.getData();
                PassengerPo passengerPo = passengerPoMapper.selectByName(data.getPassengerName());
                if (passengerPo == null) {
                    passengerPo = new PassengerPo();
                    passengerPo.setPassengerName(data.getPassengerName());
                    passengerPo.setInfantBirthday(data.getInfantBirthday());
                    passengerPoMapper.insert(passengerPo);
                }
                data.setPassengerId(passengerPo.getPassengerId());
                passengerTicketPoMapper.insertPassengerTicketPojo(data);
            }else {
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(passengerTicketPojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

}
