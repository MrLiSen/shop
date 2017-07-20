package com.shop.directive;

import com.shop.model.Promotion;
import com.shop.service.PromotionService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/14.
 */
@Component
public class PromotionListDirective extends BaseDirective{
    @Autowired
    private PromotionService promotionService;
    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels, TemplateDirectiveBody body) throws TemplateException, IOException {
        //解析参数
        Integer limit=getParameter(params,"count",Integer.class);
        Integer categoryId=getParameter(params,"categoryId",Integer.class);
        //调取查询数据
        List<Promotion> promotions= promotionService.findPromotions(limit,categoryId);
        //输出
        setVariable(env,body,"promotions",promotions);
    }
}
