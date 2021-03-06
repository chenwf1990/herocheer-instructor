package com.herocheer.instructor.service.impl;

import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.CourseScheduleDao;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.instructor.domain.vo.CourseScheduleCancelVO;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.CourseScheduleService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gaorh
 * @desc 课表信息(CourseSchedule)表服务实现类
 * @date 2021-05-14 15:51:46
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseScheduleServiceImpl extends BaseServiceImpl<CourseScheduleDao, CourseSchedule, Long> implements CourseScheduleService {

    @Resource
    private ReservationService reservationService;

    @Autowired
    private CourseInfoService courseInfoService;

    @Autowired
    @Lazy
    private WechatService wechatService;



    /**
     * 批量添加课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    @Override
    public List<CourseSchedule> batchAddCourseSchedules(List<CourseSchedule> courseScheduleList) {
        this.dao.insertBatch(courseScheduleList);
        return courseScheduleList;
    }

    /**
     * batchupdate课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return int
     */
    @Override
    public int batchupdateCourseSchedules(Long courseId,List<CourseSchedule> courseScheduleList) {
        // 批量删除
        this.dao.deleteByCourseId(courseId);
        courseScheduleList.forEach(e->e.setCourseId(courseId));
        return this.dao.insertBatch(courseScheduleList);
    }

    /**
     * 通过id找到课程时间表
     *
     * @param id id
     * @return {@link CourseSchedule}
     */
    @Override
    public CourseSchedule findCourseSchedulesById(Long id) {
        return this.dao.selectCourseSchedule(id);
    }

    /**
     * 取消课表
     *
     * @param courseScheduleCancelVO 课程安排取消
     * @return {@link CourseSchedule}
     */
    @Override
    public CourseSchedule cancelCourseSchedulesById(CourseScheduleCancelVO courseScheduleCancelVO) {
        CourseSchedule courseSchedule  = this.dao.get(courseScheduleCancelVO.getScheduleId());
        if(ObjectUtils.isEmpty(courseSchedule)){
            throw new CommonException("课表信息不存在");
        }

        // 1-被动关闭，更新预约信息状态
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status",ReserveStatusEnums.COURSE_SCHEDULE_CANCELED.getState());
        paramMap.put("courseScheduleId",courseScheduleCancelVO.getScheduleId());
        paramMap.put("type",RecruitTypeEunms.COURIER_RECRUIT.getType());
        reservationService.modifyReservationStatusByCourseScheduleId(paramMap);

        // 2-取消课表
        courseSchedule.setCancelStatus(Integer.valueOf(2));
        courseSchedule.setCancelReason(courseScheduleCancelVO.getCancelReason());
        courseSchedule.setCancelTime(System.currentTimeMillis());
        this.dao.update(courseSchedule);

        CourseInfo courseInfo = courseInfoService.get(courseSchedule.getCourseId());

        // 3-课程的课表全部取消时状态更新为已取消
        Map<String, Object> courseScheduleMap = new HashMap<>();
        //  1：未取消，2：已取消
        courseScheduleMap.put("cancelStatus",1);
        courseScheduleMap.put("courseId",courseSchedule.getCourseId());
        List<CourseSchedule> courseScheduleList = this.dao.findByLimit(courseScheduleMap);
        if(CollectionUtils.isEmpty(courseScheduleList)){

            // 3.1-更新课程的状态
            //设置课程状态：5——课程取消
            courseInfo.setState(5);
            courseInfoService.update(courseInfo);
        }

        // 4-发送微信消息
        if(ObjectUtils.isEmpty(courseInfo)){
            throw new CommonException("课程信息不存在");
        }

        // 4.1-课表已预约人openId
        Map<String, Object> openIdMap = new HashMap<>();
        openIdMap.put("courseScheduleId",courseScheduleCancelVO.getScheduleId());
        openIdMap.put("relevanceId",courseInfo.getId());
        openIdMap.put("type",RecruitTypeEunms.COURIER_RECRUIT.getType());
        Set<String> openids = reservationService.findReservationOpenidByCourseScheduleId(openIdMap);
        log.debug("课表取消时消息通知:{}",openids);

        // 4.2-发送微信消息
        if(!CollectionUtils.isEmpty(openids)){
            wechatService.sendWechatMessages(openids,courseInfo,courseScheduleCancelVO.getScheduleId());
        }

        return courseSchedule;
    }
}