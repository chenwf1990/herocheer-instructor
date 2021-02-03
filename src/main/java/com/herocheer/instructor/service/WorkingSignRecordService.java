package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.MatchSignRecordVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;

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
     * @param userEntity
     * @return
     */
    long addWorkingSignRecord(WorkingSignRecord workingSignRecord, UserEntity userEntity);

    /**
     * 获取签到状态 只返回 正常和异常
     * @param workingUserVo
     * @return
     */
    int getSignStatus(WorkingUserVo workingUserVo);

    /**
     * 查询赛事打卡记录
     * @param pageNo
     * @param pageSize
     * @param activityId
     * @param userName
     * @return
     */
    Page<MatchSignRecordVo> queryMatchSignRecord(Integer pageNo,Integer pageSize,Long activityId,String userName);

    List<WorkingSignRecord> getSignRecords(Long workingScheduleUserId);
}