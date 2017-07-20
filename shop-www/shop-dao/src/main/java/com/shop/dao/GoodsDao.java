package com.shop.dao;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.dto.GoodsDto;
import com.shop.model.Goods;
import com.shop.vo.GoodsDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Repository
public interface GoodsDao {

    @Select("SELECT g.id, g.NAME, g.caption, g.image, g.price FROM xx_goods g LEFT JOIN " +
            " xx_product_category p on g.product_category = p.id LEFT JOIN xx_goods_tag t on g.id=t.goods " +
            " where p.tree_path LIKE ',${categoryId},%' AND t.tags = #{tagId} and g.is_marketable=1 order by g.id LIMIT #{limit}")
     List<Goods> findHotGoods(@Param(value = "categoryId")Integer categoryId, @Param(value = "tagId")Integer tagId, @Param(value = "limit") Integer limit);

    @Select("SELECT g.id, g.NAME, g.caption, g.image, g.price, g.market_price" +
            " FROM  xx_goods g " +
            " LEFT JOIN xx_goods_tag t3 ON g.id = t3.goods " +
            " WHERE t3.tags = #{tagId} AND g.is_marketable = 1 " +
            " ORDER BY g.id LIMIT #{limit}")
    List<Goods> findGoods(@Param(value = "tagId") Integer tagId, @Param(value = "limit")Integer limit);


    PageList<Goods> selectForPage(GoodsDto goodsDto, PageBounds pageBounds);

    PageList<Goods> findOtherList(GoodsDto goodsDto, PageBounds pageBounds);

    PageList<Goods> list(GoodsDto goodsDto, PageBounds pageBounds);

    @Select("SELECT  " +
            "  t.id,  " +
            "  t.`name`,  " +
            "  t.caption,  " +
            "  t.sn,  " +
            "  t.price,  " +
            "  t.market_price,  " +
            "  t.unit,  " +
            "  t.type,  " +
            "  t.product_images,  " +
            "  t.specification_items,  " +
            "  t.parameter_values,  " +
            "  t.introduction,  " +
            "  t.product_category,  " +
            "  t.image  " +
            "FROM  " +
            "  xx_goods t where  " +
            " id = #{id}")
    @ResultType(GoodsDetailVo.class)
    GoodsDetailVo findById(@Param(value = "id") Integer id);
}
