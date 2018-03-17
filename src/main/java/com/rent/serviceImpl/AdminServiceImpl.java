package com.rent.serviceImpl;

import com.rent.dao.AdminDao;
import com.rent.entity.Admin;
import com.rent.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public AdminDao getRepository() {
        return adminDao;
    }

    @Override
    public Admin findByUsername(String username) {
        
        List<Admin> list=adminDao.findByUsername(username);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
