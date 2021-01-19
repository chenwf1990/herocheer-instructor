package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表服务接口
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleUserService extends BaseService<WorkingScheduleUser,Long> {
    /**
     * @author chenwf
     * @desc  批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     * @param workingScheduleUsers
     */
    void batchInsert(List<WorkingScheduleUser> workingScheduleUsers);

    /**
     * @author chenwf
     * @desc  根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     * @param params
     */
    long deleteByMap(Map<String, Object> params);

    /**
     * @author chenwf
     * @desc  值班人员列表查询
     * @date 2021-01-12 08:57:02
     * @param workingScheduleUserQueryVo
     * @return
     */
    Page<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo);

    /**
     * @author chenwf
     * @desc  查找相同时间段的值班人员
     * @date 2021-01-12 08:57:02
     * @param params
     * @return
     */
    List<String> findWorkingUser(Map<String, Object> params);
}