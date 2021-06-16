package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.vo.CourseStatisVO;
import com.herocheer.instructor.domain.vo.DutyStatisVO;
import com.herocheer.instructor.domain.vo.MatchStatisVO;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.domain.vo.ServiceTotalVO;

import java.util.List;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
public interface ReportService {
    /**
     * @author chenwf
     * @desc 服务报表统计
     * @date 2021/1/28
     * @param serviceHoursQueryVo
     * @return
     */
    Page<ServiceHoursReportVo> serviceHoursReport(ServiceHoursQueryVo serviceHoursQueryVo);

    /**
     * 值班服务时长统计
     *
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link Page<DutyStatisVO>}
     */
    Page<DutyStatisVO> findDutyStatisByPage(DutyStatisVO dutyStatisVO);

    /**
     * 值班服务时长统计
     *
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link List<DutyStatisVO>}
     */
    List<DutyStatisVO> findDutyStatis(DutyStatisVO dutyStatisVO);


    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @return {@link Page<DutyStatisVO>}
     */
    Page<MatchStatisVO> findMatchStatisByPage(MatchStatisVO matchStatisVO);

    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @return {@link List<MatchStatisVO>}
     */
    List<MatchStatisVO> findMatchStatis(MatchStatisVO matchStatisVO);

    /**
     * 课程服务时长统计
     *
     * @param courseStatisVO 当然统计学的签证官
     * @return {@link Page<CourseStatisVO>}
     */
    Page<CourseStatisVO> findCourseStatisByPage(CourseStatisVO courseStatisVO);

    /**
     * 课程服务时长统计
     *
     * @param courseStatisVO 当然统计学的签证官
     * @return {@link List<CourseStatisVO>}
     */
    List<CourseStatisVO> findCourseStatis(CourseStatisVO courseStatisVO);

    /**
     * 服务时长汇总
     *
     * @param serviceTotalVO 服务总签证官
     * @return {@link Page<ServiceTotalVO>}
     */
    Page<ServiceTotalVO> findTotalStatisByPage(ServiceTotalVO serviceTotalVO);

    /**
     * 服务时长汇总
     *
     * @param serviceTotalVO 服务总签证官
     * @return {@link List<ServiceTotalVO>}
     */
    List<ServiceTotalVO> findTotalStatis(ServiceTotalVO serviceTotalVO);
}
