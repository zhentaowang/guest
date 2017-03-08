package com.zhiweicloud.guest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.ProtocolProductMapper;
import com.zhiweicloud.guest.mapper.ProtocolProductServiceMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wzt on 2016/12/30.
 */
@Service
public class ProtocolProductService {

    private final ProtocolProductMapper protocolProductMapper;
    private final ProtocolProductServiceMapper protocolProductServiceMapper;


    @Autowired
    public ProtocolProductService(ProtocolProductMapper protocolProductMapper,ProtocolProductServiceMapper protocolProductServiceMapper) {
        this.protocolProductMapper = protocolProductMapper;
        this.protocolProductServiceMapper = protocolProductServiceMapper;
    }

    /**
     * 分页获取协议产品列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<ProtocolProduct>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolProductMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProtocolProduct> protocolList = protocolProductMapper.getListByConidition(queryCondition);
        PaginationResult<ProtocolProduct> eqr = new PaginationResult<>(count, protocolList);
        return new LZResult<>(eqr);
    }

    /**
     * 获取协议产品详情
     * @param param
     */
    public ProtocolProduct getById(Map<String,Object> param) {
        return protocolProductMapper.selectById(param);
    }

    /**
     * 获取协议产品服务详情
     * @param param
     */
    public ProtocolProductServ getByProtocolProductServiceId(Map<String,Object> param) {
        return protocolProductServiceMapper.selectByProtocolProductServiceId(param);
    }

    /**
     * 协议产品添加与修改
     * @param protocolProduct
     */
    public void saveOrUpdate(ProtocolProduct protocolProduct) {
        if (protocolProduct.getProtocolProductId() != null) {
            protocolProductMapper.updateByIdAndAirportCode(protocolProduct);
        } else {
            protocolProduct.setCreateTime(new Date());
            protocolProduct.setUpdateTime(new Date());
            protocolProduct.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            protocolProductMapper.insertBySelective(protocolProduct);
            List<ProtocolProductServ> protocolProductServList = protocolProduct.getProtocolProductServList();
            for(int i = 0; i < protocolProductServList.size(); i++){
                ProtocolProductServ protocolProductServ = new ProtocolProductServ();
                protocolProductServ.setAirportCode(protocolProduct.getAirportCode());
                protocolProductServ.setProtocolProductId(protocolProduct.getProtocolProductId());
                protocolProductServ.setCreateTime(new Date());
                protocolProductServ.setUpdateTime(new Date());
                protocolProductServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                protocolProductServiceMapper.insertBySelective(protocolProductServ);
            }
        }
    }

    /**
     * 协议产品服务添加与修改
     * @param protocolProductServ
     */
    public void saveOrUpdateProtocolProductServ(ProtocolProductServ protocolProductServ) {
        if (protocolProductServ.getProtocolProductServiceId() != null) {
            protocolProductServiceMapper.updateByIdAndAirportCode(protocolProductServ);
        } else {
            protocolProductServ.setCreateTime(new Date());
            protocolProductServ.setUpdateTime(new Date());
            protocolProductServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            protocolProductServiceMapper.insertBySelective(protocolProductServ);
        }
    }

    /**
     * 删除协议产品
     * @param airportCode
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            ProtocolProduct protocolProduct = new ProtocolProduct();
            protocolProduct.setAirportCode(airportCode);
            protocolProduct.setProtocolProductId(ids.get(i));
            protocolProduct.setIsDeleted(Constant.MARK_AS_DELETED);
            protocolProductMapper.updateByIdAndAirportCode(protocolProduct);
        }
    }

    /**
     * 协议产品查重
     * @param protocolProduct
     * @return boolean
     */
    public boolean selectByProductId(ProtocolProduct protocolProduct) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId",protocolProduct.getProductId());
        params.put("airportCode",protocolProduct.getAirportCode());
        params.put("protocolId",protocolProduct.getProtocolId());
        Long count = protocolProductMapper.selectByProductId(params);
        if(count > 0){//count大于0，说明该产品已经添加
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 删除协议产品时判断是否有订单已经引用
     * @param protocolProductId
     * @param airportCode
     * @return boolean
     */
    public boolean selectOrderByProtocolProductId(Long protocolProductId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("protocolProductId",protocolProductId);
        params.put("airportCode",airportCode);
        Long count = protocolProductMapper.selectOrderByProtocolProductId(params);
        if(count > 0){//count大于0，说明有订单已经引用该协议产品
            return true;
        }
        else{
            return false;
        }
    }
}
