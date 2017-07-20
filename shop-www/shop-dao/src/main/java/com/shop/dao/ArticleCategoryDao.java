package com.shop.dao;

import com.shop.model.ArticleCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Repository
public interface ArticleCategoryDao {

    @Select("select id, name from xx_article_category where grade=0 order by orders limit #{limit}")
    List<ArticleCategory> findRootList(@Param(value = "limit") Integer limit);
}
