package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.mapper.ProductMapper;
import com.zhiweicloud.guest.mapper.ProductServiceTypeMapper;
import com.zhiweicloud.guest.model.Product;
import com.zhiweicloud.guest.model.ProductServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zhengyiyin on 2017/2/22.
 */
@Service
public class ProductService implements IBusinessService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductServiceTypeMapper productServiceTypeMapper;

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }

        switch (operation) {
            case "list":
                success = list(request);
                break;
            case "addProduct":
                success = addProduct(request);
                break;
            case "viewEdit":
                success = viewEdit(request);
                break;
            case "getServiceMenuList":
                success = getServiceMenuList(request);
                break;
            case "deleteById":
                success = deleteById(request);
                break;
            default:
                break;
        }

        return success;
    }

    /**
     * 新增或者修改产品 modified on 2017/5/5 by zhengyiyin
     * @throws Exception
     */
    public String addProduct(JSONObject request){
        //获取参数
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        JSONArray jsonArray = JSON.parseArray(request.getString("data"));
        Product product = JSON.toJavaObject(jsonArray.getJSONObject(0), Product.class);

        LZResult<String> result = new LZResult<>();
        //判断产品是否为空
        if (product == null || StringUtils.isEmpty(product.getProductName())) {
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
        //判断产品名是否重复
        if (selectByName(airportCode, product.getProductName(), product.getProductId()) == true) {
            result.setMsg(LZStatus.REPNAM.display());
            result.setStatus(LZStatus.REPNAM.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }
        product.setAirportCode(airportCode);

        //保存产品
        List<Long> serviceTypeIds = product.getServiceTypeIds();
        if (product.getProductId() != null) {
            product.setUpdateUser(userId);
            productMapper.updateProduct(product);

            //修改时，需要删除未选择的服务id
            productServiceTypeMapper.deleteProductServiceType(product.getProductId(),product.getAirportCode(),
                    product.getUpdateUser(), ListUtil.List2String(serviceTypeIds));
        } else {

            //插入product
            product.setCreateUser(userId);
            productMapper.insertProduct(product);
        }

        //保存新选择的服务
        if(!CollectionUtils.isEmpty(serviceTypeIds)){
            ProductServiceType temp = new ProductServiceType();
            temp.setAirportCode(product.getAirportCode());
            temp.setProductId(product.getProductId());
            temp.setCreateUser(userId);
            for (Long serId : serviceTypeIds) {
                temp.setServiceTypeId(serId);
                productServiceTypeMapper.insertProductServiceType(temp);
            }
        }

        return JSON.toJSONString(LXResult.success());

    }

    /**
     * 逻辑删除产品（现在不能删除产品） modified on 2017/5/5 by zhengyiyin
     * @throws Exception
     */
    public String deleteById(JSONObject request) {
        String airportCode = request.getString("client_id");
        Long userId = request.getLong("user_id");
        try {
            Product tempProduct = new Product();
            tempProduct.setAirportCode(airportCode);
            tempProduct.setUpdateUser(userId);
            tempProduct.setIsDeleted(Constant.MARK_AS_DELETED);

            ProductServiceType productServiceType = new ProductServiceType();
            productServiceType.setAirportCode(airportCode);

            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            List<Long> ids = JSON.parseArray(jsonArray.toJSONString(),Long.class);
            for(Long productId : ids){
                //逻辑删除产品
                tempProduct.setProductId(productId);
                productMapper.deleteProduct(tempProduct);

                //逻辑删除产品的服务
                productServiceType.setProductId(productId);
                productServiceTypeMapper.deleteProductServiceType(productId,airportCode,userId,null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.error());
        }

        return JSON.toJSONString(LXResult.success());
    }

    /**
     * 查询产品列表 modified on 2017/5/4 by zhengyiyin
     * @return
     */
    public String list(JSONObject request) {
        String airportCode = request.getString("client_id");
        boolean noPage = false;

        if(request != null && null != request.getBoolean("noPage")){
            noPage = request.getBoolean("noPage");
        }

        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }

        int total = productMapper.getListCount(airportCode,null,null);
        List<Product> productList = productMapper.getProductList(airportCode,(page-1)*rows,rows,noPage);
        PaginationResult<Product> eqr = new PaginationResult<>(total, productList);
        LZResult<PaginationResult<Product>> result = new LZResult<>(eqr);
        return JSON.toJSONString(result);
    }


    /**
     * 根据产品id 获取详情 modified on 2017/5/4 by zhengyiyin
     * @return
     */
    public String viewEdit(JSONObject request) {
        String airportCode = request.getString("client_id");
        Long productId = request.getLong("productId");

        LZResult<Product> result = new LZResult<>();
        if (productId == null || StringUtils.isEmpty(airportCode)) {
            result.setMsg(LZStatus.DATA_EMPTY.display());
            result.setStatus(LZStatus.DATA_EMPTY.value());
            result.setData(null);
            return JSON.toJSONString(result);
        }

        try{
            List<Long> tempList = productServiceTypeMapper.queryProductServiceTypes(productId,airportCode);
            Product product = productMapper.queryByIdAndAirCode(productId, airportCode);
            if(product != null){
                product.setServiceTypeIds(tempList);
            }
            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(product);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取服务类型树
     * create by wzt
     * modified on 2017/5/4 by zhengyiyin
     * @return List<ProductServiceType>
     */
    public String getServiceMenuList(JSONObject request){
        Map<String,Object> param = new HashMap();
        param.put("airportCode",request.getString("client_id"));
        param.put("productId",request.getLong("productId"));

        List<ProductServiceType> result = productServiceTypeMapper.getServiceMenuList(param);
        for(int i = 0; i < result.size(); i++){
            param.put("category",result.get(i).getCategory());
            List<ProductServiceType> out = productServiceTypeMapper.getServiceTypeDropdownList(param);
            result.get(i).setServiceTypeList(out);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 产品名称查重
     * @param airportCode
     * @param productName
     * @return boolean
     */
    public boolean selectByName(String airportCode, String productName,Long productId) {
        return productMapper.getListCount(airportCode,productName,productId) > 0;
    }
}
