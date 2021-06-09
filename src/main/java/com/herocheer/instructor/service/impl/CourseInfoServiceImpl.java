package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.CourseInfoDao;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationListVO;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.TearcherVO;
import com.herocheer.instructor.enums.ActivityApprovalStateEnums;
import com.herocheer.instructor.enums.CourseApprovalState;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.service.CourseApprovalService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.CourseScheduleService;
import com.herocheer.instructor.service.CourseTearcherService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表服务实现类
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Slf4j
public class CourseInfoServiceImpl extends BaseServiceImpl<CourseInfoDao, CourseInfo,Long> implements CourseInfoService {

    @Resource
    private CourseApprovalService courseApprovalService;
    @Resource
    private ReservationService reservationService;
    @Resource
    private WechatService wechatService;

    @Autowired
    private CourseTearcherService courseTearcherService;
    @Autowired
    private UserService userService;

    @Autowired
    private CourseScheduleService courseScheduleService;


    @Override
    public Page<CourseInfo> queryPage(CourseInfoQueryVo queryVo, Long userId) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        if(queryVo.getQueryType()!=null&&queryVo.getQueryType()==3){
            queryVo.setCreatedId(userId);
        }
        List<CourseInfo> instructors = this.dao.queryList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public List<CourseApproval> approvalRecord(Long id) {
        Map<String,Object> map=new HashedMap();
        map.put("courseId",id);
        List<CourseApproval> list=courseApprovalService.findByLimit(map);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer withdraw(Long id) {
        CourseInfo courseInfo=new CourseInfo();
        courseInfo.setId(id);
        courseInfo.setApprovalStatus(CourseApprovalState.WITHDRAW.getState());
        return this.dao.update(courseInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer revoke(Long id) {
        CourseInfo courseInfo=this.dao.getCourseInfo(id);
        if(courseInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取课程信息失败!");
        }

        // 课程取消时更新预约状态为已关闭
        reservationService.updateReservationStatus(ReserveStatusEnums.EVENT_CANCELED.getState(), courseInfo.getId(), RecruitTypeEunms.COURIER_RECRUIT.getType());

        //设置课程状态 5=课程取消
        courseInfo.setState(5);
        int count = this.dao.update(courseInfo);
        // 课程取消时，发送微信消息通知
        List<String> openids = reservationService.findReservationOpenid(courseInfo.getId(), RecruitTypeEunms.COURIER_RECRUIT.getType());
        wechatService.sendWechatMessages(openids,courseInfo);
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer approval(CourseApproval courseApproval,UserEntity userEntity) {
        courseApprovalService.insert(courseApproval);
        CourseInfo courseInfo=new CourseInfo();
        courseInfo.setId(courseApproval.getCourseId());
        courseInfo.setApprovalStatus(courseApproval.getApprovalStatus());
        courseInfo.setApprovalComments(courseApproval.getApprovalComments());
        if(ActivityApprovalStateEnums.PASSED.getState()==courseApproval.getApprovalStatus()){
            courseInfo.setApprovalStatus(CourseApprovalState.PASSED.getState());
            //设置活动状态待启动
            courseInfo.setState(0);
        }
        if(ActivityApprovalStateEnums.OVERRULE.getState()==courseApproval.getApprovalStatus()){
            courseInfo.setApprovalStatus(CourseApprovalState.OVERRULE.getState());
        }
        courseInfo.setApprovalId(userEntity.getId());
        courseInfo.setApprovalBy(userEntity.getUserName());
        courseInfo.setApprovalTime(System.currentTimeMillis());
        return this.dao.update(courseInfo);
    }

    @Override
    public CourseInfoVo getCourseInfo(Long id) {
        return this.dao.getCourseInfo(id);
    }

    @Override
    public CourseInfo verificationDate(CourseInfo courseInfo) {
        if(courseInfo.getSignStartTime()< DateUtil.beginOfDay(new Date()).getTime()){
            throw new CommonException("课程报名开始时间{}不能小于当前时间!",
                    DateUtil.timeStamp2Date(courseInfo.getSignStartTime()));
        }

        if(courseInfo.getCourseStartTime() != null && courseInfo.getSignStartTime()>courseInfo.getCourseStartTime()){
            throw new CommonException("课程开始时间{}不能小于课程开始报名时间{}!",
                    DateUtil.timeStamp2Date(courseInfo.getSignStartTime()),
                    DateUtil.timeStamp2Date(courseInfo.getCourseStartTime()));
        }

        if(courseInfo.getCourseEndTime() != null &&courseInfo.getSignEndTime()>courseInfo.getCourseEndTime()){
            throw new CommonException("课程结束时间{}不能小于课程结束报名时间{}!",
                    DateUtil.timeStamp2Date(courseInfo.getSignEndTime()),
                    DateUtil.timeStamp2Date(courseInfo.getCourseEndTime()));
        }
        if(courseInfo.getApprovalStatus()==null){
            courseInfo.setApprovalStatus(CourseApprovalState.PENDING.getState());
        }
        return courseInfo;
    }

    /**
     * 通过id发现课程信息
     *
     * @param id id
     * @return {@link CourseInfoVo}
     */
    @Override
    public CourseInfo findCourseInfoById(Long id,String flag,Long userId) {
        // 获取课程信息
        CourseInfo courseInfo  = this.dao.get(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 扫码签到场景
        if(ObjectUtils.isEmpty(courseInfo)){
            return courseInfoVo;
        }
        BeanCopier.create(courseInfo.getClass(),courseInfoVo.getClass(),false).copy(courseInfo,courseInfoVo,null);

        // 获取指导项目
        CourseTearcher courseTearcher = courseTearcherService.get(courseInfo.getLecturerTeacherId());
        if(!ObjectUtils.isEmpty(courseTearcher)){
            courseInfoVo.setGuideProject(courseTearcher.getGuideProject());
        }
        if(StringUtils.isBlank(flag)){
            return courseInfoVo;
        }

        // 当前用户的预约信息
        ReservationListVO reservationListVO = reservationService.findReservationByCurUserId(ReservationQueryVo.builder().relevanceId(id).userId(userId).build());
        if(!ObjectUtils.isEmpty(reservationListVO)){
            if(reservationListVO.getCourseScheduleId() !=null){
                CourseSchedule courseSchedule = courseScheduleService.get(reservationListVO.getCourseScheduleId());
                if(!ObjectUtils.isEmpty(courseSchedule)){
                    reservationListVO.setCourseDate(courseSchedule.getCourseDate());
                    reservationListVO.setCourseStartTime(courseSchedule.getStartTime());
                    reservationListVO.setCourseEndTime(courseSchedule.getEndTime());
                }
            }
            courseInfoVo.setReservation(reservationListVO);
        }

        // 否签到和预约
        log.debug("扫码签到当前用户ID:{}",userId);
        if(null != userId){
            // 用户是否预约
            Map<String,Object> map=new HashMap<>();
            map.put("relevanceId",id);
            map.put("userId",userId);
            map.put("type", RecruitTypeEunms.COURIER_RECRUIT.getType());
            map.put("status",ReserveStatusEnums.ALREADY_RESERVE.getState());
            List<Reservation> list = reservationService.findByLimit(map);
            if(CollectionUtil.isNotEmpty(list)){
                courseInfoVo.setReservationStatus(0);
                Reservation reservation = list.get(0);
                courseInfoVo.setReservationId(reservation.getId());
                log.debug("扫码签到当前用户的预约信息:{}",reservation);
                if(reservation.getSignStatus().equals(1)){
                    courseInfoVo.setSignStatus(1);
                    courseInfoVo.setSignTime(reservation.getSignTime());
                }
            }
        }
        return courseInfoVo;
    }

    /**
     * 培训任务分页查询
     *
     * @param queryVo     VO
     * @param currentUser 当前用户
     * @return {@link Page<CourseInfo>}
     */
    @Override
    public Page<CourseInfo> findtaskByPage(CourseInfoQueryVo queryVo, UserEntity currentUser) {
        if(StringUtils.isBlank(currentUser.getOtherId())){
            throw new CommonException("您未绑定身份，请使用手机绑定功能");
        }

        //获取授课老师的ID
        log.debug("当前用户的OpenId:{}",currentUser.getOtherId());
        User user = userService.findUserByOpenId(currentUser.getOtherId());
//        User user = userService.findUserByOpenId("obOp1s2fGwPvYxvtkljRkMdtGRx4

        if(ObjectUtils.isEmpty(user)){
            throw new CommonException("您还不是公众号用户");
        }

        // 授课老师自身的培训任务
        List<TearcherVO> tearcherVOList = courseTearcherService.findCourseTearcherByPhone(user.getPhone());
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        if(CollectionUtil.isNotEmpty(tearcherVOList)){
            log.debug("授课老师ID:{}",tearcherVOList.get(0).getId());
            queryVo.setLecturerTeacherId(tearcherVOList.get(0).getId());
            List<CourseInfo> instructors = this.dao.selectCourseInfoByPage(queryVo);
            page.setDataList(instructors);
        }
        return page;
    }

    /**
     * 添加课程信息
     *
     * @param courseInfoVO 课程信息签证官
     * @return {@link CourseInfoVo}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseInfoVo addCourseInfo(CourseInfoVo courseInfoVO) {
        CourseInfo courseInfo = new CourseInfo();
        BeanCopier.create(courseInfoVO.getClass(),courseInfo.getClass(),false).copy(courseInfoVO,courseInfo,null);
        courseInfo = verificationDate(courseInfo);

        // 新增课程信息
        this.dao.insert(courseInfo);

        // 新增课表信息
        List<CourseSchedule> courseScheduleList = courseInfoVO.getCourseScheduleList();
        if(!CollectionUtils.isEmpty(courseScheduleList)){
            // 回填课表的课程ID
            Long courseId = courseInfo.getId();
            courseScheduleList.forEach(e -> e.setCourseId(courseId));
            courseScheduleService.batchAddCourseSchedules(courseScheduleList);
        }
        courseInfoVO.setId(courseInfo.getId());
        return courseInfoVO;
    }

    /**
     * 更新课程信息
     *
     * @param courseInfoVO 课程信息签证官
     * @return {@link CourseInfoVo}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseInfoVo updateCourseInfo(CourseInfoVo courseInfoVO) {
        CourseInfo courseInfo = new CourseInfo();
        BeanCopier.create(courseInfoVO.getClass(),courseInfo.getClass(),false).copy(courseInfoVO,courseInfo,null);
        courseInfo = verificationDate(courseInfo);
        // 新增课程信息
        this.dao.update(courseInfo);

        // 批量更新课表信息
        if(!CollectionUtils.isEmpty(courseInfoVO.getCourseScheduleList())){
            courseScheduleService.batchupdateCourseSchedules(courseInfoVO.getCourseScheduleList());
        }
        courseInfoVO.setId(courseInfo.getId());
        return courseInfoVO;
    }
}