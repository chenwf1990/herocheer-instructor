package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.ActivityRecord;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.ActivityReplaceCard;
import com.herocheer.instructor.domain.entity.ActivitySignRecord;
import com.herocheer.instructor.domain.vo.ActivityRecordApprovalVo;
import com.herocheer.instructor.domain.vo.ActivityRecordQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecordVo;

import java.util.List;

/**
 * @author makejava
 * @desc  活动记录表(ActivityRecord)表服务接口
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecordService extends BaseService<ActivityRecord,Long> {
    /**
     * 查询活动记录列表-分页
     * @param queryVo
     * @return
     */
    Page<ActivityRecordVo> queryPage(ActivityRecordQueryVo queryVo);

    /**
     * 根据活动记录id查询打卡记录
     * @param recordId
     * @return
     */
    List<ActivitySignRecord> getSignByRecordId(Long recordId);

    /**
     * 根据活动记录id查询补卡记录
     * @param recordId
     * @return
     */
    List<ActivityReplaceCard> getReplaceCardByRecordId(Long recordId);

    /**
     * 活动记录审批
     * @param approvalVo
     * @return
     */
    Long approval(ActivityRecordApprovalVo approvalVo, UserEntity userEntity);
}