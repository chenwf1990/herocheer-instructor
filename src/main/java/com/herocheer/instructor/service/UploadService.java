package com.herocheer.instructor.service;

import com.herocheer.common.base.entity.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/25
 * @company 厦门熙重电子科技有限公司
 */
public interface UploadService {

    UploadFileVO uploadFile(MultipartFile file);

    void downloadFile(String filePath, HttpServletResponse response);
}
