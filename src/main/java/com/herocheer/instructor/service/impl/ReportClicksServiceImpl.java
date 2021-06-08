package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.ReportClicksDao;
import com.herocheer.instructor.domain.entity.ReportClicks;
import com.herocheer.instructor.domain.vo.ReportClicksStatisVO;
import com.herocheer.instructor.domain.vo.ReportClicksVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.service.ReportClicksService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gaorh
 * @desc 点击量(ReportClicks)表服务实现类
 * @date 2021-06-08 14:41:53
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ReportClicksServiceImpl extends BaseServiceImpl<ReportClicksDao, ReportClicks, Long> implements ReportClicksService {

    /**
     * 添加点击量
     *
     * @param reportClicks 点击报告
     * @param userInfo     用户信息
     * @return {@link ReportClicks}
     */
    @Override
    public ReportClicks addReportClicks(ReportClicks reportClicks, UserInfoVo userInfo) {
        reportClicks.setUserId(userInfo.getId());
        reportClicks.setUserName(userInfo.getUserName());
        this.insert(reportClicks);
        return reportClicks;
    }

    /**
     * 找到点击量列表
     *
     * @param reportClicksVO 报告点击签证官
     * @return {@link Page <ReportClicksStatisVO>}
     */
    @Override
    public Page<ReportClicksStatisVO> findReportClicksByPage(ReportClicksVO reportClicksVO) {
        Page<ReportClicksStatisVO> page = Page.startPage(reportClicksVO.getPageNo(), reportClicksVO.getPageSize());
        List<ReportClicksStatisVO> clicksList = this.dao.selectClicksByPage(reportClicksVO);
        page.setDataList(clicksList);
        return page;
    }
}
