package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.common.constant.CommonConstant;
import com.rent.common.utils.PageUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.common.utils.UserUtil;
import com.rent.common.vo.PageVo;
import com.rent.common.vo.Result;
import com.rent.entity.Rent;
import com.rent.entity.SearchVo;
import com.rent.entity.User;
import com.rent.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "房屋信息搜索接口")
@RequestMapping("/rent")
@CrossOrigin
public class SearchController{

    @Autowired
    private RentService rentService;

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "前台分页获取发布通过信息")
    public Result<Object> getListByPage(@ModelAttribute SearchVo searchVo,
                                        @ModelAttribute PageVo page){

        Page<Rent> list=rentService.searchRent(searchVo,PageUtil.initPage(page));
        return new ResultUtil<Object>().setData(list);
    }
}
