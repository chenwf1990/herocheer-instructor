package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.dao.CourseApprovalDao;
import com.herocheer.instructor.service.CourseApprovalService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc   课程审批表(CourseApproval)表服务实现类
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourseApprovalServiceImpl extends BaseServiceImpl<CourseApprovalDao, CourseApproval,Long> implements CourseApprovalService {
    
}