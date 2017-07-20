package com.shop.directive;

import com.alibaba.fastjson.JSON;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 73121 on 2017/7/14.
 */
public abstract class BaseDirective implements TemplateDirectiveModel{
    private  static Logger logger= LoggerFactory.getLogger(BaseDirective.class);

    /**
     * 解析参数
     * @param params
     * @param parameterName
     * @param clazz
     * @param <T>
     * @return
     * @throws TemplateModelException
     */
    protected <T> T getParameter(Map params,String parameterName,Class<?> clazz) throws TemplateModelException {
        BeansWrapper beansWrapper=new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
        T value=null;
        try {
            value= (T) beansWrapper.unwrap((TemplateModel) params.get(parameterName),clazz);

        }catch (TemplateModelException e){
           logger.error("获取参数异常：{}",e);
           return  null;
        }
        return value;
    }

    protected void  setVariable(Environment env,TemplateDirectiveBody body,
                                String key,Object value)
            throws TemplateException,IOException{
        //输出
        BeansWrapper beansWrapper=new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
        if(body==null){
            env.getOut().write(JSON.toJSONString(value));
        }else{
            TemplateModel templateModel=beansWrapper.wrap(value);
            env.setVariable(key,templateModel);
            body.render(env.getOut());
        }
    }

}
