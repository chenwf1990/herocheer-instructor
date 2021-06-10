package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.ReservationMemberDao;
import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.domain.vo.ReservationMemberInfoVO;
import com.herocheer.instructor.service.ReservationMemberService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc (ReservationMember)表服务实现类
 * @date 2021-04-20 14:26:38
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ReservationMemberServiceImpl extends BaseServiceImpl<ReservationMemberDao, ReservationMember, Long> implements ReservationMemberService {

    /**
     * 预约人信息
     *
     * @param paramMap 参数映射
     * @return {@link List < ReservationMemberInfoVO >}
     */
    @Override
    public List<ReservationMemberInfoVO> findReservationMemberByCourseScheduleId(Map<String, Object> paramMap) {
        return this.dao.selectReservationMemberByCourseScheduleId(paramMap);
    }
}