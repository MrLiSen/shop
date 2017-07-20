package com.shop.dao;

import com.shop.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/18.
 */
@Repository
public interface ProductDao {
    /**
     * 查询默认货品
     * @param goodId
     * @return
     */
    @Select("SELECT id, allocated_stock, cost, exchange_point, " +
            "is_default, market_price, price, reward_point, sn, specification_values, " +
            "stock, goods FROM xx_product where goods = #{goodId} and is_default=1")
    Product findByGoodId(@Param(value = "goodId")Integer goodId);


    /**
     * 查询货品
     * @param goodId
     * @return
     */
    @Select("SELECT id, allocated_stock, cost, exchange_point, " +
            "is_default, market_price, price, reward_point, sn, specification_values, " +
            "stock, goods FROM xx_product where goods = #{goodId}")
    List<Product> findProducts(@Param(value = "goodId") Integer goodId);
}
