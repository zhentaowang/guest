package com.zhiweicloud.guest.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ProtocolTypeEnum;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.service.ProtocolService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
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

    /**
     * 协议管理 - 新增or更新
     * 需要判断name是否重复
     *
     * @param params
     * @return
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
    public LXResult save(@ApiParam(value = "protocol", required = true) @RequestBody String params,
                         @Context final HttpHeaders headers) {
        try {
            Protocol protocol = new Protocol();
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            JSONArray param = JSON.parseObject(params).getJSONArray("data");
            JSONObject param00 = JSON.parseObject(param.get(0).toString());
            protocol.setProtocolId(param00.getLong("protocolId"));
            protocol.setAirportCode(airportCode);
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
            for (int i = 0; i < authorizerList.size(); i++) {
                JSONObject authorizer00 = JSON.parseObject(authorizerList.get(i).toString());
                Authorizer authorizer = new Authorizer();
                authorizer.setAuthorizerId(authorizer00.getLong("authorizerId"));
                authorizer.setAirportCode(protocol.getAirportCode());
                authorizer.setName(authorizer00.getString("name"));
                authorizer.setCellphone(authorizer00.getString("cellphone"));
                authorizer.setTelephone(authorizer00.getString("telephone"));
                authorizers.add(authorizer);
            }
            protocol.setAuthorizerList(authorizers);
            JSONArray protocolProductList = param00.getJSONArray("protocolProduct");
            List<ProtocolProduct> protocolProducts = new ArrayList<>();
            for (int i = 0; i < protocolProductList.size(); i++) {
                JSONObject protocolProduct00 = JSON.parseObject(protocolProductList.get(i).toString());
                ProtocolProduct protocolProduct = new ProtocolProduct();
                protocolProduct.setProtocolProductId(protocolProduct00.getLong("protocolProductId"));
                protocolProduct.setAirportCode(protocol.getAirportCode());
                protocolProduct.setProductId(protocolProduct00.getLong("productId"));
                protocolProduct.setProductDesc(protocolProduct00.getString("productDesc"));
                JSONArray protocolProductServiceList = protocolProduct00.getJSONArray("protocolProductService");
                List<ProtocolProductService> protocolProductServices = new ArrayList<>();
                for (int j = 0; j < protocolProductServiceList.size(); j++) {
                    JSONObject protocolProductService00 = JSON.parseObject(protocolProductServiceList.get(j).toString());
                    ProtocolProductService protocolProductService = new ProtocolProductService();
                    protocolProductService.setProtocolProductServiceId(protocolProductService00.getLong("protocolProductServiceId"));
                    protocolProductService.setAirportCode(protocol.getAirportCode());
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
                    Map<String, Object> protocolProductFieldName = ProtocolProductDetail.getProtocolProductFieldName(protocolProductService.getServiceTypeAllocationId());
                    if (keys.size() != protocolProductFieldName.size()) {
                        return LXResult.build(4995, "传输数据字段错误");
                    } else {
                        if (protocolProductFieldName != null) {
                            for (int k = 0; k < keys.size(); k++) {
                                if (!protocolProductFieldName.containsKey(keys.toArray()[k])) {
                                    return LXResult.build(4995, "传输数据字段错误");
                                } else {
                                    if (protocolProductService00.getString(keys.toArray()[k].toString()).isEmpty()) {
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

//            param00.remove("servId");
//            param00.remove("name");
//            param00.remove("institutionClientId");
//            protocol.setServiceDetail(param00.toJSONString());
//            Set keys = param00.keySet();
//            Map<String,Object> serviceFieldName = ServiceDetail.getServiceFieldName(protocol.getServiceTypeAllocationId());

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
            if (protocolService.selectByName(protocol) == true) {
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            protocolService.saveOrUpdate(protocol);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 协议管理 - 根据id查询
     *
     * @param protocolId
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议 - 根据id查询 ", notes = "返回协议详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "188", required = true, paramType = "query")
    })
    public String view(@Context final HttpHeaders headers,
                       @QueryParam(value = "protocolId") Long protocolId
    ) {
        Map<String, Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode", airportCode);
        param.put("protocolId", protocolId);
        Protocol protocol = protocolService.getById(param);
        return JSON.toJSONString(new LZResult<>(protocol));
    }

    /**
     * 协议管理 - 根据协议产品id查询服务类别树
     *
     * @param protocolProductId
     * @return
     */
    @GET
    @Path("get-service-type-tree-by-protocol-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议管理 - 根据协议产品id查询服务类别树 ", notes = "返回服务类别树", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "protocolProductId", value = "协议产品id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String getServiceMenuList(@Context final HttpHeaders headers,
                                     @QueryParam(value = "protocolProductId") Long protocolProductId) {
        Map<String, Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode", airportCode);
        param.put("protocolProductId", protocolProductId);
        List<ProtocolProductService> serviceMenuList = protocolService.getServiceMenuList(param);
        return JSON.toJSONString(new LZResult<>(serviceMenuList));
    }



        /**
         * 重写协议列表，2017.2.23
         * @param page
         * @param rows
         * @param airportCode
         * @param protocolType
         * @param institutionClientName
         * @param protocolName
         * @return
         */
        @GET
        @Path("protocolList")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 分页查询", notes = "返回分页结果")
        public String protocolList (
                @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
                @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
                @QueryParam(value = "airportCode") String airportCode,
                @QueryParam(value = "protocolType") Integer protocolType,
                @QueryParam(value = "institutionClientName") String institutionClientName,
                @QueryParam(value = "protocolName") String protocolName){
            Protocol protocolParam = new Protocol();
            protocolParam.setAirportCode(airportCode);

            protocolParam.setInstitutionClientName(institutionClientName);
            protocolParam.setName(protocolName);
            if (protocolType != null) {
                protocolParam.setType(protocolType);
            }

            LZResult<PaginationResult<Protocol>> result = protocolService.getProtocolList(protocolParam, page, rows);
            return JSON.toJSONString(result);
        }

        /**
         * 协议类型下拉框
         * @return
         */
        @GET
        @Path("protocolTypeSelect")
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 协议类型下拉框", notes = "返回协议类型")
        public String getProtocolTypeSelect () {
            LZResult<List<Dropdownlist>> result = new LZResult<>();
            List<Dropdownlist> list = new ArrayList<Dropdownlist>();

            for (ProtocolTypeEnum e : ProtocolTypeEnum.values()) {
                Dropdownlist dropdown = new Dropdownlist();
                dropdown.setId(Long.valueOf(e.getTypeValue()));
                dropdown.setValue(e.getTypeName());
                list.add(dropdown);
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);

            return JSON.toJSONString(result);
        }

        /**
         * 协议名称name模糊匹配下拉框
         * 或者 根据authorizerId 获取协议下拉框
         * @return
         */
        @GET
        @Path(value = "getProtocolNameDropdownList")
        @ApiOperation(value = "系统中用到协议名称下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
        public LZResult<List<Dropdownlist>> getProtocolNameDropdownList (
                ContainerRequestContext request,
                @QueryParam(value = "airportCode") String airportCode,
                @QueryParam(value = "name") String name,
                @QueryParam(value = "authorizerId") Long authorizerId){
//        String airportCode = request.getHeaders().getFirst("client-id").toString();
            List<Dropdownlist> list = protocolService.getProtocolNameDropdownList(airportCode, name, authorizerId);
            return new LZResult<>(list);
        }


        /**
         * 重写协议管理 - 删除
         * @param airportCode
         * @param params ids
         * @return
         */
        @POST
        @Path("deleteProtocol")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces("application/json;charset=utf8")
        @ApiOperation(value = "协议管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
        @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
        public String deleteProtocol (
                @RequestBody RequsetParams< Long > params,
                @QueryParam(value = "airportCode") String airportCode){
            try {
                List<Long> ids = params.getData();
                Long userId = 0L;
//          Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id").toString());
//          String airportCode = headers.getRequestHeaders().getFirst("client-id").toString();
                boolean flame;

                for (int i = 0; i < ids.size(); i++) {
                    //调用order应用，根据协议id 查询有无订单关联
                    Map<String, Object> map = new HashMap<>();
                    map.put("protocolId", ids.get(i));
                    Properties p = new Properties();
                    p.load(ProtocolController.class.getClassLoader().getResourceAsStream("application.properties"));

//                String temp = HttpClientUtil.httpGetRequest(p.getProperty("guest.client.queryInstitutionClientDropdownList"), map);
//                System.out.println(temp);
//                byte[] b = temp.getBytes("ISO-8859-1");
                }
                protocolService.deleteById(ids, userId, airportCode);
                return JSON.toJSONString(LXResult.success());
            } catch (Exception e) {
                logger.error("delete protocol by ids error", e);
                return JSON.toJSONString(LXResult.error());
            }
        }


    }