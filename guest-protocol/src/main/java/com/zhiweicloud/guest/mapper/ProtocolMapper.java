package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.common.MyMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Protocol;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/30.
 */
public interface ProtocolMapper extends MyMapper<Protocol> {
    List<Dropdownlist> getProtocolDropdownList(Map<String, Object> map);
    List<Dropdownlist> getProtocolNoDropdownList(Map<String, Object> map);
    Integer updateByIdAndAirportCode(Protocol protocol);
    List<Protocol> getListByConidition(BasePagination<Map<String, Object>> queryCondition);
    int getListCount(Map<String, Object> map);
    Protocol selectById(Map<String, Object> map);
    Long selectByName(Map<String, Object> map);
    Long selectOrderByProtocolId(Map<String, Object> map);

    /**
     * 协议列表 分页查询
     * @param
     * @return
     */
    List<Protocol> queryProtocolList(@Param("protocolParam") Protocol protocolParam, @Param("begin") int begin, @Param("end") int end);

    /**
     * 协议列表数量
     * @param protocolParam
     * @return
     */
    int selectProtocolTotal(@Param("protocolParam") Protocol protocolParam);

    /**
     * 协议名称模糊查询下拉框
     * @param map
     * @return
     */
    List<Dropdownlist> getProtocolNameDropdownList(Map<String, Object> map);


}
