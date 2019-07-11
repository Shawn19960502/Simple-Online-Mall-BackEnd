package com.shuomarket.service.Impl;

import com.shuomarket.common.ServerResponse;
import com.shuomarket.dao.CategoryMapper;
import com.shuomarket.pojo.Category;
import com.shuomarket.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by Shawn.
 */
@Service("iCategoryService")
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null || isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Parameter is not correct!");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("add category successfully!");
        }
        return ServerResponse.createByErrorMessage("add category failed");
    }
}
