package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysMessageEvent;
import com.herocheer.instructor.dao.ActivityRecruitInfoDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.ApplicationListVo;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.enums.ActivityApprovalStateEnums;
import com.herocheer.instructor.enums.RecruitStateEnums;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.ActivityRecruitApprovalService;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.instructor.utils.SpringUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
public class ActivityRecruitInfoServiceImpl extends BaseServiceImpl<ActivityRecruitInfoDao, ActivityRecruitInfo,Long> implements ActivityRecruitInfoService {

    @Resource
    private ActivityRecruitDetailService activityRecruitDetailService;

    @Resource
    private ActivityRecruitApprovalService activityRecruitApprovalService;

    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    @Resource
    private UserService userService;

    @Resource
    private ReservationService reservationService;

    @Resource
    private WechatService wechatService;

    @Override
    public Page<ActivityRecruitInfo> queryPage(ActivityRecruitInfoQueryVo queryVo,Long userId) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        queryVo.setUserId(userId);
        if (queryVo.getType()!=null&&queryVo.getType()==2){
            queryVo.setApprovalStatus(ActivityApprovalStateEnums.PENDING.getState());
        }
        List<ActivityRecruitInfo> instructors = this.dao.findList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public ActivityRecruitInfoVo getActivityRecruitInfo(Long id) {
        ActivityRecruitInfoVo activityRecruitInfoVo= this.dao.getActivityRecruitInfo(id);
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
    public Integer withdraw(Long id) {
        ActivityRecruitInfo activityRecruitInfo=new ActivityRecruitInfo();
        activityRecruitInfo.setId(id);
        activityRecruitInfo.setApprovalStatus(ActivityApprovalStateEnums.WITHDRAW.getState());
        return  this.dao.update(activityRecruitInfo);
    }

    @Override
    public Integer revoke(Long id) {
        List<String> signList=workingScheduleUserService.findSignRecord(id);
        if (!signList.isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该招募信息已有打卡记录,无法取消!");
        }
        ActivityRecruitInfo activityRecruitInfo=this.dao.get(id);
        if(activityRecruitInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取招募信息失败!");
        }
        reservationService.updateReservationStatus(ReserveStatusEnums.EVENT_CANCELED.getState(),
                activityRecruitInfo.getId(),activityRecruitInfo.getRecruitType());
        Map<String,Object> map=new HashMap<>();
        map.put("reserveStatus",ReserveStatusEnums.EVENT_CANCELED.getState());
        map.put("activityId",activityRecruitInfo.getId());
        workingScheduleUserService.updateReserveStatus(map);
        List<String> openids=reservationService.findReservationOpenid(activityRecruitInfo.getId(),
                activityRecruitInfo.getRecruitType());
        // 没有统一的消息模板，暂不发送
//        wechatService.sendWechatMessages(openids,activityRecruitInfo.getTitle());
        activityRecruitInfo.setStatus(RecruitStateEnums.EVENT_CANCELED.getState());
        return this.dao.update(activityRecruitInfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo) {
        activityRecruitInfoVo.setStatus(ActivityApprovalStateEnums.PENDING.getState());
        //数据效验
        this.verificationDate(activityRecruitInfoVo);
        //状态为空,设置状态为待审核
        if(activityRecruitInfoVo.getApprovalStatus()==null){
            activityRecruitInfoVo.setApprovalStatus(ActivityApprovalStateEnums.PENDING.getState());
        }
        Integer count=this.dao.insert(activityRecruitInfoVo);

        // 采集系统消息
        if(activityRecruitInfoVo.getRecruitType().equals(2)){
            // 赛事活动招募
            SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.MATCH_JI0N_CHECK.getText(),SysMessageEnums.MATCH_JI0N_CHECK.getType(), SysMessageEnums.MATCH_JI0N_CHECK.getCode(),activityRecruitInfoVo.getId())));
        }else {
            //  驿站招募
            SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.STATION_CHECK.getText(),SysMessageEnums.STATION_CHECK.getType(), SysMessageEnums.STATION_CHECK.getCode(),activityRecruitInfoVo.getId())));
        }

        //保存赛事招募明细
        this.saveMatchDetail(activityRecruitInfoVo);
        return count;
    }

    @Override
    public Integer updateActivityRecruitInfo(ActivityRecruitInfoVo activityRecruitInfoVo) {
        //数据效验
        this.verificationDate(activityRecruitInfoVo);
        //状态为空,设置状态为待审核
        if(activityRecruitInfoVo.getApprovalStatus()==null){
            activityRecruitInfoVo.setApprovalStatus(ActivityApprovalStateEnums.PENDING.getState());
        }
        Integer count=this.dao.update(activityRecruitInfoVo);
        //保存赛事招募明细
        this.saveMatchDetail(activityRecruitInfoVo);
        return count;
    }

    public void verificationDate(ActivityRecruitInfoVo activityRecruitInfoVo){
        if(activityRecruitInfoVo.getRecruitStartDate()<DateUtil.beginOfDay(new Date()).getTime()){
            throw new CommonException("招募开始时间{}不能小于当前日期!",
                    DateUtil.timeStamp2Date(activityRecruitInfoVo.getRecruitStartDate()));
        }
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.STATION_RECRUIT.getType()){
            if(activityRecruitInfoVo.getServiceStartDate()<DateUtil.beginOfDay(new Date()).getTime()){
                throw new CommonException("服务开始时间{}不能小于当前日期",
                        DateUtil.timeStamp2Date(activityRecruitInfoVo.getServiceStartDate()));
            }
            if(activityRecruitInfoVo.getServiceStartDate()<activityRecruitInfoVo.getRecruitStartDate()){
                throw new CommonException("服务开始时间{}不能小于招募开始时间{}!",
                        DateUtil.timeStamp2Date(activityRecruitInfoVo.getServiceStartDate()),
                        DateUtil.timeStamp2Date(activityRecruitInfoVo.getRecruitStartDate()));
            }
            if(activityRecruitInfoVo.getServiceEndDate()<activityRecruitInfoVo.getRecruitEndDate()){
                throw new CommonException("服务结束时间{}不能小于招募结束时间{}!",
                        DateUtil.timeStamp2Date(activityRecruitInfoVo.getServiceEndDate()),
                        DateUtil.timeStamp2Date(activityRecruitInfoVo.getRecruitEndDate()));
            }
        }
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.MATCH_RECRUIT.getType()&&
                activityRecruitInfoVo.getMatchApproverId()!=null){
            User user=userService.get(activityRecruitInfoVo.getMatchApproverId());
            if (user==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "无效的时长审批负责人!");
            }
            activityRecruitInfoVo.setMatchApprover(user.getUserName());
        }
    }

    private void saveMatchDetail(ActivityRecruitInfoVo activityRecruitInfoVo){
        if(activityRecruitInfoVo.getRecruitType()==RecruitTypeEunms.MATCH_RECRUIT.getType()){
            if (activityRecruitInfoVo.getRecruitDetails()!=null){
                List<ActivityRecruitDetail> details=activityRecruitInfoVo.getRecruitDetails();
                for (ActivityRecruitDetail activityRecruitDetail:details){
                    if(activityRecruitDetail.getServiceDate()+DateUtil.timeToUnix(activityRecruitDetail.getServiceStartTime())<
                            DateUtil.beginOfDay(new Date()).getTime()){
                        throw new CommonException("服务开始时间{}不能小于当前日期!",
                                DateUtil.timeStamp2DateTime(activityRecruitDetail.getServiceDate()+DateUtil.timeToUnix(activityRecruitDetail.getServiceStartTime())));
                    }
                    if(activityRecruitDetail.getServiceDate()+ DateUtil.timeToUnix(activityRecruitDetail.getServiceStartTime())<
                            activityRecruitInfoVo.getRecruitStartDate()){
                        throw new CommonException("服务开始时间{}不能小于招募开始时间{}!",
                                DateUtil.timeStamp2DateTime(activityRecruitDetail.getServiceDate()+DateUtil.timeToUnix(activityRecruitDetail.getServiceStartTime())),
                                DateUtil.timeStamp2Date(activityRecruitInfoVo.getRecruitStartDate()));
                    }
                    if(activityRecruitDetail.getServiceDate()+ DateUtil.timeToUnix(activityRecruitDetail.getServiceEndTime())<
                            activityRecruitInfoVo.getRecruitEndDate()+24*60*60*1000-1){
                        throw new CommonException("服务结束时间不能小于招募结束时间!",
                                DateUtil.timeStamp2DateTime(activityRecruitDetail.getServiceDate()+ DateUtil.timeToUnix(activityRecruitDetail.getServiceEndTime())),
                                DateUtil.timeStamp2Date(activityRecruitInfoVo.getRecruitEndDate()));
                    }
                    if(activityRecruitDetail.getId()!=null){
                        activityRecruitDetailService.update(activityRecruitDetail);
                    }else {
                        activityRecruitDetail.setRecruitId(activityRecruitInfoVo.getId());
                        activityRecruitDetailService.insert(activityRecruitDetail);
                    }
                }
            }
        }
    }

    @Override
    public Integer deleteActivityRecruitInfo(Long id) {
        ActivityRecruitInfo activityRecruitInfo=this.dao.get(id);
        if(activityRecruitInfo.getStatus()!= ActivityApprovalStateEnums.PENDING.getState()&&
                activityRecruitInfo.getStatus()!=ActivityApprovalStateEnums.WITHDRAW.getState()&&
                activityRecruitInfo.getStatus()!=ActivityApprovalStateEnums.OVERRULE.getState()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该状态下无法删除");
        }
        Integer count=this.dao.delete(id);
        activityRecruitDetailService.deleteDetailByRecruitId(id);
        return count;
    }

    @Override
    public Integer deleteDetail(Long id) {
        return activityRecruitDetailService.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer approval(ActivityRecruitApproval activityRecruitApproval,UserEntity userEntity) {
        ActivityRecruitInfo activityRecruitInfo=this.dao.get(activityRecruitApproval.getRecruitId());
        if(activityRecruitInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "招募信息不存在!");
        }
        if(activityRecruitInfo.getApprovalStatus()!=ActivityApprovalStateEnums.PENDING.getState()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该招募信息非待审核状态!");
        }
        if (activityRecruitApproval.getApprovalStatus()== ActivityApprovalStateEnums.PASSED.getState()){
            //审核通过
            activityRecruitInfo.setApprovalStatus(ActivityApprovalStateEnums.PASSED.getState());
            activityRecruitInfo.setStatus(RecruitStateEnums.TO_RECRUITED.getState());
        }else if(activityRecruitApproval.getApprovalStatus()== ActivityApprovalStateEnums.OVERRULE.getState()){
            //审核驳回
            activityRecruitInfo.setApprovalStatus(ActivityApprovalStateEnums.OVERRULE.getState());
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR, "审核状态无效!");
        }
        activityRecruitApprovalService.insert(activityRecruitApproval);
        activityRecruitInfo.setApprovalStatus(activityRecruitApproval.getApprovalStatus());
        activityRecruitInfo.setApprovalComments(activityRecruitApproval.getApprovalComments());
        activityRecruitInfo.setApprovalId(userEntity.getId());
        activityRecruitInfo.setApprovalBy(userEntity.getUserName());
        activityRecruitInfo.setApprovalTime(System.currentTimeMillis());
        Integer count=this.dao.update(activityRecruitInfo);
        //生成驿站招募明细
        if(activityRecruitInfo.getApprovalStatus()==ActivityApprovalStateEnums.PASSED.getState()
            &&activityRecruitInfo.getRecruitType()==RecruitTypeEunms.STATION_RECRUIT.getType()
            && StringUtils.isNotBlank(activityRecruitInfo.getServiceHours())){
            String[] serviceHours=activityRecruitInfo.getServiceHours().split(",");
            ActivityRecruitDetail detail=new ActivityRecruitDetail();
            detail.setRecruitId(activityRecruitInfo.getId());
            detail.setRecruitNumber(activityRecruitInfo.getRecruitNumber());
            for(Long i=activityRecruitInfo.getServiceStartDate();i<=activityRecruitInfo.getServiceEndDate();i=i+24*60*60*1000){
                for(String hours:serviceHours){
                    String[] time=hours.split("-");
                    detail.setServiceDate(i);
                    detail.setServiceStartTime(time[0]);
                    detail.setServiceEndTime(time[1]);
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
            detail.setStatus(0);
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
            recruitDetails.add(detail);
        }
        return recruitDetails;
    }

    @Override
    public Page<ApplicationListVo> queryApplicationPage(Integer type, Integer pageNo, Integer pageSize, Long userId) {
        Page page = Page.startPage(pageNo,pageSize);
        Map<String,Object> map=new HashMap<>();
        if(type==1){//待审批,需要验证是否有审批权限
            int[] statusArray={ActivityApprovalStateEnums.PENDING.getState()};
            map.put("statusArray",statusArray);
        }
        if(type==2){//已审批
            int[] statusArray={ActivityApprovalStateEnums.OVERRULE.getState(),
                    ActivityApprovalStateEnums.PASSED.getState()};
            map.put("updateId",userId);
            map.put("statusArray",statusArray);
        }
        List<ApplicationListVo> list=this.dao.findApplicationList(map);
        page.setDataList(list);
        return page;
    }

    @Override
    public Integer getApplicationCount(Integer type, Long userId) {
        Map<String,Object> map=new HashMap<>();
        if(type==1){//待审批,需要验证是否有审批权限
            int[] statusArray={ActivityApprovalStateEnums.PENDING.getState()};
            map.put("statusArray",statusArray);
        }
        if(type==2){//已审批
            int[] statusArray={ActivityApprovalStateEnums.OVERRULE.getState(),
                    ActivityApprovalStateEnums.PASSED.getState()};
            map.put("updateId",userId);
            map.put("statusArray",statusArray);
        }
        Integer count=this.dao.getApplicationCount(map);
        return count;
    }

}