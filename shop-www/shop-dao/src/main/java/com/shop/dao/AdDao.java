package com.shop.dao;

import com.shop.model.Ad;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/15.
 */
@Repository
public interface AdDao {
    @Select("select id,title ,path from xx_ad where ad_position = ${positionId}")
    List<Ad> findPositionAds(@Param(value ="positionId") Integer positionId);
}
