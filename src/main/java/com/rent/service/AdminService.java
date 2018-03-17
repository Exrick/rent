package com.rent.service;


import com.rent.base.BaseService;
import com.rent.entity.Admin;
import com.rent.entity.User;

/**
 * @author Exrickx
 */
public interface AdminService extends BaseService<Admin,Integer> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    Admin findByUsername(String username);
}
