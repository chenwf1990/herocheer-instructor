package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UploadFileVO;
import com.herocheer.instructor.service.UploadService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc 图片，文件上传，下载功能
 * @date 2021/1/25
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping("/upload")
@Api(tags = "图片，文件上传，下载api")
public class UploadController {
    @Resource
    private UploadService uploadService;

    @PostMapping(value = "/uploadFile")
    @ApiOperation(value = "上传文件file")
    @AllowAnonymous
    public ResponseResult<UploadFileVO> uploadFile(MultipartFile file){
        UploadFileVO uploadFile = uploadService.uploadFile(file);
        return ResponseResult.ok(uploadFile);
    }
}
