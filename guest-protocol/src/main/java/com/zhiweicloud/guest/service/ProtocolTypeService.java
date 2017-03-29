package com.zhiweicloud.guest.service;


import com.zhiweicloud.guest.mapper.*;
import com.zhiweicloud.guest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhengyiyin on 2017/03/27.
 */
@Service
public class ProtocolTypeService {

    @Autowired
    private ProtocolTypeMapper protocolTypeMapper;

    /**
     * 协议类型 下拉框 2017.2.27重写
     * @param protocolTypeId
     * @param airportCode
     * @return
     * @throws Exception
     */
    public List<Dropdownlist> getProtocolTypeDropdownList(Long protocolTypeId,String airportCode) throws Exception{
        return protocolTypeMapper.getProtocolTypeDropdownList(protocolTypeId,airportCode);
    }

}
