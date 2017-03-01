package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.AuthorizerService;
import com.zhiweicloud.guest.service.ProtocolService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Protocol.java
 * Copyright(C) 2017 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-01-03 13:17:52 Created By wzt
 */
@Component
@Path("/")
@Api(value="协议",description="协议desc ", tags={"protocol"})
public class ProtocolController {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolController.class);
    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private AuthorizerService authorizerService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientName", value = "机构客户名称", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientNo", value = "机构客户编号", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "institutionClientType", value = "机构客户类型", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "name", value = "协议名称", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "no", value = "协议编号", dataType = "String", required = false, paramType = "query")})
    public String list(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "institutionClientName") String institutionClientName,
            @QueryParam(value = "institutionClientNo") String institutionClientNo,
            @QueryParam(value = "institutionClientType") String institutionClientType,
            @QueryParam(value = "name") String name,
            @QueryParam(value = "no") String no) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("institutionClientName",institutionClientName);
        param.put("institutionClientNo",institutionClientNo);
        param.put("institutionClientType",institutionClientType);
        param.put("name",name);
        param.put("no",no);
        LZResult<PaginationResult<Protocol>> result  = protocolService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    /**
     * 协议管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="协议管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "protocol", required = true) @RequestBody String params,
                         @Context final HttpHeaders headers){
        try{
            Protocol protocol = new Protocol();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            JSONArray param= JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            protocol.setProtocolId(param00.getLong("protocolId"));
            protocol.setAirportCode(param00.getString("airportCode"));
            protocol.setInstitutionClientId(param00.getLong("institutionClientId"));
            protocol.setName(param00.getString("name"));
            protocol.setType(param00.getInteger("type"));
            protocol.setReservationNum(param00.getString("reservationNum"));
            protocol.setStartDate(param00.getDate("startDate"));
            protocol.setEndDate(param00.getDate("endDate"));
            protocol.setClearForm(param00.getShort("clearForm"));
            protocol.setRemark(param00.getString("remark"));
            JSONArray authorizerList = param00.getJSONArray("authorizer");
            List<Authorizer> authorizers = new ArrayList<>();
            for(int i = 0; i < authorizerList.size(); i++){
                JSONObject authorizer00 = JSON.parseObject(authorizerList.get(i).toString());
                Authorizer authorizer = new Authorizer();
                authorizer.setAuthorizerId(authorizer00.getLong("authorizerId"));
                authorizer.setAirportCode(authorizer00.getString("airportCode"));
                authorizer.setName(authorizer00.getString("name"));
                authorizer.setCellphone(authorizer00.getString("cellphone"));
                authorizer.setTelephone(authorizer00.getString("telephone"));
                authorizers.add(authorizer);
            }
            protocol.setAuthorizerList(authorizers);
            JSONArray protocolProductList = param00.getJSONArray("protocolProduct");
            List<ProtocolProduct> protocolProducts = new ArrayList<>();
            for(int i = 0; i < protocolProductList.size(); i++){
                JSONObject protocolProduct00 = JSON.parseObject(protocolProductList.get(i).toString());
                ProtocolProduct protocolProduct = new ProtocolProduct();
                protocolProduct.setProtocolProductId(protocolProduct00.getLong("protocolProductId"));
                protocolProduct.setAirportCode(protocolProduct00.getString("airportCode"));
                protocolProduct.setProductId(protocolProduct00.getLong("productId"));
                protocolProduct.setProductDesc(protocolProduct00.getString("productDesc"));
                JSONArray protocolProductServiceList = protocolProduct00.getJSONArray("protocolProductService");
                List<ProtocolProductService> protocolProductServices = new ArrayList<>();
                for(int j = 0; j < protocolProductServiceList.size(); j++){
                    JSONObject protocolProductService00 = JSON.parseObject(protocolProductServiceList.get(j).toString());
                    ProtocolProductService protocolProductService = new ProtocolProductService();
                    protocolProductService.setProtocolProductServiceId(protocolProductService00.getLong("protocolProductServiceId"));
                    protocolProductService.setAirportCode(protocolProductService00.getString("airportCode"));
                    protocolProductService.setServiceTypeAllocationId(protocolProductService00.getLong("serviceTypeAllocationId"));
                    protocolProductService.setServiceId(protocolProductService00.getLong("serviceId"));
                    protocolProductService.setIsPricing(protocolProductService00.getBoolean("isPricing"));
                    protocolProductService.setIsPrioritized(protocolProductService00.getBoolean("isPrioritized"));
                    protocolProductService.setIsAvailabled(protocolProductService00.getBoolean("isAvailabled"));
                    protocolProductService00.remove("protocolProductServiceId");
                    protocolProductService00.remove("airportCode");
                    protocolProductService00.remove("serviceTypeAllocationId");
                    protocolProductService00.remove("serviceId");
                    protocolProductService00.remove("isPricing");
                    protocolProductService00.remove("isPrioritized");
                    protocolProductService00.remove("isAvailabled");
                    protocolProductService.setPricingRule(protocolProductService00.toJSONString());
                    Set keys = protocolProductService00.keySet();
                    Map<String,Object> protocolProductFieldName = ProtocolProductDetail.getProtocolProductFieldName(protocolProductService.getServiceTypeAllocationId());
                    if(keys.size() != protocolProductFieldName.size()){
                        return LXResult.build(4995, "传输数据字段错误");
                    }else{
                        if(protocolProductFieldName != null){
                            for(int k = 0; k < keys.size(); k++){
                                if(!protocolProductFieldName.containsKey(keys.toArray()[k])){
                                    return LXResult.build(4995, "传输数据字段错误");
                                }else{
                                    if(protocolProductService00.getString(keys.toArray()[k].toString()).isEmpty()){
                                        return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
                                    }
                                }
                            }
                        }
                    }
                    protocolProductServices.add(protocolProductService);
                }
                protocolProduct.setProtocolProductServiceList(protocolProductServices);
                protocolProducts.add(protocolProduct);
            }
            protocol.setProtocolProductList(protocolProducts);

            if (protocol == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            if (protocol.getName() == null || protocol.getClearForm() == null || protocol.getInstitutionClientId() == null
                    || protocol.getType() == null || protocol.getStartDate() == null || protocol.getEndDate() == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
//            if(protocol.getProtocolId() != null && protocolService.selectOrderByProtocolId(protocol.getProtocolId(),protocol.getAirportCode()) == true){//协议修改
//                if(authorizerService.selectByProtocolId(protocol.getProtocolId(),protocol.getAirportCode()) > protocol.getAuthorizerList().size()){
//                    return LXResult.build(4999, "该协议被订单引用，协议下预约人不能被删除");
//                }
//            }
            if(protocolService.selectByName(protocol) == true){
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            protocolService.saveOrUpdate(protocol);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 协议管理 - 根据id查询
     * @param airportCode
     * @param id
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议 - 根据id查询 ", notes = "返回协议详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "协议id", dataType = "Long", defaultValue = "1", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "airportCode") String airportCode,
                                   @QueryParam(value = "id") Long id
    ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        Protocol protocolServ = protocolService.getById(param);
        return JSON.toJSONString(new LZResult<>(protocolServ));
    }

    /**
     * 协议管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            List<Long> ids = params.getData();
            boolean flame;
            for(int i = 0; i < ids.size(); i++){
                flame = protocolService.selectOrderByProtocolId(ids.get(i),airportCode);
                if(flame == true){
                    return JSON.toJSONString(LXResult.build(4997, "该协议被订单引用，不能被删除"));
                }
            }
            protocolService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete protocol by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

}
