package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.mapper.ProductMapper;
import com.zhiweicloud.guest.mapper.ProductServiceTypeMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Product;
import com.zhiweicloud.guest.model.ProductServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zhengyiyin on 2017/2/22.
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductServiceTypeMapper productServiceTypeMapper;

    /**
     * 新增或者修改产品
     * @param product
     * @throws Exception
     */
    public void saveOrUpdate(Product product ,Long userId) throws Exception {

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

    }

    /**
     * 逻辑删除产品
     * @param ids
     * @param userId
     * @param airportCode
     * @throws Exception
     */
    public void deleteById(List<Long> ids, Long userId ,String airportCode) throws Exception {
        Product tempProduct = new Product();
        tempProduct.setAirportCode(airportCode);
        tempProduct.setIsDeleted(Constant.MARK_AS_DELETED);
        tempProduct.setUpdateUser(userId);

        ProductServiceType productServiceType = new ProductServiceType();
        productServiceType.setAirportCode(airportCode);
        for(Long productId : ids){
            //逻辑删除产品
            tempProduct.setProductId(productId);
            productMapper.deleteProduct(tempProduct);

            //逻辑删除产品的服务
            productServiceType.setProductId(productId);
            productServiceTypeMapper.deleteProductServiceType(productId,airportCode,userId,null);
        }


    }

    /**
     * 查询产品列表
     * @param airportCode
     * @param page
     * @param rows
     * @return
     */
    public LZResult<PaginationResult<Product>> getAll(String airportCode, Integer page, Integer rows,boolean noPage) {

        int total = productMapper.getListCount(airportCode);
        List<Product> productList = productMapper.getProductList(airportCode,(page-1)*rows,rows,noPage);
        PaginationResult<Product> eqr = new PaginationResult<>(total, productList);
        LZResult<PaginationResult<Product>> result = new LZResult<>(eqr);
        return result;
    }


    /**
     * 根据产品id 获取详情
     * @param productId
     * @param airportCode
     * @return
     */
    public Product getById(Long productId, String airportCode) {
        List<Long> tempList = productServiceTypeMapper.queryProductServiceTypes(productId,airportCode);
        Product product = productMapper.queryByIdAndAirCode(productId, airportCode);
        if(product != null){
            product.setServiceTypeIds(tempList);
        }
        return product;
    }

    /**
     * 获取服务类型树
     * @param param
     * @return List<ProductServiceType>
     */
    public List<ProductServiceType> getServiceMenuList(Map<String,Object> param){
        List<ProductServiceType> result = productServiceTypeMapper.getServiceMenuList(param);
        for(int i = 0; i < result.size(); i++){
            param.put("category",result.get(i).getCategory());
            List<ProductServiceType> out = productServiceTypeMapper.getServiceTypeDropdownList(param);
            result.get(i).setServiceTypeList(out);
        }
        return result;
    }
}
