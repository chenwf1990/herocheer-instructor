package com.herocheer.instructor.service;

import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.CourseSchedule;

import java.util.List;

/**
 * @author gaorh
 * @desc 课表信息(CourseSchedule)表服务接口
 * @date 2021-05-14 15:51:44
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseScheduleService extends BaseService<CourseSchedule, Long> {

    /**
     * 批量添加课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    List<CourseSchedule> batchAddCourseSchedules(List<CourseSchedule> courseScheduleList);

    /**
     * batchupdate课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    int batchupdateCourseSchedules(List<CourseSchedule> courseScheduleList);


    /**
     * 通过id找到课程时间表
     *
     * @param id id
     * @return {@link CourseSchedule}
     */
    CourseSchedule findCourseSchedulesById(Long id);

    /**
     * 取消课表
     *
     * @param id           id
     * @param cancelReason 取消的原因
     * @return {@link CourseSchedule}
     */
    CourseSchedule cancelCourseSchedulesById(Long id,String cancelReason);
}