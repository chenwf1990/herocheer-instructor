package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.CourseScheduleDao;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.instructor.service.CourseScheduleService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gaorh
 * @desc 课表信息(CourseSchedule)表服务实现类
 * @date 2021-05-14 15:51:46
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourseScheduleServiceImpl extends BaseServiceImpl<CourseScheduleDao, CourseSchedule, Long> implements CourseScheduleService {

    /**
     * 批量添加课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    @Override
    public List<CourseSchedule> batchAddCourseSchedules(List<CourseSchedule> courseScheduleList) {
        this.dao.insertBatch(courseScheduleList);
        return courseScheduleList;
    }

    /**
     * batchupdate课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    @Override
    public int batchupdateCourseSchedules(List<CourseSchedule> courseScheduleList) {
        return this.dao.updateBatch(courseScheduleList);
    }

    /**
     * 通过id找到课程时间表
     *
     * @param id id
     * @return {@link CourseSchedule}
     */
    @Override
    public CourseSchedule findCourseSchedulesById(Long id) {
        return this.dao.selectCourseSchedule(id);
    }
}