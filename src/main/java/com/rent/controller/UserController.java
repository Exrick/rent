package com.rent.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.rent.base.BaseController;
import com.rent.entity.User;
import com.rent.exception.RentException;
import com.rent.service.UserService;
import com.rent.common.utils.ResultUtil;
import com.rent.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;


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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public Result<Object> login(@ModelAttribute User u,
                                @RequestParam String verify,
                                HttpServletRequest request){

        if(StrUtil.isBlank(verify)||StrUtil.isBlank(u.getUsername())
                ||StrUtil.isBlank(u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        String sessionCode = (String) request.getSession().getAttribute("code");
        if(!verify.toLowerCase().equals(sessionCode)) {
            log.error("验证码错误：code:"+verify+",sessionCode:"+sessionCode);
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }

        //验证登录
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(u.getUsername(),u.getPassword());
        try {
            subject.login(token);
            return new ResultUtil<Object>().setData(null);
        }catch (Exception e){
            return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    public Result<Object> regist(@ModelAttribute User u,
                                @RequestParam String verify,
                                HttpServletRequest request){

        if(StrUtil.isBlank(verify)||StrUtil.isBlank(u.getUsername())
                ||StrUtil.isBlank(u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        String sessionCode = (String) request.getSession().getAttribute("code");
        if(!verify.toLowerCase().equals(sessionCode)) {
            log.error("验证码错误：code:"+verify+",sessionCode:"+sessionCode);
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }

        String md5Pass = DigestUtils.md5DigestAsHex(u.getPassword().getBytes());
        u.setPassword(md5Pass);
        User user=userService.save(u);
        if(user==null){
            throw new RentException("注册失败");
        }

        return new ResultUtil<Object>().setData(user);

    }

    @RequestMapping(value = "/findByUsername/{username}", method = RequestMethod.GET)
    @ApiOperation("通过用户名获取用户")
    public Result<Object> findByUsername(@PathVariable String username){

        User user=userService.findByUsername(username);
        return new ResultUtil().setData(user);
    }
}
