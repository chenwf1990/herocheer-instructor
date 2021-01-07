package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.common.base.service.BaseService;

/**
 * @author chenwf
 * @desc  新闻公告log(NewsNoticeLog)表服务接口
 * @date 2021-01-06 09:18:39
 * @company 厦门熙重电子科技有限公司
 */
public interface NewsNoticeLogService extends BaseService<NewsNoticeLog,Long> {
    /**
     * @author chenwf
     * @desc  添加审核日志
     * @date 2021-01-06 09:18:39
     * @param id
     * @param auditState
     * @param auditIdea
     * @param remarks
     */
    void addLog(Long id, int auditState, String auditIdea, String remarks);
}