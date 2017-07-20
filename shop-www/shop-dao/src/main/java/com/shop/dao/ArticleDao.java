package com.shop.dao;

import com.shop.model.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Repository
public interface ArticleDao {

    @Select("select id, title from xx_article where article_category = #{categoryId} "
            + "and is_publication = 1 order by id limit #{limit}")
    List<Article> findArticle(@Param(value = "categoryId")Integer categoryId,@Param(value = "limit") Integer limit);
}
