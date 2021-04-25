package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationListVO;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.ReservationVO;
import com.herocheer.instructor.domain.vo.SignInfoVO;

import java.util.List;

/**
 * @author makejava
 * @desc  预约记录(Reservation)表服务接口
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
public interface ReservationService extends BaseService<Reservation,Long> {

    /**
     * 课程预约
     *
     * @param reservationList 预订单
     * @param userId          用户id
     * @return {@link Reservation}
     */
    Reservation reservation(List<ReservationVO> reservationList, Long userId);

    /**
     * web端预约
     * @param reservation
     * @return
     */
    Integer webReservation(Reservation reservation);

    /**
     * 取消预约
     * @param id
     * @return
     */
    void cancel(Long id);

    /**
     * 查询预约记录
     * @param queryVo
     * @param userId
     * @return
     */
    Page<ReservationListVO> queryPage(ReservationQueryVo queryVo, Long userId);

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

    /**
     * 获取预约人员的
     * @param relevanceId
     * @return
     */
    List<String> findReservationOpenid(Long relevanceId,Integer type);


    /**
     * 添加签名信息
     *
     * @param userId          用户id
     * @param reservationList 预订单
     * @return {@link Long}
     */
    Long addSignInfo(List<ReservationVO> reservationList, Long userId);

    /**
     * 签到信息列表
     *
     * @param signInfoVO VO
     * @return {@link Page<SignInfoVO>}
     */
    Page<ReservationListVO> findSignInfoByPage(SignInfoVO signInfoVO);

    /**
     * 根据当前用户ID获取预约信息
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link List<Reservation>}
     */
    List<ReservationMember> findReservationByCurrentUserId(Long courseId, Long userId);

    /**
     * 线上签到
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link Long}
     */
    Long addOnlineSignInfo(Long courseId,Long userId);
}