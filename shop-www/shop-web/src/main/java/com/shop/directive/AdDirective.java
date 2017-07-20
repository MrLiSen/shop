package com.shop.directive;

import com.shop.model.Ad;
import com.shop.model.ArticleCategory;
import com.shop.service.AdService;
import com.shop.service.ArticleCategoryService;
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
public class AdDirective extends BaseDirective{
    @Autowired
    private AdService adService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //解析参数
        Integer positionId=getParameter(params,"positionId",Integer.class);


        //调用service查询数据
        List<Ad> ads=adService.findPositionAds(positionId);
        //输出
        setVariable(env,body,"ads",ads);
    }



}
