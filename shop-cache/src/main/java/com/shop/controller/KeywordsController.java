package com.shop.controller;

import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import com.shop.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 73121 on 2017/7/13.
 */
@RestController
@RequestMapping("keywords")
public class KeywordsController extends BaseController {
    @Autowired
    private KeywordsService keywordsService;

    @PostMapping("set")
    public ResultInfo setValue(String value){
        keywordsService.setValue(value);
        return success("添加成功");
    }

    @GetMapping("get")
    public ResultInfo get(){
        List<String> result=keywordsService.get();
        return success(result);
    }
}
