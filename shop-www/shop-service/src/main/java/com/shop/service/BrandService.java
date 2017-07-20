package com.shop.service;

import com.shop.dao.BrandDao;
import com.shop.model.Brand;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/14.
 */
@Service
public class BrandService {
    @Autowired
    private BrandDao brandDao;

    /**
     * 查询分类下品牌
     * @param categoryId
     * @param limit
     * @return
     */
    public List<Brand> findCategoryBrands(Integer categoryId, Integer limit) {
        //AssertUtil.intIsNotEmpty(categoryId,"请选择分类");
        if(limit==null||limit<1){
             limit=4;
        }

        List<Brand> brands=null;
        if(categoryId==null||categoryId<1){
            brands=brandDao.findBrands(limit);
        }else {
            brands=brandDao.findCategoryBrands(categoryId,limit);
        }

        return brands;
    }
}
