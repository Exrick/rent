package com.rent.common.utils;

import cn.hutool.core.util.StrUtil;
import com.rent.entity.User;
import com.rent.exception.RentException;
import com.rent.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Exrickx
 */
@Component
public class ShiroUtil {

    @Autowired
    private UserService userService;

    public User getUserInfo(){

        String username= SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        if(user==null){
            throw new RentException("获取登录用户失败");
        }
        return user;
    }
}
