package com.shop.dao;

import com.shop.model.Promotion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Repository
public interface PromotionDao {

    @Select("SELECT id, image, name ,title FROM xx_promotion t1 LEFT JOIN xx_product_category_promotion t2 " +
            "  on t1.id = t2.promotions WHERE t2.product_categories = #{categoryId} " +
            "  order BY t1.orders LIMIT #{limit}")
    List<Promotion> findPromotions(@Param(value = "limit") Integer limit, @Param(value = "categoryId")Integer categoryId);

    @Select("SELECT id, image, title, begin_date, end_date  " +
            "FROM xx_promotion WHERE   " +
            "(begin_date is null AND (NOW() - end_date < 0 or end_date is NULL) )  " +
            "OR (NOW() - begin_date > 0 AND (NOW() - end_date < 0 or end_date is NULL)) limit #{limit}")
    List<Promotion> findAllList(@Param(value = "limit")Integer limit);
}
