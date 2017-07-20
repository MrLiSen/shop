package com.shop.directive;

import com.shop.model.Ad;
import com.shop.model.ProductCategory;
import com.shop.service.AdService;
import com.shop.service.ProductCategoryService;
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
public class ProductCategoryParentListDirective extends BaseDirective{
    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //解析参数
        Integer categoryId=getParameter(params,"categoryId",Integer.class);


        //调用service查询数据
        List<ProductCategory> productCategories=productCategoryService.findPatent(categoryId);
        //输出
        setVariable(env,body,"productCategories",productCategories);
    }



}
