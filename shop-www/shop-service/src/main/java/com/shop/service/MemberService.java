package com.shop.service;

import com.shop.dao.MemberDao;
import com.shop.dto.MemberDto;
import com.shop.model.Member;

import com.shop.util.AssertUtil;
import com.shop.util.MD5;
import com.shop.vo.LoginIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 73121 on 2017/7/19.
 */
@Service
public class MemberService {
    @Autowired
    private MemberDao memberDao;
    private static Logger logger = LoggerFactory.getLogger(MemberService.class);
    public LoginIdentity insert(MemberDto memberDto, String imageVerifyCode, String phoneVerifyCode) {
        // 基本参数验证: 非空、格式验证、图片验证码是否相等、短信验证
        checkRegisterParams(memberDto, imageVerifyCode, phoneVerifyCode);

        // 唯一性验证：用户名、邮箱、手机号
        checkResgisterUnique(memberDto);
        // 插入数据库：密码MD5加密
        Member member = new Member();
        String password = memberDto.getPassword();
        BeanUtils.copyProperties(memberDto, member);
        member.setPassword(MD5.toMD5(password)); // 密码加密
        int count = memberDao.insert(member);
        logger.info("插入记录数：{}", count);
        // 构建返回结果
        LoginIdentity loginIdentity = buildLoginIdentity(member);
        return loginIdentity;
    }

    /**
     * 登陆
     * @param memberDto
     * @param imageVerifyCode
     * @return
     */
    public LoginIdentity login(MemberDto memberDto, String imageVerifyCode) {
        //参数验证
        checkLoginParams(memberDto,imageVerifyCode);
        Member member = memberDao.findByName(memberDto.getUsername());
        AssertUtil.isTrue(member==null,"用户名或密码错误请重新输入");
        String password=memberDto.getPassword();

        AssertUtil.isTrue(!MD5.toMD5(password).equals(member.getPassword()),"用户名或密码错误请重新输入");
        LoginIdentity loginIdentity = buildLoginIdentity(member);
        return loginIdentity;
    }


    private  static void checkLoginParams(MemberDto memberDto,String sessionVerifyCode){
        AssertUtil.isNotEmpty(memberDto.getUsername(), "请输入用户名");
        AssertUtil.isNotEmpty(memberDto.getPassword(), "请输入密码");
        AssertUtil.isTrue(!memberDto.getVerifyCode().toLowerCase().equals(sessionVerifyCode), "图片验证码输入有误，请重新输入");
    }

    /**
     * 注册时基本参数验证
     * @param memberDto
     * @param sessionVerifyCode
     * @param sessionPhoneVerifyCode
     */
    private static void checkRegisterParams(MemberDto memberDto,
                                            String sessionVerifyCode, String sessionPhoneVerifyCode) {
        AssertUtil.isNotEmpty(memberDto.getUsername(), "请输入用户名");
        AssertUtil.isNotEmpty(memberDto.getPassword(), "请输入密码");
        AssertUtil.isTrue(!memberDto.getPassword().equals(memberDto.getRePassword()), "两次密码输入不相同");
        AssertUtil.isNotEmpty(memberDto.getEmail(), "请输入邮箱");
        AssertUtil.isNotEmpty(memberDto.getPhone(), "请输入手机号");
        AssertUtil.isNotEmpty(memberDto.getPhoneVerifyCode(), "请输入手机验证码");
        AssertUtil.isTrue(!memberDto.getPhoneVerifyCode().equals(sessionPhoneVerifyCode), "手机验证码输入有误，请重新输入");
        AssertUtil.isNotEmpty(memberDto.getVerifyCode(), "请输入验证码");
        AssertUtil.isTrue(!memberDto.getVerifyCode().toLowerCase().equals(sessionVerifyCode), "图片验证码输入有误，请重新输入");
    }

    /**
     * 唯一性验证
     * @param memberDto
     */
    private void checkResgisterUnique (MemberDto memberDto) {
        Member member = memberDao.findByColumn("username", memberDto.getUsername());
        AssertUtil.isTrue(member != null, "该用户名已注册");
        member = memberDao.findByColumn("email", memberDto.getEmail());
        AssertUtil.isTrue(member != null, "该邮箱已注册");
        member = memberDao.findByColumn("phone", memberDto.getPhone());
        AssertUtil.isTrue(member != null, "该手机号已注册");
    }

    /**
     * 构建登录结果
     * @param member
     * @return
     */
    private LoginIdentity buildLoginIdentity(Member member) {
        LoginIdentity loginIdentity = new LoginIdentity();
        loginIdentity.setEmail(member.getEmail());
        loginIdentity.setId(member.getId());
        loginIdentity.setPhone(member.getPhone());
        loginIdentity.setUsername(member.getUsername());
        return loginIdentity;
    }


}
