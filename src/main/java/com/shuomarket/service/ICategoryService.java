package com.shuomarket.service;

import com.shuomarket.common.ServerResponse;

/**
 * Created by Shawn.
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
}
