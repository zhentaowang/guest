package com.zhiweicloud.productmanage.service;

import com.zhiweicloud.productmanage.mapper.ServMapper;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.model.Dropdownlist;

import com.zhiweicloud.productmanage.mapper.ProductTypeAllocationMapper;
import com.zhiweicloud.productmanage.model.ProductTypeAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
@Service
public class ProductTypeAllocationService {

    @Autowired
    private ProductTypeAllocationMapper productTypeAllocationMapper;

    @Autowired
    private ServMapper servMapper;

    /**
     * 分页获取产品类型配置列表
     * @param param
     * @param page
     * @param rows
     * @return PaginationResult<ProductTypeAllocation>
     */
    public LZResult<PaginationResult<ProductTypeAllocation>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = productTypeAllocationMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProductTypeAllocation> productTypeAllocationList = productTypeAllocationMapper.getListByConidition(queryCondition);
        PaginationResult<ProductTypeAllocation> eqr = new PaginationResult<>(count, productTypeAllocationList);
        LZResult<PaginationResult<ProductTypeAllocation>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 获取服务名称列表
     * @param param
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getServiceNameDropdownList(Map<String,Object> param){
        return servMapper.getServiceNameDropdownList(param);
    }

    /**
     * 获取服务类型列表
     * @param param
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getServiceTypeDropdownList(Map<String,Object> param){
        return productTypeAllocationMapper.getServiceTypeDropdownList(param);
    }

    /**
     * 获取产品品类列表
     * @param param
     * @return List<Dropdownlist>
     */
    public List<Dropdownlist> getProductCategoryDropdownList(Map<String,Object> param){
        return productTypeAllocationMapper.getProductCategoryDropdownList(param);
    }



}
