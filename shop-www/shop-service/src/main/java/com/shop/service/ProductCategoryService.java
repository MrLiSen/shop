package com.shop.service;

import com.shop.dao.ProductCategoryDao;
import com.shop.model.ProductCategory;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.org.mozilla.javascript.internal.regexp.SubString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategory> findRootList(Integer limit) {
        if(limit==null||limit<1){
            limit=6;
        }
        List<ProductCategory> productCategories=productCategoryDao.findRootList(limit);
        return productCategories;
    }

    public List<ProductCategory> findChildrenList(Integer parentId, Integer limit) {
        AssertUtil.intIsNotEmpty(parentId,"请选择父级分类");
        if(limit==null||limit<1){
            limit=3;
        }
        List<ProductCategory> productCategories=productCategoryDao.findChildrenList(parentId,limit);
        return productCategories;
    }

    public ProductCategory findById(Integer categoryId) {
        AssertUtil.intIsNotEmpty(categoryId,"请选择分类");

        ProductCategory productCategory=productCategoryDao.findById(categoryId);
        AssertUtil.isTrue(productCategory==null,"该分类不存在");
        return productCategory;
    }

    /**
     * 查询父级分类
     * @param categoryId
     * @return
     */
    public List<ProductCategory> findPatent(Integer categoryId) {
        ProductCategory productCategory=findById(categoryId);
        if(productCategory.getGrade()==0){//如果是根级了就无需查询
            return Collections.emptyList();
        }
        List<ProductCategory> productCategories=null;
        if(productCategory.getGrade()==1){// 如果是第一级就只查询父级
            productCategories=new ArrayList<>();
            ProductCategory patent=findById(productCategory.getParent());
            productCategories.add(patent);
            return productCategories;
        }
        String treePath=productCategory.getTreePath();
        String ids= treePath.substring(treePath.indexOf(",")+1,treePath.lastIndexOf(","));
        productCategories=productCategoryDao.findByIds(ids);
        return  productCategories;
    }
}
