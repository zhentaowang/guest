package com.airportcloud.productmanage.mapper;

import com.airportcloud.productmanage.common.MyMapper;
import com.airportcloud.productmanage.model.Dropdownlist;
import com.airportcloud.productmanage.model.Serv;
import com.airportcloud.productmanage.pageUtil.BasePagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface ServMapper extends MyMapper<Serv> {
    Serv selectById(Map<String,Object> map);
    List<Serv> getListByConidition(BasePagination<Map<String,Object>> queryCondition);
    List<Serv> getServListByConidition(Map<String,Object> map);
    int getListCount(Map<String,Object> map);
    Integer getCurrentValue();
    Integer updateByIdAndAirportCode(Serv serv);
    Long getIdByCondition(Map<String,Object> servParam);
    public List<Dropdownlist> getServiceNameDropdownList(Map<String,Object> param);
    Integer deleteByIdAndAirportCode(Serv serv);
}
