package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.mapper.ServiceTypeAllocationMapper;
import com.zhiweicloud.guest.mapper.ServMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.ServiceTypeAllocation;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Service
public class ServiceTypeAllocationService {

    private final ServiceTypeAllocationMapper serviceTypeAllocationMapper;
    private final ServMapper servMapper;

    @Autowired
    public ServiceTypeAllocationService(ServiceTypeAllocationMapper serviceTypeAllocationMapper,ServMapper servMapper) {
        this.serviceTypeAllocationMapper = serviceTypeAllocationMapper;
        this.servMapper = servMapper;
    }

    /**
     * 分页获取产品类型配置列表
     * @param param 查询参数
     * @param page 起始页
     * @param rows 每页显示数目
     * @return PaginationResult<ServiceTypeAllocation>
     */
    public LZResult<PaginationResult<ServiceTypeAllocation>> getAll(Map<String, Object> param, Integer page, Integer rows) {

        int count = serviceTypeAllocationMapper.getListCount(param);

        BasePagination<Map<String, Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ServiceTypeAllocation> serviceTypeAllocationList = serviceTypeAllocationMapper.getListByConidition(queryCondition);
        PaginationResult<ServiceTypeAllocation> eqr = new PaginationResult<>(count, serviceTypeAllocationList);
        return new LZResult<>(eqr);
    }

    /**
     * 获取服务名称列表
     * @param param 查询参数
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getServiceNameDropdownList(Map<String,Object> param){
        return servMapper.getServiceNameDropdownList(param);
    }

    /**
     * 获取服务类别列表
     * @param param 查询参数
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getServiceTypeDropdownList(Map<String,Object> param){
        return serviceTypeAllocationMapper.getServiceTypeDropdownList(param);
    }

    /**
     * 获取服务大类列表
     * @param param 查询参数
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getServiceCategoryDropdownList(Map<String,Object> param){
        return serviceTypeAllocationMapper.getServiceCategoryDropdownList(param);
    }

    /**
     * 获取服务菜单列表
     * @param param 查询参数
     * @return List<Dropdownlist>
     */
    public List<ServiceTypeAllocation> getServiceMenuList(Map<String,Object> param){
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",param.get("airportCode"));
        List<ServiceTypeAllocation> result = serviceTypeAllocationMapper.getServiceMenuList(param);
        for(ServiceTypeAllocation serviceTypeAllocation : result){
            params.put("category",serviceTypeAllocation.getCategory());
            List<Dropdownlist> out = serviceTypeAllocationMapper.getServiceTypeDropdownList(params);
            serviceTypeAllocation.setServiceTypeList(out);
        }
        return result;
    }
}
