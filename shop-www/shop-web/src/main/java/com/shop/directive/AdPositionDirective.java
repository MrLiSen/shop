package com.shop.directive;

import com.shop.model.AdPosition;
import com.shop.model.Brand;
import com.shop.service.AdPositionService;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/14.
 */
@Component
@SuppressWarnings("rawtypes")
public class AdPositionDirective extends BaseDirective{
    @Autowired
    private AdPositionService adPositionService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //解析参数
        Integer positionId=getParameter(params,"positionId",Integer.class);

        //调用service查询数据
        AdPosition adPosition=adPositionService.findById(positionId);
        //模板内容  数据+模板=输出
        String content=adPosition.getTemplate();
        StringReader reader=new StringReader(content);
        Configuration configuration=freeMarkerConfigurer.getConfiguration();
        //从spring的Freemarker视图配置中获取Freemarker的配置
        Template template=new Template("adTemplate",reader,configuration);
        Map<String,Object> dateModel=new HashMap<>();
        dateModel.put("adPosition",adPosition);
        template.process(dateModel,env.getOut());

    }



}
