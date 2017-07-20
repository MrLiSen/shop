package com.shop.directive;

import com.alibaba.fastjson.JSON;
import com.shop.model.Navigation;
import com.shop.service.NavigationService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/13.
 */
@Component
public class NavigationDirective extends BaseDirective{

    @Autowired
    private NavigationService navigationService;
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //解析参数
        BeansWrapper beansWrapper=new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
        Integer position= (Integer) beansWrapper.unwrap((TemplateModel) params.get("position"),Integer.class);
        //调用service获取数据
        List<Navigation> navigations= navigationService.findByPosition(position);
        //输出
        setVariable(env,body,"navigations",navigations);
    }
}
