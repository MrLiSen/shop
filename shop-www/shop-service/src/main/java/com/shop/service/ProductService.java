package com.shop.service;

import com.shop.dao.ProductDao;
import com.shop.model.Product;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/18.
 */
@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    public Product findGoodDefaultProduct(Integer goodId) {
        AssertUtil.intIsNotEmpty(goodId);
        Product product=productDao.findByGoodId(goodId);
        return product;
    }

    public List<Product> findGoodProduct(Integer goodId) {
        AssertUtil.intIsNotEmpty(goodId);
        List<Product> products=productDao.findProducts(goodId);
        return products;
    }
}
