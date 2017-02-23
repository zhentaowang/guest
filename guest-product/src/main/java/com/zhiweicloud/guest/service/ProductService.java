package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.ProductMapper;
import com.zhiweicloud.guest.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by zhengyiyin on 2017/2/22.
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 新增或者修改产品
     * @param product
     * @throws Exception
     */
    public void saveOrUpdate(Product product) throws Exception {
        if (product.getProductId() != null) {
            product.setUpdateTime(new Date());
            productMapper.updateProduct(product);
        } else {
            product.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
            product.setCreateTime(new Date());
            productMapper.insert(product);
        }

    }

    /**
     * 逻辑删除产品
     * @param ids
     * @param userId
     * @param airportCode
     * @throws Exception
     */
    //@TODO: userId 类型要改
    public void deleteById(List<Long> ids, Long userId ,String airportCode) throws Exception {
        Product tempProduct = new Product();
        tempProduct.setAirportCode(airportCode);
        tempProduct.setIsDeleted(Constant.MARK_AS_DELETED);
        //          tempProduct.setUpdateUser(userId);
        for(Long productId : ids){
            tempProduct.setProductId(productId);
            productMapper.deleteProduct(tempProduct);
        }


    }

    /**
     * 查询产品列表
     * @param airportCode
     * @param page
     * @param rows
     * @return
     */
    public LZResult<PaginationResult<Product>> getAll(String airportCode, Integer page, Integer rows) {
        Product productParam = new Product();
        productParam.setAirportCode(airportCode);
        productParam.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
        int total = productMapper.selectCount(productParam);

        List<Product> productList = productMapper.getProductList(airportCode,(page-1)*rows,page*rows);
        PaginationResult<Product> eqr = new PaginationResult<>(total, productList);
        LZResult<PaginationResult<Product>> result = new LZResult<>(eqr);
        return result;
    }

    public Product getById(Long productId, String airportCode) {
        return productMapper.queryByIdAndAirCode(productId, airportCode);
    }
}
