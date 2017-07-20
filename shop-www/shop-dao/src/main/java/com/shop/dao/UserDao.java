package com.shop.dao;

import com.shop.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select id, userName, password from t_user")
    List<User> find();
}
