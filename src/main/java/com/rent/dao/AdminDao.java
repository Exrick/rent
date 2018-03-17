package com.rent.dao;


import com.rent.base.BaseDao;
import com.rent.entity.Admin;

import java.util.List;

/**
 * @author Exrickx
 */
public interface AdminDao extends BaseDao<Admin,Integer> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    List<Admin> findByUsername(String username);
}
