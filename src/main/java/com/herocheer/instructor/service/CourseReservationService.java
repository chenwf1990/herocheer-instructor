package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.CourseReservation;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.CourseReservationVo;

/**
 * @author makejava
 * @desc   课程预约表(CourseReservation)表服务接口
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseReservationService extends BaseService<CourseReservation,Long> {
    /**
     * 课程预约
     * @param courseReservationVo
     * @param userId
     * @return
     */
    Integer reservation(CourseReservationVo courseReservationVo,Long userId);

    /**
     * 取消预约
     * @param id
     * @return
     */
    Integer cancel(Long id);

    /**
     * 查询预约信息
     * @param queryVo
     * @param userId
     * @return
     */
    Page<CourseInfoVo> queryPage(CourseInfoQueryVo queryVo,Long userId);

    /**
     * 根据id查询预约信息
     * @param id
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CourseReservation> getReservationPage(Long id,Integer status,Integer pageNo,Integer pageSize);
}