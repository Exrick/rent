package com.rent.controller;

import cn.hutool.core.util.StrUtil;
import com.rent.common.utils.QiniuUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "图片上传接口")
@RequestMapping("/image")
@CrossOrigin
public class ImageUploadController {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ApiOperation(value = "图片上传")
    public Result<Object> upload(@RequestParam("file") MultipartFile file,
                                 HttpServletRequest request) {

        String imagePath=null;
        String fileName = QiniuUtil.renamePic(file.getOriginalFilename());
        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            //上传七牛云服务器
            imagePath= QiniuUtil.qiniuInputStreamUpload(inputStream,fileName);
            if(StrUtil.isBlank(imagePath)){
                return new ResultUtil<Object>().setErrorMsg("上传失败，请检查七牛云配置");
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new ResultUtil<Object>().setData(imagePath);
    }
}
