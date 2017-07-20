package com.shop.service;

import com.shop.dao.AdPositionDao;
import com.shop.model.AdPosition;
import com.shop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 73121 on 2017/7/14.
 */
@Service
public class AdPositionService {
    @Autowired
    private AdPositionDao adPositionDao;

    public AdPosition findById(Integer positionId) {
        AssertUtil.intIsNotEmpty(positionId);
        AdPosition adPosition=adPositionDao.findById(positionId);
        return adPosition;
    }
}
