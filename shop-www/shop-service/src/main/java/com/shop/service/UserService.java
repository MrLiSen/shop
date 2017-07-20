package com.shop.service;

/**
 * Created by TW on 2017/7/12.
 */

import com.shop.dao.UserDao;
import com.shop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> find() {
        return  userDao.find();
    }
}
