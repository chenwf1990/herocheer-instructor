package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingVo;

import java.util.List;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表服务接口
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleService extends BaseService<WorkingSchedule,Long> {
    /**
     * @author chenwf
     * @desc  添加排班信息
     * @date 2021-01-11 14:30:02
     * @param workingSchedulsVo
     */
    void addWorkingScheduls(List<WorkingVo> workingSchedulsVo);
    /**
     * @author chenwf
     * @desc  根据id获取排班信息
     * @date 2021-01-11 14:30:02
     * @param id
     */
    WorkingVo getWorkingScheduls(Long id);

    /**
     * @author chenwf
     * @desc  编辑排班信息
     * @date 2021-01-12 08:47:02
     * @param workingVo
     */
    void updateWorkingScheduls(WorkingVo workingVo);

    /**
     * @author chenwf
     * @desc  排班列表
     * @date 2021-01-12 08:47:02
     * @param workingScheduleQueryVo
     * @return
     */
    Page<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo);

    /**
     * @author chenwf
     * @desc  批量删除排班信息
     * @date 2021-01-12 08:47:02
     * @param ids
     * @return
     */
    long batchDelete(String ids);
}