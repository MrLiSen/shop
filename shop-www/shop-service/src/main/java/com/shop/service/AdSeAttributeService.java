package com.shop.service;

import com.alibaba.fastjson.JSON;
import com.shop.dao.AdSeAttributeDao;
import com.shop.model.Attribute;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by 73121 on 2017/7/17.
 */
@Service
public class AdSeAttributeService {
    @Autowired
    private AdSeAttributeDao adSeAttributeDao;

    public List<Attribute> findAttributes(Integer categoryId) {
        AssertUtil.intIsNotEmpty(categoryId);
        List<Attribute> attributes=adSeAttributeDao.findAttibutes(categoryId);
        if(attributes==null||attributes.size()<1){
            return Collections.emptyList();
        }
        for (Attribute attribute:attributes){
            String options=attribute.getOptions();
            //fastJson
            List<String> optionList= JSON.parseArray(options,String.class);

            attribute.setOptionsList(optionList.toArray(new String[]{}));
        }
        return attributes;
    }
}
