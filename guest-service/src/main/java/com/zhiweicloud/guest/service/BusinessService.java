/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.mapper.ProductServiceTypeMapper;
import com.zhiweicloud.guest.mapper.ServDefaultMapper;
import com.zhiweicloud.guest.mapper.ServMapper;
import com.zhiweicloud.guest.mapper.ServiceTypeAllocationMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.*;
import java.util.*;

/**
 * ServMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * Created by wzt on 05/05/2017.
 */
@Service
public class BusinessService implements IBusinessService {

    private final ServMapper servMapper;
    private final ProductServiceTypeMapper productServiceTypeMapper;
    private final ServDefaultMapper servDefaultMapper;
    private final ServiceTypeAllocationMapper serviceTypeAllocationMapper;

    @Autowired
    public BusinessService(ServMapper servMapper,ProductServiceTypeMapper productServiceTypeMapper,ServDefaultMapper servDefaultMapper,ServiceTypeAllocationMapper serviceTypeAllocationMapper) {
        this.servMapper = servMapper;
        this.productServiceTypeMapper = productServiceTypeMapper;
        this.servDefaultMapper = servDefaultMapper;
        this.serviceTypeAllocationMapper = serviceTypeAllocationMapper;
    }

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }

        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "save-or-update":
                success = save(request);
                break;
            case "view":
                success = view(request);
                break;
            case "delete":
                success = delete(request);
                break;
            case "get-service-list-by-type-and-product-id":
                success = getServiceList(request);
                break;
            case "getServNameAndPositionNum":
                success = getServNameAndPositionNum(request);
                break;
            case "product-and-service-list":
                success = getProductAndServiceList(request);
                break;
            case "setDefaultRoom":
                success = setServDefault(request);
                break;
            case "service-type-allocation-list":
                success = serviceTypeAllocationList(request);
                break;
            case "service-menu-list":
                success = getServiceMenuList(request);
                break;
            case "service-category-dropdown-list":
                success = getServiceCategoryDropdownList(request);
                break;
            case "service-type-dropdown-list":
                success = getServiceTypeDropdownList(request);
                break;
            case "service-name-dropdown-list":
                success = getServiceNameDropdownList(request);
                break;
            default:
                break;
        }
        return success;
    }

    /**
     * 服务管理 - 服务列表
     * @para page 起始页
     * @para rows 每页显示数目
     * @para typeId 服务类型配置id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return LZResult 服务详情列表
     */
    public String list(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("typeId", request.getLong("typeId"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

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
        return JSON.toJSONString(new LZResult<>(eqr));

    }

    /**
     * 服务管理 - 新增or更新
     * 需要判断name是否重复
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para params 服务信息
     * @return 成功还是失败
     */
    public String save(JSONObject request) {
        try {

            Serv serv = new Serv();
            JSONArray param = JSON.parseArray(request.getString("data"));
            JSONObject param00 = JSON.parseObject(JSONObject.toJSONString(param.get(0), SerializerFeature.WriteMapNullValue));
            serv.setServId(param00.getLong("servId"));
            serv.setAirportCode(request.getString("client_id"));
            serv.setCreateUser(request.getLong("user_id"));
            serv.setUpdateUser(request.getLong("user_id"));
            serv.setName(param00.getString("name"));
            serv.setServiceTypeAllocationId(param00.getLong("serviceTypeAllocationId"));
            param00.remove("servId");
            param00.remove("name");
            param00.remove("airportCode");
            param00.remove("serviceTypeAllocationId");
            serv.setServiceDetail(JSONObject.toJSONString(param00,SerializerFeature.WriteMapNullValue));
            Set keys = param00.keySet();
            Map<String, Object> serviceFieldName = ServiceDetail.getServiceFieldName(serv.getServiceTypeAllocationId());
            if (keys.size() != serviceFieldName.size()) {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR));
            } else {
                if (serviceFieldName != null) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (!serviceFieldName.containsKey(keys.toArray()[i])) {
                            return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR));
                        } else {
                            if (param00.getString(keys.toArray()[i].toString()).isEmpty() & !keys.toArray()[i].toString().equals("plateNumber")) {
                                return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                            }
                        }
                    }
                }
            }
            if (serv.getServId() == null) {
                if (serv.getAirportCode() == null || serv.getServiceTypeAllocationId() == null || serv.getName() == null) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                }
            } else {
                if (serv.getAirportCode() == null || serv.getName() == null) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                }
            }

            Map<String,Object> params = new HashMap<>();
            params.put("serviceName",serv.getName());
            params.put("airportCode",serv.getAirportCode());
            params.put("id",serv.getServId());
            if (servMapper.selectByName(params) > 0) {
                return JSON.toJSONString(LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display()));
            }
            if (serv.getServId() != null) {
                servMapper.updateByIdAndAirportCode(serv);
            } else {
                serv.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                serv.setCreateTime(new Date());
                serv.setUpdateTime(new Date());
                servMapper.insertBySelective(serv);
            }
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));

        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    /**
     * 服务管理 - 根据id查询
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para servId 服务id
     * @return 服务详情
     */
    public String view(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("servId", request.getLong("servId"));
        Serv serv = servMapper.selectById(param);
        JSONObject result = JSON.parseObject(serv.getServiceDetail());
        result.put("servId", serv.getServId());
        result.put("airportCode", serv.getAirportCode());
        result.put("serviceTypeAllocationId", serv.getServiceTypeAllocationId());
        result.put("name", serv.getName());
        return result.toJSONString();

    }

    /**
     * 服务管理 - 删除
     * @para airportCode 机场代码
     * @para userId 用户id
     * @para params ids
     * @return 成功或失败
     */
    public String delete(JSONObject request) {
        try {

            JSONArray param = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(param.toJSONString(), Long.class);
            Map<String,Object> params = new HashMap<>();
            params.put("airportCode",request.getString("client_id"));
            for (Long id : ids) {
                params.put("serviceId", id);
                if (servMapper.selectProductByServiceId(params) > 0) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_REF_ERROR));
                }
            }

            for(Long id : ids){
                Serv serv = new Serv();
                serv.setAirportCode(request.getString("client_id"));
                serv.setUpdateUser(request.getLong("user_id"));
                serv.setServId(id);
                serv.setIsDeleted(Constant.MARK_AS_DELETED);
                servMapper.updateByIdAndAirportCode(serv);
            }
            return JSON.toJSONString(LXResult.success());

        } catch (Exception e) {
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 服务管理 - 根据服务类型配置id和产品id查询服务详情
     * @para page      起始页
     * @para rows      每页显示数目
     * @para typeId    服务类型配置id
     * @para productId 产品id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 服务详情分页结果
     */
    public String getServiceList(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));
        param.put("typeId", request.getLong("typeId"));
        param.put("productId", request.getLong("productId"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

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
        return JSON.toJSONString(new LZResult<>(eqr));

    }

    /**
     * 根据服务分类查询 服务名，服务人数
     * @para typeId 服务类型配置id
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 显示服务厅名以及可服务人数
     */
    @GET
    @Path("getServNameAndPositionNum")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "休息室/服务厅管理 - 服务查询", notes = "显示服务厅名以及可服务人数", httpMethod = "GET", produces = "application/json")
    public String getServNameAndPositionNum(JSONObject request) {

        LZResult<List<Serv>> result = new LZResult<>();
        try {
            List<Serv> servList = new ArrayList<>();
            String airportCode = request.getString("client_id");
            Long typeId = request.getLong("typeId");
            Long userId = request.getLong("user_id");

            int page = 1;
            if(request.containsKey("page")) {
                page = request.getInteger("page");
            }

            int rows = 10;
            if (request.containsKey("rows")) {
                rows = request.getInteger("rows");
            }

            //查该用户默认选择的 服务厅
            if(!request.getBoolean("isShowAll")){
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

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(servList);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }

    /**
     * 服务管理 - 获得产品和服务列表
     * @para page      起始页
     * @para rows      每页显示数目
     * @para airportCode 机场代码
     * @para userId 用户id
     * @return 分页结果
     */
    public String getProductAndServiceList(JSONObject request) {

        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", request.getString("client_id"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

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

        return JSON.toJSONString(new LZResult<>(eqr), SerializerFeature.WriteMapNullValue);

    }

    /**
     * 服务查询列表 - 设置默认
     * @para params ids
     * @return 成功还是失败
     */

    public String setServDefault(JSONObject request){

        LZResult<String> result = new LZResult<>();
        try {
            JSONArray param = JSON.parseArray(request.getString("data"));
            List<Long> servIds = JSON.parseArray(param.toJSONString(), Long.class);
            Long userId = request.getLong("user-id");
            String airportCode =  request.getString("client-id");

            //删除取消默认的服务厅
            servDefaultMapper.deleteServDefault(request.getLong("employeeId"), airportCode, ListUtil.List2String(servIds));

            //记住已选择的 默认服务厅
            if(!CollectionUtils.isEmpty(servIds)){
                ServDefault servDefault = new ServDefault();
                servDefault.setEmployeeId(userId);
                servDefault.setAirportCode(airportCode);
                for (Long servId : servIds){
                    servDefault.setServId(servId);
                    servDefaultMapper.insertServDefault(servDefault);
                }
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }

    public String serviceTypeAllocationList(JSONObject request){

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int count = serviceTypeAllocationMapper.getListCount(param);
        BasePagination<Map<String, Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ServiceTypeAllocation> serviceTypeAllocationList = serviceTypeAllocationMapper.getListByConidition(queryCondition);
        PaginationResult<ServiceTypeAllocation> eqr = new PaginationResult<>(count, serviceTypeAllocationList);
        return JSON.toJSONString(new LZResult<>(eqr));

    }

    /**
     * 服务类型配置 - 服务大类下拉框 数据
     * @return 服务菜单列表
     */
    public String getServiceMenuList(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));

        Map<String,Object> params = new HashMap<>();
        params.put("airportCode",param.get("airportCode"));
        List<ServiceTypeAllocation> result = serviceTypeAllocationMapper.getServiceMenuList(param);
        for(ServiceTypeAllocation serviceTypeAllocation : result){
            params.put("category",serviceTypeAllocation.getCategory());
            List<Dropdownlist> out = serviceTypeAllocationMapper.getServiceTypeDropdownList(params);
            serviceTypeAllocation.setServiceTypeList(out);
        }
        return JSON.toJSONString(new LZResult<>(result));

    }

    /**
     * 服务类型配置 - 服务大类下拉框 数据
     * @return 服务大类列表
     */
    public String getServiceCategoryDropdownList(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));
        return JSON.toJSONString(new LZResult<>(serviceTypeAllocationMapper.getServiceCategoryDropdownList(param)));

    }

    /**
     * 服务类型配置 - 根据category查询
     * @para category 服务大类
     * @return 服务类别列表
     */
    public String getServiceTypeDropdownList(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));
        param.put("category",request.getString("category"));
        return JSON.toJSONString(new LZResult<>(serviceTypeAllocationMapper.getServiceTypeDropdownList(param)));

    }

    /**
     * 服务类型配置 - 根据id查询
     * @para id 服务类型配置id
     * @return 服务名称列表
     */
    public String getServiceNameDropdownList(JSONObject request) {

        Map<String,Object> param = new HashMap<>();
        param.put("airportCode",request.getString("client_id"));
        param.put("id",request.getLong("id"));
        return JSON.toJSONString(new LZResult<>(servMapper.getServiceNameDropdownList(param)));

    }
}
