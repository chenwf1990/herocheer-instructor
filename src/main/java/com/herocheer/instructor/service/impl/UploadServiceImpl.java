package com.herocheer.instructor.service.impl;

import cn.hutool.core.net.URLDecoder;
import com.herocheer.common.base.entity.UploadFileVO;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.service.UploadService;
import com.herocheer.instructor.utils.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/25
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Value("${file.upload.savePath}")
    private String savePath;
    @Value("${file.upload.visitPath}")
    private String visitPath;


    @Override
    public UploadFileVO uploadFile(MultipartFile multipartFile) {
        return uploadFile(multipartFile,savePath);
    }


    private UploadFileVO uploadFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        long time = System.currentTimeMillis();
        String newFileName = time + fileName.substring(fileName.lastIndexOf("."));
        String dr = "instructor/" + DateUtil.format(new Date(), DateUtil.IMAGE_PARAENT);
        File tempFile = new File(path + dr);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        String url = path + dr + "/" + newFileName;
        try {
            file.transferTo(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadFileVO fileVO = new UploadFileVO();
        fileVO.setRelativeFilePath("/" + dr + "/" + newFileName);
        fileVO.setFilePath(visitPath + dr + "/" + newFileName);
        return fileVO;
    }

    @Override
    public void downloadFile(String filePath, HttpServletResponse response) {
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
    }
}
