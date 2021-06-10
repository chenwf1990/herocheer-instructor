package com.herocheer.instructor.service;

import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.domain.vo.ReservationMemberInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc (ReservationMember)表服务接口
 * @date 2021-04-20 14:26:38
 * @company 厦门熙重电子科技有限公司
 */
public interface ReservationMemberService extends BaseService<ReservationMember, Long> {

    /**
     * 预约人信息
     *
     * @param paramMap 参数映射
     * @return {@link List<ReservationMemberInfoVO>}
     */
    List<ReservationMemberInfoVO> findReservationMemberByCourseScheduleId(Map<String, Object> paramMap);

}