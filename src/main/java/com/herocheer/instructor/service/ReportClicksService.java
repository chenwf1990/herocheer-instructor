package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.ReportClicks;
import com.herocheer.instructor.domain.vo.ReportClicksStatisVO;
import com.herocheer.instructor.domain.vo.ReportClicksVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;

/**
 * @author gaorh
 * @desc 点击量(ReportClicks)表服务接口
 * @date 2021-06-08 14:41:52
 * @company 厦门熙重电子科技有限公司
 */
public interface ReportClicksService extends BaseService<ReportClicks, Long> {

    /**
     * 添加点击量
     *
     * @param reportClicks 点击报告
     * @param userInfo     用户信息
     * @return {@link ReportClicks}
     */
    ReportClicks addReportClicks(ReportClicks reportClicks, UserInfoVo userInfo);

    /**
     * 找到点击量列表
     *
     * @param reportClicksVO 报告点击签证官
     * @return {@link Page<ReportClicksStatisVO>}
     */
    Page<ReportClicksStatisVO> findReportClicksByPage(ReportClicksVO reportClicksVO);
}
