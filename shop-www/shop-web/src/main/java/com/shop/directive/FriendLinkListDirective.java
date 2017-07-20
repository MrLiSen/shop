package com.shop.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/15.
 */
@Component
public class FriendLinkListDirective extends BaseDirective{
    @Override
    public void execute(Environment env, Map params,
                        TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody)
            throws TemplateException, IOException {
        //解析参数

        //查询数据
        //输出

    }
}
