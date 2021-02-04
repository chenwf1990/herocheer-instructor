package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.instructor.dao.NewsNoticeLogDao;
import com.herocheer.instructor.service.NewsNoticeLogService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwf
 * @desc  新闻公告log(NewsNoticeLog)表服务实现类
 * @date 2021-01-06 09:18:39
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class NewsNoticeLogServiceImpl extends BaseServiceImpl<NewsNoticeLogDao, NewsNoticeLog,Long> implements NewsNoticeLogService {

    /**
     * @param id
     * @param auditState
     * @param remarks
     * @author chenwf
     * @desc 添加审核日志
     * @date 2021-01-06 09:18:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLog(Long id, Integer auditState,String auditIdea, String remarks) {
        NewsNoticeLog log = new NewsNoticeLog();
        log.setNewsNoticeId(id);
        log.setAuditState(auditState);
        log.setAuditIdea(auditIdea);
        log.setRemarks(remarks);
        this.dao.insert(log);
    }
}