package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.entity.UploadFileVO;
import com.herocheer.instructor.service.UploadService;
import com.herocheer.instructor.utils.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        String fileName = file.getContentType();
        long time = System.currentTimeMillis();
        String newFileName = time + "." + fileName.substring(fileName.lastIndexOf("/") + 1);
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
}
