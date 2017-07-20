package com.shop.controller;

import com.google.code.kaptcha.Constants;
import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import com.shop.constant.Constant;
import com.shop.dto.MemberDto;
import com.shop.service.MemberService;
import com.shop.service.UserService;
import com.shop.util.AssertUtil;
import com.shop.vo.LoginIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by LISEN on 2017/7/12.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    @PostMapping("register")
    @ResponseBody
    public ResultInfo register(MemberDto memberDto, HttpServletRequest request){
        // 从session中获取图片验证码以及短信验证码
        AssertUtil.isNotEmpty(memberDto.getPhone(),"请输入手机号");
        HttpSession session = request.getSession();
        String imageVerifyCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String phoneVerifyCode = (String)session.getAttribute(Constant.VERIFY_CODE_KEY + memberDto.getPhone());
        LoginIdentity loginIdentity = memberService.insert(memberDto, imageVerifyCode, phoneVerifyCode);
        session.setAttribute(Constant.LOGIN_USER_KEY, loginIdentity);
        return success("恭喜你注册成功");
    }

    @PostMapping("login")
    @ResponseBody
    public ResultInfo login(MemberDto memberDto, HttpServletRequest request){
        // 从session中获取图片验证码
        HttpSession session = request.getSession();
        String imageVerifyCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        LoginIdentity loginIdentity=memberService.login(memberDto,imageVerifyCode);
        session.setAttribute(Constant.LOGIN_USER_KEY, loginIdentity);
        return success("登陆成功");
    }


}
