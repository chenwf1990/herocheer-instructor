package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 课表信息(CourseSchedule)表数据库访问层
 * @date 2021-05-14 15:51:42
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseScheduleDao extends BaseDao<CourseSchedule, Long> {


    /**
     * 批量插入
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    int insertBatch(List<CourseSchedule> courseScheduleList);

    /**
     * 批处理更新
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    int updateBatch(List<CourseSchedule> courseScheduleList);

    /**
     * 选择课程安排
     *
     * @param id id
     * @return {@link CourseSchedule}
     */
    CourseSchedule selectCourseSchedule(Long id);
}