package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.entity.Advert;
import com.rent.service.AdvertService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "广告接口")
@RequestMapping("/advert")
@CrossOrigin
public class AdvertController extends BaseController<Advert, Integer> {

    @Autowired
    private AdvertService advertService;

    @Override
    public AdvertService getService() {
        return advertService;
    }
}
