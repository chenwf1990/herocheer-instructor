package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ReservationInfoQueryVo;
import com.herocheer.instructor.domain.vo.ReservationInfoVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表服务接口
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleUserService extends BaseService<WorkingScheduleUser,Long> {
    /**
     * @author chenwf
     * @desc  批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     * @param workingScheduleUsers
     */
    void batchInsert(List<WorkingScheduleUser> workingScheduleUsers);

    /**
     * @author chenwf
     * @desc  根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     * @param params
     */
    long deleteByMap(Map<String, Object> params);

    /**
     * @author chenwf
     * @desc  值班人员列表查询
     * @date 2021-01-12 08:57:02
     * @param workingScheduleUserQueryVo
     * @param userEntity
     * @return
     */
    Page<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo, UserEntity userEntity);

    /**
     * @author chenwf
     * @desc  查找相同时间段的值班人员
     * @date 2021-01-12 08:57:02
     * @param params
     * @return
     */
    List<String> findWorkingUser(Map<String, Object> params);

    /**
     * @author chenwf
     * @desc  更新打卡时间
     * @date 2021-01-20 20:57:02
     * @param workingScheduleUserId
     * @param userId
     * @param replaceCardTime
     * @return
     */
    int updateSignTime(Long workingScheduleUserId, Long userId, Long replaceCardTime);

    /**
     * @author chenwf
     * @desc  根据打卡时间获取打卡类型
     * @date 2021-01-20 20:57:02
     * @param signTime
     * @param serviceBeginTime
     * @return
     */
    int getPunchCardType(Long signTime, Long serviceBeginTime);

    /**
     * @author chenwf
     * @desc  根据值班任务id删除值班人员
     * @date 2021-01-20 20:57:02
     * @param idList
     * @return
     */
    int deleteByWorkingScheduleIds(List<Long> idList);

    /**
     * @author chenwf
     * @desc  值班审核
     * @date 2021-01-22 09:57:02
     * @param workingScheduleUserId
     * @param approvalType
     * @param approvalIdea
     * @param user
     * @param actualServiceTime
     * @return
     */
    int approval(Long workingScheduleUserId, int approvalType, String approvalIdea, UserEntity user, int actualServiceTime);

    /**
     * 查询活动预约或服务记录
     * @param queryVo
     * @return
     */
    Page<ReservationInfoVo> findReservationInfoPage(ReservationInfoQueryVo queryVo,Long userId);

    /**
     * 修改预约状态
     * @param reserveStatus
     * @param activityId
     * @return
     */
    Integer updateReserveStatus(Integer reserveStatus,Long activityId);

    /**
     * 获取招募活动的打卡记录
     * @param activityId
     * @return
     */
    List<String> findSignRecord(Long activityId);
}