package com.rent.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.rent.base.BaseController;
import com.rent.common.utils.ShiroUtil;
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
import org.springframework.data.redis.core.StringRedisTemplate;
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
@CrossOrigin
public class UserController extends BaseController<User, Integer> {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ShiroUtil shiroUtil;

    @Override
    public UserService getService() {
        return userService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public Result<Object> login(@ModelAttribute User u,
                                @RequestParam String verify,
                                @RequestParam String codeId){

        if(StrUtil.isBlank(verify)||StrUtil.isBlank(u.getUsername())
                ||StrUtil.isBlank(u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        //验证码
        String code=stringRedisTemplate.opsForValue().get(codeId);
        if(StrUtil.isBlank(code)){
            return new ResultUtil<Object>().setErrorMsg("验证码已过期，请重新获取");
        }

        if(!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("登陆失败，验证码错误：code:"+ verify +",sessionCode:"+code.toLowerCase());
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }

        //验证登录
        Subject subject = SecurityUtils.getSubject();
        String md5Pass = DigestUtils.md5DigestAsHex(u.getPassword().getBytes());
        UsernamePasswordToken token = new UsernamePasswordToken(u.getUsername(),md5Pass);
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
                                 @RequestParam String codeId){

        if(StrUtil.isBlank(verify)||StrUtil.isBlank(u.getUsername())
                ||StrUtil.isBlank(u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        //验证码
        String code=stringRedisTemplate.opsForValue().get(codeId);
        if(StrUtil.isBlank(code)){
            return new ResultUtil<Object>().setErrorMsg("验证码已过期，请重新获取");
        }

        if(!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:"+ verify +",sessionCode:"+code.toLowerCase());
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }

        if(userService.findByUsername(u.getUsername())!=null){
            return new ResultUtil<Object>().setErrorMsg("该用户名已被注册");
        }

        String md5Pass = DigestUtils.md5DigestAsHex(u.getPassword().getBytes());
        u.setPassword(md5Pass);
        User user=userService.save(u);
        if(user==null){
            throw new RentException("注册失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<Object> getUserInfo(){

        User user=shiroUtil.getUserInfo();
        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "修改资料",notes = "用户名密码不会修改")
    public Result<Object> edit(@ModelAttribute User u){

        User old=shiroUtil.getUserInfo();

        u.setId(old.getId());
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());

        User user=userService.update(u);
        if(user==null){
            throw new RentException("修改失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/modifyPass",method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(@ModelAttribute User u){

        User old=shiroUtil.getUserInfo();

        String newMd5Pass=DigestUtils.md5DigestAsHex(u.getNewPass().getBytes());
        if(!old.getPassword().equals(newMd5Pass)){
            throw new RentException("旧密码不正确");
        }

        old.setPassword(newMd5Pass);
        User user=userService.update(old);
        if(user==null){
            throw new RentException("修改失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

}
