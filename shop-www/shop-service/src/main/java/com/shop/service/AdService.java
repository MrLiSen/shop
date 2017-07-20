package com.shop.service;

import com.shop.dao.AdDao;
import com.shop.model.Ad;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Service
public class AdService {
    @Autowired
    private AdDao adDao;

    public List<Ad> findPositionAds(Integer positionId) {
        AssertUtil.intIsNotEmpty(positionId);
        List<Ad> ads=adDao.findPositionAds(positionId);
        return ads;
    }
}
