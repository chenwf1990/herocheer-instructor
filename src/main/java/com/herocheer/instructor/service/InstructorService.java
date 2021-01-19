package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.InstructorCert;
import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @desc  指导员审批
     * @date 2021-01-04 17:26:18
     * @param id
     * @param auditState
     * @param auditIdea
     * @return
     */
    void approval(Long id, int auditState, String auditIdea);

    /**
     * @author chenwf
     * @desc  指导员审批日志列表
     * @date 2021-01-04 17:26:18
     * @param instructorId
     * @return
     */
    List<InstructorLog> getApprovalLog(Long instructorId);

    /**
     * @author chenwf
     * @desc  添加指导员
     * @date 2021-01-04 17:26:18
     * @param instructor
     * @param userId
     */
    void addInstructor(Instructor instructor, Long userId);

    /**
     * @author chenwf
     * @desc  编剧指导员
     * @date 2021-01-04 17:26:18
     * @param instructor
     * @return
     */
    long updateInstructor(Instructor instructor);

    void loginTest(String token);

    /**
     * @author chenwf
     * @desc  指导员证书修改列表
     * @date 2021-01-14 17:26:18
     * @param instructorId
     * @return
     */
    List<InstructorCert> getInstructorCertList(Long instructorId);

    /**
     * @author chenwf
     * @desc  指导员导入
     * @date 2021-01-14 17:26:18
     * @param multipartFile
     * @param request
     * @return
     */
    void instructorImport(MultipartFile multipartFile, HttpServletRequest request);
}