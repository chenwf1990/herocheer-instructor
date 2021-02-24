package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.CourseInfoDao;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.enums.ActivityApprovalStateEnums;
import com.herocheer.instructor.enums.CourseApprovalState;
import com.herocheer.instructor.service.CourseApprovalService;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Override
    public Page<CourseInfoVo> queryPage(CourseInfoQueryVo queryVo, Long userId) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        if(queryVo.getQueryType()!=null&&queryVo.getQueryType()==3){
            queryVo.setCreatedId(userId);
        }
        List<CourseInfoVo> instructors = dao.queryList(queryVo);
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
        courseInfo.setState(CourseApprovalState.WITHDRAW.getState());
        return dao.update(courseInfo);
    }

    @Override
    public Integer approval(CourseApproval courseApproval) {
        courseApprovalService.insert(courseApproval);
        CourseInfo courseInfo=new CourseInfo();
        courseInfo.setId(courseApproval.getCourseId());
        courseInfo.setApprovalStatus(courseApproval.getApprovalStatus());
        courseInfo.setApprovalComments(courseApproval.getApprovalComments());
        if(ActivityApprovalStateEnums.PASSED.getState()==courseApproval.getApprovalStatus()){
            courseInfo.setState(CourseApprovalState.PASSED.getState());
        }
        if(ActivityApprovalStateEnums.OVERRULE.getState()==courseApproval.getApprovalStatus()){
            courseInfo.setState(CourseApprovalState.OVERRULE.getState());
        }
        return dao.update(courseInfo);
    }
}