//package com.zhiweicloud.guest.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.zhiweicloud.guest.APIUtil.LXResult;
//import com.zhiweicloud.guest.APIUtil.LZResult;
//import com.zhiweicloud.guest.APIUtil.LZStatus;
//import com.zhiweicloud.guest.APIUtil.PaginationResult;
//import com.zhiweicloud.guest.common.RequsetParams;
//import com.zhiweicloud.guest.model.Product;
//import com.zhiweicloud.guest.model.ProductServiceType;
//import com.zhiweicloud.guest.service.ProductService;
//import io.swagger.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.ws.rs.*;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.MediaType;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zhengyiyin on 2017/2/22.
// */
//
//@Component
//@Path("/")
//@Api(value="产品管理", description="产品管理desc", tags={"product"})
//public class ProductController {
//    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
//
//    @Autowired
//    private ProductService productService;
//
//    @GET
//    @Path(value = "list")
//    @Produces("application/json;charset=utf8")
//    @ApiOperation(value = "产品列表 - 分页查询", notes = "返回分页结果")
//    public String list(
//            @DefaultValue("1") @Value("起始页") @QueryParam(value = "page") Integer page,
//            @DefaultValue("10") @QueryParam(value = "rows") Integer rows,
//            @QueryParam("noPage") boolean noPage,
//            @HeaderParam("client-id") String airportCode,
//            @HeaderParam("user-id") Long userId) {
//
//        LZResult<PaginationResult<Product>> result = new LZResult<>();
//        if(StringUtils.isEmpty(airportCode)){
//            result.setMsg(LZStatus.ERROR.display());
//            result.setStatus(LZStatus.ERROR.value());
//            result.setData(null);
//            return JSON.toJSONString(result);
//        }
//        result = productService.getAll(airportCode, page, rows, noPage);
//        return JSON.toJSONString(result);
//
//    }
//
//    /**
//     * 产品配置管理 - 根据id查询
//     *
//     * @param productId
//     * @return
//     */
//    @GET
//    @Path(value = "viewEdit")
//    @Produces("application/json;charset=utf8")
//    @ApiOperation(value = "产品配置 - 根据id查询 ", notes = "返回产品信息")
//    public String viewEdit(ContainerRequestContext request,
//                           @QueryParam(value = "productId") Long productId,
//                           @HeaderParam("client-id") String airportCode) {
//        LZResult<Product> result = new LZResult<>();
//        if (productId == null || StringUtils.isEmpty(airportCode)) {
//            result.setMsg(LZStatus.DATA_EMPTY.display());
//            result.setStatus(LZStatus.DATA_EMPTY.value());
//            result.setData(null);
//            return JSON.toJSONString(result);
//        }
//        try {
//            Product guestOrder = productService.getById(productId, airportCode);
//            result.setMsg(LZStatus.SUCCESS.display());
//            result.setStatus(LZStatus.SUCCESS.value());
//            result.setData(guestOrder);
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setMsg(LZStatus.ERROR.display());
//            result.setStatus(LZStatus.ERROR.value());
//            result.setData(null);
//        }
//        return JSON.toJSONString(result);
//    }
//
//    /**
//     * 产品管理 - 新增or更新
//     * @param params
//     * @return
//     */
//    @POST
//    @Path(value="addProduct")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces("application/json;charset=utf-8")
//    @ApiOperation(value = "产品管理 - 新增/修改", notes = "返回成功还是失败", httpMethod = "POST", produces = "application/json")
//    public String addProduct(
//            @ApiParam(value = "product", required = true)
//            @RequestBody RequsetParams<Product> params,
//            @HeaderParam("client-id") String airportCode,
//            @HeaderParam("user-id") Long userId){
//        LZResult<String> result = new LZResult<>();
//        try {
//            Product product = null;
//            if (!CollectionUtils.isEmpty(params.getData())) {
//                product = params.getData().get(0);
//            }
//            if (product == null || StringUtils.isEmpty(product.getProductName())) {
//                result.setMsg(LZStatus.DATA_EMPTY.display());
//                result.setStatus(LZStatus.DATA_EMPTY.value());
//                result.setData(null);
//                return JSON.toJSONString(result);
//            }
//            if (productService.selectByName(airportCode, product.getProductName(), product.getProductId()) == true) {
//                result.setMsg(LZStatus.REPNAM.display());
//                result.setStatus(LZStatus.REPNAM.value());
//                result.setData(null);
//                return JSON.toJSONString(result);
//            }
//            product.setAirportCode(airportCode);
//            productService.saveOrUpdate(product,userId);
//            result.setMsg(LZStatus.SUCCESS.display());
//            result.setStatus(LZStatus.SUCCESS.value());
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setMsg(LZStatus.ERROR.display());
//            result.setStatus(LZStatus.ERROR.value());
//        }
//        result.setData(null);
//        return JSON.toJSONString(result);
//    }
//
//    @POST
//    @Path(value = "deleteProduct")
//    @Produces("application/json;charset=utf8")
//    @ApiOperation(value = "产品管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
//    public String deleteProduct(@Context final HttpHeaders headers,
//            @RequestBody RequsetParams<Long> params,
//            @HeaderParam("client-id") String airportCode,
//            @HeaderParam("user-id") Long userId) {
//        try {
//            List<Long> ids = params.getData();
//            productService.deleteById(ids,userId,airportCode);
//            return JSON.toJSONString(LXResult.success());
//        } catch (Exception e) {
//            logger.error("delete product by productId error", e);
//            return JSON.toJSONString(LXResult.error());
//        }
//    }
//
//    /**
//     * 产品管理 - 根据产品id查询服务类别树
//     * @param productId
//     * @return
//     */
//    @GET
//    @Path("get-service-type-tree-by-product-id")
//    @Produces("application/json;charset=utf8")
//    @ApiOperation(value = "产品管理 - 根据产品id查询服务类别树 ", notes = "返回服务类别树", httpMethod = "GET", produces = "application/json")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Long", defaultValue = "16", required = true, paramType = "query")
//    })
//    public String getServiceTypeTreeByProductId(@Context final HttpHeaders headers,
//                                                @QueryParam(value = "productId") Long productId,
//                                                @HeaderParam("client-id") String airportCode) {
//        Map<String,Object> param = new HashMap();
//        param.put("airportCode",airportCode);
//        param.put("productId",productId);
//        List<ProductServiceType> serviceMenuList = productService.getServiceMenuList(param);
//        return JSON.toJSONString(new LZResult<>(serviceMenuList));
//    }
//
//
//
//
//}
