package com.shuomarket.service;

import com.shuomarket.common.ServerResponse;
import com.shuomarket.vo.CartVo;

/**
 * Created by Shawn.
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> list (Integer userId);

    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);

    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);
}
