package com.zhiweicloud.guest.mapper;


import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Protocol;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2013/03/27.
 */
public interface ProtocolTypeMapper {

    /**
     * 协议类型 下拉框
     * @param airportCode
     * @return
     */
    List<Dropdownlist> getProtocolTypeDropdownList(@Param("protocolTypeId") Long protocolTypeId, @Param("airportCode") String airportCode);


}
