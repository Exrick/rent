package com.rent.serviceimpl;

import com.rent.dao.UserDao;
import com.rent.entity.User;
import com.rent.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    @Override
    public User findByUsername(String username) {
        
        List<User> list=userDao.findByUsername(username);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
