package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表数据库访问层
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseInfoDao extends BaseDao<CourseInfo,Long>{

    List<CourseInfoVo> queryList(CourseInfoQueryVo queryVo);

    CourseInfoVo getCourseInfo(@Param("id") Long id);

}