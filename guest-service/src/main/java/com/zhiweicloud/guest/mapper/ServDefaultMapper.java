package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.ServDefault;
import org.apache.ibatis.annotations.Param;


/**
 * Created by zhengyiyin on 2017/3/10
 */
public interface ServDefaultMapper {

    /**
     * 记住默认显示的休息室、贵宾厅
     * @param servDefault
     * @return
     */
    int insertServDefault(ServDefault servDefault);

    /**
     * 删除未选中的 休息室
     * @param employeeId
     * @param airportCode
     * @param servIds
     * @return
     */
    int deleteServDefault(@Param("employeeId") Long employeeId,@Param("airportCode") String airportCode,@Param("servIds") String servIds);


}
