package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.CourseReservationDao;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.CourseReservation;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.CourseReservationVo;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.CourseReservationService;
import com.herocheer.instructor.utils.SmsCodeUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc   课程预约表(CourseReservation)表服务实现类
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourseReservationServiceImpl extends BaseServiceImpl<CourseReservationDao, CourseReservation,Long> implements CourseReservationService {

    @Resource
    private CourseInfoService courseInfoService;

    @Override
    public Integer reservation(CourseReservationVo courseReservationVo,Long userId) {
        ResponseResult responseResult=SmsCodeUtil.verifySmsCode(courseReservationVo.getPhoneNumber(),
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
        List<CourseReservation> list=dao.findByLimit(map);
        if(list!=null || list.size()>0){
            throw new CommonException(ResponseCode.SERVER_ERROR,"您已预约该课程,无需重复预约!");
        }
        courseReservationVo.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        dao.insert(courseReservationVo);
        courseInfo.setSignNumber(courseInfo.getSignNumber()+1);
        return courseInfoService.update(courseInfo);
    }

    @Override
    public Integer cancel(Long id) {
        CourseReservation courseReservation=dao.get(id);
        if(courseReservation==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"无效的预约信息!");
        }
        CourseInfo courseInfo=courseInfoService.get(courseReservation.getCourseId());
        if(courseInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取课程信息失败!");
        }
        if(courseInfo.getSignNumber()>=1){
            courseInfo.setSignNumber(courseInfo.getSignNumber()-1);
            courseInfoService.update(courseInfo);
        }
        courseReservation.setStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
        return dao.update(courseReservation);
    }

    @Override
    public Page<CourseInfoVo> queryPage(CourseInfoQueryVo queryVo, Long userId) {
        queryVo.setCreatedId(userId);
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<CourseInfoVo> instructors = dao.queryList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public Page<CourseReservation> getReservationPage(Long id, Integer status, Integer pageNo, Integer pageSize) {
        Page page = Page.startPage(pageNo,pageSize);
        Map<String,Object> map=new HashMap<>();
        map.put("courseId",id);
        map.put("status",status);
        List<CourseReservation> list=dao.findByLimit(map);
        page.setDataList(list);
        return page;
    }
}