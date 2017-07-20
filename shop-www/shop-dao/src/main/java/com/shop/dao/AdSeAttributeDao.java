package com.shop.dao;

import com.shop.model.Attribute;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/17.
 */
@Repository
public interface AdSeAttributeDao {

    @Select("SELECT id, `name`, `options`,property_index,product_category " +
            " FROM xx_attribute WHERE product_category = #{categoryId}")
    List<Attribute> findAttibutes(@Param(value = "categoryId") Integer categoryId);
}
