package com.herocheer.instructor.controller;

import cn.hutool.core.net.URLDecoder;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UploadFileVO;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.service.UploadService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Value("${file.upload.savePath}")
    private String savePath;
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


    @PostMapping(value = "/uploadBigFile")
    @ApiOperation(value = "大文件file")
    @AllowAnonymous
    public ResponseResult<UploadFileVO> uploadBigFile(HttpServletRequest request,HttpServletResponse response){
        Integer schunk = null;
        Integer schunks = null;
        String name = null;
        String dr = "instructor/" + DateUtil.format(new Date(), DateUtil.IMAGE_PARAENT);
        String uploadPath = savePath + dr;
        BufferedOutputStream os = null;
        response.setCharacterEncoding("UTF-8");
        UploadFileVO fileVO = new UploadFileVO();
        try {
            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);//设置文件阈限
            factory.setRepository(new File(uploadPath));//设置上传路径
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(5L * 1024L * 1024L * 1024L);//设置单个文件上传大小
            upload.setSizeMax(10L * 1024L * 1024L * 1024L);// 设置总文件上传大小
            //它是用于解析request对象，得到所有上传项.每一个FileItem就相当于一个上传项.
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if(item.isFormField()){
                    if("chunk".equals(item.getFieldName())){
                        schunk = Integer.parseInt(item.getString("utf-8"));
                    }
                    if("chunks".equals(item.getFieldName())){
                        schunks = Integer.parseInt(item.getString("utf-8"));
                    }
                    if("name".equals(item.getFieldName())){
                        name = item.getString("utf-8");
                    }
                }
            }
            for (FileItem item : items) {
                if(!item.isFormField()){
                    String temFileName = name;
                    if(name != null){
                        if(schunk != null){
                            temFileName = schunk + "_" + name;
                        }
                        File temFile = new File(uploadPath,temFileName);
                        if(!temFile.exists()){//断点续传
                            item.write(temFile);
                        }
                    }
                }
            }
            //文件合并
            if(schunk != null && schunk.intValue() == schunks.intValue() - 1){
                File tempFile = new File(uploadPath,name);
                os = new BufferedOutputStream(new FileOutputStream(tempFile));
                for (int i = 0; i < schunks; i++) {
                    File f = new File(uploadPath,i + "_" + name);
                    while (!f.exists()){
                        Thread.sleep(100);
                    }
                    byte[] bytes = FileUtils.readFileToByteArray(f);
                    os.write(bytes);
                    os.flush();
                    f.delete();
                }
                os.flush();
            }

            fileVO.setRelativeFilePath("/" + dr + "/" + name);
            fileVO.setFilePath(visitPath + dr + "/" + name);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null){
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return ResponseResult.ok(fileVO);
    }
}
