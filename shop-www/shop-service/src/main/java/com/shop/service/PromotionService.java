package com.shop.service;

import com.shop.dao.PromotionDao;
import com.shop.model.Promotion;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Service
public class PromotionService {
    @Autowired
    private PromotionDao promotionDao;

    public List<Promotion> findPromotions(Integer limit, Integer categoryId) {
        //AssertUtil.intIsNotEmpty(categoryId);
        if(limit==null||limit<1){
            limit=3;
        }
        List<Promotion> promotions=null;
        if(categoryId==null||categoryId<1){
            promotions=promotionDao.findAllList(limit);
        }else {

            promotions=promotionDao.findPromotions(limit,categoryId);
        }

        return promotions;
    }
}
