package com.shop.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.base.BaseController;
import com.shop.constant.GoodsOrderType;
import com.shop.dto.GoodsDto;
import com.shop.model.Goods;
import com.shop.model.ProductCategory;
import com.shop.service.GoodsService;
import com.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/16.
 */
@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController{

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("search")
    public String search(GoodsDto goodsDto, Model model) {
        PageList<Goods> goods = goodsService.selectForPage(goodsDto);
        model.addAttribute("goodsDto", goodsDto);
        model.addAttribute("results", goods);
        model.addAttribute("paginator", goods.getPaginator());
        String sort = goodsDto.getSort();
        GoodsOrderType goodsOrderType = GoodsOrderType.findBySort(sort);
        model.addAttribute("currentOrderType", goodsOrderType);
        model.addAttribute("orderTypes", GoodsOrderType.values());
        return "goods/search";
    }
    @RequestMapping("list/{categoryId}")
    public String list(GoodsDto goodsDto, @PathVariable Integer categoryId, Model model) {
        PageList<Goods> goods = goodsService.list(goodsDto, categoryId);
        model.addAttribute("goodsDto", goodsDto);
        model.addAttribute("results", goods);
        model.addAttribute("paginator", goods.getPaginator());
        String sort = goodsDto.getSort();
        GoodsOrderType goodsOrderType = GoodsOrderType.findBySort(sort);
        model.addAttribute("currentOrderType", goodsOrderType);
        model.addAttribute("orderTypes", GoodsOrderType.values());
        model.addAttribute("categoryId", categoryId);

        ProductCategory productCategory = productCategoryService.findById(categoryId);
        model.addAttribute("productCategory", productCategory);
        return "goods/list";
    }

    @RequestMapping("content/{id}")
    public String detail(@PathVariable Integer id,Model model){
        Map<String,Object> result=goodsService.findById(id);
        model.addAllAttributes(result);
        return "goods/detail";
    }


}
