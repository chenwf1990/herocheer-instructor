package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;

import java.util.List;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApply)表服务接口
 * @date 2021-01-29 08:50:36
 * @company 厦门熙重电子科技有限公司
 */
public interface InstructorApplyService extends BaseService<InstructorApply,Long> {

    /**
     * @author chenwf
     * @desc  指导员申请
     * @date 2021-01-29 08:50:36
     * @param instructorApply
     * @param curUserId
     * @return
     */
    int addInstructorApply(InstructorApply instructorApply, Long curUserId);

    /**
     * @author chenwf
     * @desc  编辑指导员申请
     * @date 2021-01-29 08:50:36
     * @param instructorApply
     * @return
     */
    int updateInstructor(InstructorApply instructorApply);

    /**
     * @author chenwf
     * @desc  指导员申请单审批
     * @date 2021-01-29 08:50:36
     * @param id
     * @param auditState
     * @param auditIdea
     */
    void approval(Long id, int auditState, String auditIdea);

    /**
     * 指导员申请单列表查询
     * @param instructorQueryVo
     * @return
     */
    Page<InstructorApply> queryPageList(InstructorQueryVo instructorQueryVo);

    /**
     * 获取指导员认证信息
     * @param curUserId
     * @param cardNo
     * @return
     */
    List<InstructorApply> getAuthInfo(Long curUserId, String cardNo);

    /**
     * 获取审批列表信息
     * @param applyId
     * @return
     */
    List<InstructorApplyAuditLog> getApprovalLog(Long applyId);
}