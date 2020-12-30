package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.TestDao;
import com.herocheer.instructor.domain.Test;
import com.herocheer.instructor.service.TestService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chenwf
 * @desc
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestDao, Test,Long> implements TestService {
}
