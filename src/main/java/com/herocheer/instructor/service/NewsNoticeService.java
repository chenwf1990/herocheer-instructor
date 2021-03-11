package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.instructor.domain.vo.NewsQueryVo;

import java.util.List;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表服务接口
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface NewsNoticeService extends BaseService<NewsNotice,Long> {
    /**
     * @author chenwf
     * @desc  新闻活动查询列表
     * @date 2021-01-04 17:26:18
     * @param newsQueryVo
     * @return
     */
    Page<NewsNotice> queryPageList(NewsQueryVo newsQueryVo);

    /**
     * @author chenwf
     * @desc  更新新闻活动
     * @date 2021-01-04 17:26:18
     * @param newsNotice
     * @return
     */
    long updateNewsNotice(NewsNotice newsNotice);

    /**
     * @author chenwf
     * @desc  新闻活动审批
     * @date 2021-01-04 17:26:18
     * @param id
     * @param auditState
     * @param auditIdea
     * @param curUserId
     * @return
     */
    long approval(Long id, int auditState, String auditIdea, Long curUserId);


    /**
     * @author chenwf
     * @desc  添加新闻活动
     * @date 2021-01-04 17:26:18
     * @param newsNotice
     * @return
     */
    long addNews(NewsNotice newsNotice);

    /**
     * @author chenwf
     * @desc  新闻活动审批日志列表
     * @date 2021-01-04 17:26:18
     * @param newsId
     * @return
     */
    List<NewsNoticeLog> getApprovalLog(Long newsId);
}