package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.dao.WorkingReplaceCardDao;
import com.herocheer.instructor.service.WorkingReplaceCardService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表服务实现类
 * @date 2021-01-20 10:14:13
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingReplaceCardServiceImpl extends BaseServiceImpl<WorkingReplaceCardDao, WorkingReplaceCard,Long> implements WorkingReplaceCardService {

    /**
     * @param workingScheduleUserId
     * @return
     * @author chenwf
     * @desc 获取补卡信息列表
     * @date 2021-01-20 10:14:13
     */
    @Override
    public List<WorkingReplaceCard> getReplaceCardList(Long workingScheduleUserId) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("orderBy","id");
        return this.dao.findByLimit(params);
    }
}