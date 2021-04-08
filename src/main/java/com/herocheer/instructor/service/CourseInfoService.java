package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
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

    /**
     * 分页查询
     * @param queryVo
     * @param userId
     * @return
     */
    Page<CourseInfo> queryPage(CourseInfoQueryVo queryVo, Long userId);

    /**
     * 获取审批记录
     * @param id
     * @return
     */
    List<CourseApproval> approvalRecord(Long id);


    /**
     * 撤回
     * @param id
     * @return
     */
    Integer withdraw(Long id);

    /**
     * 取消课程
     * @param id
     * @return
     */
    Integer revoke(Long id);

    /**
     * 审批
     * @param courseApproval
     * @return
     */
    Integer approval(CourseApproval courseApproval,UserEntity userEntity);

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    CourseInfoVo getCourseInfo(Long id);

    /**
     * 课程时间验证
     * @param courseInfo
     * @return
     */
    CourseInfo verificationDate(CourseInfo courseInfo);

    /**
     * 通过id发现课程信息
     *
     * @param id     id
     * @param flag   国旗
     * @param userId 用户id
     * @return {@link CourseInfo}
     */
    CourseInfo findCourseInfoById(Long id,String flag,Long userId);

    /**
     * 培训任务分页查询
     *
     * @param queryVo     VO
     * @param currentUser 当前用户
     * @return {@link Page<CourseInfo>}
     */
    Page<CourseInfo> findtaskByPage(CourseInfoQueryVo queryVo, UserEntity currentUser);

}