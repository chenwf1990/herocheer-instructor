package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.RecruitApprovalLog;
import com.herocheer.instructor.dao.RecruitApprovalLogDao;
import com.herocheer.instructor.service.RecruitApprovalLogService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc  招募审批日志 (RecruitApprovalLog)表服务实现类
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class RecruitApprovalLogServiceImpl extends BaseServiceImpl<RecruitApprovalLogDao, RecruitApprovalLog,Long> implements RecruitApprovalLogService {
    
}