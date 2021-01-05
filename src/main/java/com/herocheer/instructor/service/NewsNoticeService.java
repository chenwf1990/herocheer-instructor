package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;

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
     * @param instructorQueryVo
     * @return
     */
    Page<NewsNotice> queryPageList(InstructorQueryVo instructorQueryVo);
}