package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;

import java.util.List;

/**
 * @author makejava
 * @desc  招募信息主表(ActivityRecruitInfo)表服务接口
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitInfoService extends BaseService<ActivityRecruitInfo,Long> {

    /**
     * 分页查询招募信息列表
     * @param queryVo
     * @return
     */
    Page<ActivityRecruitInfo> queryPage(ActivityRecruitInfoQueryVo queryVo,Long userId);

    /**
     * 根据id查询招募信息详情
     * @param id
     * @return
     */
    ActivityRecruitInfoVo getActivityRecruitInfo(Long id);


    /**
     * 招募信息撤回
     * @param id
     * @return
     */
    Integer withdraw(Long id);

    /**
     * 保存招募信息
     * @param activityRecruitInfoVo
     * @return
     */
    Integer addActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo);

    /**
     * 更新招募信息
     * @param activityRecruitInfoVo
     * @return
     */
    Integer updateActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo);

    /**
     * 根据招募信息id删除招募信息
     * @param id
     * @return
     */
    Integer deleteActivityRecruitInfo(Long id);

    /**
     * 删除招募详情
     * @param id
     * @return
     */
    Integer deleteDetail(Long id);

    /**
     * 招募信息审批`
     * @param activityRecruitApproval
     * @return
     */
    Integer approval(ActivityRecruitApproval activityRecruitApproval);

    /**
     * 根据招募信息id获取招募信息列表
     * @param recruitId
     * @return
     */
    List<ActivityRecruitApproval> approvalRecord(Long recruitId);

    /**
     * 查询驿站的招募时间段
     * @param recruitId 招募活动Id
     * @param dateTime  查询日期-时间戳
     * @return
     */
    List<ActivityRecruitDetailVo> getRecruitHours(Long recruitId, Long dateTime, Long userId);
}