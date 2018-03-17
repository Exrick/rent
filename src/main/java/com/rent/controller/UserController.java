package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.entity.User;
import com.rent.service.UserService;
import com.rent.utils.ResultUtil;
import com.rent.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/user")
public class UserController extends BaseController<User, Integer> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return userService;
    }

    @RequestMapping(value = "/findByUsername/{username}", method = RequestMethod.GET)
    @ApiOperation("通过用户名获取用户")
    public Result<Object> findByUsername(@PathVariable String username){

        User user=userService.findByUsername(username);
        return new ResultUtil().setData(user);
    }
}
