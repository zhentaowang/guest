package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Product;
import com.zhiweicloud.guest.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhengyiyin on 2017/2/22.
 */

@Component
@Path("/")
@Api(value="产品管理", description="产品管理desc", tags={"product"})
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GET
    @Path(value = "list")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品列表 - 分页查询", notes = "返回分页结果")
    public String list(
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
            @QueryParam(value = "userId") Long userId,
            @QueryParam(value="airportCode") String airportCode) {
        //@TODO:根据userId 查页面权限
        LZResult<PaginationResult<Product>> result = productService.getAll(airportCode, page, rows);
        return JSON.toJSONString(result);

    }

    /**
     * 产品管理 - 新增or更新
     * @param params
     * @return
     */
    @POST
    @Path(value="addProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json;charset=utf-8")
    @ApiOperation(value="角色管理 - 新增/修改", notes ="返回成功还是失败")
    public LXResult addProduct(
            @ApiParam(value = "product", required = true)
            @RequestBody RequsetParams<Product> params){
        try{
            Product product = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                product = params.getData().get(0);
            }

            if (product == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            productService.saveOrUpdate(product);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    @POST
    @Path(value = "deleteProduct")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品管理 - 删除", notes = "返回响应结果")
    public String deleteProduct(
            @QueryParam(value = "productId") Long productId,
            @QueryParam(value = "userId") Long userId,
            @QueryParam(value = "airportCode") String airportCode) {
        try {
            productService.deleteById(productId,userId,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete product by productId error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }



}
