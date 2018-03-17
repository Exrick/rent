package com.rent.service;


import com.rent.base.BaseService;
import com.rent.entity.User;

/**
 * @author Exrickx
 */
public interface UserService extends BaseService<User,Integer> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
