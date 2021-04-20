package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.ReservationMemberDao;
import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.service.ReservationMemberService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gaorh
 * @desc (ReservationMember)表服务实现类
 * @date 2021-04-20 14:26:38
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ReservationMemberServiceImpl extends BaseServiceImpl<ReservationMemberDao, ReservationMember, Long> implements ReservationMemberService {

}