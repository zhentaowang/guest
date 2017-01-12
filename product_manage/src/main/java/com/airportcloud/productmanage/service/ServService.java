package com.airportcloud.productmanage.service;

import com.airportcloud.productmanage.pageUtil.BasePagination;
import com.airportcloud.productmanage.pageUtil.PageModel;
import com.github.pagehelper.PageHelper;
import com.airportcloud.productmanage.APIUtil.LZResult;
import com.airportcloud.productmanage.APIUtil.PaginationResult;
import com.airportcloud.productmanage.common.Constant;
import com.airportcloud.productmanage.common.GeneratorSerNo;
import com.airportcloud.productmanage.mapper.ProductTypeAllocationMapper;
import com.airportcloud.productmanage.mapper.ServMapper;
import com.airportcloud.productmanage.model.Dropdownlist;
import com.airportcloud.productmanage.model.Serv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wzt on 2016/12/26.
 */
@Service
public class ServService {
    @Autowired
    private ServMapper servMapper;

    @Autowired
    private ProductTypeAllocationMapper productTypeAllocationMapper;


    public LZResult<PaginationResult<Serv>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = servMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> servList = servMapper.getListByConidition(queryCondition);
        PaginationResult<Serv> eqr = new PaginationResult<>(count, servList);
        LZResult<PaginationResult<Serv>> result = new LZResult<>(eqr);
        return result;
    }

    public LZResult<List<Serv>> getServAll(Map<String,Object> param,BigDecimal price,Integer freeRetinueNum,BigDecimal overStaffUnitPrice) {

        List<Serv> servList = servMapper.getServListByConidition(param);
        for(int i = 0; i < servList.size(); i++){
            servList.get(i).setPrice(price);
            servList.get(i).setFreeRetinueNum(freeRetinueNum);
            servList.get(i).setOverStaffUnitPrice(overStaffUnitPrice);
        }
        LZResult<List<Serv>> result = new LZResult<>(servList);
        return result;
    }

    public Serv getById(Map<String,Object> param) {
        return servMapper.selectById(param);
    }

    public void saveOrUpdate(Serv serv) {
        if (serv.getId() != null) {
            servMapper.updateByIdAndAirportCode(serv);
        } else {
            Map<String,Object> params = new HashMap<>();
            params.put("airportCode",serv.getAirportCode());
            params.put("serviceType",serv.getServiceType());
            serv.setProductTypeAllocationId(productTypeAllocationMapper.getCategoryId(params));
            serv.setIsDeleted(Constant.MARK_AS_NOT_DELETED);
            serv.setCreateTime(new Date());
            serv.setUpdateTime(new Date());
            servMapper.insert(serv);
        }
    }

    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            Serv serv = new Serv();
            serv.setAirportCode(airportCode);
            serv.setId(ids.get(i));
            serv.setIsDeleted(Constant.MARK_AS_DELETED);
            servMapper.updateByIdAndAirportCode(serv);
        }
    }
}
