package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.NewsNoticeDao;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.instructor.domain.vo.NewsQueryVo;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.enums.AuditUnitEnums;
import com.herocheer.instructor.service.NewsNoticeLogService;
import com.herocheer.instructor.service.NewsNoticeService;
import com.herocheer.instructor.service.SysRoleService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class NewsNoticeServiceImpl extends BaseServiceImpl<NewsNoticeDao, NewsNotice,Long> implements NewsNoticeService {
    @Resource
    private NewsNoticeLogService newsNoticeLogService;
    @Resource
    private SysRoleService sysRoleService;

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
        for (NewsNotice newsNotice : newsNotices) {
            if(newsNotice.getAuditState() != AuditStateEnums.to_pass.getState()){
                newsNotice.setIsPublic(2);
            }
        }
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
    @Transactional(rollbackFor = Exception.class)
    public long updateNewsNotice(NewsNotice newsNotice) {
        NewsNotice model = this.dao.get(newsNotice.getId());
        if(model.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("审核通过不能修改");
        }
        //修改成功设置为待审核
        if(newsNotice.getAuditState() == null) {
            newsNotice.setAuditState(AuditStateEnums.to_audit.getState());
        }
        return this.dao.update(newsNotice);
    }

    /**
     * @param id
     * @param auditState
     * @param auditIdea
     * @param curUserId
     * @return
     * @author chenwf
     * @desc 新闻活动审批
     * @date 2021-01-04 17:26:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long approval(Long id, int auditState, String auditIdea, Long curUserId) {
        NewsNotice newsNotice = this.dao.get(id);
        if(newsNotice.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("该新闻已审批");
        }
        //检测是否有审批权限
        boolean isAuth = sysRoleService.checkIsAuditAuth(curUserId, AuditUnitEnums.NEWS_AUDIT.getRoleCode());
        if(!isAuth){
            throw new CommonException("没有审批的权限");
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
    @Transactional(rollbackFor = Exception.class)
    public long addNews(NewsNotice newsNotice) {
        long count = this.dao.insert(newsNotice);
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
        List<Integer> auditStates = Arrays.asList(1,2);
        Map<String,Object> params = new HashMap<>();
        params.put("newsNoticeId",newsId);
        params.put("orderBy","id desc");
        List<NewsNoticeLog> list = newsNoticeLogService.findByLimit(params);
        list = list.stream().filter(s -> auditStates.contains(s.getAuditState())).collect(Collectors.toList());
        return list;
    }
}