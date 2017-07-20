package com.shop.service;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.constant.GoodsOrderType;
import com.shop.dao.GoodsDao;
import com.shop.dto.GoodsDto;
import com.shop.model.*;
import com.shop.util.AssertUtil;

import com.shop.vo.GoodsDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/15.
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private  ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    public List<Goods> findHotGoods(Integer categoryId, Integer tagId, Integer limit) {
        //AssertUtil.intIsNotEmpty(categoryId);
        AssertUtil.intIsNotEmpty(tagId);
        if(limit==null||limit<1){
            limit=10;
        }
        List<Goods> goods=null;
        if(categoryId==null||categoryId<1){
            goods=goodsDao.findGoods(tagId,limit);

        }else{
            goods=goodsDao.findHotGoods(categoryId,tagId,limit);
        }

        return goods;
    }
    /**
     * 分页查询
     * @param goodsDto
     * @return
     */
    public PageList<Goods> selectForPage(GoodsDto goodsDto) {
        if (StringUtils.isBlank(goodsDto.getSort())) {
            goodsDto.setSort(GoodsOrderType.create_date.getSort()); // 默认是日期降序
        }
        PageList<Goods> goods = goodsDao.selectForPage(goodsDto, goodsDto.buildPageBounds());
        return goods;
    }

    public PageList<Goods> list(GoodsDto goodsDto, Integer categoryId) {
        if (StringUtils.isBlank(goodsDto.getSort())) {
            AssertUtil.intIsNotEmpty(categoryId,"请选择一个分类");
            goodsDto.setSort(GoodsOrderType.sales.getSort()); // 默认是销量降序
        }
        goodsDto.setProductCategoryId(categoryId);
        //构建treepath
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        PageList<Goods> goods = null;
        if (productCategory.getGrade() > 1) { // 代表此分类下有商品
            goods = goodsDao.list(goodsDto, goodsDto.buildPageBounds());
        } else { // 该分类没有 但子分类有
            String treePath = "";
            if (productCategory.getGrade() == 0) { // 如果是根级的话没有tree_path ,1,
                treePath = "," + categoryId +  ",";
            } else {
                treePath = productCategory.getTreePath() + categoryId + ","; // ,1,7,
            }
            goodsDto.setTreePath(treePath);
            goods = goodsDao.findOtherList(goodsDto, goodsDto.buildPageBounds());
        }

        return goods;

    }

    public Map<String,Object> findById(Integer id) {
         //         1. 基本参数验证
        AssertUtil.intIsNotEmpty(id);
        //        2. 获取货品的基本信息--》根据ID查询相关字段
        GoodsDetailVo goodsDetailVo=goodsDao.findById(id);
        AssertUtil.isTrue(goodsDetailVo==null,"该商品不存在，请重新选择");
        //        3. 格式化(将json字符串转化为java对象)：图片集合、规格项集合、商品参数
        String productImages =goodsDetailVo.getProductImages();
        List<ProductImage> productImageList= JSON.parseArray(productImages,ProductImage.class);

        String specificationItems =goodsDetailVo.getSpecificationItems();
        List<SpecificationItem> specificationItemList= JSON.parseArray(specificationItems,SpecificationItem.class);

        String parameterValues =goodsDetailVo.getParameterValues();
        List<ParameterValue> parameterValueList= JSON.parseArray(parameterValues,ParameterValue.class);
        //        4. 获取货品下的默认商品信息
        Product defaultProduct =productService.findGoodDefaultProduct(goodsDetailVo.getId());
        AssertUtil.isTrue(defaultProduct==null,"该商品不存在 请重选择");
        //        5. 获取所有商品信息
        List<Product> products=productService.findGoodProduct(goodsDetailVo.getId());
        //        6. 获取货品分类
        ProductCategory productCategory=productCategoryService.findById(goodsDetailVo.getProductCategory());
        //        7. 构建一个返回map
        Map<String, Object> result = new HashMap<>();
        result.put("good", goodsDetailVo);
        result.put("specificationValues", specificationItemList);
        result.put("parameterValues", parameterValueList);
        result.put("productImages", productImageList);
        result.put("defaultProduct", defaultProduct);
        result.put("productCategory", productCategory);
        result.put("products", products);
        return result;
    }


   
}
