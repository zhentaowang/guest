package com.airportcloud.protocolmanage.service;


import com.airportcloud.protocolmanage.APIUtil.LZResult;
import com.airportcloud.protocolmanage.APIUtil.PaginationResult;
import com.airportcloud.protocolmanage.common.Constant;
import com.airportcloud.protocolmanage.mapper.ProtocolMapper;
import com.airportcloud.protocolmanage.mapper.ProtocolServMapper;
import com.airportcloud.protocolmanage.model.ProtocolServ;
import com.airportcloud.protocolmanage.pageUtil.BasePagination;
import com.airportcloud.protocolmanage.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/5.
 */
@Service
public class ProtocolServService {
    @Autowired
    private ProtocolServMapper protocolservMapper;

    @Autowired
    private ProtocolMapper protocolMapper;

    public LZResult<PaginationResult<ProtocolServ>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolservMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProtocolServ> protocolservList = protocolservMapper.getListByConidition(queryCondition);
        PaginationResult<ProtocolServ> eqr = new PaginationResult<>(count, protocolservList);
        LZResult<PaginationResult<ProtocolServ>> result = new LZResult<>(eqr);
        return result;
    }

    public Map<String,Object> getById(Map<String,Object> param) {
        return protocolservMapper.selectById(param);
    }

    public void saveOrUpdate(ProtocolServ protocolServ) {
        if (protocolServ.getId() != null) {
            protocolservMapper.updateByIdAndAirportCode(protocolServ);
        } else {
            Map<String,Object> protocolParam = new HashMap<>();
            protocolParam.put("airportCode",protocolServ.getAirportCode());
            protocolParam.put("protocolName",protocolServ.getProtocolName());
            protocolServ.setProtocolId(protocolMapper.getIdByCondition(protocolParam));
            Map<String,Object> servParam = new HashMap<>();
            servParam.put("airportCode",protocolServ.getAirportCode());
            servParam.put("serviceName",protocolServ.getServiceName());
            protocolServ.setServiceId(protocolservMapper.getIdByCondition(servParam));
            Map<String,Object> productTypeAllocationParam = new HashMap<>();
            productTypeAllocationParam.put("airportCode",protocolServ.getAirportCode());
            productTypeAllocationParam.put("serviceType",protocolServ.getServiceType());
            protocolServ.setProductTypeAllocationId(protocolservMapper.getCategoryId(productTypeAllocationParam));
            protocolservMapper.insert(protocolServ);
        }
    }

    public void deleteById(List<String> ids) {
        for(int i = 1; i< ids.size();i++){
            ProtocolServ temp = new ProtocolServ();
            temp.setAirportCode(ids.get(0));
            temp.setId(Long.parseLong(ids.get(i)));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            protocolservMapper.updateByIdAndAirportCode(temp);
        }
    }
}
