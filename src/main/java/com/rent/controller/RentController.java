package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.base.BaseService;
import com.rent.common.utils.ResultUtil;
import com.rent.common.vo.Result;
import com.rent.entity.Rent;
import com.rent.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "房屋信息接口")
@RequestMapping("/rent")
@CrossOrigin
public class RentController extends BaseController<Rent,Integer>{

    @Autowired
    private RentService rentService;

    @Override
    public RentService getService() {
        return rentService;
    }

    @RequestMapping(value = "/post",method = RequestMethod.GET)
    @ApiOperation(value = "发布信息")
    public Result<Object> post(){


        return new ResultUtil<Object>().setData(null);
    }
}
