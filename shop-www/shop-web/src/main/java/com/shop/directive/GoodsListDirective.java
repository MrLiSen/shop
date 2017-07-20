package com.shop.directive;

import com.shop.model.Brand;
import com.shop.model.Goods;
import com.shop.service.BrandService;
import com.shop.service.GoodsService;
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
public class GoodsListDirective extends BaseDirective{
    @Autowired
    private GoodsService goodsService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //解析参数
        Integer limit=getParameter(params,"count",Integer.class);
        Integer categoryId=getParameter(params,"categoryId",Integer.class);
        Integer tagId=getParameter(params,"tagId",Integer.class);

        //调用service查询数据
        List<Goods> goods=goodsService.findHotGoods(categoryId,tagId,limit);

        //输出A
        setVariable(env,body,"goods",goods);
    }



}
