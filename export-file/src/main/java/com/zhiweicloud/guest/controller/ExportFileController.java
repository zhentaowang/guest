package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.service.ExportFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Map;

/**
 * ExportFileController.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/22 15:31 
 * @author tiecheng
 */
@Component
@Path("/")
@Api(value = "导出文件", description = "", tags = {"导出文件"})
public class ExportFileController {

    @Autowired
    private ExportFileService exportFileService;

    @Autowired
    private MyService.Iface client;

    /**
     * 导出文件
     * Excel
     *
     * @param orderCheckDetail 参数
     * @param airportCode      机场码
     * @param userId           用户ID
     * @return
     */
    @GET
    @Path("exportFile")
    @Produces("application/x-msdownload;charset=utf8")
    @ApiOperation(value = "导出文件 - 默认Excel", notes = "返回分页结果", httpMethod = "GET", produces = "application/x-msdownload")
    public void exportFile(
        @BeanParam final OrderCheckDetail orderCheckDetail,
        @HeaderParam("client-id") String airportCode,
        @HeaderParam("user-id") Long userId,
        @Context HttpServletResponse response) {
        try {
            // 获取数据 -- 服务间调用
            Map result = null;
            JSONObject params = new JSONObject();
            params.put("client_id", airportCode);
            params.put("user_id", userId);
            params.put("queryCustomerId", orderCheckDetail.getQueryCustomerId());
            params.put("queryProtocolType",orderCheckDetail.getQueryProtocolType());
            params.put("queryProtocolId",orderCheckDetail.getQueryProtocolId());
            params.put("queryProductName",orderCheckDetail.getQueryProductName());
            Response re = ClientUtil.clientSendData(client, "businessService", params);
            if (re !=null && re.getResponeCode().getValue() == 200) {
                result = ByteBufferUtil.convertByteBufferToJSON(re.getResponseJSON());
            }
            if (result != null) {
                exportFileService.exportExcel(orderCheckDetail, result, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出账单
     * Excel
     *
     * @param airportCode 机场码
     * @param userId      用户ID
     *         类型【firstClass:头等舱账单,frequentFlyer:常旅客账单,airChina:国际航空账单,chinaSouthernAirlines:南方航空账单】
     * @return
     */
    @GET
    @Path("exportBill")
    @Produces("application/x-msdownload;charset=utf8")
    @ApiOperation(value = "导出文件 - 默认Excel", notes = "返回分页结果", httpMethod = "GET", produces = "application/x-msdownload")
    public void exportBill(
        @HeaderParam("client-id") String airportCode,
        @HeaderParam("user-id") Long userId,
        @BeanParam final CheckQueryParam checkQueryParam,
        @Context HttpServletResponse response) {
        try {
            exportFileService.exportBill(checkQueryParam, checkQueryParam.getType(), response, userId, airportCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
