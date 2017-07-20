package com.shop.service;

import com.shop.constant.RedisConstantKey;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/13.
 */
@Service
public class KeywordsService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 将关键字存入redis
     * @param value
     */
    public void setValue(String value) {
        //基本参数验证
        AssertUtil.isNotEmpty(value,"请输入搜索关键字");
        //存入redis
        String[] values=value.split(",");
        BoundListOperations<String,String> listOperations=redisTemplate.boundListOps(RedisConstantKey.HOT_KEYWORDS_KEY);
        listOperations.rightPushAll(values);
    }

    /**
     * 获取热门关键字
     * @return
     */
    public List<String> get() {
        BoundListOperations<String,String> listOperations=redisTemplate.boundListOps(RedisConstantKey.HOT_KEYWORDS_KEY);
        return listOperations.range(0,-1);
    }
}
