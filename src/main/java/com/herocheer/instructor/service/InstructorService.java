package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenwf
 * @desc  指导员表(Instructor)表服务接口
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface InstructorService extends BaseService<Instructor,Long> {
    /**
     * @author chenwf
     * @desc  指导员列表查询
     * @date 2021-01-04 17:26:18
     * @param instructorQueryVo
     * @return
     */
    Page<Instructor> queryPageList(InstructorQueryVo instructorQueryVo);

    /**
     * @author chenwf
     * @desc  指导员导入
     * @date 2021-01-14 17:26:18
     * @param multipartFile
     * @param request
     * @return
     */
    void instructorImport(MultipartFile multipartFile, HttpServletRequest request);

    /**
     * @author chenwf
     * @desc  根据用户id查找指导员信息
     * @date 2021-01-21 09:26:18
     * @param userId
     * @return
     */
    Instructor findInstructorByUserId(Long userId);

    /**
     * 添加指导员
     * @param instructorApply
     */
    Instructor saveInstructor(InstructorApply instructorApply);

    int deleteInstructor(Long id);
}