package com.rent.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rent.base.BaseController;
import com.rent.common.constant.CommonConstant;
import com.rent.common.utils.PageUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.common.utils.UserUtil;
import com.rent.service.RegionService;
import com.rent.service.UserService;
import com.rent.vo.PageVo;
import com.rent.vo.Result;
import com.rent.entity.Rent;
import com.rent.entity.User;
import com.rent.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;

    @Override
    public RentService getService() {
        return rentService;
    }

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(value = "/getListByPage",method = RequestMethod.GET)
    @ApiOperation(value = "前台分页获取发布通过信息")
    public Result<Object> getListByPage(@ModelAttribute PageVo page){

        Page<Rent> list=rentService.findByStatus(CommonConstant.STATUS_RENT_POST, PageUtil.initPage(page));
        return new ResultUtil<Object>().setData(list);
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ApiOperation(value = "发布信息", notes = "需要token获取发布者")
    public Result<Object> post(@RequestParam String token,
                               @ModelAttribute Rent rent){

        User user=userUtil.getUserInfo(token);
        rent.setUserId(user.getId());
        rent.setProvinceName(regionService.findByCode(rent.getProvince()).getName());
        rent.setCityName(regionService.findByCode(rent.getCity()).getName());
        rent.setAreaName(regionService.findByCode(rent.getArea()).getName());
        Rent r=rentService.save(rent);
        return new ResultUtil<Object>().setData(r);
    }

    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    @ApiOperation(value = "后台审核通过")
    public Result<Object> pass(@ApiParam("需要token获取管理员") @RequestParam String token,
                               @ApiParam("发布信息唯一id标识") @RequestParam Integer rentId){

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

    @RequestMapping(value = "/admin/back",method = RequestMethod.POST)
    @ApiOperation(value = "后台审核驳回")
    public Result<Object> back(@ApiParam("需要token获取管理员") @RequestParam String token,
                               @ApiParam("发布信息唯一id标识") @RequestParam Integer rentId,
                               @ApiParam("驳回理由") @RequestParam String reason){

        User user=userUtil.getUserInfo(token);
        if(!CommonConstant.TYPE_USER_ADMIN.equals(user.getType())){
            return new ResultUtil<Object>().setErrorMsg("您不具备管理员权限");
        }
        Rent rent=rentService.get(rentId);
        if(rent==null){
            return new ResultUtil<Object>().setErrorMsg("通过rentId获取发布信息失败");
        }
        rent.setStatus(CommonConstant.STATUS_RENT_BACK);
        rent.setBackReason(reason);
        rentService.update(rent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/admin/cancel",method = RequestMethod.POST)
    @ApiOperation(value = "后台下架")
    public Result<Object> cancel(@ApiParam("需要token获取管理员") @RequestParam String token,
                               @ApiParam("发布信息唯一id标识") @RequestParam Integer rentId){

        User user=userUtil.getUserInfo(token);
        if(!CommonConstant.TYPE_USER_ADMIN.equals(user.getType())){
            return new ResultUtil<Object>().setErrorMsg("您不具备管理员权限");
        }
        Rent rent=rentService.get(rentId);
        if(rent==null){
            return new ResultUtil<Object>().setErrorMsg("通过rentId获取发布信息失败");
        }
        rent.setStatus(CommonConstant.STATUS_RENT_CANCEL);
        rentService.update(rent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/getListByPage",method = RequestMethod.GET)
    @ApiOperation(value = "后台用户分页获取自己发布的信息")
    public Result<Object> getUserListByPage(@ModelAttribute PageVo pageVo,
                                            @ApiParam("需要token获取登录用户") @RequestParam String token){

        User user=userUtil.getUserInfo(token);
        Page<Rent> list=rentService.findByUserIdOrderByCreateTimeDesc(user.getId(), PageUtil.initPage(pageVo));
        return new ResultUtil<Object>().setData(list);
    }

    @RequestMapping(value = "/user/deal",method = RequestMethod.POST)
    @ApiOperation(value = "后台用户修改成交状态")
    public Result<Object> getUserListByPage(@ApiParam("需要token获取登录用户") @RequestParam String token,
                                            @ApiParam("发布信息唯一id标识") @RequestParam Integer rentId,
                                            @ApiParam("成交价格") @RequestParam BigDecimal dealPrice,
                                            @ApiParam("成交时间 接收格式yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) String dealTime){

        User user=userUtil.getUserInfo(token);
        Rent rent=rentService.get(rentId);
        rent.setDealStatus(CommonConstant.STATUS_RENT_DEAL);
        rent.setDealPrice(dealPrice);
        if(StrUtil.isNotBlank(dealTime)){
            rent.setDealTime(DateUtil.parse(dealTime, "yyyy-MM-dd HH:mm:ss"));
        }
        rentService.update(rent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/detail/{rentId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取发布信息详情")
    public Result<Object> getDetailById(@PathVariable Integer rentId){

        Rent rent=rentService.get(rentId);
        User user=userService.get(rent.getUserId());
        rent.setUsername(user.getUsername());
        return new ResultUtil<Object>().setData(rent);
    }

    @RequestMapping(value = "/viewCount/{rentId}",method = RequestMethod.GET)
    @ApiOperation(value = "记录浏览人数")
    public Result<Object> viewCount(@PathVariable Integer rentId){

        Rent rent=rentService.get(rentId);
        rent.setViewCount(rent.getViewCount()+1);
        rentService.update(rent);
        return new ResultUtil<Object>().setData(rent);
    }
}
