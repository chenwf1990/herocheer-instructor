package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.InstructorApplyAuditLogDao;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApplyAuditLog)表服务实现类
 * @date 2021-01-29 09:02:43
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class InstructorApplyAuditLogServiceImpl extends BaseServiceImpl<InstructorApplyAuditLogDao, InstructorApplyAuditLog,Long> implements InstructorApplyAuditLogService {
    
}