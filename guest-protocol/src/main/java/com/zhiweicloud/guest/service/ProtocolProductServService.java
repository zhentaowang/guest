package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.mapper.ProtocolProductServiceMapper;
import com.zhiweicloud.guest.model.ProtocolProductServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/3/8.
 */
@Service
public class ProtocolProductServService {

    @Autowired
    private ProtocolProductServiceMapper protocolProductServiceMapper;

    /**
     * 根据服务id获取协议产品服务列表
     * 2017-3-8 zhengyiyin
     * @param serviceId
     * @param airportCode
     * @return
     */
    public List<ProtocolProductServ> getProtocolProductServiceByServiceId(Long serviceId, String airportCode) throws Exception{
        Map<String,Object> params = new HashMap<>();
        params.put("serviceId",serviceId);
        params.put("airportCode",airportCode);
        return protocolProductServiceMapper.selectByProtocolProductId(params);
    }
}
