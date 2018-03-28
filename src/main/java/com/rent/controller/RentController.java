package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.base.BaseService;
import com.rent.common.constant.CommonConstant;
import com.rent.common.utils.PageUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.common.utils.UserUtil;
import com.rent.common.vo.PageVo;
import com.rent.common.vo.Result;
import com.rent.entity.Rent;
import com.rent.entity.User;
import com.rent.exception.RentException;
import com.rent.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(value = "/getListByPage",method = RequestMethod.GET)
    @ApiOperation(value = "前台分页获取发布通过信息")
    public Result<Object> getListByPage(@ModelAttribute PageVo page){

        List<Rent> list=rentService.findByStatusIs(CommonConstant.STATUS_RENT_POST, PageUtil.initPage(page));
        return new ResultUtil<Object>().setData(list);
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ApiOperation(value = "发布信息", notes = "需要token获取发布者")
    public Result<Object> post(@RequestParam String token,
                               @ModelAttribute Rent rent){

        User user=userUtil.getUserInfo(token);
        rent.setUserId(user.getId());
        Rent r=rentService.save(rent);
        return new ResultUtil<Object>().setData(r);
    }

    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    @ApiOperation(value = "后台审核通过", notes = "需要token获取管理员 rentId获取信息唯一id标识")
    public Result<Object> pass(@RequestParam String token,
                               @RequestParam Integer rentId){

        User user=userUtil.getUserInfo(token);
        if(!CommonConstant.TYPE_USER_ADMIN.equals(user.getType())){
            return new ResultUtil<Object>().setErrorMsg("您不具备管理员权限");
        }
        Rent rent=rentService.get(rentId);
        if(rent==null){
            return new ResultUtil<Object>().setErrorMsg("通过rentId获取发布信息失败");
        }
        rent.setStatus(CommonConstant.STATUS_RENT_POST);
        rentService.update(rent);
        return new ResultUtil<Object>().setData(null);
    }
}
