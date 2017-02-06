package com.zhiweicloud.productmanage.mapper;

import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.productmanage.model.Serv;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface ServMapper extends MyMapper<Serv> {
    Serv selectById(Map<String,Object> map);
    List<Serv> getListByConidition(BasePagination<Map<String,Object>> queryCondition);
    List<Serv> getServListByConidition(BasePagination<Map<String,Object>> queryCondition);
    int getListCount(Map<String,Object> map);
    int getServListCount(Map<String,Object> map);
    Integer getCurrentValue();
    Integer updateByIdAndAirportCode(Serv serv);
    Long getIdByCondition(Map<String,Object> servParam);
    public List<Dropdownlist> getServiceNameDropdownList(Map<String,Object> param);
    Integer deleteByIdAndAirportCode(Serv serv);
}
