package com.rent.controller;

import com.rent.utils.CreateVerifyCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Exrickx
 */
@RestController
public class CaptchaController {

    @RequestMapping("/captcha")
    public void drawCaptcha(HttpSession session, HttpServletResponse response) throws IOException {

        CreateVerifyCode vCode = new CreateVerifyCode(116,36,4,10);
        session.setAttribute("code", vCode.getCode());
        vCode.write(response.getOutputStream());
    }
}
