package com.shop.dao;

import com.shop.model.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Repository
public interface BrandDao {

    @Select("SELECT id, name ,logo FROM xx_brand t1 LEFT JOIN xx_product_category_brand t2 " +
            " on t1.id = t2.brands where t2.product_categories=#{categoryId} ORDER BY " +
            " t1.orders LIMIT #{limit}")
    List<Brand> findCategoryBrands(@Param(value = "categoryId") Integer categoryId, @Param(value = "limit")Integer limit);

    @Select("select id ,name ,logo from xx_brand order by orders limit #{limit}")
    List<Brand> findBrands(@Param(value = "limit")Integer limit);
}
