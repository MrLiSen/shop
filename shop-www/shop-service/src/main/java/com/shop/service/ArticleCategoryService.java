package com.shop.service;

import com.shop.dao.ArticleCategoryDao;
import com.shop.model.ArticleCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Service
public class ArticleCategoryService {
    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    public List<ArticleCategory> findRootList(Integer limit) {
        if(limit==null||limit<1){
            limit=2;
        }
        List<ArticleCategory> articleCategories=articleCategoryDao.findRootList(limit);
        return  articleCategories;
    }
}
