package com.shop.controller;

import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 73121 on 2017/7/20.
 */
@RequestMapping("cart")
@Controller
public class CartController extends BaseController{

    @RequestMapping("list")
    public String cart() {

        return "cart/list";
    }
}
