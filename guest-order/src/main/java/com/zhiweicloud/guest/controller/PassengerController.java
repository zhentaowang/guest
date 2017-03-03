package com.zhiweicloud.guest.controller;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.service.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/2.
 */
@Component
@Path("/")
@Api(value = "订单客户", description = "passengerdesc ", tags = {"passenger"})
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    /**
     * 身份证号模糊匹配下拉框 数据
     * @return
     */
    @GET
    @Path(value="getIdentityCardDropdownList")
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="乘客身份证模糊匹配下拉框，不重复的字符串",notes="乘客身份证模糊匹配下拉框", httpMethod="GET",produces="application/json",tags={"common:公共接口"})
    public LZResult<List<String>> queryInstitutionClientDropdownList(
            ContainerRequestContext request,
            @QueryParam(value = "identityCard") String identityCard) {
        LZResult<List<String>> result = new LZResult<>();
        String airportCode = request.getHeaders().getFirst("client-id").toString();
        if(airportCode == null){
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
            return result;
        }
        List<String> list = passengerService.getIdentityCardDropdownList(identityCard, airportCode);
        result.setMsg(LZStatus.SUCCESS.display());
        result.setStatus(LZStatus.SUCCESS.value());
        result.setData(list);
        return result;
    }
}