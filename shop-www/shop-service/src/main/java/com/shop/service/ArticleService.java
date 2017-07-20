package com.shop.service;

import com.shop.dao.ArticleDao;
import com.shop.dao.BrandDao;
import com.shop.model.Article;
import com.shop.model.Brand;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;


    public List<Article> findArticleList(Integer categoryId,Integer limit) {
        AssertUtil.intIsNotEmpty(categoryId);
        if(limit==null||limit<1){
            limit=6;
        }
        List<Article> articles=articleDao.findArticle(categoryId,limit);
        return  articles;
    }
}
