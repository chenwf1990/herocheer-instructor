package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenwf
 * @desc  指导员表(Instructor)表数据库访问层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface InstructorDao extends BaseDao<Instructor,Long>{

    /**
     * @param instructorQueryVo
     * @return
     * @author chenwf
     * @desc 指导员列表查询
     * @date 2021-01-04 17:26:18
     */
    List<Instructor> queryPageList(InstructorQueryVo instructorQueryVo);

    /**
     * @author chenwf
     * @desc 批量插入指导员数据
     * @date 2021-01-18 17:26:18
     * @param instructors
     * @return
     */
    long batchInsert(@Param("list") List<Instructor> instructors);
}