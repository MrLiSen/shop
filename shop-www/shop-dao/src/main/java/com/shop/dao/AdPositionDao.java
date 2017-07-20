package com.shop.dao;

import com.shop.model.AdPosition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by 73121 on 2017/7/14.
 */
@Repository
public interface AdPositionDao {


    AdPosition findById( @Param(value = "positionId") Integer positionId);
}
