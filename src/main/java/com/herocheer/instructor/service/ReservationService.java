package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;

/**
 * @author makejava
 * @desc  预约记录(Reservation)表服务接口
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
public interface ReservationService extends BaseService<Reservation,Long> {

    /**
     * 课程预约
     * @param courseId
     * @param userId
     * @return
     */
    Integer reservation(Long courseId, Long userId);

    /**
     * 取消预约
     * @param id
     * @return
     */
    Integer cancel(Long id);

    /**
     * 查询预约记录
     * @param queryVo
     * @param userId
     * @return
     */
    Page<Reservation> queryPage(ReservationQueryVo queryVo, Long userId);

    /**
     * 查询招募预约详情
     * @param id
     * @return
     */
    ActivityRecruitInfoVo getActivity(Long id);

    /**
     * 获取课程预约详情
     * @param id
     * @return
     */
    CourseInfoVo getCourse(Long id);

    /**
     * 批量修改预约状态
     * @param status
     * @param relevanceId
     * @param type
     * @return
     */
    Integer updateReservationStatus(Integer status,Long relevanceId,Integer type);
}