package com.rent.controller;

import com.rent.common.utils.IpInfoUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "IP接口")
@RequestMapping("/ip")
@CrossOrigin
public class IpLocateController {

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value = "IP信息")
    public Result<Object> upload(HttpServletRequest request) {

        String result= IpInfoUtil.getIpInfo(IpInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }
}
