package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.service.ProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@Component
@Path("/")
@Api(value = "系统公共接口", description = "产品品类desc ", tags = {"commonController"})
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ProtocolService protocolService;

    /**
     * 协议表 - 查询协议名称
     *
     * @return
     */
    @GET
    @Path("protocol-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议表 - 查询协议名称", notes = "返回协议名称列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "协议类型:VIP、CIP", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "协议名称", dataType = "String", required = false, paramType = "query")
    })
    public String getProtocolDropdownList(@QueryParam(value = "airportCode") String airportCode,
                                          @QueryParam(value = "type") String type,
                                          @QueryParam(value = "name") String name
    ) {
        List<Dropdownlist> protocolNameList = protocolService.getProtocolDropdownList(airportCode, type, name);
        return JSON.toJSONString(new LZResult<>(protocolNameList));
    }

    /**
     * 协议表 - 查询协议编号
     *
     * @return
     */
    @GET
    @Path("protocol-no-dropdown-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议表 - 查询协议编号", notes = "返回协议编号列表", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "协议类型:VIP、CIP", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "no", value = "协议编号", dataType = "String", required = false, paramType = "query")
    })
    public String getProtocolNoDropdownList(@QueryParam(value = "airportCode") String airportCode,
                                            @QueryParam(value = "type") String type,
                                            @QueryParam(value = "no") String no
    ) {
        List<Dropdownlist> protocolNameList = protocolService.getProtocolNoDropdownList(airportCode, type, no);
        return JSON.toJSONString(new LZResult<>(protocolNameList));
    }


    /**
     * 协议名称name模糊匹配下拉框
     * 或者 根据authorizerId 获取协议下拉框
     * 或者 预约号
     * 或者 预约人
     *
     * @return
     */
    @GET
    @Path(value = "getProtocolNameDropdownList")
    @ApiOperation(value = "系统中用到协议名称下拉框，只包含id，和value的对象", notes = "根据数据字典的分类名称获取详情数据,下拉", httpMethod = "GET", produces = "application/json", tags = {"common:公共接口"})
    @Produces("application/json;charset=utf-8")
    public String getProtocolNameDropdownList(
            @HeaderParam("client-id") String airportCode,
            @HeaderParam("user-id") Long userId,
            @QueryParam(value = "protocolId") Long protocolId,
            @QueryParam(value = "protocolName") String protocolName,
            @QueryParam(value = "protocolType") Long protocolType,
            @QueryParam(value = "authorizerId") Long authorizerId,
            @QueryParam(value = "authorizerName") String authorizerName,
            @QueryParam(value = "reservationNum") String reservationNum) {
        LZResult<List<Map>> result = new LZResult<>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("airportCode", airportCode);
            map.put("protocolId", protocolId);
            map.put("protocolName", protocolName);
            map.put("protocolType", protocolType);//协议类型
            map.put("authorizerId", authorizerId);
            map.put("authorizerName", authorizerName);//预约人
            map.put("reservationNum", reservationNum);//预约号
            List<Map> list = protocolService.getProtocolNameDropdownList(map);

            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            headerMap.put("user-id", userId);
            headerMap.put("client-id", airportCode);


            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("clientId") != null) {
                    paramMap.put("institutionClientId", list.get(i).get("clientId"));
                    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.httpGetRequest("http://institution-client/institution-client/view", headerMap, paramMap));
                    if (jsonObject != null) {
                        JSONObject institutionClientObject = jsonObject.getJSONObject("data");
                        String clientValue = institutionClientObject.get("name").toString();
                        list.get(i).put("clientValue",clientValue);
                    }
                }
            }

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);

    }
}
