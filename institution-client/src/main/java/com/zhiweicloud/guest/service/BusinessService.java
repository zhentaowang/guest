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
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ThriftClientUtils;
import com.zhiweicloud.guest.mapper.InstitutionClientMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * InstitutionClientMapper.java
 * Copyright(C) 2017 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
 */
@Service
@Api(value = "客户管理", description = "", tags = {"客户管理"})
public class BusinessService implements IBusinessService {

    private final InstitutionClientMapper institutionClientMapper;

    @Autowired
    public BusinessService(InstitutionClientMapper institutionClientMapper) {
        this.institutionClientMapper = institutionClientMapper;
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
            case "saveOrUpdate":
                success = save(request);
                break;
            case "view":
                success = view(request);
                break;
            case "delete":
                success = delete(request);
                break;
            case "queryInstitutionClientDropdownList":
                success = queryInstitutionClientDropdownList(request);
                break;
            case "getInstitutionType":
                success = getInstitutionType(request);
                break;
            default:
                break;
        }

        return success;
    }

    /**
     * 客户列表-分页查询
     * @param request
     * @return
     */
    public String list(JSONObject request) {
        InstitutionClient param = new InstitutionClient();
        param.setNo(request.getString("no"));
        param.setName(request.getString("name"));
        param.setType(request.getString("type"));
        param.setAirportCode(request.getString("client_id"));

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");

        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        BasePagination<InstitutionClient> queryCondition = new BasePagination<>(param, new PageModel(page, rows));

        Integer count = institutionClientMapper.getListByConiditionCount(param);

        List<InstitutionClient> institutionClientList = institutionClientMapper.getListByConidition(queryCondition);

        PaginationResult<InstitutionClient> eqr = new PaginationResult<>(count, institutionClientList);
        LZResult<PaginationResult<InstitutionClient>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);
    }

    /**
     * 新增修改客户
     * @param request
     * @return
     */
    public String save(JSONObject request) {
        LZResult<String> result = new LZResult<>();
        try {
            InstitutionClient institutionClient = null;
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            institutionClient = JSON.toJavaObject(jsonArray.getJSONObject(0), InstitutionClient.class);


            Long userId = request.getLong("user_id");
            String airportCode = request.getString("client_id");


            if (institutionClient == null || institutionClient.getName() == null || "".equals(institutionClient.getName())
                    || institutionClient.getEmployeeId() == null || "".equals(institutionClient.getEmployeeId())
                    || institutionClient.getType() == null || "".equals(institutionClient.getType())) {
                result.setMsg(LZStatus.DATA_EMPTY.display());
                result.setStatus(LZStatus.DATA_EMPTY.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
            institutionClient.setAirportCode(airportCode);
            //判断是否重名
            int exists = institutionClientMapper.judgeRepeat(institutionClient);
            if (institutionClient.getInstitutionClientId() != null) {
                institutionClient.setUpdateUser(userId);
                institutionClient.setUpdateTime(new Date());
            } else {
                institutionClient.setCreateUser(userId);
                institutionClient.setCreateTime(new Date());
            }
            if (exists > 0) {
                result.setMsg(LZStatus.REPNAM.display());
                result.setStatus(LZStatus.REPNAM.value());
                result.setData(institutionClient.getName());
                return JSON.toJSONString(result);
            }
            if (institutionClient.getInstitutionClientId() != null) {
                institutionClientMapper.updateByPrimaryKeySelective(institutionClient);
            } else {
                institutionClientMapper.insertSelective(institutionClient);
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(null);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }


    /**
     * 查询客户详情
     * @param request
     * @return
     */
    public String view(JSONObject request) {
        try {
            String airportCode = request.getString("client_id");
            Long institutionClientId = request.getLong("institutionClientId");
            InstitutionClient institutionClient = institutionClientMapper.viewByIdAndAirCode(institutionClientId,airportCode);
            return JSON.toJSONString(new LZResult<>(institutionClient));
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }


    /**
     * 机构客户管理 - 删除
     * {
     * "data": [
     * 6,7,8
     * ]
     * <p>
     * }
     *
     * @return 返回被引用的机构客户ID集合
     */
    public String delete(JSONObject request) {
        LZResult<Object> lzResult = new LZResult();
        try {
            Long userId = Long.valueOf(request.getString("user_id"));
            String airportCode = request.getString("client_id");
            JSONArray jsonarray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonarray.toJSONString(), Long.class);

            List<Long> deleteIds = new ArrayList<>();
            StringBuilder names = new StringBuilder();
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("user_id", userId);
            headerMap.put("client_id", airportCode);
            headerMap.put("operation", "protocolList");
            for (Long id : ids) {
                headerMap.put("institutionClientId", id);
                String s = ThriftClientUtils.invokeRemoteMethodCallBack(headerMap, "guest-protocol");

                JSONObject protocolList = JSON.parseObject(s);
                if (protocolList != null) {
                    JSONArray rows = protocolList.getJSONObject("data").getJSONArray("rows");
                    if (rows.size() > 0) {
                        names.append(institutionClientMapper.viewByIdAndAirCode(id, airportCode).getName());
                        names.append(",");
                    }else {
                        deleteIds.add(id);
                    }
                }
            }

            if (deleteIds.size() > 0){
                Map params = new HashMap();
                params.put("ids",deleteIds);
                params.put("userId", userId);
                params.put("airportCode", airportCode);
                institutionClientMapper.deleteBatchByIdsAndUserId(params);
            }
            if(names != null && names.length() > 0){
                lzResult.setData(names);
                lzResult.setMsg(LZStatus.DATA_REF_ERROR.display());
                lzResult.setStatus(LZStatus.DATA_REF_ERROR.value());
            }else{
                lzResult.setData(null);
                lzResult.setMsg(LZStatus.SUCCESS.display());
                lzResult.setStatus(LZStatus.SUCCESS.value());
            }
        } catch (Exception e) {
            return this.errorMsg(e);
        }
        return JSON.toJSONString(lzResult);
    }


    /**
     * 客户下拉框 数据
     *
     * @return
     */
    public String queryInstitutionClientDropdownList(JSONObject request) {
        try{
            LZResult<Object> result = new LZResult<>();
            String airportCode = request.getString("client_id");
            String name = request.getString("name");
            String no = request.getString("no");
            // 1:南航休息室账单('南方航空股份有限公司','中国国际航空股份有限公司').2:头等舱账单（只带出协议类型为头等舱的客户）. 3:常旅客账单（只带出协议类型为金银卡的客户） ,如果type为空，查询所有客户
            String type = request.getString("type");

            List<Dropdownlist> list = institutionClientMapper.getInstitutionClientDropdownList(airportCode, name, no,type);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
            return JSON.toJSONString(result);
        }catch (Exception e){
            return this.errorMsg(e);
        }
    }


    /**
     * 返回机构类型下拉框
     * @param request
     * @return
     */
    public String getInstitutionType(JSONObject request) {
        LZResult<Object> result = new LZResult<>();
        try {
            String airportCode = request.getString("client_id");
            List<Map> list = institutionClientMapper.queryInstitutionType(airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return this.errorMsg(e);
        }
    }


    /**
     * 统一处理错误信息
     * @param e
     * @return
     */
    private String errorMsg(Exception e){
        e.printStackTrace();
        LZResult result = new LZResult<>();
        result.setMsg(LZStatus.ERROR.display());
        result.setStatus(LZStatus.ERROR.value());
        result.setData(null);
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }
}
