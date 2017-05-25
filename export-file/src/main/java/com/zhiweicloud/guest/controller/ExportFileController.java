package com.zhiweicloud.guest.controller;

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
        @HeaderParam("client_id") String airportCode,
        @HeaderParam("user_id") Long userId,
        @Context HttpServletResponse response) {
        try {
            exportFileService.exportExcel(orderCheckDetail, airportCode,userId, response);
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
        @BeanParam final CheckQueryParam checkQueryParam,
        @HeaderParam("client_id") String airportCode,
        @HeaderParam("user_id") Long userId,
        @Context HttpServletResponse response) {
        try {
            exportFileService.exportBill(checkQueryParam, checkQueryParam.getType(), response, userId, airportCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("exportExcelForTrain")
    @Produces("application/x-msdownload;charset=utf8")
    @ApiOperation(value = "导出文件 - 默认Excel", notes = "返回分页结果", httpMethod = "GET", produces = "application/x-msdownload")
    public void exportExcelForTrain(
        @BeanParam final CheckQueryParam checkQueryParam,
        @HeaderParam("client_id") String airportCode,
        @HeaderParam("user_id") Long userId,
        @Context HttpServletResponse response) {
        try {
            exportFileService.exportExcelForTrain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
