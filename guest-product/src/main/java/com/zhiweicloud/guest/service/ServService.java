package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.ServMapper;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
@Service
public class ServService {

    @Autowired
    private ServMapper servMapper;

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

        List<Long> serviceId= servMapper.getServiceId(param);
        if(serviceId.size() !=0){
            StringBuffer ids = new StringBuffer();
            for(int i = 0; i < serviceId.size(); i++){
                ids.append(serviceId.get(i)+",");
            }
            param.put("ids",ids.substring(0,ids.length() - 1));
        }
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
     * 服务名称查重
     * @param serv
     * @return boolean
     */
    public boolean selectByName(Serv serv) {
        Map<String,Object> params = new HashMap<>();
        params.put("serviceName",serv.getName());
        params.put("airportCode",serv.getAirportCode());
        params.put("id",serv.getId());
        Long count = servMapper.selectByName(params);
        if(count > 0){//count大于0，说明该名称已存在
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 删除服务时判断是否有协议已经引用
     * @param serviceId
     * @return boolean
     */
    public boolean selectProtocolByServiceId(Long serviceId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("serviceId",serviceId);
        Long count = servMapper.selectProtocolByServiceId(params);
        if(count > 0){//count大于0，说明有协议已经引用该服务
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 服务添加与修改
     * @param serv
     */
    public void saveOrUpdate(Serv serv) {
        if (serv.getId() != null) {
            servMapper.updateByIdAndAirportCode(serv);
        } else {
            serv.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            serv.setCreateTime(new Date());
            serv.setUpdateTime(new Date());
            servMapper.insertSelective(serv);
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
