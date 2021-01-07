package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.vo.NewsQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表数据库访问层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface NewsNoticeDao extends BaseDao<NewsNotice,Long>{
    /**
     * @author chenwf
     * @desc  新闻活动查询列表
     * @date 2021-01-04 17:26:18
     * @param newsQueryVo
     * @return
     */
    List<NewsNotice> queryPageList(NewsQueryVo newsQueryVo);
}