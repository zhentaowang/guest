package com.zhiweicloud.productmanage.controller;

import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.productmanage.model.Serv;
import com.zhiweicloud.productmanage.service.ServService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Serv.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 13:17:52 Created By wzt
 */
@RestController
@Api(value="服务",description="服务desc ", tags={"service"})
public class ServController {
    private static final Logger logger = LoggerFactory.getLogger(ServController.class);
    @Autowired
    private ServService servService;

    @RequestMapping(value ="/list")
    @ApiOperation(value = "服务管理 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
                    @ApiImplicitParam(name = "productCategory", value = "产品品类", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "serviceType", value = "服务类型", dataType = "String", required = false, paramType = "query"),
                    @ApiImplicitParam(name = "name", value = "服务名称", dataType = "String", required = false, paramType = "query")})
    public LZResult<PaginationResult<Serv>> list(HttpServletRequest request) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",request.getParameter("airportCode"));
        param.put("productCategory", request.getSession().getAttribute("productCategory"));
        param.put("serviceType", request.getParameter("serviceType"));
        param.put("name",request.getParameter("name"));
        LZResult<PaginationResult<Serv>> result  = servService.getAll(param,Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("rows")));
        return result;
    }

    @RequestMapping(value ="/serv-list")
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
    public  LZResult<PaginationResult<Serv>> servList(
            @RequestParam(value = "airportCode", required = true, defaultValue = "LJG") String airportCode,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "productTypeAllocationId", required = true, defaultValue = "1") Long productTypeAllocationId,
            @RequestParam(value = "protocolId", required = true, defaultValue = "1") Long protocolId,
            @RequestParam(value = "price", required = true) BigDecimal price,
            @RequestParam(value = "freeRetinueNum", required = false) Integer freeRetinueNum,
            @RequestParam(value = "overStaffUnitPrice", required = false) BigDecimal overStaffUnitPrice,
            @RequestParam(value = "description", required = false) String description) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("productTypeAllocationId",productTypeAllocationId);
        param.put("protocolId",protocolId);
        LZResult<PaginationResult<Serv>> result  = servService.getServAll(param,page,rows,price,freeRetinueNum,overStaffUnitPrice,description);
        return result;
    }

    /**
     * 服务管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/save-or-update", method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="服务管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "service", required = true) @RequestBody RequsetParams<Serv> params){
        try{
            Serv serv = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                serv = params.getData().get(0);
            }

            if (serv == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
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
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "服务 - 根据id查询 ", notes = "返回服务详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场code", dataType = "String", defaultValue = "LJG", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "服务id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public LZResult<Serv> view(@RequestParam(value = "airportCode", defaultValue = "LJG", required = true) String airportCode,
                               @RequestParam(value = "id", defaultValue = "16", required = true) Long id
                               ) {
        Map<String,Object> param = new HashMap();
        param.put("airportCode",airportCode);
        param.put("id",id);
        Serv serv = servService.getById(param);
        return new LZResult<>(serv);
    }

    /**
     * 服务管理 - 删除
     * @param airportCode
     * @param params ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public LXResult delete(
            @RequestBody RequsetParams<Long> params,
            @RequestParam(value = "airportCode", required = true) String airportCode,HttpServletRequest request) {
        try {
            List<Long> ids = params.getData();
            servService.deleteById(ids,airportCode,Long.parseLong(request.getSession().getAttribute("productTypeAllocationId").toString()));
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete serv by ids error", e);
            return LXResult.error();
        }
    }

}
