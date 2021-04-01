package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.vo.CourseTearcherVO;
import com.herocheer.instructor.domain.vo.TearcherVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 授课老师(CourseTearcher)表数据库访问层
 * @date 2021-03-29 17:12:09
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseTearcherDao extends BaseDao<CourseTearcher, Long> {

    /**
     * 分页授课老师列表
     *
     * @param courseTearcherVO tearcherVO
     * @return {@link List<CourseTearcher>}
     */
    List<CourseTearcher>  selectTearcherByPage(CourseTearcherVO courseTearcherVO);

    /**
     * 老师信息
     *
     * @param paramMap 参数映射
     * @return {@link List<TearcherVO>}
     */
    List<TearcherVO>  selectCourseTearcher(Map<String, Object> paramMap);
}