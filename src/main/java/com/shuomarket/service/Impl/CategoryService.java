package com.shuomarket.service.Impl;

import com.shuomarket.common.ServerResponse;
import com.shuomarket.dao.CategoryMapper;
import com.shuomarket.pojo.Category;
import com.shuomarket.service.ICategoryService;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by Shawn.
 */
@Service("iCategoryService")
public class CategoryService implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

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

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName){
        if(categoryId == null || isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Parameter is not correct!");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("Update category name successful.");
        }
        return ServerResponse.createByErrorMessage("Update category name failed.");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("Can't find the children category!");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = new HashSet<>();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryIdList = new ArrayList<>();
        if(categoryId != null) {
            for(Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet , Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }

}
