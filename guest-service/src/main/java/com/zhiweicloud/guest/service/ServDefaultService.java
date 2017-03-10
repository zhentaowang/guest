package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.mapper.ServDefaultMapper;
import com.zhiweicloud.guest.model.ServDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhengyiyin on 2017/3/10.
 */
@Service
public class ServDefaultService {

    @Autowired
    private ServDefaultMapper servDefaultMapper;

    /**
     * 新增默认的服务
     * @param servDefault
     * @return
     * @throws Exception
     */
    public int insertServDefault(ServDefault servDefault) throws Exception{
        return servDefaultMapper.insertServDefault(servDefault);
    }

    /**
     * 删除未选中的 休息室
     * @param employeeId
     * @param airportCode
     * @param servIds
     * @return
     */
    public int deleteServDefault(Long employeeId, String airportCode, String servIds) throws Exception{
        return servDefaultMapper.deleteServDefault(employeeId, airportCode, servIds);
    }


}
