package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ReportClicks;
import com.herocheer.instructor.domain.vo.ReportClicksStatisVO;
import com.herocheer.instructor.domain.vo.ReportClicksVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 点击量(ReportClicks)表数据库访问层
 * @date 2021-06-08 14:41:52
 * @company 厦门熙重电子科技有限公司
 */
public interface ReportClicksDao extends BaseDao<ReportClicks, Long> {

    /**
     * 选择点击的页面
     *
     * @param reportClicksVO 报告点击签证官
     * @return {@link List<ReportClicksStatisVO>}
     */
    List<ReportClicksStatisVO> selectClicksByPage(ReportClicksVO reportClicksVO);

}
