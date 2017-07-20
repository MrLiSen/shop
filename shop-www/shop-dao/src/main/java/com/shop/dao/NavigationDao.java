package com.shop.dao;

import com.shop.model.Navigation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 73121 on 2017/7/13.
 */
@Repository
public interface NavigationDao {
    @Select("select id,name,url,is_blank_target from xx_navigation" +
            " where version=0 and position=#{position} order by orders")
    List<Navigation> findByPosition(@Param(value = "position") Integer position);
}
