/**
Copyright 2016-2017 author IronC.
*/
package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.pojo.CustomerDetailPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerPoMapper {

    int insert(CustomerPo customerPo);

    int delete(@Param("customerId") Long customerId);

    int deleteByIdBogus(@Param("customerId") Long customerId);

    int update(CustomerPo customerPo);

    int updateTotal(CustomerPo customerPo);

    CustomerPo select(@Param("customerId") Long customerId);

    CustomerPo selectBySysCode(String sysCode);

    List<Map<Long, String>> selectCustomerDropDownList();

    CustomerDetailPojo selectCustomerDetail(@Param("customerId")Long customerId);

}