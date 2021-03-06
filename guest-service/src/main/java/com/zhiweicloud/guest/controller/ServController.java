package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.ProductServiceType;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.model.ServDefault;
import com.zhiweicloud.guest.model.ServiceDetail;
import com.zhiweicloud.guest.service.ServDefaultService;
import com.zhiweicloud.guest.service.ServService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * ServController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value="服务",description="服务desc", tags={"service"})
public class ServController {
    private static final Logger logger = LoggerFactory.getLogger(ServController.class);

    private final ServService servService;
    private final ServDefaultService servDefaultService;

    @Autowired
    public ServController(ServService servService,ServDefaultService servDefaultService) {
        this.servService = servService;
        this.servDefaultService = servDefaultService;
    }

    /**
     * 服务管理 - 服务列表
     * @param page 起始页
     * @param rows 每页显示数目
     * @param typeId 服务类型配置id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return LZResult 服务详情列表
     */
    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")})
    public String list(@QueryParam(value = "page") Integer page,
                       @QueryParam(value = "rows") Integer rows,
                       @QueryParam(value = "typeId") Long typeId,
                       @HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("typeId", typeId);
        LZResult<PaginationResult<JSONObject>> result = servService.getAll(param, page, rows);
        return JSON.toJSONString(result);
    }

    /**
     * 服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param airportCode 机场代码
     * @param userId 用户id
     * @param params 服务信息
     * @return 成功还是失败
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult save(@ApiParam(value = "service", required = true) @RequestBody String params,
                         @HeaderParam("client-id") String airportCode,
                         @HeaderParam("user-id") Long userId) {
        try {
            Serv serv = new Serv();
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(JSONObject.toJSONString(param.get(0),SerializerFeature.WriteMapNullValue));
            serv.setServId(param00.getLong("servId"));
            serv.setAirportCode(airportCode);
            serv.setCreateUser(userId);
            serv.setUpdateUser(userId);
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
                return LXResult.build(LZStatus.DATA_TRANSFER_ERROR);
            } else {
                if (serviceFieldName != null) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (!serviceFieldName.containsKey(keys.toArray()[i])) {
                            return LXResult.build(LZStatus.DATA_TRANSFER_ERROR);
                        } else {
                            if (param00.getString(keys.toArray()[i].toString()).isEmpty() & !keys.toArray()[i].toString().equals("plateNumber")) {
                                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                            }
                        }
                    }
                }
            }
            if (serv.getServId() == null) {
                if (serv.getAirportCode() == null || serv.getServiceTypeAllocationId() == null || serv.getName() == null) {
                    return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                }
            } else {
                if (serv.getAirportCode() == null || serv.getName() == null) {
                    return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                }
            }

            if (servService.selectByName(serv)) {
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            servService.saveOrUpdate(serv);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 服务管理 - 根据id查询
     * @param airportCode 机场代码
     * @param userId 用户id
     * @param servId 服务id
     * @return 服务详情
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务 - 根据id查询 ", notes = "返回服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "servId", value = "服务id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(@HeaderParam("client-id") String airportCode,
                       @HeaderParam("user-id") Long userId,
                       @QueryParam(value = "servId") Long servId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("servId", servId);
        Serv serv = servService.getById(param);
        JSONObject result = JSON.parseObject(serv.getServiceDetail());
        result.put("servId", serv.getServId());
        result.put("airportCode", serv.getAirportCode());
        result.put("serviceTypeAllocationId", serv.getServiceTypeAllocationId());
        result.put("name", serv.getName());
        return result.toJSONString();
    }

    /**
     * 服务管理 - 删除
     * @param airportCode 机场代码
     * @param userId 用户id
     * @param params ids
     * @return 成功或失败
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        try {
            List<Long> ids = params.getData();
            for (Long id : ids) {
                if (servService.selectProductByServiceId(id, airportCode)) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_REF_ERROR));
                }
            }
            servService.deleteById(ids, airportCode, userId);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete serv by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 服务管理 - 根据服务类型配置id和产品id查询服务详情
     * @param page      起始页
     * @param rows      每页显示数目
     * @param typeId    服务类型配置id
     * @param productId 产品id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return 服务详情分页结果
     */
    @GET
    @Path("get-service-list-by-type-and-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 根据服务类型配置id和产品id查询服务详情", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "typeId", value = "服务类型配置id", dataType = "Long", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Long", defaultValue = "39", required = true, paramType = "query")})
    public String getServiceList(@QueryParam(value = "page") Integer page,
                                 @QueryParam(value = "rows") Integer rows,
                                 @QueryParam(value = "typeId") Long typeId,
                                 @QueryParam(value = "productId") Long productId,
                                 @HeaderParam("client-id") String airportCode,
                                 @HeaderParam("user-id") Long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        param.put("typeId", typeId);
        param.put("productId", productId);
        LZResult<PaginationResult<JSONObject>> result = servService.getServiceListByTypeId(param, page, rows);
        return JSON.toJSONString(result);
    }


    /**
     * 根据服务分类查询 服务名，服务人数
     * @param typeId 服务类型配置id
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return 显示服务厅名以及可服务人数
     */
    @GET
    @Path("getServNameAndPositionNum")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "休息室/服务厅管理 - 服务查询", notes = "显示服务厅名以及可服务人数", httpMethod = "GET", produces = "application/json")
    public String getServNameAndPositionNum(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "typeId") Long typeId,
            @QueryParam(value = "isShowAll") boolean isShowAll,
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId) {
        LZResult<List<Serv>> result = new LZResult<>();
        try {
            List<Serv> list = servService.getServNameAndPositionNum(typeId, userId, airportCode, page, rows, isShowAll);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 服务管理 - 获得产品和服务列表
     * @param page      起始页
     * @param rows      每页显示数目
     * @param airportCode 机场代码
     * @param userId 用户id
     * @return 分页结果
     */
    @GET
    @Path("product-and-service-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query")})
    public String getProductAndServiceList(@QueryParam(value = "page") Integer page,
                                           @QueryParam(value = "rows") Integer rows,
                                           @HeaderParam("client-id") String airportCode,
                                           @HeaderParam("user-id") Long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("airportCode", airportCode);
        LZResult<PaginationResult<ProductServiceType>> result = servService.getProductAndServiceList(param, page, rows);

        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 服务查询列表 - 设置默认
     * @param params ids
     * @return 成功还是失败
     */
    @POST
    @Path(value="setDefaultRoom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value = "服务查询列表 - 设置默认", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public String setServDefault(
            @RequestBody RequsetParams<Long> params,
            @Context final HttpHeaders headers){
        LZResult<String> result = new LZResult<>();
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode =  headers.getRequestHeaders().getFirst("client-id");
            List<Long> servIds = params.getData();

            //删除取消默认的服务厅
            servDefaultService.deleteServDefault(userId, airportCode, ListUtil.List2String(servIds));

            //记住已选择的 默认服务厅
            if(!CollectionUtils.isEmpty(servIds)){
                ServDefault servDefault = new ServDefault();
                servDefault.setEmployeeId(userId);
                servDefault.setAirportCode(airportCode);
                for (Long servId : servIds){
                    servDefault.setServId(servId);
                    servDefaultService.insertServDefault(servDefault);
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





}
