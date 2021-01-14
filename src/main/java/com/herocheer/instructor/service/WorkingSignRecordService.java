package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.common.base.service.BaseService;

import java.util.List;

/**
 * @author chenwf
 * @desc  值班签到记录(WorkingSignRecord)表服务接口
 * @date 2021-01-12 11:17:12
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingSignRecordService extends BaseService<WorkingSignRecord,Long> {
    /**
     * @author chenwf
     * @desc  根据值班人员id获取打卡信息列表
     * @date 2021-01-12 11:17:12
     * @param workingScheduleUserId
     * @return
     */
    List<WorkingSignRecord> getPunchCardList(Long workingScheduleUserId);

    /**
     * @author chenwf
     * @desc  添加打卡信息
     * @date 2021-01-12 11:17:12
     * @param workingSignRecord
     * @return
     */
    long addWorkingSignRecord(WorkingSignRecord workingSignRecord);
}