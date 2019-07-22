package com.shuomarket.service;

import com.github.pagehelper.PageInfo;
import com.shuomarket.common.ServerResponse;
import com.shuomarket.pojo.Shipping;

/**
 * Created By Shawn.
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
