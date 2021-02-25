package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.dao.WorkingScheduleUserDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ReservationInfoQueryVo;
import com.herocheer.instructor.domain.vo.ReservationInfoVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.enums.*;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.CommonService;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class WorkingScheduleUserServiceImpl extends BaseServiceImpl<WorkingScheduleUserDao, WorkingScheduleUser,Long> implements WorkingScheduleUserService {
    @Resource
    private WorkingScheduleDao workingScheduleDao;
    @Resource
    private ActivityRecruitInfoService activityRecruitInfoService;
    @Resource
    private CommonService commonService;
    @Resource
    private CourierStationService courierStationService;

    /**
     * @param workingScheduleUserQueryVo
     * @param userId
     * @return
     * @author chenwf
     * @desc 值班人员列表查询
     * @date 2021-01-12 08:57:02
     */
    @Override
    public Page<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo, Long userId) {
        Page page = Page.startPage(workingScheduleUserQueryVo.getPageNo(),workingScheduleUserQueryVo.getPageSize());
        List<WorkingSchedulsUserVo> dataList = this.dao.queryPageList(workingScheduleUserQueryVo);
        if(!dataList.isEmpty()){
            dataList.forEach(w ->{
                long scheduleBeginTime = w.getScheduleTime() + DateUtil.timeToUnix(w.getServiceBeginTime());
                if(w.getSignInTime() == null && System.currentTimeMillis() <= scheduleBeginTime){//服务开始时间大于当前时间不去设置状态
                    w.setSignStatus(-1);//前端打卡状态放空
                    w.setStatus(-1);//前端审核状态放空
                }else {
                    Long serviceBeginTime = w.getScheduleTime() + DateUtil.timeToUnix(w.getServiceBeginTime());
                    Long serviceEndTime = w.getScheduleTime() + DateUtil.timeToUnix(w.getServiceEndTime());
                    if (w.getStatus() == AuditStatusEnums.to_pass.getState()) {
                        w.setSignStatus(SignStatusEnums.SIGN_NORMAL.getStatus());
                    } else {
                        int signStatus = commonService.getPunchCardStatus(serviceBeginTime, serviceEndTime, w.getSignInTime(), w.getSignOutTime());
                        w.setSignStatus(signStatus);
                        if (signStatus != SignStatusEnums.SIGN_ABNORMAL.getStatus()) {
                            w.setSignStatus(SignStatusEnums.SIGN_NORMAL.getStatus());
                        }
                    }
                    //当天之前的都不做审核，前端审核状态放空 || 不是负责人不能审批
                    if(DateUtil.beginOfDay(new Date()).getTime() >= scheduleBeginTime || userId != w.getApproveId()){
                        w.setStatus(-1);//当天之前的都不做审核，前端审核状态放空
                    }
                    if(w.getReplaceCardState() > 0){
                        w.setReplaceCardState(1);
                    }
                    //计算超出时长
                    long time = serviceEndTime - serviceBeginTime;
                    long signInTime = w.getSignInTime() == null ? serviceBeginTime : w.getSignInTime();
                    long signOutTime = w.getSignOutTime() == null ? serviceEndTime : w.getSignOutTime();
                    w.setExceedServiceTime((int) ((signOutTime - signInTime - time) / 60 /1000));
                }
            });
        }
        page.setDataList(dataList);
        return page;
    }

    /**
     * @param workingScheduleUsers
     * @author chenwf
     * @desc 批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<WorkingScheduleUser> workingScheduleUsers) {
        this.dao.batchInsert(workingScheduleUsers);
    }

    /**
     * @param params
     * @author chenwf
     * @desc 根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteByMap(Map<String, Object> params) {
        return this.dao.deleteByMap(params);
    }

    /**
     * @param params
     * @return
     * @author chenwf
     * @desc 查找相同时间段的值班人员
     * @date 2021-01-12 08:57:02
     */
    @Override
    public List<String> findWorkingUser(Map<String, Object> params) {
        params.put("serviceBeginTime",DateUtil.addMin(String.valueOf(params.get("serviceBeginTime")),1));
        return this.dao.findWorkingUser(params);
    }

    /**
     * @param workingScheduleUserId
     * @param userId
     * @param replaceCardTime
     * @return
     * @author chenwf
     * @desc 更新打卡时间
     * @date 2021-01-20 20:57:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSignTime(Long workingScheduleUserId, Long userId, Long replaceCardTime) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
        Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime());
        int type = getPunchCardType(replaceCardTime,serviceBeginTime);
        WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
        scheduleUser.setId(workingUserVo.getWorkingScheduleUserId());
        if(type == SignType.SIGN_IN.getType()){//签到补卡
            //未签到或者签到时间 > 补卡时间，可更新
            if(workingUserVo.getSignInTime() == null || replaceCardTime < workingUserVo.getSignInTime()){
                scheduleUser.setSignInTime(replaceCardTime);
                scheduleUser.setServiceTime((int) (serviceEndTime - replaceCardTime));
                scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
                this.dao.update(scheduleUser);
            }
        }else{//签退补卡
            if(workingUserVo.getSignOutTime() == null || replaceCardTime > workingUserVo.getSignOutTime()){
                scheduleUser.setSignOutTime(replaceCardTime);
                scheduleUser.setServiceTime((int) (replaceCardTime - serviceBeginTime));
                scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
                this.dao.update(scheduleUser);
            }
        }
        return type;
    }

    /**
     * 根据打卡时间获取打卡类型
     * @param signTime
     * @param serviceBeginTime
     * @return
     */
    public int getPunchCardType(Long signTime, Long serviceBeginTime) {
        if(signTime < serviceBeginTime + DateUtil.ONE_HOURS) {//签到补卡
            return SignType.SIGN_IN.getType();
        }
        return SignType.SIGN_OUT.getType();
    }

    /**
     * @param idList
     * @return
     * @author chenwf
     * @desc 根据值班任务id删除值班人员
     * @date 2021-01-20 20:57:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByWorkingScheduleIds(List<Long> idList) {
        return this.dao.deleteByWorkingScheduleIds(idList);
    }

    /**
     * @param workingScheduleUserId
     * @param approvalType
     * @param approvalIdea
     * @param user
     * @param actualServiceTime
     * @return
     * @author chenwf
     * @desc 值班审核
     * @date 2021-01-22 09:57:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int approval(Long workingScheduleUserId, int approvalType, String approvalIdea, UserEntity user, int actualServiceTime) {
        //判断是否审批负责人
        WorkingUserVo workingUserVo = isHasApprovalAuth(workingScheduleUserId, user);
        WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
        int beginMinute = DateUtil.timeToSecond(workingUserVo.getServiceBeginTime());
        int endMinute = DateUtil.timeToSecond(workingUserVo.getServiceEndTime());
        if(approvalType == ApprovalTypeEnums.SIGN_TIME.getType()) {
            if(workingUserVo.getSignInTime() != null && workingUserVo.getSignOutTime() != null){
                actualServiceTime = (int) ((workingUserVo.getSignOutTime() - workingUserVo.getSignInTime()) / 60 /1000);
            }else{
                approvalType = ApprovalTypeEnums.SERVICE_TIME.getType();
                actualServiceTime = endMinute - beginMinute;
            }
        }else if(approvalType == ApprovalTypeEnums.SERVICE_TIME.getType()) {
            actualServiceTime = endMinute - beginMinute;
        }
        scheduleUser.setId(workingScheduleUserId);
        scheduleUser.setApprovalType(approvalType);
        scheduleUser.setApprovalIdea(approvalIdea);
        scheduleUser.setApprovalId(user.getId());
        scheduleUser.setApprovalTime(System.currentTimeMillis());
        scheduleUser.setActualServiceTime(actualServiceTime);
        scheduleUser.setStatus(AuditStatusEnums.to_pass.getState());
        return this.dao.update(scheduleUser);
    }


    //判断是否审批负责人
    private WorkingUserVo isHasApprovalAuth(Long workingScheduleUserId, UserEntity user) {
        //查找当前的负责人
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        if(user.getUserType() != UserTypeEnums.sysAdmin.getCode()) {
            if (workingUserVo.getActivityType() == RecruitTypeEunms.STATION_RECRUIT.getType()) {
                //查询驿站
                CourierStation courierStation = courierStationService.get(workingUserVo.getCourierStationId());
                if (courierStation.getUserId() != user.getId()) {
                    throw new CommonException("不是驿站负责人，不能审批");
                }
            } else if (workingUserVo.getActivityType() == RecruitTypeEunms.STATION_RECRUIT.getType()) {
                //查询活动
                ActivityRecruitInfo activityRecruitInfo = activityRecruitInfoService.get(workingUserVo.getActivityId());
                if (activityRecruitInfo.getMatchApproverId() != user.getId()) {
                    throw new CommonException("不是赛事负责人，不能审批");
                }
            }
        }
        return workingUserVo;
    }

    @Override
    public Page<ReservationInfoVo> findReservationInfoPage(ReservationInfoQueryVo queryVo,Long userId) {
        Page page=Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        if(queryVo.getType()!=null&&queryVo.getType()==2){
            queryVo.setReserveStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        }
        if(queryVo.getType()!=null&&queryVo.getType()==3){
            queryVo.setUserId(userId);
        }
        List<ReservationInfoVo> list=this.dao.findReservationInfo(queryVo);
        page.setDataList(list);
        return page;
    }
}