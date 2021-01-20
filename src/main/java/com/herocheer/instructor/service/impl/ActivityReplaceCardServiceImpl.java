package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.ActivityReplaceCard;
import com.herocheer.instructor.dao.ActivityReplaceCardDao;
import com.herocheer.instructor.service.ActivityReplaceCardService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc  活动补卡(ActivityReplaceCard)表服务实现类
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ActivityReplaceCardServiceImpl extends BaseServiceImpl<ActivityReplaceCardDao, ActivityReplaceCard,Long> implements ActivityReplaceCardService {
    
}