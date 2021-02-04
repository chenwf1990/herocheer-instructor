package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingReplaceCardDao;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.enums.*;
import com.herocheer.instructor.service.WorkingReplaceCardService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表服务实现类
 * @date 2021-01-20 19:43:45
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingReplaceCardServiceImpl extends BaseServiceImpl<WorkingReplaceCardDao, WorkingReplaceCard,Long> implements WorkingReplaceCardService {
    @Resource
    private WorkingSignRecordService workingSignRecordService;
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;
    @Resource
    private WorkingScheduleDao workingScheduleDao;

    /**
     * @param workingScheduleUserId
     * @return
     * @author chenwf
     * @desc 获取补卡列表
     * @date 2021-01-21 08:43:45
     */
    @Override
    public List<WorkingReplaceCard> getReplaceCardList(Long workingScheduleUserId) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("orderBy","id");
        List<WorkingReplaceCard> workingReplaceCards = this.dao.findByLimit(params);
        return workingReplaceCards;
    }

    /**
     * @param workingReplaceCard
     * @param userEntity
     * @author chenwf
     * @desc 添加补卡信息
     * @date 2021-01-20 19:43:45
     */
    @Override
    public int saveReplaceCard(WorkingReplaceCard workingReplaceCard, UserEntity userEntity) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingReplaceCard.getWorkingScheduleUserId());
        params.put("userId",userEntity.getId());
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
        if(!DateUtil.betweenTime(DateUtil.beginOfDay(
                new Date(workingUserVo.getScheduleTime())).getTime(),
                DateUtil.endOfDay(new Date(workingUserVo.getScheduleTime())).getTime(),
                workingReplaceCard.getReplaceCardTime())){
            throw new CommonException("只能补当前值班日期的卡");
        }
        int type = workingScheduleUserService.getPunchCardType(workingReplaceCard.getReplaceCardTime(), serviceBeginTime);
        if(type == SignType.SIGN_IN.getType()){
            if(workingUserVo.getSignInTime() != null && workingReplaceCard.getReplaceCardTime() > workingUserVo.getSignInTime()){
                throw new CommonException("补卡时间不能大于签到时间");
            }
        }else{
            if(workingUserVo.getSignOutTime() != null && workingReplaceCard.getReplaceCardTime() < workingUserVo.getSignOutTime()){
                throw new CommonException("补卡时间不能小于签退时间");
            }
        }
        //查询是否补卡
        Map<String,Object> cardMap = new HashMap<>();
        cardMap.put("type",type);
        cardMap.put("workingScheduleUserId",workingReplaceCard.getWorkingScheduleUserId());
        cardMap.put("approvalStatusList", Arrays.asList(AuditStateEnums.to_audit.getState()));
        int replaceCardCount = this.dao.findReplaceCardCount(cardMap);
        if(replaceCardCount > 0){
            throw new CommonException("补卡审核中..请勿重复申请");
        }
        workingReplaceCard.setType(type);
        workingReplaceCard.setApprovalStatus(AuditStateEnums.to_audit.getState());
        return this.dao.insert(workingReplaceCard);
    }

    /**
     * @param id
     * @param approvalStatus
     * @param approvalComments
     * @param userEntity
     * @return
     * @author chenwf
     * @desc 补卡审核
     * @date 2021-01-20 19:43:45
     */
    @Override
    public int approval(Long id, int approvalStatus, String approvalComments, UserEntity userEntity) {
        WorkingReplaceCard card = this.dao.get(id);
        if(card.getApprovalStatus() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("已审核通过");
        }
        card.setApprovalStatus(approvalStatus);
        card.setApprovalComments(approvalComments);
        card.setApprovalId(userEntity.getId());
        card.setApprovalBy(userEntity.getUserName());
        card.setApprovalTime(System.currentTimeMillis());
        int count = this.dao.update(card);
        int type = card.getType();
        if(approvalStatus == AuditStateEnums.to_pass.getState()){//审核通过
            //添加打卡记录
            WorkingSignRecord signRecord = new WorkingSignRecord();
            signRecord.setWorkingScheduleUserId(card.getWorkingScheduleUserId());
            signRecord.setReplaceCardId(card.getId());
            signRecord.setSignTime(card.getReplaceCardTime());
            signRecord.setType(card.getType());
            signRecord.setIsReissueCard(ReissueCardEnums.NO.getState());
            signRecord.setType(type);
            workingSignRecordService.insert(signRecord);
            //审核通过，用户值班表也需要做更新
            Map<String,Object> params = new HashMap<>();
            params.put("workingScheduleUserId",card.getWorkingScheduleUserId());
            List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
            WorkingUserVo workingUserVo = workingUserVos.get(0);
            WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
            scheduleUser.setId(workingUserVo.getWorkingScheduleUserId());
            //备注：补卡时间只能再签到时间和签退时间之间
            if(card.getType() == SignType.SIGN_IN.getType()){
                //无签到 || 签到时间 > 补卡时间
                if(workingUserVo.getSignInTime() == null || workingUserVo.getSignInTime() > card.getReplaceCardTime()){
                    scheduleUser.setSignInTime(card.getReplaceCardTime());
                    scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
                    if(workingUserVo.getSignOutTime() != null){
                        scheduleUser.setStatus(AuditStatusEnums.to_pass.getState());
                        int serviceTime = (int) (workingUserVo.getSignOutTime() - card.getReplaceCardTime());
                        scheduleUser.setServiceTime(serviceTime / 60 / 1000);
                        scheduleUser.setActualServiceTime(scheduleUser.getServiceTime());
                        scheduleUser.setApprovalType(ApprovalTypeEnums.SIGN_TIME.getType());
                        scheduleUser.setApprovalTime(System.currentTimeMillis());
                        scheduleUser.setApprovalId(userEntity.getId());
                    }
                }else{
                    throw new CommonException("补卡时间只能再签到时间和签退时间之间");
                }
            }else{
                if(workingUserVo.getSignOutTime() == null || workingUserVo.getSignOutTime() > card.getReplaceCardTime()){
                    scheduleUser.setSignOutTime(card.getReplaceCardTime());
                    scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
                    if(workingUserVo.getSignInTime() != null){
                        scheduleUser.setStatus(AuditStatusEnums.to_pass.getState());
                        int serviceTime = (int) (card.getReplaceCardTime() - workingUserVo.getSignInTime());
                        scheduleUser.setServiceTime(serviceTime / 60 / 1000);
                        scheduleUser.setActualServiceTime(scheduleUser.getServiceTime());
                        scheduleUser.setApprovalType(ApprovalTypeEnums.SIGN_TIME.getType());
                        scheduleUser.setApprovalTime(System.currentTimeMillis());
                        scheduleUser.setApprovalId(userEntity.getId());
                    }
                }else {
                    throw new CommonException("补卡时间只能再签到时间和签退时间之间");
                }
            }
            workingScheduleUserService.update(scheduleUser);
        }
        return count;
    }
}