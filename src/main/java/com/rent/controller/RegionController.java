package com.rent.controller;

import com.rent.base.BaseController;
import com.rent.common.utils.ResultUtil;
import com.rent.vo.Result;
import com.rent.entity.Region;
import com.rent.service.RegionService;
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
@Api(description = "地区省市县接口")
@RequestMapping("/region")
@CrossOrigin
public class RegionController extends BaseController<Region,Integer>{

    @Autowired
    private RegionService rentService;

    @Override
    public RegionService getService() {
        return rentService;
    }

    @RequestMapping(value = "/getProvince",method = RequestMethod.GET)
    @ApiOperation(value = "获取省数据")
    public Result<Object> getProvince(){

        List<Region> list=rentService.findByParentIdOrderByOrderAsc(0);
        return new ResultUtil<Object>().setData(list);
    }

    @RequestMapping(value = "/getChildren/{regionId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取省或市子级数据")
    public Result<Object> getChildren(@PathVariable Integer regionId){

        List<Region> list=rentService.findByParentIdOrderByOrderAsc(regionId);
        return new ResultUtil<Object>().setData(list);
    }
}
