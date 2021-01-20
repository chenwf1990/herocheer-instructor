package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.dao.ActivityRecruitDetailDao;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc  活动招募-明细(ActivityRecruitDetail)表服务实现类
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ActivityRecruitDetailServiceImpl extends BaseServiceImpl<ActivityRecruitDetailDao, ActivityRecruitDetail,Long> implements ActivityRecruitDetailService {

    @Override
    public Long deleteDetailByRecruitId(Long recruitId) {
        return dao.deleteDetailByRecruitId(recruitId);
    }
}