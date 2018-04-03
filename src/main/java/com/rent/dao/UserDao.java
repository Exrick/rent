package com.rent.dao;


import com.rent.base.BaseDao;
import com.rent.entity.User;

import java.util.List;

/**
 * 用户数据处理层
 * @author Exrickx
 */
public interface UserDao extends BaseDao<User,Integer> {

    /**
     * 通过用户名获取正常用户
     * @param username
     * @param status
     * @return
     */
    List<User> findByUsernameAndStatus(String username,Integer status);
}
