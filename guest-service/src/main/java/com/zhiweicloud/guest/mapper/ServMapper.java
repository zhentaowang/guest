package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface ServMapper {
    Serv selectById(Map<String, Object> map);
    void insertBySelective(Serv serv);
    Long selectProductByServiceId(Map<String, Object> map);
    List<Serv> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    List<Serv> getServListByTypeId(BasePagination<Map<String, Object>> queryCondition);
    List<Serv> getServListByCondition(Map<String, Object> map);
    int getServListCount(Map<String, Object> map);
    Long selectByName(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Serv serv);
    List<Dropdownlist> getServiceNameDropdownList(Map<String, Object> param);

    /**
     * 根据服务分类查询 服务名，服务人数
     * @param typeId
     * @param airportCode
     * @return
     */
    List<Serv> getServNameAndPositionNum(@Param("typeId") Long typeId, @Param("userId") Long userId, @Param("airportCode") String airportCode,
                                         @Param("begin") int begin,@Param("rows")int rows, @Param("hasDefault") boolean hasDefault);
}
