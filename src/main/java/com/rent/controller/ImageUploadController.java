package com.rent.controller;

import cn.hutool.core.util.StrUtil;
import com.rent.common.utils.QiniuUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
        String filePath = request.getSession().getServletContext().getRealPath("/upload")+"\\"
                + QiniuUtil.renamePic(file.getOriginalFilename());
        try {
            //保存至服务器
            File f=new File((filePath));
            if(!f.exists()){
                f.mkdirs();
            }
            file.transferTo(f);
            log.info("开始上传至七牛云");
            //上传七牛云服务器
            imagePath= QiniuUtil.qiniuUpload(filePath);
            if(StrUtil.isBlank(imagePath)){
                return new ResultUtil<Object>().setErrorMsg("上传失败，请检查七牛云配置");
            }
            log.info("上传成功:"+imagePath);
            // 路径为文件且不为空则进行删除
            if (f.isFile() && f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new ResultUtil<Object>().setData(imagePath);
    }
}
