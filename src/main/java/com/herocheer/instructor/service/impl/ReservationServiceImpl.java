package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.ReservationDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.entity.ReservationMember;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationListVO;
import com.herocheer.instructor.domain.vo.ReservationMemberInfoVO;
import com.herocheer.instructor.domain.vo.ReservationMemberVO;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.ReservationVO;
import com.herocheer.instructor.domain.vo.SignInfoVO;
import com.herocheer.instructor.enums.CourseApprovalState;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.enums.SignType;
import com.herocheer.instructor.service.ActivityRecruitDetailService;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.CourseScheduleService;
import com.herocheer.instructor.service.ReservationMemberService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author makejava
 * @desc  预约记录(Reservation)表服务实现类
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
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

    @Autowired
    private ReservationMemberService reservationMemberService;

    @Resource
    private CourseScheduleService courseScheduleService;

    @Resource
    private ReservationService reservationService;


    /**
     * 课程预约
     *
     * @param reservationMemberVO 预订单
     * @param userId          用户id
     * @return {@link Reservation}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Reservation reservation(ReservationMemberVO reservationMemberVO, Long userId) {
        List<ReservationVO> reservationList = reservationMemberVO.getReservationList();
        if(CollectionUtils.isEmpty(reservationList)){
            throw new CommonException("报名信息为空");
        }
        Long courseId = reservationList.get(0).getCourseId();
        if(null == courseId){
            throw new CommonException("预约课程ID不能为空");
        }

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

        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());

        // 固定课程
        if(courseInfo.getOfferCourseType().equals(2)){
            map.put("courseScheduleId",reservationMemberVO.getCourseScheduleId());
        }
        log.debug("当前用户报名信息:{}",map);
        List<Reservation> list=this.dao.findByLimit(map);
        if(!list.isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }

        // 重复报名需要在限制人数之前
        if(courseInfo.getLimitNumber() !=null && courseInfo.getSignNumber()>=courseInfo.getLimitNumber()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"抱歉，已达到报名人数上限，无法报名");
        }

        if(userId==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }
        User user=userService.get(userId);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }

        //保存招募信息
        Reservation reservation=new Reservation();
        reservation.setRelevanceId(courseInfo.getId());
        //设置状态线上预约
        reservation.setSource(1);
        reservation.setType(RecruitTypeEunms.COURIER_RECRUIT.getType());
        reservation.setTitle(courseInfo.getTitle());
        reservation.setImage(courseInfo.getImage());
        reservation.setStartTime(courseInfo.getCourseStartTime());
        reservation.setEndTime(courseInfo.getCourseEndTime());
        reservation.setAddress(courseInfo.getAddress());
        reservation.setLongitude(courseInfo.getLongitude());
        reservation.setLatitude(courseInfo.getLatitude());
        reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        reservation.setUserId(userId);

        // 固定课程场景
        if(reservationMemberVO.getCourseScheduleId() != null){
            reservation.setCourseScheduleId(reservationMemberVO.getCourseScheduleId());
            reservation.setCourseDate(reservationMemberVO.getCourseDate());
        }
        this.dao.insert(reservation);

        // 报名人员（多人）
        ReservationMember reservationMember = null;
        Set<Integer> intSet = reservationList.stream().map((ReservationVO reservationVO)->reservationVO.getRelationType()).collect(Collectors.toSet());
        for (ReservationVO reservationVO:reservationList){
            reservationMember  = ReservationMember.builder().build();
            //保存用户信息
            reservationMember.setRelevanceId(reservation.getId());
            reservationMember.setUserId(userId);
            reservationMember.setUserName(reservationVO.getUserName());

            // 是否有家长一起报名
            if (intSet.contains(0)){
                if(StringUtils.hasText(reservationVO.getPhone())){
                    reservationMember.setPhone(reservationVO.getPhone());
                }else{
                    reservationMember.setPhone(null);
                }
            }else {
                reservationMember.setPhone(userService.get(userId).getPhone());
            }

            reservationMember.setIdentityNumber(reservationVO.getCertificateNo());
            reservationMember.setInsuranceStatus(reservationVO.getInsuranceStatus());
            reservationMember.setRelationType(reservationVO.getRelationType());
            reservationMemberService.insert(reservationMember);
        }

        // 固定课程（扣减库存）
        if(reservationMemberVO.getCourseScheduleId() != null){
            // 数据库行锁，防止超卖
            CourseSchedule courseSchedule = courseScheduleService.findCourseSchedulesById(reservationMemberVO.getCourseScheduleId());
            if(courseSchedule.getLimitNumber() > 0){
                courseSchedule.setLimitNumber(courseSchedule.getLimitNumber()-1);
                courseScheduleService.update(courseSchedule);
            }
            return reservation;
        }

        // 非固定课程更新报名人数
        courseInfo.setSignNumber(courseInfo.getSignNumber()+1);
        courseInfoService.update(courseInfo);
        return reservation;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer webReservation(Reservation reservation) {
        CourseInfo courseInfo = courseInfoService.get(reservation.getRelevanceId());
        if (courseInfo == null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取课程信息失败!");
        }
        if (courseInfo.getApprovalStatus() != null && courseInfo.getApprovalStatus().equals(0)){
            throw new CommonException(ResponseCode.SERVER_ERROR,"课程还在审批中，暂无法报名");
        }
        if (courseInfo.getApprovalStatus() != null && (courseInfo.getApprovalStatus().equals(2) || courseInfo.getApprovalStatus().equals(3))){
            throw new CommonException(ResponseCode.SERVER_ERROR,"课程已驳回或已撤回，暂无法报名");
        }

        User user=userService.findUserByPhone(reservation.getPhone(),null);
        log.debug("老年人预约用户手机：{}",reservation.getPhone());
        log.debug("老年人预约用户：{}",user);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseInfo.getId());
        map.put("userId",user.getId());
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list=this.dao.findByLimit(map);
        if(!list.isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }

        // 非固定课程
        if(courseInfo.getOfferCourseType().equals(1)){
            // 重复报名需要在限制人数之前
            if(courseInfo.getSignNumber() >= courseInfo.getLimitNumber()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"报名人数已满，无法报名");
            }
        }else {
            // 固定课程
            if(reservation.getCourseScheduleId() != null){
                CourseSchedule courseSchedule = courseScheduleService.get(reservation.getCourseScheduleId());

                if (courseSchedule == null){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"获取固定课程的课表失败!");
                }
                reservation.setCourseDate(courseSchedule.getCourseDate());

                if(courseSchedule.getLimitNumber() < 1){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"报名人数已满，无法报名");
                }
            }else{
                    throw new CommonException(ResponseCode.SERVER_ERROR,"固定课程的课表ID不为空!");
            }
        }

        //保存招募信息
        reservation.setRelevanceId(courseInfo.getId());
        //设置状态线上预约
        reservation.setSource(1);
        reservation.setType(RecruitTypeEunms.COURIER_RECRUIT.getType());
        reservation.setTitle(courseInfo.getTitle());
        reservation.setImage(courseInfo.getImage());
        reservation.setStartTime(courseInfo.getCourseStartTime());
        reservation.setEndTime(courseInfo.getCourseEndTime());
        reservation.setAddress(courseInfo.getAddress());
        reservation.setLongitude(courseInfo.getLongitude());
        reservation.setLatitude(courseInfo.getLatitude());
        //保存用户信息
        reservation.setUserId(user.getId());
        reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        this.dao.insert(reservation);

        // 报名人员
        ReservationMember reservationMember  = ReservationMember.builder().build();
        reservationMember.setRelevanceId(reservation.getId());
        reservationMember.setUserId(user.getId());
        reservationMember.setUserName(reservation.getName());
        reservationMember.setPhone(reservation.getPhone());
        reservationMember.setIdentityNumber(reservation.getIdentityNumber());
        reservationMember.setInsuranceStatus(4);
        reservationMember.setRelationType(0);
        reservationMemberService.insert(reservationMember);

        // 非固定课程
        if(courseInfo.getOfferCourseType().equals(1)){
            courseInfo.setSignNumber(courseInfo.getSignNumber()+1);
            return courseInfoService.update(courseInfo);
        }else {
            /*
             * 固定课程
             * 数据库行锁，防止高并发超卖
             **/
            CourseSchedule schedule = courseScheduleService.findCourseSchedulesById(reservation.getCourseScheduleId());
            if(schedule.getLimitNumber() > 0){
                schedule.setLimitNumber(schedule.getLimitNumber()-1L);
            }
            return courseScheduleService.update(schedule);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancel(Long id) {
        Reservation reservation = this.dao.get(id);
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
            if(reservation.getSource().equals(1)){
                //已预约数减一
                activityRecruitDetail.setHadRecruitNumber(activityRecruitDetail.getHadRecruitNumber()-1);
                activityRecruitDetailService.update(activityRecruitDetail);
            }

            //设置状态取消预约
            workingScheduleUser.setReserveStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
            workingScheduleUserService.update(workingScheduleUser);

            // 取消报名的预约
            reservation.setStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
            this.dao.update(reservation);
        }else if(reservation.getType().equals(RecruitTypeEunms.COURIER_RECRUIT.getType())){

            CourseInfo courseInfo = courseInfoService.get(reservation.getRelevanceId());
            if(courseInfo == null){
                throw new CommonException(ResponseCode.SERVER_ERROR,"获取课程信息失败!");
            }

            // 固定课程线程取消预约
            if(reservation.getSource().equals(1)){
                if(reservation.getCourseScheduleId() != null){
                    // 数据库行锁，防止超卖
                    CourseSchedule courseSchedule = courseScheduleService.findCourseSchedulesById(reservation.getCourseScheduleId());
                    courseSchedule.setLimitNumber(courseSchedule.getLimitNumber() + 1);
                    courseScheduleService.update(courseSchedule);
                }else if(courseInfo.getSignNumber() >= 1){
                    // 线上预约取消(非固定课程)
                    //已预约数减一
                    courseInfo.setSignNumber(courseInfo.getSignNumber()-1);
                    courseInfoService.update(courseInfo);
                }
            }
            reservation.setStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
            this.dao.update(reservation);
        }
    }

    @Override
    public Page<ReservationListVO> queryPage(ReservationQueryVo queryVo, Long userId) {

        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        if(queryVo.getQueryType()!=null){
            if (queryVo.getQueryType()==3){
                if(userId==null){
                    return page;
                }
                queryVo.setUserId(userId);
            }
        }
        List<ReservationListVO> instructors = this.dao.findList(queryVo);
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
        ReservationListVO reservationListVO = this.dao.selectByCurUserId(ReservationQueryVo.builder().id(id).build());
        if(reservationListVO == null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"无效的预约信息!");
        }
        CourseInfoVo courseInfoVo=courseInfoService.getCourseInfo(reservationListVO.getRelevanceId());
        if(courseInfoVo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取招募信息失败!");
        }
        courseInfoVo.setReservationStatus(reservationListVO.getStatus());

        // 当前用户的预约信息
        if(reservationListVO.getCourseScheduleId() !=null){
            CourseSchedule courseSchedule = courseScheduleService.get(reservationListVO.getCourseScheduleId());
            if(!ObjectUtils.isEmpty(courseSchedule)){
                reservationListVO.setCourseDate(courseSchedule.getCourseDate());
                reservationListVO.setCourseStartTime(courseSchedule.getStartTime());
                reservationListVO.setCourseEndTime(courseSchedule.getEndTime());
                reservationListVO.setCancelReason(courseSchedule.getCancelReason());
            }
        }
        courseInfoVo.setReservation(reservationListVO);
        return courseInfoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateReservationStatus(Integer status, Long relevanceId, Integer type) {
        return this.dao.updateReservationStatus(status,relevanceId,type);
    }

    @Override
    public List<String> findReservationOpenid(Long relevanceId,Integer type) {
        return this.dao.findReservationOpenid(relevanceId,type);
    }

    /**
     * 添加签名信息
     *
     * @param userId          用户id
     * @param reservationMemberVO 预订单
     * @return {@link Long}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addSignInfo(ReservationMemberVO reservationMemberVO, Long userId) {
        // 是否预约过
        List<ReservationVO> reservationList = reservationMemberVO.getReservationList();
        if(null == reservationList.get(0).getCourseId()){
            throw new CommonException("预约课程ID不能为空");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("relevanceId",reservationList.get(0).getCourseId());
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list= this.dao.findByLimit(map);

        // 区分固定课程和非固定课程
        if(reservationMemberVO.getCourseScheduleId() == null){
            // 预约过签到(线上签到)
            if(!list.isEmpty()){
                throw new CommonException("您已线上预约过非固定课程，无法线下签到");
            }
        }else {

            // 每节课只需报名一次
            map.put("courseScheduleId",reservationMemberVO.getCourseScheduleId());
            List<Reservation> reservationedList= this.dao.findByLimit(map);
            if(!reservationedList.isEmpty()){
                throw new CommonException("您已报名过此节课了,无需再预约");
            }

            //  固定课程只有你报名的上一节课结束了才能再报名
            if(!list.isEmpty() && list.get(0).getCourseScheduleId() != null){
                // 取出最新的一条预约记录
                CourseSchedule courseSchedule=  courseScheduleService.get(list.get(0).getCourseScheduleId());
                Long coursetime = courseSchedule.getCourseDate() + DateUtil.timeToUnix(courseSchedule.getEndTime());
                if(coursetime > System.currentTimeMillis()){
                    throw new CommonException("您之前已报名的课时还未结束，暂无法预约");
                }
            }
        }

        Long currentLong = System.currentTimeMillis();
        // 未预约签到(线下签到)
        this.offLineSign(reservationMemberVO,userId,currentLong);
        return currentLong;
    }


    /**
     * 线下签到
     *
     * @param userId          用户id
     * @param reservationMemberVO 预订单
     * @param currentLong     目前的长
     * @return {@link Integer}
     */
    private void offLineSign(ReservationMemberVO reservationMemberVO, Long userId, Long currentLong) {
        List<ReservationVO> reservationList = reservationMemberVO.getReservationList();
        CourseInfo courseInfo = courseInfoService.get(reservationList.get(0).getCourseId());
        if(courseInfo != null){
            if(!courseInfo.getApprovalStatus().equals(CourseApprovalState.PASSED.getState())){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程还在审核中!");
            }

            if(courseInfo.getSignStartTime()>System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名未开始!");
            }

            /*if(courseInfo.getSignEndTime()+24*60*60*1000-1<System.currentTimeMillis()){
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,课程报名已结束!");
            }*/
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,获取课程信息失败!");
        }

        // 重复报名需要在限制人数之前
        // 上课结束时间线下预约不限制人数
        /*if(courseInfo.getSignNumber()>=courseInfo.getLimitNumber()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"抱歉，已达到报名人数上限，无法报名");
        }*/

        if(userId==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }
        User user=userService.get(userId);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取用户信息失败!");
        }

        Reservation reservation = new Reservation();
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
        // 线下预约
        reservation.setSource(2);
        // 当前预约用户的ID
        reservation.setUserId(userId);
        reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());

        // 线下签到信息
        reservation.setSignTime(currentLong);
        reservation.setSignType(SignType.SIGN_OFFLINE.getType());
        reservation.setSignStatus(SignStatusEnums.SIGN_DONE.getStatus());

        // 固定课程字段
        if(reservationMemberVO.getCourseScheduleId() != null){
            reservation.setCourseScheduleId(reservationMemberVO.getCourseScheduleId());
            reservation.setCourseDate(reservationMemberVO.getCourseDate());
        }

        this.dao.insert(reservation);

        // 线下报名人员
        Set<Integer> intSet = reservationList.stream().map((ReservationVO reservationVO)->reservationVO.getRelationType()).collect(Collectors.toSet());
        ReservationMember reservationMember = null;
        for (ReservationVO reservationVO: reservationList){
            reservationMember  = ReservationMember.builder().build();
            reservationMember.setRelevanceId(reservation.getId());
            reservationMember.setUserId(userId);
            reservationMember.setUserName(reservationVO.getUserName());

            if(intSet.contains(0)){
                reservationMember.setPhone(reservationVO.getPhone());
            }else {
                reservationMember.setPhone(userService.get(userId).getPhone());
            }

            reservationMember.setIdentityNumber(reservationVO.getCertificateNo());
            reservationMember.setInsuranceStatus(reservationVO.getInsuranceStatus());
            reservationMember.setRelationType(reservationVO.getRelationType());
            reservationMemberService.insert(reservationMember);
        }

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
    public Page<ReservationListVO> findSignInfoByPage(SignInfoVO signInfoVO) {
        Page page = Page.startPage(signInfoVO.getPageNo(),signInfoVO.getPageSize());
        List<ReservationListVO> instructors = this.dao.selectSignInfoByPage(signInfoVO);
        page.setDataList(instructors);
        return page;
    }

    /**
     * 根据当前用户ID获取预约信息（包含已预约和未预约）
     *
     * @param relevanceId 进程id
     * @param userId   用户id
     * @return {@link List<Reservation>}
     */
    @Override
    public List<ReservationMemberInfoVO> findReservationByCurrentUserId(Long relevanceId, Long userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",relevanceId);
        map.put("userId",userId);
        List<ReservationMemberInfoVO> list = reservationMemberService.findReservationMemberByCourseScheduleId(map);
        return list;
    }

    /**
     * 根据当前用户ID获取已预约信息
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link List<Reservation>}
     */
    private List<Reservation> findReservationedByCurrentUserId(Long courseId, Long userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list = this.dao.findByLimit(map);
        return list;
    }


    /**
     * 线上签到
     *
     * @param courseId 进程id
     * @param userId   用户id
     * @return {@link Long}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addOnlineSignInfo(Long courseId, Long userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("relevanceId",courseId);
        map.put("userId",userId);
        map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
        map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
        List<Reservation> list= this.dao.findByLimit(map);

        Long currentLong = System.currentTimeMillis();

        // 预约过签到(线上签到)
        if(list.isEmpty()){
            throw new CommonException("您未线上预约,请预约");
        }
        this.onLineSign(list.get(0),currentLong);
        return currentLong;
    }

    /**
     * 根据当前用户id获取预约信息
     *
     * @param queryVo 查询签证官
     * @return {@link ReservationListVO}
     */
    @Override
    public ReservationListVO findReservationByCurUserId(ReservationQueryVo queryVo) {
        return this.dao.selectByCurUserId(queryVo);
    }

    /**
     * 按课程课表id修改预订状态
     *
     * @param paramMap 参数映射
     * @return {@link Integer}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer modifyReservationStatusByCourseScheduleId(Map<String, Object> paramMap) {
        return this.dao.updateReservationStatusByCourseScheduleId(paramMap);
    }

    /**
     * 找到课表取消时的预约人
     *
     * @param paramMap 参数映射
     * @return {@link List<String>}
     */
    @Override
    public Set<String> findReservationOpenidByCourseScheduleId(Map<String, Object> paramMap) {
        return this.dao.selectReservationOpenidByCourseScheduleId(paramMap);
    }

    /**
     * 找到预订信息
     *
     * @param queryVO 查询签证官
     * @return {@link List<ReservationListVO>}
     */
    @Override
    public List<ReservationListVO> findReservationInfo(ReservationQueryVo queryVO) {
        return this.dao.findList(queryVO);
    }
}