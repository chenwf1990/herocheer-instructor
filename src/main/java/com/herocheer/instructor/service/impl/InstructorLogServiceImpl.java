package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.instructor.dao.InstructorLogDao;
import com.herocheer.instructor.service.InstructorLogService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwf
 * @desc  指导员审批日志(InstructorLog)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class InstructorLogServiceImpl extends BaseServiceImpl<InstructorLogDao, InstructorLog,Long> implements InstructorLogService {

    /**
     * @param instructorId
     * @param auditState
     * @param auditIdea
     * @param remarks
     * @author chenwf
     * @desc 指导员审批日志(InstructorLog)表服务接口
     * @date 2021-01-04 17:26:18
     */
    @Override
    public void addLog(Long instructorId, int auditState, String auditIdea, String remarks) {
        InstructorLog log = new InstructorLog();
        log.setInstructorId(instructorId);
        log.setAuditState(auditState);
        log.setAuditIdea(auditIdea);
        log.setRemarks(remarks);
        this.dao.insert(log);
    }
}