package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.common.base.service.BaseService;

/**
 * @author makejava
 * @desc  活动招募-明细(ActivityRecruitDetail)表服务接口
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitDetailService extends BaseService<ActivityRecruitDetail,Long> {

    Long deleteDetailByRecruitId(Long recruitId);

}