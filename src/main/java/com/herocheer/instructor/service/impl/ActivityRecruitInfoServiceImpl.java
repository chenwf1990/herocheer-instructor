package com.herocheer.instructor.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.dao.ActivityRecruitInfoDao;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.enums.ActivityApprovalStateEnums;
import com.herocheer.instructor.enums.RecruitStateEnums;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.service.ActivityRecruitApprovalService;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  招募信息主表(ActivityRecruitInfo)表服务实现类
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ActivityRecruitInfoServiceImpl extends BaseServiceImpl<ActivityRecruitInfoDao, ActivityRecruitInfo,Long> implements ActivityRecruitInfoService {

    @Resource
    private ActivityRecruitDetailService activityRecruitDetailService;

    @Resource
    private ActivityRecruitApprovalService activityRecruitApprovalService;

    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    @Override
    public Page<ActivityRecruitInfo> queryPage(ActivityRecruitInfoQueryVo queryVo,Long userId) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        queryVo.setUserId(userId);
        List<ActivityRecruitInfo> instructors = dao.findList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public ActivityRecruitInfoVo getActivityRecruitInfo(Long id) {
        ActivityRecruitInfoVo activityRecruitInfoVo= dao.getActivityRecruitInfo(id);
        //获取赛事招募时段信息
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.MATCH_RECRUIT.getType()){
            Map<String,Object> map=new HashMap<>();
            map.put("recruitId",id);
            List<ActivityRecruitDetail> details=activityRecruitDetailService.findByLimit(map);
            if(details!=null){
                activityRecruitInfoVo.setRecruitDetails(details);
            }
        }
        return activityRecruitInfoVo;
    }

    @Override
    public Integer addActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo) {
        activityRecruitInfoVo.setStatus(RecruitStateEnums.PENDING.getState());
        Integer count=dao.insert(activityRecruitInfoVo);
        //保存赛事招募明细
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.MATCH_RECRUIT.getType()){
            if (activityRecruitInfoVo.getRecruitDetails()!=null){
                List<ActivityRecruitDetail> details=activityRecruitInfoVo.getRecruitDetails();
                for (ActivityRecruitDetail activityRecruitDetail:details){
                    activityRecruitDetail.setRecruitId(activityRecruitInfoVo.getId());
                    activityRecruitDetailService.insert(activityRecruitDetail);
                }
            }
        }
        return count;
    }

    @Override
    public Integer updateActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo) {
        if(activityRecruitInfoVo.getStatus()!= RecruitStateEnums.PENDING.getState()&&
                activityRecruitInfoVo.getStatus()!=RecruitStateEnums.WITHDRAW.getState()&&
                activityRecruitInfoVo.getStatus()!=RecruitStateEnums.OVERRULE.getState()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该状态下无法修改");
        }
        //如果状态为撤回或者驳回,修改时将状态更改为待审核
        if(activityRecruitInfoVo.getStatus()==RecruitStateEnums.WITHDRAW.getState()||
                activityRecruitInfoVo.getStatus()==RecruitStateEnums.OVERRULE.getState()){
            activityRecruitInfoVo.setStatus(RecruitStateEnums.PENDING.getState());
        }
        Integer count=dao.update(activityRecruitInfoVo);
        //保存赛事招募明细
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.MATCH_RECRUIT.getType()){
            if (activityRecruitInfoVo.getRecruitDetails()!=null){
                List<ActivityRecruitDetail> details=activityRecruitInfoVo.getRecruitDetails();
                for (ActivityRecruitDetail activityRecruitDetail:details){
                    if(activityRecruitDetail.getId()!=null){
                        activityRecruitDetailService.update(activityRecruitDetail);
                    }else {
                        activityRecruitDetail.setRecruitId(activityRecruitInfoVo.getId());
                        activityRecruitDetailService.insert(activityRecruitDetail);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public Integer deleteActivityRecruitInfo(Long id) {
        ActivityRecruitInfo activityRecruitInfo=dao.get(id);
        if(activityRecruitInfo.getStatus()!= RecruitStateEnums.PENDING.getState()&&
                activityRecruitInfo.getStatus()!=RecruitStateEnums.WITHDRAW.getState()&&
                activityRecruitInfo.getStatus()!=RecruitStateEnums.OVERRULE.getState()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该状态下无法删除");
        }
        Integer count=dao.delete(id);
        activityRecruitDetailService.deleteDetailByRecruitId(id);
        return count;
    }

    @Override
    public Integer deleteDetail(Long id) {
        return activityRecruitDetailService.delete(id);
    }

    @Override
    public Integer approval(ActivityRecruitApproval activityRecruitApproval) {
        ActivityRecruitInfo activityRecruitInfo=dao.get(activityRecruitApproval.getRecruitId());
        if(activityRecruitInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "招募信息不存在!");
        }
        if(activityRecruitInfo.getStatus()!=RecruitStateEnums.PENDING.getState()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该招募信息非待审核状态!");
        }
        if (activityRecruitApproval.getApprovalStatus()== ActivityApprovalStateEnums.PASSED.getState()){
            //审核通过
            activityRecruitInfo.setStatus(RecruitStateEnums.TO_RECRUITED.getState());
        }else if(activityRecruitApproval.getApprovalStatus()== ActivityApprovalStateEnums.OVERRULE.getState()){
            //审核驳回
            activityRecruitInfo.setStatus(RecruitStateEnums.OVERRULE.getState());
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR, "审核状态无效!");
        }
        activityRecruitApprovalService.insert(activityRecruitApproval);
        activityRecruitInfo.setApprovalStatus(activityRecruitApproval.getApprovalStatus());
        activityRecruitInfo.setApprovalComments(activityRecruitApproval.getApprovalComments());
        Integer count=dao.update(activityRecruitInfo);
        //生成驿站招募明细
        if(activityRecruitInfo.getStatus()==RecruitStateEnums.TO_RECRUITED.getState()
            &&activityRecruitInfo.getRecruitType()==RecruitTypeEunms.STATION_RECRUIT.getType()
            && StringUtils.isNotBlank(activityRecruitInfo.getServiceHours())){
            List<Map<String,String>> list = JSONArray.parseObject(activityRecruitInfo.getServiceHours(), List.class);
            ActivityRecruitDetail detail=new ActivityRecruitDetail();
            detail.setRecruitId(activityRecruitInfo.getId());
            detail.setRecruitNumber(activityRecruitInfo.getRecruitNumber());
            for(Long i=activityRecruitInfo.getServiceStartDate();i<activityRecruitInfo.getServiceEndDate();i=i+24*60*60*1000){
                for(Map<String,String> map:list){
                    detail.setServiceDate(i);
                    detail.setServiceStartTime(map.get("startTime"));
                    detail.setServiceEndTime(map.get("endTime"));
                    activityRecruitDetailService.insert(detail);
                    detail.setId(null);
                }
            }
        }
        return count;
    }

    @Override
    public List<ActivityRecruitApproval> approvalRecord(Long recruitId) {
        Map<String,Object> map=new HashMap<>();
        map.put("recruitId",recruitId);
        return activityRecruitApprovalService.findByLimit(map);
    }

    @Override
    public List<ActivityRecruitDetailVo> getRecruitHours(Long recruitId, Long dateTime, Long userId) {
        if(dateTime==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "查询日期不能为空!");
        }
        List<ActivityRecruitDetailVo> details=activityRecruitDetailService.getRecruitHours(recruitId,dateTime,dateTime+24*60*60*1000-1);
        List<ActivityRecruitDetailVo> recruitDetails=new ArrayList<>();
        for (ActivityRecruitDetailVo detail:details){
            if (detail.getRecruitNumber()<=detail.getHadRecruitNumber()){
                detail.setStatus(1);
            }else{
                Map<String,Object> map=new HashMap<>();
                map.put("userId",userId);
                map.put("scheduleTime",detail.getServiceDate());
                map.put("serviceBeginTime",detail.getServiceStartTime());
                map.put("serviceEndTime",detail.getServiceEndTime());
                List<String> userNames=workingScheduleUserService.findWorkingUser(map);
                if(userNames!=null && userNames.size()>0){
                    detail.setStatus(1);
                }
            }
            if(detail.getStatus()!=1){
                detail.setStatus(0);
            }
            recruitDetails.add(detail);
        }
        return recruitDetails;
    }
}