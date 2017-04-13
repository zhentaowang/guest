package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;

import com.zhiweicloud.guest.common.HttpClientUtil;

import com.zhiweicloud.guest.mapper.ProductServiceTypeMapper;
import com.zhiweicloud.guest.mapper.ServMapper;
import com.zhiweicloud.guest.model.ProductServiceType;
import com.zhiweicloud.guest.model.ProtocolProductDetail;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Service
public class ServService {

    private final ServMapper servMapper;
    private final ProductServiceTypeMapper productServiceTypeMapper;

    @Autowired
    public ServService(ServMapper servMapper,ProductServiceTypeMapper productServiceTypeMapper) {
        this.servMapper = servMapper;
        this.productServiceTypeMapper = productServiceTypeMapper;
    }

    /**
     * 分页获取服务列表
     * @param param 查询参数
     * @param page 起始页
     * @param rows 每页显示数目
     * @return PaginationResult<Serv>
     */
    public LZResult<PaginationResult<JSONObject>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = servMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> servList = servMapper.getListByConidition(queryCondition);
        List<JSONObject> servJson = new ArrayList<>();
        for(Serv serv : servList){
            JSONObject result = new JSONObject();
            if(serv.getServiceDetail() != null){
                result = JSON.parseObject(serv.getServiceDetail());
            }
            result.put("servId",serv.getServId());
            result.put("airportCode",serv.getAirportCode());
            result.put("serviceTypeAllocationId",serv.getServiceTypeAllocationId());
            result.put("name",serv.getName());
            result.put("no",serv.getNo());
            servJson.add(result);
        }
        PaginationResult<JSONObject> eqr = new PaginationResult<>(count, servJson);
        return new LZResult<>(eqr);
    }

    /**
     * 分页获取产品列表
     * @param param 查询参数
     * @param page 起始页
     * @param rows 每页显示数目
     * @return PaginationResult<ProductServiceType>
     */
    public LZResult<PaginationResult<ProductServiceType>> getProductAndServiceList(Map<String,Object> param, Integer page, Integer rows) {

        int count = productServiceTypeMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProductServiceType> productList = productServiceTypeMapper.getListByConidition(queryCondition);
        for(ProductServiceType productServiceType : productList){
            Map<String,Object> params = new HashMap<>();
            params.put("airportCode",param.get("airportCode"));
            params.put("productId",productServiceType.getProductId());
            params.put("typeId",productServiceType.getServiceTypeId());
            List<Serv> servList = servMapper.getServListByCondition(params);
            productServiceType.setServiceList(servList);
        }
        PaginationResult<ProductServiceType> eqr = new PaginationResult<>(count, productList);
        return new LZResult<>(eqr);
    }

    /**
     * 获取服务详情
     * @param param 查询参数
     * @return Serv
     */
    public Serv getById(Map<String,Object> param) {
        return servMapper.selectById(param);
    }

    /**
     * 服务名称查重
     * @param serv 服务查重参数
     * @return boolean
     */
    public boolean selectByName(Serv serv) {
        Map<String,Object> params = new HashMap<>();
        params.put("serviceName",serv.getName());
        params.put("airportCode",serv.getAirportCode());
        params.put("id",serv.getServId());
        Long count = servMapper.selectByName(params);
        return  count > 0;//count大于0，说明该名称已存在
    }

    /**
     * 删除服务时判断是否有订单已经引用
     * @param serviceId 服务id
     * @return boolean
     */
    public boolean selectProductByServiceId(Long serviceId,String airportCode) {
        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",airportCode);
        params.put("serviceId",serviceId);
        Long count = servMapper.selectProductByServiceId(params);
        return count > 0;//count大于0，说明有订单已经引用该服务
    }

    /**
     * 服务添加与修改
     * @param serv 服务信息
     */
    public void saveOrUpdate(Serv serv) {
        if (serv.getServId() != null) {
            servMapper.updateByIdAndAirportCode(serv);
        } else {
            serv.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            serv.setCreateTime(new Date());
            serv.setUpdateTime(new Date());
            servMapper.insertBySelective(serv);
        }
    }

    /**
     * 服务删除
     * @param ids 多个服务id
     * @param airportCode 机场代码
     */
    public void deleteById(List<Long> ids,String airportCode, Long userId) {
        for(Long id : ids){
            Serv serv = new Serv();
            serv.setAirportCode(airportCode);
            serv.setUpdateUser(userId);
            serv.setServId(id);
            serv.setIsDeleted(Constant.MARK_AS_DELETED);
            servMapper.updateByIdAndAirportCode(serv);
        }
    }

    /**
     * 根据服务类型配置id和产品id查询服务详情
     * @param param 查询参数
     * @param page 起始页
     * @param rows 每页显示数目
     * @return PaginationResult<JSONObject>
     */
    public LZResult<PaginationResult<JSONObject>> getServiceListByTypeId(Map<String,Object> param, Integer page, Integer rows) {

        int count = servMapper.getServListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<Serv> serviceList = servMapper.getServListByTypeId(queryCondition);
        List<JSONObject> servJson = new ArrayList<>();
        for(Serv serv : serviceList){
            JSONObject result = new JSONObject();
            result.put("servId",serv.getServId());
            result.put("airportCode",serv.getAirportCode());
            result.put("serviceTypeAllocationId",serv.getServiceTypeAllocationId());
            result.put("name",serv.getName());
            result.put("no",serv.getNo());
            if(param.get("typeId") != null){
                Map<String,Object> protocolProductFieldName = ProtocolProductDetail.getProtocolProductFieldName(Long.parseLong(param.get("typeId").toString()));
                if(protocolProductFieldName != null){
                    result.putAll(protocolProductFieldName);
                }
                result.put("isPricing",0);
                result.put("isPrioritized",0);
                result.put("isAvailabled",0);
            }
            servJson.add(result);
        }
        PaginationResult<JSONObject> eqr = new PaginationResult<>(count, servJson);
        return new LZResult<>(eqr);
    }

    /**
     * 根据服务分类查询 服务名，服务人数
     * @param typeId 服务类型配置id
     * @param airportCode 机场代码
     * @return servList
     */
    public List<Serv> getServNameAndPositionNum(Long typeId, Long userId, String airportCode, Integer page, Integer rows, boolean isShowAll) throws Exception{
        List<Serv> servList = new ArrayList<>();
        //查该用户默认选择的 服务厅
        if(!isShowAll){
            servList = servMapper.getServNameAndPositionNum(typeId,userId,airportCode,(page-1)*rows,rows,true);

        }

        //若没有设置默认，查询全部
        if(CollectionUtils.isEmpty(servList)){
            servList = servMapper.getServNameAndPositionNum(typeId,userId,airportCode,(page-1)*rows,rows,false);
        }
        if(CollectionUtils.isEmpty(servList)){
            return null;
        }

        Map<String, Object> headerMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", airportCode);
        for(Serv serv : servList){
            paramMap.put("servId", serv.getServId());
            //根据servId,服务厅的id 从order_service 统计服务人数
            JSONObject orderServiceJSONObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-order/guest-order/getServerNumByServlId", headerMap, paramMap));
            //解析协议产品服务对象,统计人数
            int servNum = Integer.valueOf(orderServiceJSONObject.get("data").toString());
            serv.setServerNum(servNum);
        }
        return servList;
    }
}
