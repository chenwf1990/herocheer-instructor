package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.InstructorCert;
import com.herocheer.instructor.dao.InstructorCertDao;
import com.herocheer.instructor.service.InstructorCertService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorCert)表服务实现类
 * @date 2021-01-14 15:55:06
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class InstructorCertServiceImpl extends BaseServiceImpl<InstructorCertDao, InstructorCert,Long> implements InstructorCertService {
    
}