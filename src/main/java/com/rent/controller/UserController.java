package com.rent.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.rent.base.BaseController;
import com.rent.common.utils.UserUtil;
import com.rent.entity.User;
import com.rent.service.UserService;
import com.rent.common.utils.ResultUtil;
import com.rent.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


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
    private UserUtil userUtil;

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
        String md5Pass = DigestUtils.md5DigestAsHex(u.getPassword().getBytes());
        User user=userService.findByUsername(u.getUsername());
        if(user==null){
            return new ResultUtil<Object>().setErrorMsg("用户不存在或已被拉黑");
        }
        if(!user.getPassword().equals(md5Pass)){
            return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
        }

        String key= UUID.randomUUID().toString().replace("-","");
        user.setToken(key);
        String value=new Gson().toJson(user);
        stringRedisTemplate.opsForValue().set(key,value,30L, TimeUnit.MINUTES);
        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/logout/{token}",method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout(@PathVariable String token){

        stringRedisTemplate.delete(token);
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
            return new ResultUtil<Object>().setErrorMsg("注册失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/info/{token}",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<Object> getUserInfo(@PathVariable String token){

        User user=userUtil.getUserInfo(token);
        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "修改资料",notes = "用户名密码不会修改 需要通过token获取原用户信息")
    public Result<Object> edit(@ModelAttribute User u){

        if(StrUtil.isBlank(u.getToken())){
            return new ResultUtil<Object>().setErrorMsg("token不能为空");
        }
        User old=userUtil.getUserInfo(u.getToken());

        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());

        User user=userService.update(u);
        if(user==null){
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        //更新登录用户资料
        user.setToken(u.getToken());
        String value=new Gson().toJson(user);
        stringRedisTemplate.opsForValue().set(u.getToken(),value,30L, TimeUnit.MINUTES);

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/modifyPass",method = RequestMethod.POST)
    @ApiOperation(value = "修改密码",notes = "需token、password、newPass参数")
    public Result<Object> modifyPass(@ModelAttribute User u){

        if(StrUtil.isBlank(u.getToken())){
            return new ResultUtil<Object>().setErrorMsg("token不能为空");
        }
        User old=userUtil.getUserInfo(u.getToken());

        String oldMd5Pass=DigestUtils.md5DigestAsHex(u.getPassword().getBytes());
        if(!old.getPassword().equals(oldMd5Pass)){
            return new ResultUtil<Object>().setErrorMsg("旧密码不正确");
        }

        String newMd5Pass=DigestUtils.md5DigestAsHex(u.getNewPass().getBytes());
        old.setPassword(newMd5Pass);
        User user=userService.update(old);
        if(user==null){
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

}
