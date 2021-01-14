package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.RecruitDetail;
import com.herocheer.instructor.dao.RecruitDetailDao;
import com.herocheer.instructor.service.RecruitDetailService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc  活动招募-明细 (RecruitDetail)表服务实现类
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class RecruitDetailServiceImpl extends BaseServiceImpl<RecruitDetailDao, RecruitDetail,Long> implements RecruitDetailService {
    
}