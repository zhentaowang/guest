package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.Pagination;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.FlightCenterApiPoMapper;
import com.zhiweicloud.guest.mapper.SourceApiPoMapper;
import com.zhiweicloud.guest.po.SourceApiPo;
import com.zhiweicloud.guest.pojo.ApiQueryPojo;
import com.zhiweicloud.guest.pojo.FlightCenterApiPojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SourceService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/6/9 17:44
 * @author tiecheng
 */
@Service
public class ApiService {

    private static final Log log = LogFactory.getLog(ApiService.class);

    @Autowired
    private SourceApiPoMapper sourceApiPoMapper;

    @Autowired
    private FlightCenterApiPoMapper flightCenterApiPoMapper;

    /**
     * 查询第三方数据来源下拉菜单
     * @param request
     * @return
     */
    public String querySourceDropDownList(JSONObject request) {
        FlightCenterResult<List<String>> result = new FlightCenterResult<>();
        try {
            result.setData(sourceApiPoMapper.selectSourceDropDownList());
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    public String querySourceApiByNameAndDate(JSONObject request) {
        FlightCenterResult<List<SourceApiPo>> result = new FlightCenterResult<>();
        Map<String, Object> params = new HashMap<>();
        try {
            String sourceName = request.getString("sourceName");
            Date startDate = request.getDate("startDate");
            Date endDate = request.getDate("endDate");

            params.put("sourceName", sourceName);
            params.put("startDate", startDate);
            params.put("endDate", endDate);

            result.setData(sourceApiPoMapper.selectSourceApiByNameAndDate(params));
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询第三方接口请求数量
     * @param request
     * @return
     */
    public String countForSourceApi(JSONObject request) {
        FlightCenterResult<Integer> result = new FlightCenterResult<>();
        try {
            ApiQueryPojo apiQueryPojo  = JSON.parseObject(request.toJSONString(), ApiQueryPojo.class);
            result.setData(sourceApiPoMapper.countByCondition(apiQueryPojo));
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询第三方接口请求信息（分页）
     * @param request
     * @return
     */
    public String querySourceApis(JSONObject request) {
        FlightCenterResult<List<SourceApiPo>> result = new FlightCenterResult<>();
        try {
            ApiQueryPojo apiQueryPojo = new ApiQueryPojo();
            apiQueryPojo.setApiName(request.getString("apiName"));
            apiQueryPojo.setSourceName(request.getString("sourceName"));
            apiQueryPojo.setInvokeState(request.getString("state"));
            apiQueryPojo.setInvokeResult(request.getString("result"));
            apiQueryPojo.setStartDate(request.getDate("startDate"));
            apiQueryPojo.setEndDate(request.getDate("endDate"));

            List<SourceApiPo> list;
            Integer page = request.getInteger("page"); // 页码
            if (page != null && page > 0) {
                Integer len = request.getInteger("len"); // 分页长度
                len = len == null ? 10 : len;
                list = sourceApiPoMapper.selectsByConditionForPage(apiQueryPojo, page - 1, len);
            }else {
                list = sourceApiPoMapper.selects(apiQueryPojo);
            }
            result.setData(list);
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询航班中心接口请求数量
     * @param request
     * @return
     */
    public String countForFlightCenterApi(JSONObject request) {
        FlightCenterResult<Integer> result = new FlightCenterResult<>();
        try {
            ApiQueryPojo apiQueryPojo  = JSON.parseObject(request.toJSONString(), ApiQueryPojo.class);
            result.setData(flightCenterApiPoMapper.countByCondition(apiQueryPojo));
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询航班中心接口请求信息（分页）
     * @param request
     * @return
     */
    public String queryFlightCenterApis(JSONObject request) {
        FlightCenterResult<List<FlightCenterApiPojo>> result = new FlightCenterResult<>();
        try {
            ApiQueryPojo apiQueryPojo = new ApiQueryPojo();
            apiQueryPojo.setApiName(request.getString("apiName"));
            apiQueryPojo.setCustomerId(request.getLong("customerId"));
            apiQueryPojo.setCustomerName(request.getString("customerName"));
            apiQueryPojo.setInvokeState(request.getString("state"));
            apiQueryPojo.setInvokeResult(request.getString("result"));
            apiQueryPojo.setStartDate(request.getDate("startDate"));
            apiQueryPojo.setEndDate(request.getDate("endDate"));

            List<FlightCenterApiPojo> flightCenterApiPos;
            Integer page = request.getInteger("page"); // 页码
            if (page != null && page > 0) {
                Integer len = request.getInteger("len"); // 分页长度
                len = len == null ? 10 : len;
                flightCenterApiPos = flightCenterApiPoMapper.selectsByConditionForPage(apiQueryPojo,page - 1,len);
            }else {
                flightCenterApiPos = flightCenterApiPoMapper.selects(apiQueryPojo);
            }
            result.setData(flightCenterApiPos);
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询航班中心接口请求信息（分页，数据 + 总数）
     * @param request
     * @return
     */
    public String querySourceApisPage(JSONObject request) {
        FlightCenterResult result = new FlightCenterResult<>();
        Pagination pagination = new Pagination<>();
        try {
            ApiQueryPojo apiQueryPojo = new ApiQueryPojo();
            apiQueryPojo.setApiName(request.getString("apiName"));
            apiQueryPojo.setCustomerId(request.getLong("customerId"));
            apiQueryPojo.setCustomerName(request.getString("customerName"));
            apiQueryPojo.setInvokeState(request.getString("state"));
            apiQueryPojo.setInvokeResult(request.getString("result"));
            apiQueryPojo.setStartDate(request.getDate("startDate"));
            apiQueryPojo.setEndDate(request.getDate("endDate"));

            List<SourceApiPo> sourceApiPos;
            Integer page = request.getInteger("page"); // 页码
            Integer len = request.getInteger("len"); // 分页长度
            if (page == null || page <= 0) {
                page = 1;
            }
            if (len == null || len <= 0) {
                len = 10;
            }
            sourceApiPos = sourceApiPoMapper.selectsByConditionForPage(apiQueryPojo,page - 1,len);
            int count = sourceApiPoMapper.countByCondition(apiQueryPojo);
            pagination.setTotal(count);
            pagination.setRows(sourceApiPos);
            result.setData(pagination);
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据条件查询航班中心接口请求信息（分页，数据 + 总数）
     * @param request
     * @return
     */
    public String queryFlightCenterApisPage(JSONObject request) {
        FlightCenterResult result = new FlightCenterResult<>();
        Pagination pagination = new Pagination<>();
        try {
            ApiQueryPojo apiQueryPojo = new ApiQueryPojo();
            apiQueryPojo.setApiName(request.getString("apiName"));
            apiQueryPojo.setCustomerId(request.getLong("customerId"));
            apiQueryPojo.setCustomerName(request.getString("customerName"));
            apiQueryPojo.setInvokeState(request.getString("state"));
            apiQueryPojo.setInvokeResult(request.getString("result"));
            apiQueryPojo.setStartDate(request.getDate("startDate"));
            apiQueryPojo.setEndDate(request.getDate("endDate"));

            List<FlightCenterApiPojo> flightCenterApiPojos;
            Integer page = request.getInteger("page"); // 页码
            Integer len = request.getInteger("len"); // 分页长度
            if (page == null || page <= 0) {
                page = 1;
            }
            if (len == null || len <= 0) {
                len = 10;
            }
            flightCenterApiPojos = flightCenterApiPoMapper.selectsByConditionForPage(apiQueryPojo,page - 1,len);
            int count = flightCenterApiPoMapper.countByCondition(apiQueryPojo);
            pagination.setTotal(count);
            pagination.setRows(flightCenterApiPojos);
            result.setData(pagination);
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setData(null);
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
        }
        return JSON.toJSONString(result);
    }

}
