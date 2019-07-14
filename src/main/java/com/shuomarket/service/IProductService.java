package com.shuomarket.service;

import com.shuomarket.common.ServerResponse;
import com.shuomarket.pojo.Product;
import com.shuomarket.vo.ProductDetailVo;

/**
 * Created by Shawn.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

}
