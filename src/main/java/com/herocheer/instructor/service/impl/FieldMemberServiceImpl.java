package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.FieldMemberDao;
import com.herocheer.instructor.domain.entity.FieldMember;
import com.herocheer.instructor.service.FieldMemberService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gaorh
 * @desc 场地采集人员(FieldMember)表服务实现类
 * @date 2021-04-21 16:01:52
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class FieldMemberServiceImpl extends BaseServiceImpl<FieldMemberDao, FieldMember, Long> implements FieldMemberService {

}