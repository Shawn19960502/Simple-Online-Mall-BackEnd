package com.shuomarket.service;

import com.shuomarket.common.ServerResponse;
import com.shuomarket.pojo.Category;

import java.util.List;

/**
 * Created by Shawn.
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
