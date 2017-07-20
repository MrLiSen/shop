package com.shop.dao;

import com.shop.model.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Repository
public interface ProductCategoryDao {
    @Select("select id, name FROM xx_product_category WHERE grade = 0 ORDER BY orders LIMIT #{limit}")
    List<ProductCategory> findRootList(@Param(value = "limit") Integer limit);

    @Select("select id, name FROM xx_product_category WHERE parent= #{parentId} ORDER BY orders LIMIT #{limit}")
    List<ProductCategory> findChildrenList(@Param(value = "parentId")Integer parentId,@Param(value = "limit") Integer limit);

    @Select("select id, name, tree_path, grade, parent from xx_product_category where id = #{categoryId}")
    ProductCategory findById(@Param(value = "categoryId") Integer categoryId);

    @Select("select id, name, tree_path, grade, parent from xx_product_category where id in (${ids})")
    List<ProductCategory> findByIds(@Param(value = "ids") String ids);
}
