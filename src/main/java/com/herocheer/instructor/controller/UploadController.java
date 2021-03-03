package com.herocheer.instructor.controller;

import cn.hutool.core.net.URLDecoder;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UploadFileVO;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.service.UploadService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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
    @Value("${file.upload.visitPath}")
    private String visitPath;

    @Resource
    private UploadService uploadService;

    @PostMapping(value = "/uploadFile")
    @ApiOperation(value = "上传文件file")
    @AllowAnonymous
    public ResponseResult<UploadFileVO> uploadFile(MultipartFile file){
        UploadFileVO uploadFile = uploadService.uploadFile(file);
        return ResponseResult.ok(uploadFile);
    }

    @GetMapping(value = "/downloadFile")
    @ApiOperation(value = "下载文件")
    @AllowAnonymous
    public ResponseResult downloadFile(@ApiParam("文件地址") @RequestParam String filePath, HttpServletResponse response){
        InputStream is = null;
        BufferedOutputStream out = null;
        try {
            String fileUrl = filePath;
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            if(!filePath.contains("http") && !filePath.contains("https")){
                fileUrl = visitPath + filePath;
            }
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            fileUrl = fileUrl.substring(0,fileUrl.lastIndexOf("/"))+"/"+ URLDecoder.decode(fileName, Charset.defaultCharset());
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                out = new BufferedOutputStream(response.getOutputStream());
                //创建一个Buffer字符串
                byte[] buffer = new byte[1048576];
                //每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                //使用一个输入流从buffer里把数据读取出来
                while( (len=is.read(buffer)) != -1 ){
                    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    out.write(buffer, 0, len);
                    out.flush();
                }
            }else{
                throw new CommonException("文件链接请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseResult.ok();
    }


    @PostMapping(value = "/upload")
    @ApiOperation(value = "富文本编辑器上传文件api")
    @AllowAnonymous
    public Map<String, Object> upload(MultipartFile file){
        UploadFileVO uploadFile = uploadService.uploadFile(file);
        Map<String,Object> map = new HashMap<>();
        map.put("link",uploadFile.getFilePath());
        return map;
    }
}
