package com.shop.controller;

import com.alibaba.fastjson.JSON;
import com.shop.base.BaseController;
import com.shop.base.ResultInfo;
import com.shop.constant.Constant;
import com.shop.exception.ParamException;
import com.shop.util.AssertUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TW on 2017/7/18.
 */
@RequestMapping("sms")
@Controller
public class SmsController extends BaseController {

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.appkey}")
    private String smsAppKey;
    @Value("${sms.secret}")
    private String smsSecret;
    @Value("${sms.type}")
    private String smsType;
    @Value("${sms.free.sign.name}")
    private String smsFreeSignName;
    @Value("${sms.template.code}")
    private String smsTemplateCode;

    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @RequestMapping("send")
    @ResponseBody
    public ResultInfo sendVerifyCode(String phone, HttpServletRequest request) {

        AssertUtil.isNotEmpty(phone, "请输入手机号");
        HttpSession session = request.getSession();
        String random = (String)session.getAttribute(Constant.VERIFY_CODE_KEY + phone);
        if (StringUtils.isNotBlank(random)) {
            return success("发送成功");
        }
        random = RandomUtils.nextInt(100000, 999999) + "";
        // 发送
        send(phone, random);

        request.getSession().setAttribute(Constant.VERIFY_CODE_KEY+ phone, random);
        request.getSession().setMaxInactiveInterval(300); // 五分钟有效
        return success("发送成功");
    }

    /**
     * 发送
     * @param phone
     * @param random
     */
    private void send(String phone, String random) {
        TaobaoClient client = new DefaultTaobaoClient(smsUrl, smsAppKey, smsSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(smsType);
        req.setSmsFreeSignName(smsFreeSignName);

        Map<String, String> paramter = new HashMap<>();
        paramter.put("verifyCode", random);
        req.setSmsParam(JSON.toJSONString(paramter));
        req.setRecNum(phone);
        req.setSmsTemplateCode(smsTemplateCode);
        try {
            AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
            AssertUtil.isTrue(!response.isSuccess(), "短信发送失败，请联系客服");
        } catch (Exception e) {
            logger.error("Sms send failure，{}", e);
            throw new ParamException("短信发送失败，请联系客服");
        }
    }

}
