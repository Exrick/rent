package com.rent.common.utils;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.rent.entity.User;
import com.rent.exception.RentException;
import com.rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Exrickx
 */
@Component
public class UserUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public User getUserInfo(String token){

        String u = stringRedisTemplate.opsForValue().get(token);
        if(StrUtil.isBlank(u)){
            throw new RentException("用户未登录或登录已失效");
        }
        User user=new Gson().fromJson(u,User.class);
        stringRedisTemplate.opsForValue().set(token,u,30L, TimeUnit.MINUTES);
        return user;
    }
}
