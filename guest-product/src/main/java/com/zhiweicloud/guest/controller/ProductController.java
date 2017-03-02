package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Product;
import com.zhiweicloud.guest.model.ProductServiceType;
import com.zhiweicloud.guest.service.ProductService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String list(ContainerRequestContext request,
            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
            @DefaultValue("10") @QueryParam(value = "rows") Integer rows) {
        //@TODO:根据userId 查页面权限
        String airportCode = request.getHeaders().getFirst("client-id").toString();
        LZResult<PaginationResult<Product>> result = new LZResult<>();
        if(StringUtils.isEmpty(airportCode)){
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
        result = productService.getAll(airportCode, page, rows);
        return JSON.toJSONString(result);

    }

    /**
     * 产品配置管理 - 根据id查询
     *
     * @param productId
     * @return
     */
    @GET
    @Path(value = "viewEdit")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品配置 - 根据id查询 ", notes = "返回产品信息")
    public String viewEdit(ContainerRequestContext request,
                           @QueryParam(value = "productId") Long productId) {
        LZResult<Product> result = new LZResult<>();
        try {
            String airportCode = request.getHeaders().getFirst("client-id").toString();
            if(StringUtils.isEmpty(airportCode)){
                result.setMsg(LZStatus.ERROR.display());
                result.setStatus(LZStatus.ERROR.value());
                result.setData(null);
                return JSON.toJSONString(result);
            }
            Product guestOrder = productService.getById(productId, airportCode);

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(guestOrder);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
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
            @RequestBody RequsetParams<Product> params,
            @Context final HttpHeaders headers){
        try{
            Product product = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                product = params.getData().get(0);
            }

            if (product == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id"));
            String airportCode = headers.getRequestHeaders().getFirst("client-id");
            product.setAirportCode(airportCode);

            productService.saveOrUpdate(product, userId);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    @POST
    @Path(value = "deleteProduct")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    public String deleteProduct(@Context final HttpHeaders headers,
            @RequestBody RequsetParams<Long> params) {
        try {
            Long userId = Long.valueOf(headers.getRequestHeaders().getFirst("user-id").toString());
            String airportCode = headers.getRequestHeaders().getFirst("client-id").toString();
            List<Long> ids = params.getData();
            productService.deleteById(ids,userId,airportCode);
            return JSON.toJSONString(LXResult.success());
        } catch (Exception e) {
            logger.error("delete product by productId error", e);
            return JSON.toJSONString(LXResult.error());
        }
    }

    /**
     * 产品管理 - 根据产品id查询服务类别树
     * @param productId
     * @return
     */
    @GET
    @Path("get-service-type-tree-by-product-id")
    @Produces("application/json;charset=utf8")
    @ApiOperation(value = "产品管理 - 根据产品id查询服务类别树 ", notes = "返回服务类别树", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
    })
    public String view(@Context final HttpHeaders headers,
                                                   @QueryParam(value = "productId") Long productId) {
        Map<String,Object> param = new HashMap();
        String airportCode = headers.getRequestHeaders().getFirst("client-id");
        param.put("airportCode",airportCode);
        param.put("productId",productId);
        List<ProductServiceType> serviceMenuList = productService.getServiceMenuList(param);
        return JSON.toJSONString(new LZResult<>(serviceMenuList));
    }




}
