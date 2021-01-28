package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingReplaceCardDao;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.enums.ReissueCardEnums;
import com.herocheer.instructor.service.WorkingReplaceCardService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(workingReplaceCard.getReplaceCardTime() < System.currentTimeMillis()){
            throw new CommonException("当前时间段不能补卡");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingReplaceCard.getWorkingScheduleUserId());
        params.put("userId",userEntity.getId());
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
        Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime());
        if(!DateUtil.betweenTime(serviceBeginTime,serviceEndTime)){
            throw new CommonException("只能补当前值班日期的卡");
        }
        int type = workingScheduleUserService.getPunchCardType(workingReplaceCard.getReplaceCardTime(), serviceBeginTime);
        //查询是否补卡
        Map<String,Object> cardMap = new HashMap<>();
        cardMap.put("type",type);
        cardMap.put("workingScheduleUserId",workingReplaceCard.getWorkingScheduleUserId());
        cardMap.put("approvalStatusList", Arrays.asList(AuditStateEnums.to_pass.getState(),AuditStateEnums.to_audit.getState()));
        int replaceCardCount = this.dao.findReplaceCardCount(cardMap);
        if(replaceCardCount > 0){
            throw new CommonException("已补卡");
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
        WorkingReplaceCard workingReplaceCard = this.dao.get(id);
        if(workingReplaceCard.getApprovalStatus() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("已审核通过");
        }
        workingReplaceCard.setApprovalStatus(approvalStatus);
        workingReplaceCard.setApprovalComments(approvalComments);
        workingReplaceCard.setApprovalId(userEntity.getId());
        workingReplaceCard.setApprovalBy(userEntity.getUserName());
        workingReplaceCard.setApprovalTime(System.currentTimeMillis());
        int count = this.dao.update(workingReplaceCard);
        if(approvalStatus == AuditStateEnums.to_pass.getState()){//审核通过
            //审核通过更新用户签到时间
            int type = workingScheduleUserService.updateSignTime(workingReplaceCard.getWorkingScheduleUserId(), userEntity.getId(), workingReplaceCard.getReplaceCardTime());
            //添加打卡记录
            WorkingSignRecord signRecord = new WorkingSignRecord();
            signRecord.setWorkingScheduleUserId(workingReplaceCard.getWorkingScheduleUserId());
            signRecord.setReplaceCardId(workingReplaceCard.getId());
            signRecord.setSignTime(workingReplaceCard.getReplaceCardTime());
            signRecord.setType(workingReplaceCard.getType());
            signRecord.setIsReissueCard(ReissueCardEnums.NO.getState());
            signRecord.setType(type);
            workingSignRecordService.insert(signRecord);
        }
        return count;
    }
}