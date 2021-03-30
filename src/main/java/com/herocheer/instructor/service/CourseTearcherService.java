package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.vo.CourseTearcherVO;
import com.herocheer.instructor.domain.vo.TearcherVO;

import java.util.List;

/**
 * @author gaorh
 * @desc 授课老师(CourseTearcher)表服务接口
 * @date 2021-03-29 17:12:09
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseTearcherService extends BaseService<CourseTearcher, Long> {

    /**
     * 添加授课老师
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link CourseTearcher}
     */
    CourseTearcher addCourseTearcher(CourseTearcherVO courseTearcherVO);

    /**
     * 修改授课老师
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link CourseTearcher}
     */
    CourseTearcher modifyCourseTearcher(CourseTearcherVO courseTearcherVO);

    /**
     * 分页授课老师列表
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link Page<CourseTearcher>}
     */
    Page<CourseTearcher> findCourseTearcherByPage(CourseTearcherVO courseTearcherVO);

    /**
     * 查询授课老师
     *
     * @param name 的名字
     * @return {@link List<CourseTearcher>}
     */
    List<TearcherVO> findCourseTearcherByName(String name);

}