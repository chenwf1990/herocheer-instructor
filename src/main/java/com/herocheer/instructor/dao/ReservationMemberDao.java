package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.domain.vo.ReservationMemberInfoVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc (ReservationMember)表数据库访问层
 * @date 2021-04-20 14:26:38
 * @company 厦门熙重电子科技有限公司
 */
public interface ReservationMemberDao extends BaseDao<ReservationMember, Long> {

    /**
     * 预约人信息
     *
     * @param paramMap 参数映射
     * @return {@link List<ReservationMemberInfoVO>}
     */
    List<ReservationMemberInfoVO>  selectReservationMemberByCourseScheduleId(Map<String, Object> paramMap);

}