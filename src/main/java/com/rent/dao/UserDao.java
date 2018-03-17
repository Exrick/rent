package com.rent.dao;


import com.rent.base.BaseDao;
import com.rent.entity.User;

import java.util.List;

/**
 * @author Exrickx
 */
public interface UserDao extends BaseDao<User,Integer> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    List<User> findByUsername(String username);
}
