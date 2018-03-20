package com.rent.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class Captcha implements Serializable{

    private String captchaId;

    private String code;
}
