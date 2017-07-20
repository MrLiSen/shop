package com.shop.controller;

import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import com.shop.constant.Constant;
import com.shop.constant.UrlConstant;
import com.shop.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 73121 on 2017/7/13.
 */
@Controller
public class IndexController extends BaseController{
    @Autowired
    private NavigationService navigationService;

    @Value(value = "${app.cache.service-url}")
    private String cacheUrl;

    @RequestMapping("index")
    public String index(Model model){
    //获取热门关键字
        RestTemplate restTemplate=new RestTemplate();
        String url=cacheUrl+ UrlConstant.HOT_KEYWORDS_URL;
        ResponseEntity<ResultInfo> entity=restTemplate.getForEntity(url,ResultInfo.class);
        if(entity.getStatusCode()== HttpStatus.OK){
            // 请求成功
            ResultInfo resultInfo = entity.getBody();
            if(resultInfo.getResultCode()== Constant.SUCCESS_CODE){
                List<String> hotKeywords= (List<String>) resultInfo.getResult();
                model.addAttribute("hotKeywords",hotKeywords);
            }
        }
        return "index";
    }

    @RequestMapping("register")
    public String register(String redirectUrl,Model model) {
        model.addAttribute("redirectUrl",redirectUrl);
        return "user/register";
    }
    @RequestMapping("login")
    public String login(String redirectUrl,Model model) {
        model.addAttribute("redirectUrl",redirectUrl);
        return "user/login";
    }


    @RequestMapping("logout")
    @ResponseBody
    public ResultInfo logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.LOGIN_USER_KEY);
        return success("退出成功");
    }
}
