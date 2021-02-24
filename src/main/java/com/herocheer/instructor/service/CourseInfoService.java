package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;

import java.util.List;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表服务接口
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
public interface CourseInfoService extends BaseService<CourseInfo,Long> {

    Page<CourseInfoVo> queryPage(CourseInfoQueryVo queryVo, Long userId);

    List<CourseApproval> approvalRecord(Long id);

    Integer withdraw(Long id);

    Integer approval(CourseApproval courseApproval);

    CourseInfoVo getCourseInfo(Long id);
}