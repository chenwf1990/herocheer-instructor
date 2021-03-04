package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.dao.WorkingSignRecordDao;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.MatchSignRecordVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.enums.AuditStatusEnums;
import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.enums.SignType;
import com.herocheer.instructor.service.CommonService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  值班签到记录(WorkingSignRecord)表服务实现类
 * @date 2021-01-12 11:17:13
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class WorkingSignRecordServiceImpl extends BaseServiceImpl<WorkingSignRecordDao, WorkingSignRecord,Long> implements WorkingSignRecordService {
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;
    @Resource
    private WorkingScheduleDao workingScheduleDao;
    @Resource
    private CommonService commonService;
    /**
     * @param workingScheduleUserId
     * @return
     * @author chenwf
     * @desc 根据值班人员id获取打卡信息列表
     * @date 2021-01-12 11:17:12
     */
    @Override
    public List<WorkingSignRecord> getPunchCardList(Long workingScheduleUserId) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("orderBy","signTime");
        List<WorkingSignRecord> signRecords = this.dao.findByLimit(params);
        return signRecords;
    }

    /**
     * @param workingSignRecord
     * @param userEntity
     * @return
     * @author chenwf
     * @desc 添加打卡信息
     * @date 2021-01-12 11:17:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addWorkingSignRecord(WorkingSignRecord workingSignRecord, UserEntity userEntity) {
        workingSignRecord.setSignTime(System.currentTimeMillis());//设置打卡时间
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingSignRecord.getWorkingScheduleUserId());
        params.put("userId",userEntity.getId());
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        if(workingUserVos.isEmpty()){
            throw new CommonException("没有该值班人员");
        }
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime()) + 59 * 1000;
        Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime()) + 59 * 1000;
        if(!DateUtil.betweenTime()){
            throw new CommonException("当天值班才能打卡");
        }
        int signStatus = getSignStatus(workingUserVo);//计算打卡状态
        int signType = workingScheduleUserService.getPunchCardType(workingSignRecord.getSignTime(),serviceBeginTime);

        workingSignRecord.setSignStatus(signStatus);
        workingSignRecord.setType(signType);

        WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
        scheduleUser.setId(workingUserVo.getWorkingScheduleUserId());
        if(signType == SignType.SIGN_IN.getType()){
            if(workingUserVo.getSignInTime() == null){//打上班卡已第一次为准
                scheduleUser.setSignInTime(workingSignRecord.getSignTime());
            }else{
                scheduleUser.setServiceTime((int) ((workingSignRecord.getSignTime() - workingUserVo.getSignInTime()) / 60 / 1000));
            }
            workingScheduleUserService.update(scheduleUser);
        }else{//打下班卡已最后一次为准
            //获取签到的第一条数据
            Map<String,Object> signRecordMap = new HashMap<>();
            signRecordMap.put("workingScheduleUserId",workingSignRecord.getWorkingScheduleUserId());
            signRecordMap.put("orderBy","signTime");
            List<WorkingSignRecord> signRecords = this.dao.findByLimit(signRecordMap);
            if(!signRecords.isEmpty()){//判断是否有第一次打卡时间
                scheduleUser.setServiceTime((int) ((workingSignRecord.getSignTime() - signRecords.get(0).getSignTime()) / 60 / 1000));
            }
            scheduleUser.setSignOutTime(workingSignRecord.getSignTime());
            //判断早晚打卡是否有存在异常
            scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
            if(workingUserVo.getSignInTime() != null && serviceBeginTime >= workingUserVo.getSignInTime()
                    && serviceEndTime + DateUtil.TWO_HOURS >= workingSignRecord.getSignTime()){
                //早晚打卡都是正常的，不需要审核
                scheduleUser.setStatus(AuditStatusEnums.to_pass.getState());
            }
            workingScheduleUserService.update(scheduleUser);
        }
        return this.dao.insert(workingSignRecord);
    }

    /**
     * 获取签到状态 只返回 正常和异常
     * @param workingUserVo
     * @return
     */
    @Override
    public int getSignStatus(WorkingUserVo workingUserVo) {
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
        Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime());
        serviceBeginTime = serviceBeginTime + 59 * 1000;//准点准时打卡也算正常打卡
        serviceEndTime = serviceEndTime + 59 * 1000;//准点准时打卡也算正常打卡
        //获取打卡状态
        Long curTime = System.currentTimeMillis();
        if(serviceBeginTime >= curTime || serviceEndTime + DateUtil.TWO_HOURS >= curTime){
            return SignStatusEnums.SIGN_NORMAL.getStatus();
        }
        return SignStatusEnums.SIGN_ABNORMAL.getStatus();
    }

    @Override
    public Page<MatchSignRecordVo> queryMatchSignRecord(Integer pageNo, Integer pageSize, Long activityId, String userName) {
        Page page=Page.startPage(pageNo,pageSize);
        List<MatchSignRecordVo> list=this.dao.findMatchSignRecord(activityId,userName);
        page.setDataList(list);
        return page;
    }

    @Override
    public List<WorkingSignRecord> getSignRecords(Long workingScheduleUserId) {
        Map<String,Object> signMap = new HashMap<>();
        signMap.put("workingScheduleUserId",workingScheduleUserId);
        signMap.put("orderBy","signTime");
        List<WorkingSignRecord> signRecords = this.dao.findByLimit(signMap);
        return signRecords;
    }
}