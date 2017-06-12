package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.CustomerPoMapper;
import com.zhiweicloud.guest.pojo.CustomerDetailPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CustomerService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/9 15:54
 * @author tiecheng
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerPoMapper customerPoMapper;

    public String queryCustomerDropDownList(JSONObject request) {
        FlightCenterResult<List<Map<Long, String>>> result = new FlightCenterResult<>();
        try {
            result.setData(customerPoMapper.selectCustomerDropDownList());
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    public String queryCustomerDetail(JSONObject request) {
        FlightCenterResult<CustomerDetailPojo> result = new FlightCenterResult<>();
        try {
            Long customerId = request.getLong("customerId");
            customerId = (customerId == null ? 0L : customerId);
            result.setData(customerPoMapper.selectCustomerDetail(customerId));
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

}
