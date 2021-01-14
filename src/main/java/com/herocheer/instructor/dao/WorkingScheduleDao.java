package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表数据库访问层
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleDao extends BaseDao<WorkingSchedule,Long>{
    /**
     * @author chenwf
     * @desc  查询排班列表信息
     * @date 2021-01-12 09:42:02
     * @param workingScheduleQueryVo
     * @return
     */
    List<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo);

    /**
     * @param idList
     * @return
     * @author chenwf
     * @desc 批量删除排班信息
     * @date 2021-01-12 08:47:02
     */
    long batchDelete(List<Long> idList);
}