package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Serv;
import com.zhiweicloud.guest.service.ServService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@Component
@Path("guest-product")
@Api(value="服务",description="服务desc", tags={"service"})
public class ServController {
    private static final Logger logger = LoggerFactory.getLogger(ServController.class);
    @Autowired
    private ServService servService;

    @GET
    @Path("list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "productCategory", value = "产品品类", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "serviceType", value = "服务类型", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "name", value = "服务名称", dataType = "String", required = false, paramType = "query")})
    public String list( @QueryParam(value = "airportCode") String airportCode,
                        @QueryParam(value = "page") Integer page,
                        @QueryParam(value = "rows") Integer rows,
                        @QueryParam(value = "productCategory") String productCategory,
                        @QueryParam(value = "serviceType") String serviceType,
                        @QueryParam(value = "name") String name) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("productCategory", productCategory);
        param.put("serviceType", serviceType);
        param.put("name",name);
        LZResult<PaginationResult<Serv>> result  = servService.getAll(param,page,rows);
        return JSON.toJSONString(result);
    }

    @GET
    @Path("serv-list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "productTypeAllocationId", value = "产品类型配置id", dataType = "Long", defaultValue = "1",required = true, paramType = "query"),
                    @ApiImplicitParam(name = "protocolId", value = "协议id", dataType = "Long", defaultValue = "1",required = true, paramType = "query"),
                    @ApiImplicitParam(name = "price", value = "服务单价", dataType = "BigDecimal", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "freeRetinueNum", value = "免费随员人数", dataType = "Integer", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "overStaffUnitPrice", value = "超员单价", dataType = "BigDecimal", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "description", value = "价格说明", dataType = "String", required = false, paramType = "query")})
    public  String servList(
            @QueryParam(value = "airportCode") String airportCode,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "productTypeAllocationId") Long productTypeAllocationId,
            @QueryParam(value = "protocolId") Long protocolId,
            @QueryParam(value = "price") BigDecimal price,
            @QueryParam(value = "freeRetinueNum") Integer freeRetinueNum,
            @QueryParam(value = "overStaffUnitPrice") BigDecimal overStaffUnitPrice,
            @QueryParam(value = "description") String description) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("productTypeAllocationId",productTypeAllocationId);
        param.put("protocolId",protocolId);
        LZResult<PaginationResult<Serv>> result  = servService.getServAll(param,page,rows,price,freeRetinueNum,overStaffUnitPrice,description);
        if(result.getData().getTotal() == 0){
            result.setMsg("该类型下所有服务已经被添加");
            result.setStatus(5000);
            return  JSON.toJSONString(result);
        }else{
            return JSON.toJSONString(result);
        }
    }

    /**
     * 服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @POST
    @Path("save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value="服务管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "service", required = true) @RequestBody RequsetParams<Serv> params){
        try{
            Serv serv = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                serv = params.getData().get(0);
            }
            if (serv == null || serv.getProductTypeAllocationId() == null || serv.getName() == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            if(servService.selectByName(serv) == true){
                return LXResult.build(LZStatus.REPNAM.value(), LZStatus.REPNAM.display());
            }
            servService.saveOrUpdate(serv);
            return  LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 服务管理 - 根据id查询
     * @param airportCode
     * @param id
     * @return
     */
    @GET
    @Path("view")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务 - 根据id查询 ", notes = "返回服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "服务id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(@QueryParam(value = "airportCode") String airportCode,
                               @QueryParam(value = "id") Long id
                               ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        Serv serv = servService.getById(param);
        return JSON.toJSONString(new LZResult<>(serv));
    }

    /**
     * 服务管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public String delete(
            @RequestBody RequsetParams<Long> params,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            List<Long> ids = params.getData();
            for(int i = 0; i < ids.size(); i++){
                if(servService.selectProtocolByServiceId(ids.get(i),airportCode) == true){
                    return JSON.toJSONString(LXResult.build(4996, "该服务被协议引用，不能被删除"));
                }
            }
            servService.deleteById(ids,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete serv by ids error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

}
