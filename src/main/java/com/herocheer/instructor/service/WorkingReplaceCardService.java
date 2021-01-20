package com.herocheer.instructor.service;

import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.common.base.service.BaseService;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表服务接口
 * @date 2021-01-20 19:43:45
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingReplaceCardService extends BaseService<WorkingReplaceCard,Long> {
    /**
     * @author chenwf
     * @desc  添加补卡信息
     * @date 2021-01-20 19:43:45
     * @param workingReplaceCard
     * @param userEntity
     */
    int saveReplaceCard(WorkingReplaceCard workingReplaceCard, UserEntity userEntity);

    /**
     * @author chenwf
     * @desc  补卡审核
     * @date 2021-01-20 19:43:45
     * @param id
     * @param approvalStatus
     * @param approvalComments
     * @param userEntity
     * @return
     */
    int approval(Long id, int approvalStatus, String approvalComments, UserEntity userEntity);
}