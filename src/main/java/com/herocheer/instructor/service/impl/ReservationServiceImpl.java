package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.ReservationDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.CourseReservationVo;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.SmsCodeUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  预约记录(Reservation)表服务实现类
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ReservationServiceImpl extends BaseServiceImpl<ReservationDao, Reservation,Long> implements ReservationService {

    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private ActivityRecruitInfoService activityRecruitInfoService;

    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    @Resource
    private WorkingScheduleService workingScheduleService;

    @Resource
    private ActivityRecruitDetailService activityRecruitDetailService;
    @Override
    public Integer reservation(CourseReservationVo courseReservationVo, Long userId) {
        ResponseResult responseResult= SmsCodeUtil.verifySmsCode(courseReservationVo.getPhone(),
                courseReservationVo.getVerificationCode());
        if(responseResult.getCode()!=200){
            throw new CommonException(ResponseCode.SERVER_ERROR, responseResult.getMessage());
        }
        CourseInfo courseInfo=courseInfoService.get(courseReservationVo.getCourseId());
        if(courseInfo!=null){
            if(courseInfo.getSignStartTime()>System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名未开始!");
            }
            if(courseInfo.getSignEndTime()<System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名已结束!");
            }
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,获取课程信息失败!");
        }
        if(courseInfo.getSignNumber()>=courseInfo.getLimitNumber()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,预约人数已满!");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("courseId",courseReservationVo.getCourseId());
        map.put("userId",courseReservationVo.getUserId());
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        List<Reservation> list=this.dao.findByLimit(map);
        if(list!=null || list.size()>0){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }
        Reservation reservation=new Reservation();
        //保存招募信息
        reservation.setRelevanceId(courseInfo.getId());
        reservation.setType(RecruitTypeEunms.COURIER_RECRUIT.getType());
        reservation.setTitle(courseInfo.getTitle());
        reservation.setImage(courseInfo.getImage());
        reservation.setStartTime(courseInfo.getCourseStartTime());
        reservation.setEndTime(courseInfo.getCourseEndTime());
        reservation.setAddress(courseInfo.getAddress());
        reservation.setLongitude(courseInfo.getLongitude());
        reservation.setLatitude(courseInfo.getLatitude());
        //保存用户信息
        reservation.setUserId(userId);
        reservation.setName(courseReservationVo.getName());
        reservation.setIdentityNumber(courseReservationVo.getIdentityNumber());
        if(StringUtils.isNotEmpty(courseReservationVo.getPhone())){
            reservation.setPhone(courseReservationVo.getPhone().replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
        }
        reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        this.dao.insert(reservation);
        courseInfo.setSignNumber(courseInfo.getSignNumber()+1);
        return courseInfoService.update(courseInfo);
    }

    @Override
    public Integer cancel(Long id) {
        Reservation reservation=this.dao.get(id);
        if(reservation==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"无效的预约信息!");
        }
        if(reservation.getType()==RecruitTypeEunms.STATION_RECRUIT.getType()
                || reservation.getType()==RecruitTypeEunms.MATCH_RECRUIT.getType()){
            WorkingScheduleUser workingScheduleUser=workingScheduleUserService.get(reservation.getWorkingId());
            if(workingScheduleUser==null){
                throw new CommonException(ResponseCode.SERVER_ERROR,"获取排班信息失败!");
            }
            WorkingSchedule workingSchedule=workingScheduleService.get(workingScheduleUser.getWorkingScheduleId());
            if(workingSchedule==null || workingSchedule.getActivityDetailId()==null){
                throw new CommonException(ResponseCode.SERVER_ERROR,"获取排班信息失败!");
            }
            ActivityRecruitDetail activityRecruitDetail=activityRecruitDetailService.get(workingSchedule.getActivityDetailId());
            if(new Date().getTime()>activityRecruitDetail.getServiceDate()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"活动当天不能取消预约!");
            }
            //已预约数减一
            activityRecruitDetail.setHadRecruitNumber(activityRecruitDetail.getHadRecruitNumber()-1);
            activityRecruitDetailService.update(activityRecruitDetail);
            //设置状态取消预约
            workingScheduleUser.setReserveStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
            workingScheduleUserService.update(workingScheduleUser);
        }else if(reservation.getType()==RecruitTypeEunms.COURIER_RECRUIT.getType()){
            CourseInfo courseInfo=courseInfoService.get(reservation.getRelevanceId());
            if(courseInfo==null){
                throw new CommonException(ResponseCode.SERVER_ERROR,"获取课程信息失败!");
            }
            if(courseInfo.getSignNumber()>=1){
                //已预约数减一
                courseInfo.setSignNumber(courseInfo.getSignNumber()-1);
                courseInfoService.update(courseInfo);
            }
        }
        reservation.setStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
        return this.dao.update(reservation);
    }

    @Override
    public Page<Reservation> queryPage(ReservationQueryVo queryVo, Long userId) {

        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        queryVo.setUserId(userId);
        if(queryVo.getQueryType()!=null){
            if (queryVo.getQueryType()==3){
                if(userId==null){
                    return page;
                }
                queryVo.setUserId(userId);
            }
        }
        List<Reservation> instructors = this.dao.findList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public ActivityRecruitInfoVo getActivity(Long id) {
        Reservation reservation=this.dao.get(id);
        if(reservation==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"无效的预约信息!");
        }
        ActivityRecruitInfoVo activityRecruitInfoVo=activityRecruitInfoService.getActivityRecruitInfo(reservation.getRelevanceId());
        if(activityRecruitInfoVo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取招募信息失败!");
        }
        if(reservation.getStatus()==ReserveStatusEnums.ALREADY_RESERVE.getState()
                && activityRecruitInfoVo.getServiceEndDate()<System.currentTimeMillis()){
            activityRecruitInfoVo.setReservationStatus(ReserveStatusEnums.IN_END.getState());
        }else {
            activityRecruitInfoVo.setReservationStatus(reservation.getStatus());
        }
        return activityRecruitInfoVo;
    }

    @Override
    public CourseInfoVo getCourse(Long id) {
        Reservation reservation=this.dao.get(id);
        if(reservation==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"无效的预约信息!");
        }
        CourseInfoVo courseInfoVo=courseInfoService.getCourseInfo(reservation.getRelevanceId());
        if(courseInfoVo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取招募信息失败!");
        }
        if(reservation.getStatus()==ReserveStatusEnums.ALREADY_RESERVE.getState()
                && courseInfoVo.getCourseEndTime()<System.currentTimeMillis()){
            courseInfoVo.setReservationStatus(ReserveStatusEnums.IN_END.getState());
        }else {
            courseInfoVo.setReservationStatus(reservation.getStatus());
        }
        return courseInfoVo;
    }
}