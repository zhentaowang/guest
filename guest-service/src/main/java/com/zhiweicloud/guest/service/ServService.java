package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.*;

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
    public LZResult<PaginationResult<JSONObject>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = servMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> servList = servMapper.getListByConidition(queryCondition);
        List<JSONObject> servJson = new ArrayList<>();
        for(int i = 0; i < servList.size(); i++){
            JSONObject result = new JSONObject();
            if(servList.get(i).getServiceDetail() != null){
                result = JSON.parseObject(servList.get(i).getServiceDetail());
            }
            result.put("servId",servList.get(i).getServId());
            result.put("airportCode",servList.get(i).getAirportCode());
            result.put("serviceTypeAllocationId",servList.get(i).getServiceTypeAllocationId());
            result.put("name",servList.get(i).getName());
            result.put("no",servList.get(i).getNo());
            servJson.add(result);
        }
        PaginationResult<JSONObject> eqr = new PaginationResult<>(count, servJson);
        LZResult<PaginationResult<JSONObject>> result = new LZResult<>(eqr);
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
        params.put("id",serv.getServId());
        Long count = servMapper.selectByName(params);
        if(count > 0){//count大于0，说明该名称已存在
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 删除服务时判断是否有产品已经引用
     * @param serviceId
     * @return boolean
     */
    public boolean selectProductByServiceId(Long serviceId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("serviceId",serviceId);
        Long count = servMapper.selectProductByServiceId(params);
        if(count > 0){//count大于0，说明有产品已经引用该服务
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
        if (serv.getServId() != null) {
            servMapper.updateByIdAndAirportCode(serv);
        } else {
            serv.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
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
            serv.setServId(ids.get(i));
            serv.setIsDeleted(Constant.MARK_AS_DELETED);
            servMapper.updateByIdAndAirportCode(serv);
        }
    }
}
