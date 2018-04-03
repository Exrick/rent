package com.rent.controller;

import com.rent.common.utils.CreateVerifyCode;
import com.rent.common.utils.ResultUtil;
import com.rent.vo.Captcha;
import com.rent.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "验证码接口")
@RequestMapping("/captcha")
@CrossOrigin
public class CaptchaController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    @ApiOperation(value = "初始化验证码")
    public Result<Object> initCaptcha() {

        String codeId= UUID.randomUUID().toString().replace("-","");
        String code=new CreateVerifyCode().randomStr(4);
        Captcha captcha=new Captcha();
        captcha.setCaptchaId(codeId);
        //缓存验证码
        stringRedisTemplate.opsForValue().set(codeId,code,3L, TimeUnit.MINUTES);
        return new ResultUtil<Object>().setData(captcha);
    }

    @RequestMapping(value = "/draw", method = RequestMethod.GET)
    @ApiOperation(value = "根据验证码ID获取图片")
    public void drawCaptcha(String codeId,HttpServletResponse response) throws IOException {

        //得到验证码 生成指定验证码
        String code=stringRedisTemplate.opsForValue().get(codeId);
        CreateVerifyCode vCode = new CreateVerifyCode(116,36,4,10,code);
        vCode.write(response.getOutputStream());
    }
}
