package com.rent.controller;

import com.rent.common.utils.CreateVerifyCode;
import com.rent.common.utils.ResultUtil;
import com.rent.common.vo.Captcha;
import com.rent.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrickx
 */
@RestController
public class CaptchaController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/initCaptcha")
    public Result<Object> initCaptcha() {

        String codeId= UUID.randomUUID().toString();
        String code=new CreateVerifyCode().randomStr(4);
        Captcha captcha=new Captcha();
        captcha.setCaptchaId(codeId);
        //缓存验证码
        stringRedisTemplate.opsForValue().set(codeId,code,5L, TimeUnit.MINUTES);
        return new ResultUtil<Object>().setData(captcha);
    }

    @RequestMapping("/drawCaptcha")
    public void drawCaptcha(String codeId,HttpServletResponse response) throws IOException {

        //得到验证码 生成指定验证码
        String code=stringRedisTemplate.opsForValue().get(codeId);
        CreateVerifyCode vCode = new CreateVerifyCode(116,36,4,10,code);
        vCode.write(response.getOutputStream());
    }
}
