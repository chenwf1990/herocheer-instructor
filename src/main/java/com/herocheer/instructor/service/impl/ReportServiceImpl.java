package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.WorkingScheduleUserDao;
import com.herocheer.instructor.domain.vo.CourseStatisVO;
import com.herocheer.instructor.domain.vo.DutyStatisVO;
import com.herocheer.instructor.domain.vo.MatchStatisVO;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.domain.vo.ServiceTotalVO;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private WorkingScheduleUserDao workingScheduleUserDao;

    @Resource
    private CourseInfoService courseInfoService;
    /**
     * @param serviceHoursQueryVo
     * @return
     * @author chenwf
     * @desc 服务报表统计
     * @date 2021/1/28
     */
    @Override
    public Page<ServiceHoursReportVo> serviceHoursReport(ServiceHoursQueryVo serviceHoursQueryVo) {
        Page page = Page.startPage(serviceHoursQueryVo.getPageNo(),serviceHoursQueryVo.getPageSize());
        List<ServiceHoursReportVo> reportVos = workingScheduleUserDao.serviceHoursReport(serviceHoursQueryVo);
        page.setDataList(reportVos);
        return page;
    }

    /**
     * 值班服务时长统计
     *
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link Page< DutyStatisVO >}
     */
    @Override
    public Page<DutyStatisVO> findDutyStatisByPage(DutyStatisVO dutyStatisVO) {
        Page page = Page.startPage(dutyStatisVO.getPageNo(),dutyStatisVO.getPageSize());
        List<DutyStatisVO> dutyStatisList = workingScheduleUserDao.selectDutyStatisByPage(dutyStatisVO);
        page.setDataList(dutyStatisList);
        return page;
    }

    /**
     * 值班服务时长统计
     *
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link List<DutyStatisVO>}
     */
    @Override
    public List<DutyStatisVO> findDutyStatis(DutyStatisVO dutyStatisVO) {
        return workingScheduleUserDao.selectDutyStatisByPage(dutyStatisVO);
    }

    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @return {@link Page<DutyStatisVO>}
     */
    @Override
    public Page<MatchStatisVO> findMatchStatisByPage(MatchStatisVO matchStatisVO) {
        Page page = Page.startPage(matchStatisVO.getPageNo(),matchStatisVO.getPageSize());
        List<MatchStatisVO> matchStatisList = workingScheduleUserDao.selectMatchStatisByPage(matchStatisVO);
        page.setDataList(matchStatisList);
        return page;
    }

    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @return {@link List<MatchStatisVO>}
     */
    @Override
    public List<MatchStatisVO> findMatchStatis(MatchStatisVO matchStatisVO) {
        return workingScheduleUserDao.selectMatchStatisByPage(matchStatisVO);
    }

    /**
     * 课程服务时长统计
     *
     * @param courseStatisVO 当然统计学的签证官
     * @return {@link Page< CourseStatisVO >}
     */
    @Override
    public Page<CourseStatisVO> findCourseStatisByPage(CourseStatisVO courseStatisVO) {
        Page page = Page.startPage(courseStatisVO.getPageNo(),courseStatisVO.getPageSize());
        List<CourseStatisVO> courseStatisList = courseInfoService.findCourseStatisByPage(courseStatisVO);
        page.setDataList(courseStatisList);
        return page;
    }

    /**
     * 课程服务时长统计
     *
     * @param courseStatisVO 当然统计学的签证官
     * @return {@link List<CourseStatisVO>}
     */
    @Override
    public List<CourseStatisVO> findCourseStatis(CourseStatisVO courseStatisVO) {
        return courseInfoService.findCourseStatisByPage(courseStatisVO);
    }

    /**
     * 服务时长汇总
     *
     * @param serviceTotalVO 服务总签证官
     * @return {@link Page< ServiceTotalVO >}
     */
    @Override
    public Page<ServiceTotalVO> findTotalStatisByPage(ServiceTotalVO serviceTotalVO) {
        Page page = Page.startPage(serviceTotalVO.getPageNo(),serviceTotalVO.getPageSize());
        List<ServiceTotalVO> serviceTotalList = workingScheduleUserDao.selectTotalStatisByPage(serviceTotalVO);
        page.setDataList(serviceTotalList);
        return page;
    }

    /**
     * 服务时长汇总
     *
     * @param serviceTotalVO 服务总签证官
     * @return {@link List<ServiceTotalVO>}
     */
    @Override
    public List<ServiceTotalVO> findTotalStatis(ServiceTotalVO serviceTotalVO) {
        return workingScheduleUserDao.selectTotalStatisByPage(serviceTotalVO);
    }
}
