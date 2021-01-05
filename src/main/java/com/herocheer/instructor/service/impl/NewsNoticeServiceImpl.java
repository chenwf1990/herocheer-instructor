package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.dao.NewsNoticeDao;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.NewsNoticeService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class NewsNoticeServiceImpl extends BaseServiceImpl<NewsNoticeDao, NewsNotice,Long> implements NewsNoticeService {

    /**
     * @param instructorQueryVo
     * @return
     * @author chenwf
     * @desc 新闻活动查询列表
     * @date 2021-01-04 17:26:18
     */
    @Override
    public Page<NewsNotice> queryPageList(InstructorQueryVo instructorQueryVo) {
        Page page = Page.startPage(instructorQueryVo.getPageNo(),instructorQueryVo.getPageSize());
        List<NewsNotice> newsNotices = this.dao.queryPageList(instructorQueryVo);
        page.setDataList(newsNotices);
        return page;
    }
}