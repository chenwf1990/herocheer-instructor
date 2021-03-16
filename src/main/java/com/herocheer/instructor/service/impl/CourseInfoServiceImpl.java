package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.CourseInfoDao;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.enums.ActivityApprovalStateEnums;
import com.herocheer.instructor.enums.CourseApprovalState;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.service.CourseApprovalService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表服务实现类
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourseInfoServiceImpl extends BaseServiceImpl<CourseInfoDao, CourseInfo,Long> implements CourseInfoService {

    @Resource
    private CourseApprovalService courseApprovalService;
    @Resource
    private ReservationService reservationService;
    @Resource
    private WechatService wechatService;
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


    @Override
    public Integer withdraw(Long id) {
        CourseInfo courseInfo=new CourseInfo();
        courseInfo.setId(id);
        courseInfo.setApprovalStatus(CourseApprovalState.WITHDRAW.getState());
        return this.dao.update(courseInfo);
    }

    @Override
    public Integer revoke(Long id) {
        CourseInfo courseInfo=this.dao.getCourseInfo(id);
        if(courseInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取课程信息失败!");
        }
        reservationService.updateReservationStatus(ReserveStatusEnums.EVENT_CANCELED.getState(),
                courseInfo.getId(), RecruitTypeEunms.COURIER_RECRUIT.getType());
        //设置课程状态 5=课程取消
        courseInfo.setState(5);
        List<String> openids=reservationService.findReservationOpenid(courseInfo.getId(),
                RecruitTypeEunms.COURIER_RECRUIT.getType());
        wechatService.sendWechatMessages(openids,courseInfo.getTitle());
        return this.dao.update(courseInfo);
    }

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
        if(courseInfo.getSignStartTime()>courseInfo.getCourseStartTime()){
            throw new CommonException("课程开始时间{}不能小于课程开始报名时间{}!",
                    DateUtil.timeStamp2Date(courseInfo.getSignStartTime()),
                    DateUtil.timeStamp2Date(courseInfo.getCourseStartTime()));
        }
        if(courseInfo.getSignEndTime()>courseInfo.getCourseEndTime()){
            throw new CommonException("课程结束时间{}不能小于课程结束报名时间{}!",
                    DateUtil.timeStamp2Date(courseInfo.getSignEndTime()),
                    DateUtil.timeStamp2Date(courseInfo.getCourseEndTime()));
        }
        if(courseInfo.getApprovalStatus()==null){
            courseInfo.setApprovalStatus(CourseApprovalState.PENDING.getState());
        }
        return courseInfo;
    }
}