package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.ReservationDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.SignInfoVO;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.enums.SignType;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private UserService userService;

    @Override
    public Integer reservation(Long courseId, Long userId) {
        CourseInfo courseInfo=courseInfoService.get(courseId);
        if(courseInfo!=null){
            if(courseInfo.getSignStartTime()>System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名未开始!");
            }
            if(courseInfo.getSignEndTime()+24*60*60*1000-1<System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名已结束!");
            }
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,获取课程信息失败!");
        }
        if(courseInfo.getSignNumber()>=courseInfo.getLimitNumber()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"抱歉，已达到报名人数上限，无法报名");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list=this.dao.findByLimit(map);
        if(!list.isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }
        if(userId==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }
        User user=userService.get(userId);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
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
        reservation.setName(user.getUserName());
        reservation.setIdentityNumber(user.getCertificateNo());
        reservation.setPhone(user.getPhone());
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
            if(System.currentTimeMillis()>activityRecruitDetail.getServiceDate()){
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
        activityRecruitInfoVo.setReservationStatus(reservation.getStatus());
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
        courseInfoVo.setReservationStatus(reservation.getStatus());
        return courseInfoVo;
    }

    @Override
    public Integer updateReservationStatus(Integer status, Long relevanceId, Integer type) {
        return this.dao.updateReservationStatus(status,relevanceId,type);
    }

    @Override
    public List<String> findReservationOpenid(Long relevanceId,Integer type) {
        return this.dao.findReservationOpenid(relevanceId,type);
    }

    /**
     * 添加签到信息
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link String}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addSignInfo(Long courseId, Long userId) {
        // 是否预约过
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list= this.dao.findByLimit(map);

        Long currentLong = System.currentTimeMillis();
        // 预约过签到(线上签到)
        if(!list.isEmpty()){
           this.onLineSign(list.get(0),currentLong);
            return currentLong;
        }

        // 未预约签到(线下签到)
        if(list.isEmpty()){
            // 添加预约记录
            this.offLineSign(courseId,userId,currentLong);
            return currentLong;
        }
        return null;
    }


    /**
     * 线下签到
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link Integer}
     */
    private Integer offLineSign(Long courseId, Long userId, Long currentLong) {
        CourseInfo courseInfo = courseInfoService.get(courseId);
        if(courseInfo!=null){
            if(courseInfo.getSignStartTime()>System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名未开始!");
            }
            /*if(courseInfo.getSignEndTime()+24*60*60*1000-1<System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名已结束!");
            }*/
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,获取课程信息失败!");
        }

        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list=this.dao.findByLimit(map);
        if(!list.isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }

        // 重复报名需要在限制人数之前
        if(courseInfo.getSignNumber()>=courseInfo.getLimitNumber()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"抱歉，已达到报名人数上限，无法报名");
        }

        if(userId==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }
        User user=userService.get(userId);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
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
        reservation.setName(user.getUserName());
        reservation.setIdentityNumber(user.getCertificateNo());
        reservation.setPhone(user.getPhone());
        reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());

        // 线下签到信息
        reservation.setSignTime(currentLong);
        reservation.setSignType(SignType.SIGN_ONLINE.getType());
        reservation.setSignStatus(SignStatusEnums.SIGN_DONE.getStatus());
        this.dao.insert(reservation);
        courseInfo.setSignNumber(courseInfo.getSignNumber()+1);
        return courseInfoService.update(courseInfo);

    }

    /**
     * 线上签到
     *
     * @param reservation 预订
     * @return {@link Integer}
     */
    private Integer onLineSign(Reservation reservation,Long currentLong ) {
        reservation.setSignTime(currentLong);
        reservation.setSignType(SignType.SIGN_ONLINE.getType());
        reservation.setSignStatus(SignStatusEnums.SIGN_DONE.getStatus());
        return this.dao.update(reservation);
    }


    /**
     * 签到信息列表
     *
     * @param signInfoVO VO
     * @return {@link Page< SignInfoVO >}
     */
    @Override
    public Page<Reservation> findSignInfoByPage(SignInfoVO signInfoVO) {
        Page page = Page.startPage(signInfoVO.getPageNo(),signInfoVO.getPageSize());
        List<Reservation> instructors = this.dao.selectSignInfoByPage(signInfoVO);
        page.setDataList(instructors);
        return page;
    }
}