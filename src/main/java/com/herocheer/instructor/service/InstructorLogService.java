package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.common.base.service.BaseService;

/**
 * @author chenwf
 * @desc  指导员审批日志(InstructorLog)表服务接口
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface InstructorLogService extends BaseService<InstructorLog,Long> {
    /**
     * @author chenwf
     * @desc  指导员审批日志(InstructorLog)表服务接口
     * @date 2021-01-04 17:26:18
     * @param instructorId
     * @param auditState
     * @param auditIdea
     * @param remarks
     */
    void addLog(Long instructorId, int auditState, String auditIdea, String remarks);
}