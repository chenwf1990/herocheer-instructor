package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.common.base.service.BaseService;

import java.util.List;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表服务接口
 * @date 2021-01-20 10:14:13
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingReplaceCardService extends BaseService<WorkingReplaceCard,Long> {
    /**
     * @author chenwf
     * @desc  获取补卡信息列表
     * @date 2021-01-20 10:14:13
     * @param workingScheduleUserId
     * @return
     */
    List<WorkingReplaceCard> getReplaceCardList(Long workingScheduleUserId);
}