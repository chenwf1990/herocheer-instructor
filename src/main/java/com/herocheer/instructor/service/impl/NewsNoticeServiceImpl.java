package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.NewsNoticeDao;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.instructor.domain.vo.NewsQueryVo;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.service.NewsNoticeLogService;
import com.herocheer.instructor.service.NewsNoticeService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class NewsNoticeServiceImpl extends BaseServiceImpl<NewsNoticeDao, NewsNotice,Long> implements NewsNoticeService {
    @Resource
    private NewsNoticeLogService newsNoticeLogService;

    /**
     * @param newsQueryVo
     * @return
     * @author chenwf
     * @desc 新闻活动查询列表
     * @date 2021-01-04 17:26:18
     */
    @Override
    public Page<NewsNotice> queryPageList(NewsQueryVo newsQueryVo) {
        Page page = Page.startPage(newsQueryVo.getPageNo(),newsQueryVo.getPageSize());
        List<NewsNotice> newsNotices = this.dao.queryPageList(newsQueryVo);
        page.setDataList(newsNotices);
        return page;
    }

    /**
     * @param newsNotice
     * @return
     * @author chenwf
     * @desc 更新新闻活动
     * @date 2021-01-04 17:26:18
     */
    @Override
    public long updateNewsNotice(NewsNotice newsNotice) {
        NewsNotice model = this.dao.get(newsNotice.getId());
        if(model.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("审核通过不能修改");
        }
        //修改成功设置为待审核
        newsNotice.setAuditState(AuditStateEnums.to_audit.getState());
        newsNoticeLogService.addLog(newsNotice.getId(),newsNotice.getAuditState(),newsNotice.getAuditIdea(),"修改");
        return this.dao.update(newsNotice);
    }

    /**
     * @param id
     * @param auditState
     * @param auditIdea
     * @return
     * @author chenwf
     * @desc 新闻活动审批
     * @date 2021-01-04 17:26:18
     */
    @Override
    public long approval(Long id, int auditState, String auditIdea) {
        NewsNotice newsNotice = this.dao.get(id);
        if(newsNotice.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("该新闻已审批");
        }
        newsNotice.setAuditState(auditState);
        newsNotice.setAuditTime(System.currentTimeMillis());
        newsNotice.setAuditIdea(auditIdea);
        long count = this.dao.update(newsNotice);
        newsNoticeLogService.addLog(id,auditState,newsNotice.getAuditIdea(),"审核");
        return count;
    }

    /**
     * @param newsNotice
     * @return
     * @author chenwf
     * @desc 添加新闻活动
     * @date 2021-01-04 17:26:18
     */
    @Override
    public long addNews(NewsNotice newsNotice) {
        long count = this.dao.insert(newsNotice);
        newsNoticeLogService.addLog(newsNotice.getId(),newsNotice.getAuditState(),newsNotice.getAuditIdea(),"新增");
        return count;
    }

    /**
     * @param newsId
     * @return
     * @author chenwf
     * @desc 新闻活动审批日志列表
     * @date 2021-01-04 17:26:18
     */
    @Override
    public List<NewsNoticeLog> getApprovalLog(Long newsId) {
        Map<String,Object> params = new HashMap<>();
        params.put("newsNoticeId",newsId);
        params.put("orderBy","id desc");
        return newsNoticeLogService.findByLimit(params);
    }
}