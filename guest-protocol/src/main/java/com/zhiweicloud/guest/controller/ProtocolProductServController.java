package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.model.ProtocolProductServ;
import com.zhiweicloud.guest.service.ProtocolProductServService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/8.
 */
@Component
@Path("/")
@Api(value="协议产品服务",description="协议产品服务desc ", tags={"ProtocolProductServ"})
public class ProtocolProductServController {

    @Autowired
    private ProtocolProductServService protocolProductServService;

    /**
     * 根据服务servId 查询 协议产品服务列表
     * @param servId
     * @param headers
     * @return
     */
    @GET
    @Path("getProtocolProductServByServId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "协议产品服务 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String getProtocolProductServByServId (@QueryParam(value = "servId") Long servId,
                                                        @Context final HttpHeaders headers){
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        LZResult<List<ProtocolProductServ>> result = new LZResult<>();
        try {
            List<ProtocolProductServ> proServList = protocolProductServService.getProtocolProductServiceByServiceId(servId, airportCode);
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(proServList);
        }catch (Exception e){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }
}
