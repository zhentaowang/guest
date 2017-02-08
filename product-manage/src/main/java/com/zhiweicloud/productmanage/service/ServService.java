package com.zhiweicloud.productmanage.service;

import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.productmanage.mapper.ProductTypeAllocationMapper;
import com.zhiweicloud.productmanage.mapper.ServMapper;
import com.zhiweicloud.productmanage.model.Serv;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 分页获取服务列表
     * @param param
     * @param page
     * @param rows
     * @return PaginationResult<Serv>
     */
    public LZResult<PaginationResult<Serv>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = servMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> servList = servMapper.getListByConidition(queryCondition);
        PaginationResult<Serv> eqr = new PaginationResult<>(count, servList);
        LZResult<PaginationResult<Serv>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 按类型获取服务列表
     * @param param
     * @param page
     * @param rows
     * @param price
     * @param freeRetinueNum
     * @param overStaffUnitPrice
     * @param description
     * @return List<Serv>
     */
    public LZResult<PaginationResult<Serv>> getServAll(Map<String,Object> param, Integer page, Integer rows,BigDecimal price,Integer freeRetinueNum,BigDecimal overStaffUnitPrice,String description) {

        int count = servMapper.getServListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> servList = servMapper.getServListByConidition(queryCondition);
        for(int i = 0; i < servList.size(); i++){
            servList.get(i).setPrice(price);
            servList.get(i).setDescription(description);
            if(servList.get(i).getProductTypeAllocationId() == 4){
                if(freeRetinueNum != null && overStaffUnitPrice != null){
                    servList.get(i).setFreeRetinueNum(freeRetinueNum);
                    servList.get(i).setOverStaffUnitPrice(overStaffUnitPrice);
                }
            }
        }
        PaginationResult<Serv> eqr = new PaginationResult<>(count, servList);
        LZResult<PaginationResult<Serv>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 获取服务详情
     * @param param
     * @return Serv
     */
    public Serv getById(Map<String,Object> param) {
        return servMapper.selectById(param);
    }

    /**
     * 服务添加与修改
     * @param serv
     */
    public void saveOrUpdate(Serv serv) {
        if (serv.getId() != null) {
            servMapper.updateByIdAndAirportCode(serv);
        } else {
            Map<String,Object> params = new HashMap<>();
            Short isDeleted = 0;
            serv.setIsDeleted(isDeleted);
            serv.setCreateTime(new Date());
            serv.setUpdateTime(new Date());
            servMapper.insert(serv);
        }
    }

    /**
     * 服务删除
     * @param ids
     * @param airportCode
     */
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
